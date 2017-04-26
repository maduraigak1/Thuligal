package com.iyaltamizh.thuligal.Activity;
import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.iyaltamizh.thuligal.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static android.net.NetworkInfo.*;

public class LoginActivity extends Activity {

    ImageView bLogin, BsignUp;
    EditText etUsername, etPassword;
    private ProgressDialog pDialog;
    int state = 0;
    File file, sdcard;
    String path,username;
    Firebase ref;


    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        verifyStoragePermissions(this);
        verifysmsPermissions(this);
        setContentView(R.layout.activity_login);
        sdcard = Environment.getExternalStorageDirectory();
        Intent i = getIntent();

        state = i.getIntExtra("state", 1);
        if (state == 1) {
        }
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (ImageView) findViewById(R.id.bLogin);
        //bLogin.setOnClickListener(this);
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        if (isNetworkAvailable()) {

            Toast.makeText(getApplicationContext(), "You are in Online >> Please enter the credentials!", Toast.LENGTH_LONG).show();
        }
        // Login button Click Event
        bLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (isNetworkAvailable()) {
                    Authen();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "You are in Offline >> Offline Mode", Toast.LENGTH_LONG)
                            .show();
                    navigateListActivity();
                }

            }
        });
        BsignUp = (ImageView) findViewById(R.id.signup);
        BsignUp.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent homeIntent = new Intent(getApplicationContext(), Signup.class);
                startActivity(homeIntent);

            }
        });

    }

    /*if (username.equals("admin") && password.equals("admin")) {
        Toast.makeText(getApplicationContext(), "You are successfully logged in!", Toast.LENGTH_LONG).show();
        navigateListActivity();
    } else {
        Toast.makeText(getApplicationContext(),
                "Please enter the credentials!", Toast.LENGTH_LONG)
                .show();
    }

}*

        // Check for empty data in the form
        if (!username.isEmpty() && !password.isEmpty()) {
            // login user
            //checkLogin(username, password);
            // Put Http parameter username with value of Email Edit View control
            params.put("username", username);
            // Put Http parameter password with value of Password Edit View control
            params.put("password", password);
            // Invoke RESTful Web Service with Http parameters
            invokeWS(params);
        } else {
            // Prompt user to enter credentials
            Toast.makeText(getApplicationContext(),
                    "Please enter the credentials!", Toast.LENGTH_LONG)
                    .show();
        }
    }

});


/**
* function to verify login details in mysql db
*/
    public void Authen() {
        // Show Progress Dialog
        pDialog.setMessage("Logging in ...");
        pDialog.show();
        // Make RESTful webservice call using AsyncHttpClient object
        //  AsyncHttpClient client = new AsyncHttpClient();
        Firebase ref = new Firebase("https://chillishop.firebaseio.com//0/user/0/pass");

        Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                // Authenticated successfully with payload authData
                pDialog.hide();
                try {

                    FileWriter fw = new FileWriter(sdcard + "/ChilliShop/log/user.enc");
                    fw.write(username);
                    fw.close();
                }catch (IOException e){

                }
                sev();
                Toast.makeText(getApplicationContext(), "You are successfully logged in!", Toast.LENGTH_LONG).show();
                navigateListActivity();
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                // Authenticated failed with error firebaseError
                pDialog.hide();
                Toast.makeText(getApplicationContext(),
                        "Please enter correct credentials!", Toast.LENGTH_LONG)
                        .show();
                etUsername.setText("");
                etPassword.setText("");
            }
        };

