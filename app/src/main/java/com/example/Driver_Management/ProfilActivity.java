package com.example.Driver_Management;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class ProfilActivity extends AppCompatActivity {

    private static final int GALLERY_INTENT_CODE = 1023 ;
    TextView name,email,phone,prenom,adresse;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        name    = findViewById(R.id.textView);
        prenom  = findViewById(R.id.textView2);
        email   = findViewById(R.id.textView3);
        adresse = findViewById(R.id.textView4);
        phone   = findViewById(R.id.textView5);

        //Obtenir des instances Firebase
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();


        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                name.setText(documentSnapshot.getString("fName"));
                prenom.setText(documentSnapshot.getString("prenom"));
                email.setText(documentSnapshot.getString("email"));
                adresse.setText(documentSnapshot.getString("adresse"));
                phone.setText(documentSnapshot.getString("phone"));

            }
        });
    }


    public void page6(View view)
    {
        startActivity(new Intent(this, HomeActivity.class));
    }

}


