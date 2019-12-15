package com.glasses.flightapp.flightapp.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.glasses.flightapp.flightapp.Models.Flight;
import com.glasses.flightapp.flightapp.Models.FlightFromToRequest;
import com.glasses.flightapp.flightapp.REST.FlightSearch.FlightResource;
import org.restlet.resource.ClientResource;

import java.util.ArrayList;
import java.util.List;

public abstract class FlightFromToSearchAsyncTask extends FlightSearchAsyncTask<FlightFromToRequest, Void, List<Flight>> {
    FlightFromToSearchAsyncTask(OnResultListener<List<Flight>> listener) {
        super(listener);
    }

    @Override
    protected List<Flight> doInBackground(FlightFromToRequest... requests) {
        if(requests.length != 1 || requests[0] == null)
            return new ArrayList<>();

        FlightFromToRequest request = requests[0];

        ClientResource cr = new ClientResource(this.getURI(request));

        FlightResource resource = cr.wrap(FlightResource.class);
        List response = resource.findByFromToAndDate(
                request.getDate(),
                request.getStartIATA(),
                request.getEndIATA()
        );

        //because of some reasons the response list is not mapped correctly to
        //Flight objects, so we do this conversion manually
        ObjectMapper mapper = new ObjectMapper().registerModule(new JSR310Module());
        try {
            List<Flight> flights = mapper.convertValue(response, mapper.getTypeFactory().constructCollectionType(ArrayList.class, Flight.class));
            if(flights != null)
                return postReceiveFilter(flights, request);

        } catch(IllegalArgumentException iae) {
            iae.printStackTrace();
        }

        return new ArrayList<>();
    }
}
