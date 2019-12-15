package com.glasses.flightapp.flightapp.AsyncTask;

import android.os.AsyncTask;
import com.glasses.flightapp.flightapp.Models.WeatherRequest;
import com.glasses.flightapp.flightapp.REST.FlightSearch.WeatherResource;
import com.glasses.flightapp.flightapp.Weather.HeWeather6;
import com.glasses.flightapp.flightapp.Weather.WeatherContainer;
import org.restlet.resource.ClientResource;

public class WeatherAsyncTask extends AsyncTask<WeatherRequest, Void, HeWeather6> {

    private OnResultListener<HeWeather6> listener;
    private Exception exception;

    WeatherAsyncTask() {
    }

    public WeatherAsyncTask(OnResultListener<HeWeather6> listener) {
        this.listener = listener;
    }

    protected HeWeather6 doInBackground(WeatherRequest... requests) {
        if(requests.length != 1 || requests[0] == null)
            return null;

        WeatherRequest request = requests[0];

        ClientResource cr = this.getResource(request);
        WeatherResource resource=cr.wrap(WeatherResource.class);

        WeatherContainer response;
        try {
            response = resource.findByLocation(
                    request.getLocation()
            );
        } catch(Exception e) {
            this.exception = e;
            return null;
        }

        if(response == null || response.getElements().length == 0)
            return null;

        return response.getElements()[0];
    }

    ClientResource getResource(WeatherRequest request) {
       return new ClientResource(
               "https://free-api.heweather.com/s6/weather?key=630afdc89a1f4c5d80e76f4d307b5941&location=" + request.getLocation() + "&lang=en"
       );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onPostExecute(HeWeather6 result) {
        if(listener == null)
            return;

        if(result == null) {
            listener.onFailure(exception);
            return;
        }

        listener.onSuccess(result);
    }
}


