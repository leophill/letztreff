package com.lu.uni.letztreff.tf.sozial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import android.location.Location;

import com.lu.uni.letztreff.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1234;
    public static final String TAG = "TAG";
    public Location mlocation = new Location("uni.lu");

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    // Get Firestore database object
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;

    // Set default location to (Uni Belval, Luxembourg)
    //private final  mDefaultLocation = [49.656639, 19.636917];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // find view
        Button buttonSignIn = findViewById(R.id.btnlogin);
        // Set onclick listener
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                if (auth.getCurrentUser() != null) {
                    Toast.makeText(getApplicationContext(), "User already signed in, must sign out first",
                            Toast.LENGTH_SHORT).show();
                    // already signed in. Cleanup zombie session
                    auth.signOut();
                } else {
                    // Choose authentication providers
                    List<AuthUI.IdpConfig> providers = Arrays.asList(
                            new AuthUI.IdpConfig.EmailBuilder().build(),
                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                            new AuthUI.IdpConfig.FacebookBuilder().build());

                    // Create and launch sign-in intent
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(providers)
                                    .setLogo(R.drawable.ic_lt_logo)
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        });

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into
        if (requestCode == 1234) {
            // Successfully signed in
            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(getApplicationContext(), "Successfully signed in", Toast.LENGTH_SHORT)
                        .show();

                // Get device last location
                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    Log.d(TAG, "Location successfully retrieved!");
                                    mlocation = location;
                                } else {
                                    Log.d(TAG, "Failed to retrieve current Location. Switching to default!");
                                    //Set default coordinates to University of Luxembourg in Belval
                                    mlocation.setLatitude(49.504470);
                                    mlocation.setLongitude(5.948397);
                                }
                            }
                        });


                // Store user information in Firestore database
                DocumentReference documentReference = fStore.collection("Users").document(user.getUid());
                // Create a new user with a first and last name
                Map<String, Object> userdata = new HashMap<>();
                userdata.put("Username", user.getDisplayName());
                userdata.put("Email", user.getEmail());
                userdata.put("Phone", user.getPhoneNumber());
                userdata.put("Location", new GeoPoint(mlocation.getLatitude(), mlocation.getLongitude()));

                // Add a new document with a generated ID

                documentReference.set(userdata).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "User successfully created in database!");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "User could not be created in the database!", e);
                    }
                });

                // Start next activity
                launchEventActivity(user);
            }
        } else {
            // Sign in failed. If response is null the user canceled the sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            Toast.makeText(getApplicationContext(), "Unable to Sign in", Toast.LENGTH_SHORT).show();
        }
    }

    private void launchEventActivity(FirebaseUser user) {
        if (user != null) {
            startActivity(new Intent(this, EventActivity.class));
            finish();
        }
    }

}