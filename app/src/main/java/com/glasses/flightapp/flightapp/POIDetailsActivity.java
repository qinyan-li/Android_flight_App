package com.glasses.flightapp.flightapp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.*;
import com.glasses.flightapp.flightapp.Database.PoiViewModel;
import com.glasses.flightapp.flightapp.Models.Poi;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.*;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.Task;

public class POIDetailsActivity extends NavigationActivity implements GoogleApiClient.OnConnectionFailedListener {
    public static final int PLACE_PICKER_REQUEST = 1;
    public static final String POI_DETAIL_ID = "poi_detail_id";
    public static final String POI_COORDS = "coords";

    private static final double POI_COORDS_OFFSET_HORI = 0.05;
    private static final double POI_COORDS_OFFSET_VERT = 0.05;

    private GeoDataClient geoDataClient;
    private PoiViewModel viewModel;
    private Place place;
    private double[] coords;
    private ImageButton favBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poidetails);
        initNavigation();

        String id = getIntent().getStringExtra(POI_DETAIL_ID);
        coords = getIntent().getDoubleArrayExtra(POI_COORDS);
        if (id == null && coords == null) {
            finish();
            return;
        }

        viewModel = ViewModelProviders.of(this).get(PoiViewModel.class);
        geoDataClient = Places.getGeoDataClient(this);

        favBtn = findViewById(R.id.favBtn);

        //search for specific place ID (asynchronous) and show place picker in case ID
        //was not found
        if(id != null) {
            Task<PlaceBufferResponse> responseTask = geoDataClient.getPlaceById(id);
            responseTask.addOnSuccessListener(places -> {
                if(places.getCount() == 1) {
                    place = places.get(0);
                    updateCoords(place);

                    renderPlace();
                    places.release();

                    return;
                }

                places.release();
                showPicker();
            });
        } else {
            showPicker();
        }
    }

    private void updateCoords(Place place) {
        coords = new double[]{
                place.getLatLng().latitude - POI_COORDS_OFFSET_HORI,
                place.getLatLng().longitude - POI_COORDS_OFFSET_VERT,
                place.getLatLng().latitude + POI_COORDS_OFFSET_HORI,
                place.getLatLng().longitude + POI_COORDS_OFFSET_VERT
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //picker returns
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                place = PlacePicker.getPlace(this, data);
                updateCoords(place);

                renderPlace();
            }
        }
    }

    private void renderPlace() {
        TextView placeName = findViewById(R.id.placeName);
        placeName.setText(place.getName());
        ((TextView) findViewById(R.id.address)).setText(place.getAddress());

        CharSequence phoneNumber = place.getPhoneNumber();
        if (phoneNumber != null && phoneNumber.length() != 0) {
            ((TextView) findViewById(R.id.phoneNumber)).setText(phoneNumber);
        }

        ((RatingBar) findViewById(R.id.ratingBar)).setRating(place.getRating());
        Uri websiteUri = place.getWebsiteUri();
        if (websiteUri != null) {
            TextView websiteTextView = findViewById(R.id.websiteUrl);
            websiteTextView.setText(websiteUri.toString());
        }

        getPlacePhoto(place.getId());

        Poi savedPoi = viewModel.getPoi(place.getId());
        if(savedPoi != null)
            favBtn.setImageResource(R.drawable.ic_star);
        else
            favBtn.setImageResource(R.drawable.ic_star_border);

        favBtn.setOnClickListener(v -> {
            if(savedPoi != null) {
                viewModel.delete(savedPoi);
                favBtn.setImageResource(R.drawable.ic_star_border);
                Toast.makeText(POIDetailsActivity.this, R.string.deleted_from_poi_list, Toast.LENGTH_SHORT).show();
            } else {
                Poi poi = new Poi(
                        place.getId(),
                        place.getName().toString(),
                        (place.getAddress() == null ? null : place.getAddress().toString())
                );

                viewModel.insert(poi);
                favBtn.setImageResource(R.drawable.ic_star);
                Toast.makeText(POIDetailsActivity.this, R.string.saved_to_poi_list, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showPicker() {
        try {
            PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
            if(coords != null) {
                LatLngBounds.Builder boundsBuilder = LatLngBounds.builder()
                        .include(new LatLng(coords[0], coords[1]))
                        .include(new LatLng(coords[2], coords[3]));
                intentBuilder.setLatLngBounds(boundsBuilder.build());
            }
            startActivityForResult(intentBuilder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    public void getPlacePhoto(String placeId) {
        final Task<PlacePhotoMetadataResponse> photoMetadataResponse = geoDataClient.getPlacePhotos(placeId);

        photoMetadataResponse.addOnCompleteListener(task -> {
            PlacePhotoMetadataResponse photos = task.getResult();
            PlacePhotoMetadataBuffer photoMetadataBuffer = photos.getPhotoMetadata();

            if (photoMetadataBuffer.getCount() > 0) {
                PlacePhotoMetadata photoMetadata = photoMetadataBuffer.get(0);

                Task<PlacePhotoResponse> photoResponse = geoDataClient.getPhoto(photoMetadata);
                photoResponse.addOnCompleteListener(task1 -> {
                    ImageView placeImage = findViewById(R.id.placeImage);
                    PlacePhotoResponse photo = task1.getResult();

                    Bitmap bitmap = photo.getBitmap();
                    placeImage.setImageBitmap(bitmap);

                    photoMetadataBuffer.release();
                });
            }
        });
    }

    public void onDifferentLocationButtonPressed(View view) {
        showPicker();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), "Loading map failed", Toast.LENGTH_LONG).show();
    }
}
