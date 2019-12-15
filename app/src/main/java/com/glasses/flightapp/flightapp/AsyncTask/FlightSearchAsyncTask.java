package com.glasses.flightapp.flightapp.AsyncTask;

import android.os.AsyncTask;
import com.glasses.flightapp.flightapp.Converter.CustomJacksonConverter;
import com.glasses.flightapp.flightapp.Models.FlightRequest;
import org.restlet.engine.Engine;
import org.restlet.engine.converter.ConverterHelper;
import org.restlet.ext.jackson.JacksonConverter;

import java.util.List;

/**
 * generic async task to search for flights with a variety of search patterns (e.g.
 * date + flight no., date + from + to)
 *
 * @param <Params>      type of accepted inputs on execution
 * @param <Progress>    progress type
 * @param <Result>      type of returned result
 */
public abstract class FlightSearchAsyncTask<Params extends FlightRequest, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    private OnResultListener<Result> callback;
    Exception exception;

    FlightSearchAsyncTask(OnResultListener<Result> listener) {
        this.callback = listener;
    }

    @Override
    protected void onPreExecute() {
        List<ConverterHelper> converters = Engine.getInstance().getRegisteredConverters();
        converters.removeIf(converter -> converter instanceof JacksonConverter);
        //add own converter to enable JSR310 module used for correct parsing of DateTime
        converters.add(new CustomJacksonConverter());
    }

    @Override
    protected void onPostExecute(Result result) {
        if(result == null && exception != null) {
            callback.onFailure(exception);
            return;
        }

        callback.onSuccess(result);
    }

    /**
     * filter called after reception and parsing of network result, but before
     * actually returning the result to the caller of the async task to enable
     * sub classes to modify the received result
     *
     * @param result    received and parsed network result
     * @param request   request the caller passed on task execution
     * @return filtered (or untouched) result
     */
    protected Result postReceiveFilter(Result result, FlightRequest request) {
        return result;
    }

    /**
     * get URI (with scheme) of the REST endpoint used to process the request
     *
     * @param request   request the caller passed on task execution
     * @return URI to be called
     */
    public abstract String getURI(FlightRequest request);
}
