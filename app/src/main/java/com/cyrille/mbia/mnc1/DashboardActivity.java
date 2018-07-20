package com.cyrille.mbia.mnc1;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardActivity extends AppCompatActivity {

    String EmailHolder;
    TextView Email;
    Button LogOUT ;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    FirebaseUser mUser;
    //@SuppressLint("SetTextI18n")
    public static final String TAG="LOGIN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

       Email = (TextView)findViewById(R.id.textView1);
        LogOUT = (Button)findViewById(R.id.button1);

        Intent intent = getIntent();

        // Réception d'un e-mail d'utilisateur par MainActivity.
        EmailHolder = intent.getStringExtra(login.userEmail);

        // Configuration de l'e-mail reçu pour TextView.
        Email.setText(Email.getText().toString()+ EmailHolder);

        // Ajout de l'écouteur de clic au bouton Déconnexion.

        LogOUT.setOnClickListener(new View.OnClickListener() {
           // @Override
            public void onClick(View v) {


                //Fin de l'activité DashBoard en cours sur le bouton cliquer.
               finish();

                Toast.makeText(DashboardActivity.this,"Déconnexion effectuée avec succès", Toast.LENGTH_LONG).show();
                //Intent intent=new Intent(DashboardActivity.this,login.class);
                //startActivity(intent);
               /*if (v.getId() == R.id.button1) {
                    AuthUI.getInstance()
                            .signOut(this)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                public void onComplete(@NonNull Task<Void> task) {
                                    // user is now signed out
                                    startActivity(new Intent(DashboardActivity.this, login.class));
                                    finish();
                                }
                            });
                }*/

            }
        });

    }


}