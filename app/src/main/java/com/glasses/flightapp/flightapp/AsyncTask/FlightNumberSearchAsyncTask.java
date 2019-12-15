package com.glasses.flightapp.flightapp.AsyncTask;

import com.glasses.flightapp.flightapp.Models.Flight;
import com.glasses.flightapp.flightapp.Models.FlightNumberRequest;
import com.glasses.flightapp.flightapp.REST.FlightSearch.FlightResource;
import org.restlet.resource.ClientResource;

public abstract class FlightNumberSearchAsyncTask extends FlightSearchAsyncTask<FlightNumberRequest, Void, Flight> {
    FlightNumberSearchAsyncTask(OnResultListener<Flight> listener) {
        super(listener);
    }

    @Override
    protected Flight doInBackground(FlightNumberRequest... requests) {
        if(requests.length != 1 || requests[0] == null)
            return null;

        FlightNumberRequest request = requests[0];

        ClientResource cr = new ClientResource(this.getURI(request));

        FlightResource resource = cr.wrap(FlightResource.class);
        Flight flight = resource.findByFlightAndDate(
                request.getDate(),
                request.getFlightNo()
        );

        if(flight == null)
            return null;

        return postReceiveFilter(flight, request);
    }
}
