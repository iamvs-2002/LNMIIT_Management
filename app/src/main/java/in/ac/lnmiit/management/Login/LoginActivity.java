package in.ac.lnmiit.management.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import in.ac.lnmiit.management.HomePage.HomeActivity;
import in.ac.lnmiit.management.R;

public class LoginActivity extends AppCompatActivity {

    CardView login_Card;
    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 1001;
    private GoogleApiClient googleApiClient;
    private ProgressBar login_progressBar;
    private RelativeLayout login_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Setting up GoogleApiClient to sign in with Google
        signInWithGoogle();

        // Log In using Google CardView
        login_Card = findViewById(R.id.login_Card);
        login_progressBar = findViewById(R.id.login_progressBar);
        login_layout = findViewById(R.id.login_layout);
        login_Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(LoginActivity.this, "Login Button clicked!", Toast.LENGTH_SHORT).show();
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, RC_SIGN_IN);
            }
        });
    }

    private void signInWithGoogle() {
        enableProgressBar();
        // implement sign in with google
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        disableProgressBar();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            if (account == null) {
                disableProgressBar();
                Log.e("TAG", "Error signing in");
                return;
            }
            String idToken = account.getIdToken();
            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
            firebaseAuthWithGoogle(credential);
        } else {
            disableProgressBar();
            Log.e("TAG", "Login Unsuccessful. " + result);
        }
    }

    // check if the selected email is LNMIIT email
    private boolean checkEmailPattern(String email){
        String[] split = email.split("@");
        String domain = split[1]; //This Will Give You The Domain After '@'
        if (domain.equals("lnmiit.ac.in")) {
            return true;
        }
        else{
            Toast.makeText(this, "Kindly use college email-id.", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
    private void firebaseAuthWithGoogle(AuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if(checkEmailPattern(mAuth.getCurrentUser().getEmail()))
                                launchHomeScreen();
                            else{
                                signOut();
                            }
                        } else {
                            task.getException().printStackTrace();
                            Toast.makeText(LoginActivity.this, "Error logging in. Please try again later.", Toast.LENGTH_SHORT).show();
                        }
                        disableProgressBar();
                    }
                });
    }

    private void signOut() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        GoogleSignInClient mGoogleSignInClient ;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getBaseContext(), gso);
        mAuth.signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
    }
    @Override
    protected void onStart() {
        super.onStart();
        // check if Firebase user is not null, i.e., if the user has already signed in
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null)
            launchHomeScreen();
    }

    private void launchHomeScreen() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void enableProgressBar() {
//        login_progressBar.setVisibility(View.VISIBLE);
//        login_layout.setClickable(false);
//        login_layout.setAlpha(0.5f);
    }

    private void disableProgressBar() {
//        login_progressBar.setVisibility(View.GONE);
//        login_layout.setClickable(true);
//        login_layout.setAlpha(1.0f);
    }
}