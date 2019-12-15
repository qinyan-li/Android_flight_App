package com.glasses.flightapp.flightapp.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.glasses.flightapp.flightapp.CouponListActivity;
import com.glasses.flightapp.flightapp.Database.CouponViewModel;
import com.glasses.flightapp.flightapp.Database.FlightViewModel;
import com.glasses.flightapp.flightapp.FlightDetailActivity;
import com.glasses.flightapp.flightapp.Models.Coupon;
import com.glasses.flightapp.flightapp.Models.Flight;
import com.glasses.flightapp.flightapp.R;
import com.glasses.flightapp.flightapp.SearchActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class FlightNotificationService extends FirebaseMessagingService {

    public static final String MESSAGE_TITLE = "title";
    public static final String MESSAGE_BODY = "body";
    public static final String FLIGHT_NUMBER = "flightNo";
    public static final String FLIGHT_DATE = "date";
    public static final String REFERENCED_FLIGHT = "flightNo";
    public static final String COUPON_CODE = "couponCode";
    private static final String COUPON_NAME = "couponName";
    private static final String COUPON_DESCRIPTION = "couponDescription";

    NotificationManager notificationManager;

    public FlightNotificationService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            setupChannels();
        }
        Intent notificationIntent;
        if (remoteMessage.getData().get(REFERENCED_FLIGHT) != null) {
            notificationIntent = flightNotification(remoteMessage);
        } else if (remoteMessage.getData().get(COUPON_CODE) != null) {
            notificationIntent = couponNotification(remoteMessage);
        } else {
            notificationIntent = new Intent(this, SearchActivity.class);
        }

        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        final PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,
                getString(R.string.default_notification_channel_id))
                .setSmallIcon(R.drawable.ic_airplane)
                .setContentTitle(remoteMessage.getData().get(MESSAGE_TITLE))
                .setContentText(remoteMessage.getData().get(MESSAGE_BODY))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(155, notificationBuilder.build());
    }

    @NonNull
    private Intent couponNotification(RemoteMessage remoteMessage) {
        Intent notificationIntent;CouponViewModel couponViewModel = new CouponViewModel(getApplication());
        String code = remoteMessage.getData().get(COUPON_CODE);
        String name = remoteMessage.getData().get(COUPON_NAME);
        String description = remoteMessage.getData().get(COUPON_DESCRIPTION);
        Coupon coupon = new Coupon(code, name, description);
        couponViewModel.insert(coupon);
        notificationIntent = new Intent(this, CouponListActivity.class);
        return notificationIntent;
    }

    @NonNull
    private Intent flightNotification(RemoteMessage remoteMessage) {
        Intent notificationIntent;
        notificationIntent = new Intent(this, FlightDetailActivity.class);
        FlightViewModel flightViewModel = new FlightViewModel(getApplication());
        String flightNumber = remoteMessage.getData().get(FLIGHT_NUMBER);

        String date = remoteMessage.getData().get(FLIGHT_DATE);
        ZonedDateTime flightDate = ZonedDateTime.ofInstant(Instant.parse(date), ZoneId.of("GMT"));

        Flight flight = flightViewModel.getFlight(flightNumber, flightDate);
        if (flight != null) {
            notificationIntent.putExtra(FlightDetailActivity.FLIGHT_DETAIL, flight);
        } else {
            notificationIntent = new Intent(this, SearchActivity.class);
        }
        return notificationIntent;
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels() {
        CharSequence adminChannelName = getString(R.string.notifications_channel_name);
        String adminChannelDescription = getString(R.string.notifications_channel_description);

        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(getString(R.string.default_notification_channel_id),
                adminChannelName, NotificationManager.IMPORTANCE_LOW);
        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.RED);
        adminChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }
}
