package com.iyaltamizh.thuligal.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.iyaltamizh.thuligal.R;

import java.util.Map;

public class Signup extends AppCompatActivity {


    EditText ed7,ed8;
    Button sign;
    String Mailid,pass;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        pDialog = new ProgressDialog(this);
        setContentView(R.layout.activity_signup);
        sign=(Button) findViewById(R.id.button3);
        ed7=(EditText) findViewById(R.id.editText6);
        ed8=(EditText) findViewById(R.id.editText9);

sign.setOnClickListener(
        new View.OnClickListener() {

            public void onClick(View view) {
                pDialog.setMessage("Registering  ...");
                pDialog.show();
                Mailid=ed7.getText().toString();
                pass=ed8.getText().toString();
                RegUser(Mailid,pass);
            }            }
        );

    }

    public void RegUser(String user,String pass){
        Firebase ref = new Firebase("https://chillishop.firebaseio.com/");

        ref.createUser(user, pass, new Firebase.ValueResultHandler<Map<String, Object>>() {

            public void onSuccess(Map<String, Object> result) {
                if(result.get("uid").toString().length()>0)
                Toast.makeText(getApplicationContext(), "Registered successfully", Toast.LENGTH_LONG).show();
                pDialog.hide();
                finish();


                System.out.println("New account with uid: " + result.get("uid"));
            }

            public void onError(FirebaseError firebaseError) {
                // there was an error
                pDialog.hide();
                Toast.makeText(getApplicationContext(), "Error Occurred", Toast.LENGTH_LONG).show();
            }
        });

    }
}
