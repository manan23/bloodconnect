package com.example.user.bloodconnect.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.bloodconnect.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 08-09-2017.
 */

public class register extends AppCompatActivity {

    Spinner Gender, Bloodgroup;
    EditText name, number, email, city, password;
    Button submit,dob;
    private FirebaseAuth mauth;
    FirebaseUser user;
    DatabaseReference reference;
    public  String id;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        String gender[] = {"Gender", "Male", "Female"};
        String bloodgroup[] = {"Select Blood Group", "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-", "Bombay Blood Group"};
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        mauth=FirebaseAuth.getInstance();
        reference= FirebaseDatabase.getInstance().getReference().child("register");

        Gender = (Spinner) findViewById(R.id.editgender);
        Bloodgroup = (Spinner) findViewById(R.id.editblood);
        name = (EditText) findViewById(R.id.editname);
        number = (EditText) findViewById(R.id.editnumber);
        email = (EditText) findViewById(R.id.editemail);
        city = (EditText) findViewById(R.id.editcity);
        password = (EditText) findViewById(R.id.editpass);
        dob = (Button) findViewById(R.id.editdob);
        submit = (Button) findViewById(R.id.submit);



        ArrayAdapter adapter = new ArrayAdapter(register.this, R.layout.support_simple_spinner_dropdown_item, gender);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter adapter1 = new ArrayAdapter(register.this, R.layout.support_simple_spinner_dropdown_item, bloodgroup);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Gender.setAdapter(adapter);
        Bloodgroup.setAdapter(adapter1);

        Calendar cal=Calendar.getInstance();
        final int year=cal.get(cal.YEAR);
        final int month=cal.get(cal.MONTH);
        final int day=cal.get(cal.DAY_OF_MONTH);



        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 DatePickerDialog datepicker=new DatePickerDialog(register.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            dob.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                },year,month,day);
               datepicker.show();
            }

        });




        submit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {


                 String name1 = name.getText().toString();
                 String number1 = number.getText().toString();
                 String email1 = email.getText().toString();
                 String city1 = city.getText().toString();
                 String password1 = password.getText().toString();
                 String  gender1= Gender.getSelectedItem().toString();
                 String dob1=dob.getText().toString();
                 String bloodgroup1=Bloodgroup.getSelectedItem().toString();




        if (name1.equals("") || number1.equals("") || email1.equals("") || city1.equals("") || password1.equals("") || gender1.equals("Gender") || bloodgroup1.equals("Select Blood Group")||dob1.equals(""))
        {
            Toast.makeText(register.this, "ENTER ALL THE FIELDS", Toast.LENGTH_SHORT).show();
        }

        else if (email1.matches(EMAIL_PATTERN)) {

               if (number1.length()==10)
              {
                     if (password.length()<=5)
                     {
                         password.setError("Password Is Short");
                     }
                     else {

                         ProgressDialog progress=new ProgressDialog(register.this);
                         progress.setMessage("SIGNING UP.. PLEASE WAIT!!!!");
                         progress.show();

                         registeruserauth(name1,number1,email1,city1,password1,gender1,dob1,bloodgroup1);

                          progress.cancel();



                     }


              }
            else
                {
                  number.setError("Enter the Corrrect Number");
                }
        }
        else
        {
            email.setError("Email Is Not Correct");
        }


             }
         });


    }


    private void registeruserauth( final String name1, final String number1,final String email1,final String city1,final String password1,final String  gender1,final String dob1,final String bloodgroup1 )
    {

        mauth.createUserWithEmailAndPassword(email1,password1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {

                    id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Map userDetails = new HashMap<>();
                    userDetails.put("Name",name1);
                    userDetails.put("Number",number1);
                    userDetails.put("Email",email1);
                    userDetails.put("City",city1);
                    userDetails.put("Gender",gender1);
                    userDetails.put("Blood Group",bloodgroup1);
                    userDetails.put("Dob",dob1);

                   reference.child(id).setValue(userDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                                   if(task.isSuccessful()){
                                       Toast.makeText(register.this, "registered successfully", Toast.LENGTH_SHORT).show();
                                       Intent intent=new Intent(register.this,MainActivity.class);
                                       startActivity(intent);
                                       user.sendEmailVerification()
                                               .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                   @Override
                                                   public void onComplete(@NonNull Task<Void> task) {
                                                       if (task.isSuccessful()) {
                                                           Toast.makeText(register.this, "Email verfication sent", Toast.LENGTH_SHORT).show();
                                                           mauth.signOut();
                                                       }
                                                   }
                                               });
                                   }
                       }
                   });

                }
                else
                {
                    Toast.makeText(register.this, "Registeration Problem...!!! ", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}