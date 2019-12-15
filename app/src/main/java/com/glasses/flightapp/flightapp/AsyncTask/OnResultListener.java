package com.glasses.flightapp.flightapp.AsyncTask;

public interface OnResultListener<Result> {
    void onSuccess(Result result);

    void onFailure(Exception exception);
}
