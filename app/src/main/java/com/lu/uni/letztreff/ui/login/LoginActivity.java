package com.lu.uni.letztreff.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Arrays;
import java.util.List;
import com.lu.uni.letztreff.R;
import com.lu.uni.letztreff.tf.sozial.MainActivity;
import com.lu.uni.letztreff.ui.login.LoginViewModel;
import com.lu.uni.letztreff.ui.login.LoginViewModelFactory;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1234;
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
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
                    // already signed in
                } else {
                    // Choose authentication providers
                    List<AuthUI.IdpConfig> providers = Arrays.asList(
                            new AuthUI.IdpConfig.EmailBuilder().build());
                    // Create and launch sign-in intent
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(providers)
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        });
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
                launchMainActivity(user);
            }
        } else {
            // Sign in failed. If response is null the user canceled the sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            Toast.makeText(getApplicationContext(), "Unable to Sign in", Toast.LENGTH_SHORT).show();
        }
    }
    private void launchMainActivity(FirebaseUser user) {
        if (user != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}