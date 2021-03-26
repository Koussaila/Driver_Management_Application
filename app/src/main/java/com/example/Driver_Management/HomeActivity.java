package com.example.Driver_Management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {
    Button button,btndeconnexion,btntrajet;
    private FirebaseAuth mAuth;
    private static final String TAG="Connexion";
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        button=findViewById(R.id.button1);
        btntrajet=findViewById(R.id.button2);
        btndeconnexion=findViewById(R.id.button4);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMessage("Connexion réussie avec: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("Déconnexion réussie.");
                }
                // ...
            }
        };


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ProfilActivity.class);
                startActivity(intent);
            }
        });

        btntrajet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, TrajetActivity.class);
                startActivity(intent);
            }
        });

        btndeconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                toastMessage("Deconnexion reussie");
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }

    public void page3(View view)
    {
        startActivity(new Intent(this, TrajetActivity.class));
    }

    public void page2(View view)
    {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void page7(View view)
    {
        startActivity(new Intent(this, ProfilActivity.class));
    }

    public void page5(View view)
    {
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}







