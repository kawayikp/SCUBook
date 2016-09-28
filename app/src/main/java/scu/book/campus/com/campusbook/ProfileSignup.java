package scu.book.campus.com.campusbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import scu.book.campus.com.campusbook.model.User;

/**
 * Created by qizhao on 5/18/16.
 */
public class ProfileSignup extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
    EditText name;
    EditText email;
    EditText phone;
    String name_s;
    String email_s;
    String phone_s;
    Firebase myFirebaseRef;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final SharedPreferences myPrefs = this.getSharedPreferences("myPrefs", MODE_PRIVATE);

            setContentView(R.layout.profile_signup);
            Firebase.setAndroidContext(this);



        Button signUp = (Button) findViewById(R.id.sign_up);




        name = (EditText) findViewById(R.id.input_name);
        email = (EditText) findViewById(R.id.input_email);
        phone = (EditText) findViewById(R.id.input_phone);
        //Creating a shared preference





        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFirebaseRef = new Firebase("https://flickering-torch-3960.firebaseio.com/");
                name_s = name.getText().toString();
                email_s = email.getText().toString();
                phone_s = phone.getText().toString();

                if (name_s == null || name_s.length() == 0){
                    Toast.makeText(getApplicationContext(), "Your name is invalid, please re-enter!", Toast.LENGTH_SHORT).show();
                } else if (!isEmailValid(email_s) || email_s == null || email_s.length() == 0){
                    Toast.makeText(getApplicationContext(),"Your icon_buyer_email address is invalid, please re-enter!" , Toast.LENGTH_SHORT).show();

                }else {
                    Firebase usersRef = myFirebaseRef.child("Users");
                    User user_1 = new User(name_s, email_s, phone_s);
                    user_1.isRegistered = true;
                    usersRef.push().setValue(user_1);
                    SharedPreferences.Editor prefsEditor = myPrefs.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(user_1);
                    Log.d("User json", json);
                    prefsEditor.putString("User", json);
                    prefsEditor.commit();


                /*Firebase alanRef2 = myFirebaseRef.child("Books").child("Book2");
                Books alan = new Books("15", "book2");
                alanRef2.setValue(alan);*/


                    Intent homePage = new Intent(ProfileSignup.this,HomeActivity.class);
                    startActivity(homePage);
                }


             }
        });

    }
    public boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
