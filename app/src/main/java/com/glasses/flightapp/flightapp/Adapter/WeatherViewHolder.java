package com.glasses.flightapp.flightapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.glasses.flightapp.flightapp.R;
import com.glasses.flightapp.flightapp.Weather.HeWeather6;
import com.glasses.flightapp.flightapp.Weather.daily_forecast;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class WeatherViewHolder extends RecyclerView.ViewHolder {
    private TextView tvWeatherIn;
    private TextView tvForecastDate;

    private ImageView ivNowCond;
    private TextView tvNowTemp;
    private TextView tvNowWindSpd;
    private ImageView ivNowWindDir;

    private ImageView ivForecastCond;
    private TextView tvForecastTemp;
    private TextView tvForecastWindSpd;
    private ImageView ivForecastWindDir;
    private View forecast;

    private Context context;

    public WeatherViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;

        tvWeatherIn = itemView.findViewById(R.id.weather_title);
        tvForecastDate = itemView.findViewById(R.id.weather_forecast_date);

        ivNowCond = itemView.findViewById(R.id.weather_now_cond);
        tvNowTemp = itemView.findViewById(R.id.weather_now_temp);
        tvNowWindSpd = itemView.findViewById(R.id.weather_now_wind_spd);
        ivNowWindDir = itemView.findViewById(R.id.weather_now_wind_dir);

        ivForecastCond = itemView.findViewById(R.id.weather_fc_cond);
        tvForecastTemp = itemView.findViewById(R.id.weather_fc_temp);
        tvForecastWindSpd = itemView.findViewById(R.id.weather_fc_wind_spd);
        ivForecastWindDir = itemView.findViewById(R.id.weather_fc_wind_dir);
        forecast = itemView.findViewById(R.id.weather_fc_info);
    }

    public void bind(String cityName, ZonedDateTime flightStart, HeWeather6 weather) {
        tvWeatherIn.setText(context.getString(R.string.weather_in, cityName));

        ivNowCond.setImageResource(getIdentifier("cond_" + weather.getNow().getCond_code(), "drawable", R.drawable.cond_999));
        tvNowTemp.setText(context.getString(R.string.weather_temp, weather.getNow().getTmp()));
        tvNowWindSpd.setText(context.getString(R.string.weather_wind, weather.getNow().getWind_spd()));
        ivNowWindDir.setImageResource(getIdentifier("wind_arrow_" + weather.getNow().getWind_dir().toLowerCase(), "drawable", R.drawable.wind_arrow_n));

        //no forecast available if flight is in more than 2 days
        long daysUntilFlight = ChronoUnit.DAYS.between(LocalDate.now(), flightStart.toLocalDate());
        if(daysUntilFlight > 2 || daysUntilFlight < 0) {
            forecast.setVisibility(View.GONE);
            ivForecastCond.setVisibility(View.GONE);
            tvForecastDate.setVisibility(View.GONE);
            return;
        }

        int i = (int) daysUntilFlight;
        daily_forecast fc = weather.getDaily_forecast()[i];
        ivForecastCond.setImageResource(getIdentifier("cond_" + fc.getCond_code_d(), "drawable", R.drawable.cond_999));
        tvForecastTemp.setText(context.getString(R.string.weather_temp, fc.getTmp_max()));
        tvForecastWindSpd.setText(context.getString(R.string.weather_wind, fc.getWind_spd()));
        ivForecastWindDir.setImageResource(getIdentifier("wind_arrow_" + fc.getWind_dir().toLowerCase(), "drawable", R.drawable.wind_arrow_n));

        DateTimeFormatter df = DateTimeFormatter.ofPattern(context.getString(R.string.date_format));
        tvForecastDate.setText(df.format(LocalDate.now().plusDays(daysUntilFlight)));
    }

    private int getIdentifier(String name, String defType, int defaultValue) {
        int id = context.getResources().getIdentifier(name, defType, context.getPackageName());
        if(id == 0)
            return defaultValue;

        return id;
    }
}
