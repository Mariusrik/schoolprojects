package no.westerdals.eksamen.app2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import no.westerdals.eksamen.app2.Model.ResturantMarker;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.InfoWindowAdapter {

    private GoogleMap googleMap;
    DatabaseReference databaseReference;
    Map<String, ResturantMarker> resturantMarkerMap;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_fragment);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        LatLng hotelLocation = new LatLng(59.916142d, 10.759617d);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(hotelLocation, 15);
        googleMap.animateCamera(cameraUpdate);

        googleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET ))
                .position(hotelLocation).title(getText(R.string.app_name).toString()));


        databaseReference = FirebaseDatabase.getInstance().getReference().child("resturants");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                genrerateResturantMarkerList((Map<String, Object>) dataSnapshot.getValue());
                placeMarkers();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO:
            }
        });

        googleMap.setInfoWindowAdapter(this);
    }

    private void genrerateResturantMarkerList(Map<String, Object> value) {
        resturantMarkerMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : value.entrySet()) {
            Log.v("TEST", entry.getKey().toString());
            Log.v("TEST", entry.getValue().toString());

            //String name = entry.getKey().toString();
            String imgURL = ((Map) entry.getValue()).get("image").toString();
            String name = ((Map) entry.getValue()).get("name").toString();
            String desription = ((Map) entry.getValue()).get("description").toString();
            Double latidude = (Double) ((Map) ((Map) entry.getValue()).get("coordinates")).get("latitude");
            Double longitude = (Double) ((Map) ((Map) entry.getValue()).get("coordinates")).get("longitude");

            Log.v("NYTEST", name);
            Log.v("NYTEST", imgURL);
            Log.v("NYTEST", desription);
            Log.v("NYTEST", latidude + "");
            Log.v("NYTEST", longitude + "");


            final ResturantMarker resturantMarker = new ResturantMarker(name, desription, latidude, longitude);

            storageReference.child(imgURL).getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    resturantMarker.setImageByteArray(bytes);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    //TODO: Handle any errors
                }
            });

            resturantMarkerMap.put(name, resturantMarker);
        }
    }

    private void placeMarkers() {
        for (Map.Entry<String, ResturantMarker> entry : resturantMarkerMap.entrySet()) {
            LatLng position = new LatLng(((ResturantMarker) entry.getValue()).getLatitude(), ((ResturantMarker) entry.getValue()).getLongitude());
            newPoint(position, entry.getKey());
        }
    }

    private void newPoint(LatLng position, String name) {
        googleMap.addMarker(new MarkerOptions().position(position).title(name));
    }

    private byte[] bytesArray;

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        if (marker.getTitle().equals(getText(R.string.app_name).toString()))
            return null;

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_info_window, null);

        TextView markerHeaderTextView = (TextView) view.findViewById(R.id.marker_header);
        TextView markerDescriptionTextView = (TextView) view.findViewById(R.id.marker_description);
        ImageView imageView = (ImageView) view.findViewById(R.id.marker_image);

        ResturantMarker markerObject = resturantMarkerMap.get(marker.getTitle());

        byte[] imageByteArray = markerObject.getImageByteArray();

        if (imageByteArray != null)
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length));

        markerHeaderTextView.setText(marker.getTitle().toString());
        markerDescriptionTextView.setText(markerObject.getDescription());
        return view;
    }

}
