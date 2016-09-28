package scu.book.campus.com.campusbook;

/**
 * Created by Kawayipk on 5/25/16.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import scu.book.campus.com.campusbook.Constants.SharedData;
import scu.book.campus.com.campusbook.adapter.BuyerBookListAdapter;
import scu.book.campus.com.campusbook.model.Books;
import scu.book.campus.com.campusbook.model.BuyerList;
import scu.book.campus.com.campusbook.model.User;


public class BookHistory extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Firebase myFirebaseRef;
    List<BuyerList> list;
    BuyerBookListAdapter adapter;
    int totalNumberOfbooks = 0;
    SharedPreferences myPrefs;
    String seller_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_book_record);
        list = new ArrayList<>();

        // get user information
        Gson gson = new Gson();
        myPrefs = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        final String json = myPrefs.getString("User", "");
        Log.d("User obj", json);

        if (json == null || json.length() == 0) {
            Intent intent = new Intent(this, BookHistory_anonymous.class);
            startActivity(intent);
        }

        final User user_obj = gson.fromJson(json, User.class);
        seller_email = user_obj.getEmail();
        Log.d("seller_email", seller_email);


        //for firebase
        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase("https://flickering-torch-3960.firebaseio.com").child("Books");
        Query query = myFirebaseRef.orderByChild("sellerEmail").equalTo(seller_email);

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                BuyerList books = dataSnapshot.getValue(BuyerList.class);
                list.add(books);
                adapter.notifyDataSetChanged();
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

        });

        totalNumberOfbooks = list.size();
        ListView lv = (ListView) findViewById(R.id.listView_buyer_book);
        adapter = new BuyerBookListAdapter(this, R.layout.buyer_book_record_card, list);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        BuyerList book = list.get(position);


//        ArrayList<Object> buyers = (ArrayList<Object>) book.getBuyers();
//        BuyerDetails buyer1 = new BuyerDetails("Kush", "book_buyer1@google.com");
//        BuyerDetails buyer2 = new BuyerDetails("Luv", "book_buyer1@google.com");
//        buyers.add(buyer1);
//        buyers.add(buyer2);


        // for test OK
//        myPrefs = this.getSharedPreferences("buyerhistory", MODE_PRIVATE);
//        String buyernames = "book1_buyer1,book1_buyer2";
//        String buyeremails = "book1_buyer1@google.com,book1_buyer2@google.com";
//
        try {
            Toast.makeText(getBaseContext(), "fetching buyer info", Toast.LENGTH_LONG).show();

            String buyernames = book.getBuyerDetails().getName();
            String buyeremails = book.getBuyerDetails().getEmail();
            SharedPreferences.Editor prefsEditor = myPrefs.edit();
            prefsEditor.putString("buyernames", buyernames);
            prefsEditor.putString("buyeremails", buyeremails);
            prefsEditor.commit();


        Intent intent = new Intent(this, BuyerHistory.class);
        //     intent.putExtra("buyers", buyer1);
        startActivity(intent);
        } catch (NullPointerException npe) {
            Toast.makeText(getBaseContext(), "No buyer found for your book yet", Toast.LENGTH_LONG).show();

            System.out.println("No buyer found");
        }

    }
}
