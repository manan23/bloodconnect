package com.example.user.bloodconnect.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.bloodconnect.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by User on 27-09-2017.
 */

public class LoginActivity extends AppCompatActivity {
    EditText number, password;
    String number1;
    Button login;
    TextView register,forgetpass;
    private FirebaseAuth mauth;

    private FirebaseAuth.AuthStateListener mauthlistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        number = (EditText) findViewById(R.id.number);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);
        forgetpass= (TextView) findViewById(R.id.forgetpass);

        mauth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startsignin();
            }
        });
        mauthlistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                }
            }
        };

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress = number.getText().toString();
                if(emailAddress.equals(""))
                {
                    Toast.makeText(LoginActivity.this, "enter the Email Address", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    mauth.sendPasswordResetEmail(emailAddress)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, "password reset link sent", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(LoginActivity.this, "password link not sent successfully", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mauth.addAuthStateListener(mauthlistener);
    }


    public void startsignin() {
        final ProgressDialog progress =ProgressDialog.show(LoginActivity.this,"Logging In","Authenticating.. Please Wait!!!! ",false,false);
        String email = number.getText().toString();
        String password1 = password.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password1)) {
            progress.dismiss();
            Toast.makeText(this, "Enter all the Credientials", Toast.LENGTH_SHORT).show();
        }
        else {
            mauth.signInWithEmailAndPassword(email, password1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {
                        progress.dismiss();
                        Toast.makeText(LoginActivity.this, "Enter The Correct Credientials", Toast.LENGTH_SHORT).show();
                    }

                }

            });
        }


    }
}

