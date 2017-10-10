package no.westerdals.eksamen.app2;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
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

import net.glxn.qrgen.android.QRCode;

import java.util.Random;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class MainFragment extends Fragment implements View.OnClickListener {

    FirebaseAuth mAuth;
    DatabaseReference rootReference;
    DatabaseReference roomNumberReference;
    DatabaseReference reservationReference;

    TextView roomNumberTextView;

    //just for demo purpopses, gives random room number to simulate that the hotel scans the QR code to make reservation on your account
    private int randomRoomReservationRoomNumber = new Random().nextInt(1000 - 100 + 1) + 100;


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.v("DESTROY", "main fragment destroyed");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // TODO: should i have getter in activity instead of getting instance again?
        mAuth = FirebaseAuth.getInstance();
        rootReference = FirebaseDatabase.getInstance().getReference();
        roomNumberReference = rootReference.child("users").child(mAuth.getCurrentUser().getUid()).child("roomNumber");
        reservationReference = rootReference.child("roomReservations").child(mAuth.getCurrentUser().getUid()).child("roomNumber");
    }

    @Override
    public void onStart() {
        super.onStart();

        roomNumberReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String roomNumber = dataSnapshot.getValue(String.class);
                roomNumberTextView.setText(roomNumber);

                NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
                View header = navigationView.getHeaderView(0);
                TextView text = (TextView) header.findViewById(R.id.drawer_room_number);
                text.setText(roomNumber);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO: warning
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.main_fragment, null);

        roomNumberTextView = (TextView) root.findViewById(R.id.room_number_text_view);


        ImageView QRimageView = (ImageView) root.findViewById(R.id.QR_image_view);
        QRimageView.setOnClickListener(this);

        //gets QR bitmap from string (user id).
        Bitmap myBitmap = QRCode.from(mAuth.getCurrentUser().getUid()).withSize(600, 600).withColor(0xFF000000, 0xFFFAFAFA).bitmap();
        QRimageView.setImageBitmap(myBitmap);

        return root;
    }


    public void imagePopup(ImageView imageView) {

        ImageView tempImageView = imageView;


        AlertDialog.Builder imageDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.custom_dialog,(ViewGroup) getView().findViewById(R.id.popup_layout));
        ImageView image = (ImageView) layout.findViewById(R.id.popup_img);
        image.setImageDrawable(tempImageView.getDrawable());
        imageDialog.setView(layout);


        imageDialog.setPositiveButton("Simulate Scan", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                reservationReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Sets a static roomnumber to simulate that the reception sets a roomNumber.
                        rootReference.child("roomReservations").child(mAuth.getCurrentUser().getUid()).child("roomNumber").setValue(randomRoomReservationRoomNumber + "");
                        roomNumberReference.setValue(dataSnapshot.getValue(String.class));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //TODO:
                    }
                });

            }

        });

        imageDialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });


        imageDialog.create();
        imageDialog.show();
    }


    @Override
    public void onClick(View v) {

        if (v == getView().findViewById(R.id.QR_image_view))
            imagePopup((ImageView) v);
    }
}
