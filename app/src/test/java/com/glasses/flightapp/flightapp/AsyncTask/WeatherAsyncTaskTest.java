package com.glasses.flightapp.flightapp.AsyncTask;

import com.glasses.flightapp.flightapp.Models.WeatherRequest;
import com.glasses.flightapp.flightapp.REST.FlightSearch.WeatherResource;
import com.glasses.flightapp.flightapp.Weather.HeWeather6;
import com.glasses.flightapp.flightapp.Weather.WeatherContainer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.restlet.data.Parameter;
import org.restlet.data.Reference;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.net.UnknownHostException;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.times;

@RunWith(RobolectricTestRunner.class)
public class WeatherAsyncTaskTest {
    private WeatherRequest request;
    private ClientResource mockCResource;
    private WeatherResource mockResource;

    private WeatherAsyncTask task;
    private WeatherAsyncTask taskUnprepared;

    @Before
    public void setUp() {
        request = new WeatherRequest("Munich");
        mockCResource = Mockito.mock(ClientResource.class);
        mockResource = Mockito.mock(WeatherResource.class);

        Mockito.doReturn(mockResource).when(mockCResource).wrap(WeatherResource.class);

        task = new WeatherAsyncTask() {
            @Override
            ClientResource getResource(WeatherRequest request) {
                return mockCResource;
            }
        };

        taskUnprepared = new WeatherAsyncTask();
    }

    @Test
    public void testTaskWithOneValidResult() throws Exception {
        HeWeather6 weather = new HeWeather6();
        WeatherContainer weatherContainer = new WeatherContainer();
        weatherContainer.setElements(new HeWeather6[]{weather});

        Mockito.doReturn(weatherContainer).when(mockResource).findByLocation("Munich");

        task.execute(request);

        Robolectric.flushBackgroundThreadScheduler();

        HeWeather6 response = task.get();

        assertNotNull(response);
        assertEquals(weather, response);
        Mockito.verify(mockCResource, times(1))
                .wrap(WeatherResource.class);
    }

    @Test
    public void testTaskSimulateNoInternetConnection() throws Exception {
        Mockito.doThrow(new ResourceException(new UnknownHostException()))
                .when(mockResource).findByLocation("Munich");

        task.execute(request);

        Robolectric.flushBackgroundThreadScheduler();

        HeWeather6 response = task.get();

        assertNull(response);
        Mockito.verify(mockCResource, times(1))
                .wrap(WeatherResource.class);
    }

    @Test
    public void testTaskWithoutRequests() throws Exception {
        task.execute();

        Robolectric.flushBackgroundThreadScheduler();

        HeWeather6 response = task.get();

        assertNull(response);
    }

    @Test
    public void testTaskWithNullRequest() throws Exception {
        WeatherRequest request = null;
        task.execute(request);

        Robolectric.flushBackgroundThreadScheduler();

        HeWeather6 response = task.get();

        assertNull(response);
    }

    @Test
    public void testTaskWithMultipleRequests() throws Exception {
        WeatherRequest request2 = new WeatherRequest("Dusseldorf");
        task.execute(request, request2);

        Robolectric.flushBackgroundThreadScheduler();

        HeWeather6 response = task.get();

        assertNull(response);
    }

    @Test
    public void testGetResource() {
        ClientResource cr = taskUnprepared.getResource(request);

        Reference ref = cr.getRequest().getResourceRef();
        assertEquals("https", ref.getScheme());
        assertEquals("free-api.heweather.com", ref.getAuthority());
        assertEquals("/s6/weather", ref.getPath());
        assertTrue(ref.getQueryAsForm()
                .contains(new Parameter("location", "Munich")));

    }
}