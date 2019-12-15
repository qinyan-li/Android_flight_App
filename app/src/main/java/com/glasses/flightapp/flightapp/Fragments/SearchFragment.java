package com.glasses.flightapp.flightapp.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.glasses.flightapp.flightapp.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Abstract search fragment for flights, can be implemented to accommodate different
 * search types.
 */
public abstract class SearchFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = SearchFragment.class.getSimpleName();

    LocalDate flightDate = LocalDate.now();

    private FlightNumberSearchFragment.OnFragmentInteractionListener mListener;

    private DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
        flightDate = LocalDate.of(year, month + 1, day);
        updateLabel();
    };

    public SearchFragment() {
        //Default constructor required for fragments
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    abstract View inflateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    abstract Button getSearchButton(View view);

    abstract EditText getDateInput(View view);

    abstract boolean validInput();

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflateView(inflater, container, savedInstanceState);

        getSearchButton(view).setOnClickListener(this);
        getDateInput(view).setOnClickListener(this);

        return view;
    }

    /**
     * Search for flights and forward the user to the view to display the flights.
     * This method is intended to be overwritten by the subclass.
     *
     * @param view the view that calls this method
     */
    public void searchFlights(View view) {
        if (mListener != null) {
            mListener.onFragmentInteraction(view);
        }
    }

    /**
     * {@inheritDoc}
     */
    private void updateLabel() {
        if(getView() == null)
            return;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(getString(R.string.date_format));
        EditText date = getView().findViewById(R.id.date);
        date.setText(flightDate.format(dtf));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.SearchButton:
                if(validInput())
                    searchFlights(view);
                break;
            case R.id.date:
                Context ctx = getContext();
                if(ctx == null)
                    return;

                DatePickerDialog dialog = new DatePickerDialog(ctx, date, flightDate.getYear(), flightDate.getMonthValue() - 1, flightDate.getDayOfMonth());
                dialog.getDatePicker().setMinDate(LocalDate.now().toEpochDay() * 24 * 60 * 60 * 1000);
                dialog.show();
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FlightNumberSearchFragment.OnFragmentInteractionListener) {
            mListener = (FlightNumberSearchFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(View view);
    }
}
