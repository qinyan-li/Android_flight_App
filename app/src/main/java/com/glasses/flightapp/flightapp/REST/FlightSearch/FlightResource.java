package com.glasses.flightapp.flightapp.REST.FlightSearch;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.glasses.flightapp.flightapp.Models.Flight;
import org.restlet.resource.Get;

import java.time.LocalDate;
import java.util.List;

public interface FlightResource {
    /**
     * searches for a specific flight (identified by flight number and date of departure)
     *
     * notice: date needs to be provided in local time w.r.t. the departure
     * airport, i.e. the date will differ for a flight departing on 11:55 pm and 00:05 am
     * local time (!), even though both flights may take place on the same day w.r.t. zulu
     * time (UTC)
     *
     * @param date      Date of departure in departure's local time zone
     * @param flightNo  IATA flight designator (IATA airline code + numeric flight no.)
     *
     * @return flight object if one was found with provided search pattern, NULL otherwise
     */
    @Nullable
    @Get("?byFlightAndDate")
    Flight findByFlightAndDate(LocalDate date, String flightNo);

    /**
     * searches for flights connecting the "from" with the "to" airport (in this direction)
     * on a given date
     *
     * notice: date needs to be provided in local time w.r.t. the departure
     * airport, i.e. the date will differ for a flight departing on 11:55 pm and 00:05 am
     * local time (!), even though both flights may take place on the same day w.r.t. zulu
     * time (UTC)
     *
     * @param date      Date of departure in departure's local time zone
     * @param fromIATA  IATA airport code (e.g. MUC)
     * @param toIATA    IATA airport code
     * @return list of flights matching the given search pattern, if no matching flights
     * can be found then an empty list is returned
     */
    @NonNull
    @Get("?byFlightAndDate")
    List<Flight> findByFromToAndDate(LocalDate date, String fromIATA, String toIATA);
}
