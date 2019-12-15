package com.glasses.flightapp.flightapp.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName="coupon")
public class Coupon implements Serializable {
    @PrimaryKey
    @NonNull
    private String code;

    @ColumnInfo
    @NonNull
    private String name;

    @ColumnInfo
    private String description;

    public Coupon(@NonNull String code, @NonNull String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    @NonNull
    public String getCode() {
        return code;
    }

    public void setCode(@NonNull String code) {
        this.code = code;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