// Or with an email/password combination
        username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        ref.authWithPassword(username, password, authResultHandler);




          /* rootRef1.addValueEventListener(new ValueEventListener() {
               public void onDataChange(DataSnapshot dataSnapshot) {

                   String username = etUsername.getText().toString().trim();
                   String password = etPassword.getText().toString().trim();

                   if(username.equals("")&&password.equals("")){
                       Toast.makeText(getApplicationContext(),
                               "Please enter the credentials!", Toast.LENGTH_LONG)
                               .show();
                   }
                 else  if(dataSnapshot.getValue().equals(password)&&username.equals("admin"))

                   {

                       pDialog.hide();
                       Toast.makeText(getApplicationContext(), "You are successfully logged in!", Toast.LENGTH_LONG).show();
                       navigateListActivity();
                   }
                   else

                   {
                       pDialog.hide();
                       Toast.makeText(getApplicationContext(),
                               "Please enter correct credentials!", Toast.LENGTH_LONG)
                               .show();
                       etUsername.setText("");
                       etPassword.setText("");
                   }
               }

               @Override
               public void onCancelled(FirebaseError firebaseError) {

               }


           });*/
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    //client.post("http://10.91.22.96/ChilliShop/api/login/logincheck/", params, new AsyncHttpesponseHandler() {
    // When the response returned by REST has Http response code '200'

           /* public void onSuccess(String response) {
                // Hide Progress Dialog

                try {
                    // JSON Object
                   // JSONObject obj = new JSONObject(response);
                    // When the JSON response has status boolean value assigned with true
                    if (obj.getInt("response_code") == 1) {
                        Toast.makeText(getApplicationContext(), "You are successfully logged in!", Toast.LENGTH_LONG).show();
                        JSONObject result = obj.getJSONObject("result");
                        employeeid = result.getString("employeeid");
                        // Navigate to Home screen
                        if (result.getInt("admin") == 1) {
                            navigateAdminHomeActivity();
                        } else {
                            navigateEmployeeHomeActivity();
                        }

                    }
                    // Else display error message
                    else {
                        // errorMsg.setText(obj.getString("error_msg"));
                        Toast.makeText(getApplicationContext(), "Invalid Username/Password", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }

            // When the response returned by REST has Http response code other than '200'
            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {
                // Hide Progress Dialog
                pDialog.hide();
                // When Http response code is '404'
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]" + error, Toast.LENGTH_LONG).show();
                    }
                }
            });
        }*/

    public void navigateListActivity() {
        //Intent homeIntent = new Intent(getApplicationContext(), filter.class);
       // startActivity(homeIntent);
    }

    /* public void navigateEmployeeHomeActivity(){
         Intent homeIntent = new Intent(getApplicationContext(),EmployeeHomeActivity.class);
         homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         homeIntent.putExtra("employeeid", employeeid);
         startActivity(homeIntent);
     }*/
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void showDialogTime() throws Exception {
        showDialog();
        Thread.sleep(10000);
        hideDialog();

    }

    public void sev() {

        //startService(new Intent(this, Sync.class));
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int REQUEST_SEND_SMS = 1;
    private static String[] PERMISSIONS_SMS = {
            Manifest.permission.SEND_SMS,
    };


    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    public static void verifysmsPermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.SEND_SMS);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_SMS,REQUEST_SEND_SMS
            );
        }
    }












  /*  public void getelements(final String val) {


        Firebase ref = new Firebase("https://chillishop.firebaseio.com/" + val);
        ref.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                items newPost = snapshot.getValue(items.class);
                File uri = new File(path + val + "/" + newPost.getName());
                try {
                    uri.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                createImage(uri, newPost.getValue());


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

            //... ChildEventListener also defines onChildChanged, onChildRemoved,
            //    onChildMoved and onCanceled, covered in later sections.
        });

    }*/


    public void createImage(File url, String image) {
        byte[] imageByteArray = decodeImage(image);
        FileOutputStream imageOutFile = null;
        try {
            imageOutFile = new FileOutputStream(url);
            imageOutFile.write(imageByteArray);
            imageOutFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void encod() {
        try {
            // Reading a Image file from file system
            FileInputStream imageInFile = new FileInputStream(file);
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);

            // Converting Image byte array into Base64 String
            String imageDataString = encodeImage(imageData);

            // Converting a Base64 String into Image byte array
            byte[] imageByteArray = decodeImage(imageDataString);

            // Write a image byte array into file system
            FileOutputStream imageOutFile = new FileOutputStream(
                    "/home/arunkumar/Downloads/images1111.jpg");

            imageOutFile.write(imageByteArray);

            imageInFile.close();
            imageOutFile.close();

            System.out.println("Image Successfully Manipulated!");
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
    }


    public static String encodeImage(byte[] imageByteArray) {
        return Base64.encodeToString(imageByteArray, 1);
    }

    public static byte[] decodeImage(String imageDataString) {
        return Base64.decode(imageDataString, 1);
    }

}