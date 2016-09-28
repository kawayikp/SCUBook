package scu.book.campus.com.campusbook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.firebase.client.Query;

import java.util.ArrayList;
import java.util.List;

import scu.book.campus.com.campusbook.adapter.BuyerBookListAdapter;
import scu.book.campus.com.campusbook.adapter.BuyerUserListAdapter;
import scu.book.campus.com.campusbook.model.User;

/**
 * Created by Kawayipk on 5/25/16.
 */
public class BuyerHistory extends AppCompatActivity implements AdapterView.OnItemClickListener {

    SharedPreferences myPrefs;
    Firebase myFirebaseRef;
    int totalNumberOfUsers = 0;
    ArrayList<User> buyers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_user_record);

        Intent i = getIntent();
//        buyers = (ArrayList<User>) savedInstanceState.getSerializable("buyers");
        myPrefs = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        String buyerName = myPrefs.getString("buyernames", "Ester");
        String buyerEmail = myPrefs.getString("buyeremails", "Eclara@scu.edu");
        User user = new User(buyerName, buyerEmail, "98237232");
//        buyers = (ArrayList<User>) getIntent().getSerializableExtra("buyers");


        // for test OK
//        myPrefs = this.getSharedPreferences("buyerhistory", MODE_PRIVATE);
//        String usrsnames = myPrefs.getString("buyernames", "");
//        String usremails = myPrefs.getString("buyeremails", "");
//        String[] username = usrsnames.split(",");
//        String[] usremail = usremails.split(",");
//        for (int i = 0; i < username.length; i++) {
//            users.add(new User(username[i],usremail[i],""));
//        }


        // for firebase
//        Firebase.setAndroidContext(this);
//        myFirebaseRef = new Firebase("https://flickering-torch-3960.firebaseio.com/Users");
//        Query query = myFirebaseRef.orderByChild("sellerEmail").equalTo("yliu4@scu.edu");
//
//        totalNumberOfUsers = users.size();
        buyers.add(user);

        ListView lv = (ListView) findViewById(R.id.listView_buyer_user);
        lv.setAdapter(new BuyerUserListAdapter(this, R.layout.buyer_user_record_card, buyers));
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }


}
