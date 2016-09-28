package scu.book.campus.com.campusbook.adapter;

import android.content.Context;

import com.firebase.client.Firebase;

/**
 * Created by kushahuja on 5/23/16.
 */
public class FireBaseAdapter {
    public static Firebase myFirebaseRef;

    public  FireBaseAdapter(Context context){
        Firebase.setAndroidContext(context);

    }

    public  Firebase getFirebase() {
        myFirebaseRef = new Firebase("https://flickering-torch-3960.firebaseio.com/");

        return myFirebaseRef;
    }
}
