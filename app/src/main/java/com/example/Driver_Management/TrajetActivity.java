package com.example.Driver_Management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class TrajetActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText depart,dated,arrivee,datea,volume,prix;
    Button valider;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    DatabaseReference ref;
    Trajet trajet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trajet);

        depart=findViewById(R.id.depart) ;
        dated=findViewById(R.id.dated);
        arrivee=findViewById(R.id.arrivee);
        datea=findViewById(R.id.datea);
        volume=findViewById(R.id.volume);
        prix=findViewById(R.id.prix);
        valider=findViewById(R.id.valider);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        trajet=new Trajet();
        ref=FirebaseDatabase.getInstance().getReference().child("Trajet");
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double price=Double.parseDouble(prix.getText().toString().trim());
                Double vol=Double.parseDouble(volume.getText().toString().trim());
                trajet.setArrivee(arrivee.getText().toString().trim());
                trajet.setDepart(depart.getText().toString().trim());
                trajet.setDatea(datea.getText().toString().trim());
                trajet.setDated(dated.getText().toString().trim());
                trajet.setPrix(price);
                trajet.setVolume(vol);

                ref.push().setValue(trajet);

                Toast.makeText(TrajetActivity.this, "Insertion réussie", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            }
        });
    }
}


