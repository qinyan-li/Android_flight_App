package com.glasses.flightapp.flightapp.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.glasses.flightapp.flightapp.Converter.ArrayConverter;
import com.glasses.flightapp.flightapp.Converter.DateConverter;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Model class representing one flight (i.e. a connection between two airports
 * on a specific date).
 *
 * All setters are needed for automatic API parsing, so do not delete them if your IDE
 * recommends you to do so.
 */
@Entity(tableName="my_favorite")
@TypeConverters({DateConverter.class, ArrayConverter.class})
public class Flight implements Serializable {
    @PrimaryKey
    private int id;

    /**
     * flight number, IATA airline code + numeric flight ID)
     */
    @ColumnInfo(name = "flight_no")
    private String flightNo;

    /**
     * display name of the airline (e.g. "Lufthansa")
     */
    @ColumnInfo(name = "airline")
    private String airlineName;

    /**
     * departure time in zulu time zone (UTC)
     */
    private ZonedDateTime start;

    /**
     * arrival time in zulu time zone (UTC)
     */
    private ZonedDateTime end;

    /**
     * departure IATA airport code
     */
    @ColumnInfo(name = "start_iata")
    private String startIATA;

    /**
     * arrival IATA airport code
     */
    @ColumnInfo(name = "end_iata")
    private String endIATA;

    /**
     * display name of departure airport (e.g. "Munich")
     */
    @ColumnInfo(name = "start_name")
    private String startName;

    /**
     * display name of departure airport (e.g. "Beijing")
     */
    @ColumnInfo(name="end_name")
    private String endName;

    @ColumnInfo(name="weather_name")
    private String weatherName;

    @ColumnInfo(name="start_lat")
    private float startLat;

    @ColumnInfo(name="start_lng")
    private float startLng;

    @ColumnInfo(name="end_lat")
    private float endLat;

    @ColumnInfo(name="end_lng")
    private float endLng;

    /**
     * aircraft type (e.g. "A380")
     */
    private String aircraft;

    /**
     * the coordinate bounds for the arrival city
     */
    @ColumnInfo(name="lat_lng_bounds")
    private double[] latLngBounds;

    @JsonIgnore
    @Ignore
    private boolean saved = false;

    /**
     * default constructor, needed for automatic API parsing
     */
    public Flight() {
    }

    @Ignore
    public Flight(String flightNo, String airlineName, String startIATA, String endIATA, String startName, String endName, String aircraft,
                  double[] latLngBounds) {
        this.flightNo = flightNo;
        this.airlineName = airlineName;
        this.startIATA = startIATA;
        this.endIATA = endIATA;
        this.startName = startName;
        this.endName = endName;
        this.aircraft = aircraft;
        this.latLngBounds = latLngBounds;
    }

    public Flight changeDate(LocalDate date) {
        this.setStart(ZonedDateTime.of(
            date,
            this.getStart().toLocalTime(),
            this.getStart().getZone()
        ));

        this.setEnd(ZonedDateTime.of(
            date,
            this.getEnd().toLocalTime(),
            this.getEnd().getZone()
        ));
        if(this.getEnd().compareTo(this.getStart()) < 0)
            this.setEnd(this.getEnd().plusDays(1));

        return this;
    }

    public int getId() {
        id = this.hashCode();
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getStartIATA() {
        return startIATA;
    }

    public void setStartIATA(String startIATA) {
        this.startIATA = startIATA;
    }

    public String getEndIATA() {
        return endIATA;
    }

    public void setEndIATA(String endIATA) {
        this.endIATA = endIATA;
    }

    public String getStartName() {
        return startName;
    }

    public void setStartName(String startName) {
        this.startName = startName;
    }

    public String getEndName() {
        return endName;
    }

    public void setEndName(String endName) {
        this.endName = endName;
    }

    public String getAircraft() {
        return aircraft;
    }

    public void setAircraft(String aircraft) {
        this.aircraft = aircraft;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public boolean isSaved() {
        return this.saved;
    }

    public String getWeatherName() {
        return weatherName;
    }

    public void setWeatherName(String weatherName) {
        this.weatherName = weatherName;
    }

    public float getStartLat() {
        return startLat;
    }

    public void setStartLat(float startLat) {
        this.startLat = startLat;
    }

    public float getStartLng() {
        return startLng;
    }

    public void setStartLng(float startLng) {
        this.startLng = startLng;
    }

    public float getEndLat() {
        return endLat;
    }

    public void setEndLat(float endLat) {
        this.endLat = endLat;
    }

    public float getEndLng() {
        return endLng;
    }

    public void setEndLng(float endLng) {
        this.endLng = endLng;
    }

    public LatLng getStartLatLng() {
        return new LatLng(startLat, startLng);
    }

    public LatLng getEndLatLng() {
        return new LatLng(endLat, endLng);
    }

    public double[] getLatLngBounds() {
        return latLngBounds;
    }

    public void setLatLngBounds(double[] latLngBounds) {
        this.latLngBounds = latLngBounds;
    }

    @Override
    public String toString() {
        return "FlightNo："+this.getFlightNo()+ "\n"
                +"Start Time："+this.getStart()+ "\n"
                +"End Time："+this.getEnd()+ "\n"
                +"Start IATA："+this.getStartIATA()+ "\n"
                +"End IATA："+this.getEndIATA()+ "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(flightNo, flight.flightNo) &&
                Objects.equals(airlineName, flight.airlineName) &&
                Objects.equals(start, flight.start) &&
                Objects.equals(end, flight.end) &&
                Objects.equals(startIATA, flight.startIATA) &&
                Objects.equals(endIATA, flight.endIATA) &&
                Objects.equals(startName, flight.startName) &&
                Objects.equals(endName, flight.endName) &&
                Objects.equals(aircraft, flight.aircraft);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightNo, airlineName, start, end, startIATA, endIATA, startName, endName, aircraft);
    }
}
