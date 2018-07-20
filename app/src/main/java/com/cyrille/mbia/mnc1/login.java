package com.cyrille.mbia.mnc1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class login extends AppCompatActivity {
    EditText Email, Password;
    Button LogInButton, RegisterButton;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    FirebaseUser mUser;
    String email, password;
    ProgressDialog dialog;
    public static final String userEmail="";

    public static final String TAG="LOGIN";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogInButton = (Button) findViewById(R.id.buttonLogin);

        RegisterButton = (Button) findViewById(R.id.buttonRegister);

        Email = (EditText) findViewById(R.id.editEmail);
        Password = (EditText) findViewById(R.id.editPassword);
        dialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mUser != null) {
                    Intent intent = new Intent(login.this, DashboardActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else
                {
                    Log.d(TAG,"AuthStateChanged:Logout");
                }

            }
        };
       // LogInButton.setOnClickListener((View.OnClickListener) this);
        //RegisterButton.setOnClickListener((View.OnClickListener) this);
        //Adding click listener to log in button.
        LogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // L'appel de EditText est vide ou aucune méthode.
                userSign();


            }
        });

        // Ajout de l'écouteur de clic pour enregistrer le bouton.
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Ouverture d'une nouvelle activité d'enregistrement d'utilisateur en utilisant l'intention de cliquer sur le bouton.
                Intent intent = new Intent(login.this, Register.class);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        //removeAuthSateListner est utilisé dans la fonction onStart juste à des fins de vérification, cela aide à vous déconnecter.
        mAuth.removeAuthStateListener(mAuthListner);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListner != null) {
            mAuth.removeAuthStateListener(mAuthListner);
        }

    }

    @Override
    public void onBackPressed() {
        login.super.finish();
    }



    private void userSign() {
        email = Email.getText().toString().trim();
        password = Password.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(login.this, "Entrez le bon email", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(login.this, "Entrez le mot de passe correct", Toast.LENGTH_SHORT).show();
            return;
        }
        dialog.setMessage("Connexion Veuillez patienter...");
        dialog.setIndeterminate(true);
        dialog.show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    dialog.dismiss();

                    Toast.makeText(login.this, "Connexion pas réussie", Toast.LENGTH_SHORT).show();

                } else {
                    dialog.dismiss();

                    checkIfEmailVerified();

                }
            }
        });

    }
    //Cette fonction permet de vérifier si l'email est vérifié ou non.
    private void checkIfEmailVerified(){
        FirebaseUser users=FirebaseAuth.getInstance().getCurrentUser();
        boolean emailVerified=users.isEmailVerified();
        if(!emailVerified){
            Toast.makeText(this,"Vérifier l'identifiant d'email",Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            finish();
        }
        else {
            Email.getText().clear();

            Password.getText().clear();
            Intent intent = new Intent(login.this, DashboardActivity.class);

            // Envoi d'un e-mail à l'activité du tableau de bord en utilisant l'intention.
            intent.putExtra(userEmail,email);

            startActivity(intent);

        }
    }

    }

