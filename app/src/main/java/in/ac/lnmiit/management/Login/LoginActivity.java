package in.ac.lnmiit.management.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import in.ac.lnmiit.management.HomePage.HomeActivity;
import in.ac.lnmiit.management.R;

public class LoginActivity extends AppCompatActivity {

    CardView login_Card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        login_Card = findViewById(R.id.login_Card);

        login_Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Login Button clicked!", Toast.LENGTH_SHORT).show();
                signInWithGoogle();
            }
        });
    }

    private void signInWithGoogle() {
    }

    @Override
    protected void onStart() {
        super.onStart();
        // check if Firebase user is not null
    }
    private void launchHomeScreen() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }
}