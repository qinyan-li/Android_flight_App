package com.glasses.flightapp.flightapp.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName="poi_favorite")
public class Poi implements Serializable {
    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo
    @NonNull
    private String name;

    @ColumnInfo
    private String address;

    public Poi(@NonNull String id, @NonNull String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
