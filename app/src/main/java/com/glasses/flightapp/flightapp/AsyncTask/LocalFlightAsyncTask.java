package com.glasses.flightapp.flightapp.AsyncTask;

import android.content.Context;
import android.util.Log;
import com.glasses.flightapp.flightapp.Models.FlightRequest;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * interface providing default implementations for local REST API simulation (used
 * for multiple inheritance in local flight search tasks)
 */
public interface LocalFlightAsyncTask {
    /**
     * Get file URI to serve local, static file on API call. This method also prepares
     * the file for REST request, i.e. it creates the required static files in the app's
     * data directory (because static serving of android resources is not possible directly).
     *
     * @param ctx       reference to applications context (used for file handling)
     * @param request   request the caller passed on task execution
     * @return URI to be called
     */
    default String getURI(WeakReference<Context> ctx, FlightRequest request) {
        Context context = ctx.get();
        if(context == null) {
            Log.w("LocalFlightAsyncTask", "weak reference yields no context object");
            return "";
        }

        String connectionId = request.getIdentifier();
        if(!getValidConnections().contains(connectionId))
            connectionId = "none_" + request.getClass().getSimpleName().toLowerCase();

        String filename = context.getFilesDir() + "/" + connectionId + ".json";

        int id = context.getResources().getIdentifier("raw/" + connectionId, null, context.getPackageName());
        InputStream in = context.getResources().openRawResource(id);
        setupFile(in, filename);

        return "file://" + filename;
    }

    /**
     * provides a whitelist of all allowed flight identifiers ({@link FlightRequest})
     * that are represented locally
     *
     * @return list of allowed identifiers
     */
    List<String> getValidConnections();

    /**
     * writes the content of a android resource into a predefined file
     *
     * @param in        input stream pointing to the android resource
     * @param filename  file path to output file
     */
    default void setupFile(InputStream in, String filename) {
        FileOutputStream out;
        try {
            out = new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        byte[] buff = new byte[1024];
        int read;

        try {
            while ((read = in.read(buff)) > 0) {
                out.write(buff, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
