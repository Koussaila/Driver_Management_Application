package com.example.Driver_Management;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText mName,mEmail,mPassword,mPhone,mPrenom,mAdresse;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mName   = findViewById(R.id.nom);
        mPrenom   = findViewById(R.id.prenom);
        mEmail      = findViewById(R.id.email);
        mAdresse   = findViewById(R.id.adresse);
        mPassword   = findViewById(R.id.password);
        mPhone      = findViewById(R.id.telephone);
        mRegisterBtn= findViewById(R.id.valider);
        mLoginBtn   = findViewById(R.id.createText);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                final String nom  = mName.getText().toString();
                final String prenom    = mPrenom.getText().toString();
                final String phone    = mPhone.getText().toString();
                final String adresse    = mAdresse.getText().toString();


                if(TextUtils.isEmpty(email)){
                    mEmail.setError("L'E-mail est obligatoire.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Le Mot de passe est obligatoire.");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("Le mot de passe doit contenir> = 6 caractères");
                    return;
                }

                // register the user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            // send verification link

                            FirebaseUser fuser = fAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(RegisterActivity.this, "Un E-mail de vérification a été envoyé.", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: Email non envoyé " + e.getMessage());
                                }
                            });

                            Toast.makeText(RegisterActivity.this, "Utilisateur créé avec succès.", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fName",nom);
                            user.put("adresse",adresse);
                            user.put("email",email);
                            user.put("phone",phone);
                            user.put("prenom",prenom);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: Le profil utilisateur est créé pour "+ userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));

                        }else {
                            Toast.makeText(RegisterActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });



        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

    }

    public void page2(View view)
    {
        startActivity(new Intent(this, LoginActivity.class));
    }

}

