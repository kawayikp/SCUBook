package scu.book.campus.com.campusbook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import scu.book.campus.com.campusbook.Constants.SharedData;
import scu.book.campus.com.campusbook.adapter.BuyerBookListAdapter;
import scu.book.campus.com.campusbook.model.Books;
import scu.book.campus.com.campusbook.model.BuyerDetails;
import scu.book.campus.com.campusbook.model.BuyerList;
import scu.book.campus.com.campusbook.model.User;

/**
 * Created by qizhao on 5/18/16.
 */
public class Buyer extends Fragment implements AdapterView.OnItemClickListener {

    public static boolean bookSelected = false;
    BuyerList bookObj;

    ImageView bookImage;
    TextView bookName;
    TextView bookPrice;
    TextView isbn;

    ImageView bookImageSecondPage;

    TextView sellerName;
    TextView sellerEmail;
    TextView sellerPhone;


    LinearLayout page1;
    LinearLayout page2;

    RelativeLayout page0;
    LinearLayout page3;


    ImageView bookImageSThirdPAge;


    Firebase myFirebaseRef;
    List<BuyerList> list;
    BuyerBookListAdapter adapter;
    int totalNumberOfbooks = 0;
    SharedPreferences myPrefs;
    String seller_email;
    User user_obj;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Firebase firebaseRef;
        View rootView = inflater.inflate(
                R.layout.buyer_page, container, false);
        list = new ArrayList<>();

        // get user information
        Gson gson = new Gson();
        myPrefs = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        final String json = myPrefs.getString("User", "");
        Log.d("User obj", json);

        if (json != null && json.length() != 0) {
            user_obj = gson.fromJson(json, User.class);
            seller_email = user_obj.getEmail();
            Log.d("seller_email", seller_email);
        }


        //for firebase
        Firebase.setAndroidContext(getActivity());
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
        ListView lv = (ListView) rootView.findViewById(R.id.listView_buyer_book);
        adapter = new BuyerBookListAdapter(getActivity(), lv.getId(), list);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        lv.setOnItemClickListener(this);

        Firebase.setAndroidContext(getContext());
        firebaseRef = new Firebase("https://flickering-torch-3960.firebaseio.com/Books");


        System.out.println("Buyer page started");

        Query query2 = firebaseRef.orderByChild("sellerEmail").equalTo("kkju@gmail.com");
        query2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Books books = dataSnapshot.getValue(Books.class);

                System.out.println("book name retrived is " + books.getBookName());

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
        Button page1Next = (Button) rootView.findViewById(R.id.button_buyer1_1);
        Button page2Next = (Button) rootView.findViewById(R.id.button_buyer2_1);
        Button page3Next = (Button) rootView.findViewById(R.id.button_buyer3_1);
        Button page2Back = (Button) rootView.findViewById(R.id.button5);
        Button page3Back = (Button) rootView.findViewById(R.id.button6);
        Button showMap = (Button) rootView.findViewById(R.id.map_button);
        bookImage = (ImageView) rootView.findViewById(R.id.book_image);
        bookName = (TextView) rootView.findViewById(R.id.book_name);
        bookPrice = (TextView) rootView.findViewById(R.id.book_price);
        isbn = (TextView) rootView.findViewById(R.id.isbn);

        bookImageSecondPage = (ImageView) rootView.findViewById(R.id.book_image_second_page);

        sellerName = (TextView) rootView.findViewById(R.id.seller_name);
        sellerEmail = (TextView) rootView.findViewById(R.id.seller_email);
        sellerPhone = (TextView) rootView.findViewById(R.id.seller_phone);

        bookImageSThirdPAge = (ImageView) rootView.findViewById(R.id.book_image_third_page);
        page1 = (LinearLayout) rootView.findViewById(R.id.buyer_page1);
        page2 = (LinearLayout) rootView.findViewById(R.id.buyer_page2);
        page3 = (LinearLayout) rootView.findViewById(R.id.buyer_page3);
        page0 = (RelativeLayout) rootView.findViewById(R.id.buyer_page0);
        if (bookSelected == false) {
            System.out.println("Changing the visinility");
            page0.setVisibility(View.VISIBLE);
            page1.setVisibility(View.GONE);
            page2.setVisibility(View.GONE);
            page3.setVisibility(View.GONE);
        }
        final LinearLayout soldPage = (LinearLayout) rootView.findViewById(R.id.seller_page_sold);
//        final SharedPreferences myPrefs = getContext().getSharedPreferences("myPrefs", getContext().MODE_PRIVATE);
//        if (bookSelected == true) {
//            SharedPreferences myPrefs = getContext().getSharedPreferences("myPrefs", getContext().MODE_PRIVATE);
//            String json = myPrefs.getString("selectedbook", "");
//            Log.d("Book obj", json);
//            Gson gson = new Gson();
//            bookObj = gson.fromJson(json, Books.class);
//            if (json != null && json != "")
//                bookName.setText(bookObj.getBookName());
//            bookPrice.setText(bookObj.getBookPrice());
//
//            Bitmap bookImageBitmap = SharedData.getDecodedImageFromString(bookObj.getBookImage());
//            bookImage.setImageBitmap(bookImageBitmap);
//
//>>>>>>> aebe56ae62be1e0a5e510efc17dcb5f8004c2786
//
        page1Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page1.setVisibility(View.GONE);
                page2.setVisibility(View.VISIBLE);
                page3.setVisibility(View.GONE);

                sellerName.setText(bookObj.getSellerName());
                sellerEmail.setText(bookObj.getSellerEmail());
                bookImageSecondPage.setImageBitmap(SharedData.getDecodedImageFromString(bookObj.getBookImage()));
            }
        });
        page2Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page1.setVisibility(View.GONE);
                page2.setVisibility(View.GONE);
                page3.setVisibility(View.VISIBLE);
                bookImageSThirdPAge.setImageBitmap(SharedData.getDecodedImageFromString(bookObj.getBookImage()));

            }
        });
        page3Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (json == null || json.length()==0){

                    Intent gotoSignup = new Intent(getActivity(), LandingPage.class);
                    startActivity(gotoSignup);
                    Toast.makeText(getContext(), "Please Signup first to sell books!", Toast.LENGTH_SHORT).show();




                } else {
                    page3.setVisibility(View.GONE);
                    soldPage.setVisibility(View.VISIBLE);

                    Firebase myFirebaseRef = new Firebase("https://flickering-torch-3960.firebaseio.com");
                    System.out.println("Hello updating the buyer list" + bookObj.getKey());
                    Firebase buyerUpdate = myFirebaseRef.child("Books").child(bookObj.getKey());

                    // TO update the information
                /*Firebase alanRef = usersRef.child("alanisawesome");
                Map<String, Object> nickname = new HashMap<String, Object>();
                nickname.put("nickname", "Alan The Machine");
                alanRef.updateChildren(nickname);*/

//                Firebase alanRef = myFirebaseRef.child("alanisawesome");
//                Map<String, Object> buyerList = new HashMap<String, Object>();
//
//                Map<String, Object> buyerList = new ObjectMapper().convertValue(bd, Map.class);
//                buyerList.put("buyerDetails", bd);

                    System.out.println("Hello updating the buyer list" + user_obj.getName()
                    );
                    BuyerDetails bd = new BuyerDetails();
                    bd.setName(user_obj.getName());
                    bd.setEmail(user_obj.getEmail());
                    bookObj.setBuyerDetails(bd);
//    bookObj.getBuyerDetails().setEmail(user_obj.getEmail());

//                buyerUpdate.
                    buyerUpdate.setValue(bookObj);
//                buyerUpdate.child("sellerName").setValue("KKK");
                    System.out.println("Hello updating the buyer list" + bookObj.getKey());
                }



            }
        });
        page2Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page1.setVisibility(View.VISIBLE);
                page2.setVisibility(View.GONE);
                page3.setVisibility(View.GONE);
            }
        });
        page3Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page1.setVisibility(View.GONE);
                page2.setVisibility(View.VISIBLE);
                page3.setVisibility(View.GONE);
            }
        });
        showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("location_name", bookObj.getSellerLocation());
                intent.putExtra("seller", bookObj.getSellerName());
                intent.putExtra("seller_email", bookObj.getSellerEmail());
                startActivity(intent);
            }
        });
        return rootView;
    }


//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        System.out.println("Buyer page fragment visible onHIddenChanged " + hidden);
//        if (!hidden) {
//
//            if (bookSelected == true) {
//                SharedPreferences myPrefs = getContext().getSharedPreferences("myPrefs", getContext().MODE_PRIVATE);
//                String json = myPrefs.getString("selectedbook", "");
//                Log.d("Book obj", json);
//                Gson gson = new Gson();
//                bookObj = gson.fromJson(json, Books.class);
//                if (json != null || json != "")
//                    bookName.setText(bookObj.getBookName());
//                bookPrice.setText(bookObj.getBookPrice());
//                Bitmap bookImageBitmap = SharedData.getDecodedImageFromString(bookObj.getBookImage());
//                bookImage.setImageBitmap(bookImageBitmap);
//
//
//                bookSelected = false;
//
//            }
//
//        }
//    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d("Book obj", "SetUserVisibliitHint " + isVisibleToUser + " is book selected" + bookSelected);

        if (isVisibleToUser) {
            if (bookSelected == false) {
                System.out.println("Changing the visinility");

                page0.setVisibility(View.VISIBLE);
                page1.setVisibility(View.GONE);
                page2.setVisibility(View.GONE);
                page3.setVisibility(View.GONE);
            }
            if (bookSelected == true) {
                page0.setVisibility(View.GONE);
                page1.setVisibility(View.VISIBLE);
                page2.setVisibility(View.GONE);
                page3.setVisibility(View.GONE);
                bookSelected = false;
                SharedPreferences myPrefs = getContext().getSharedPreferences("myPrefs", getContext().MODE_PRIVATE);
                String json = myPrefs.getString("selectedbook", "");
                Log.d("Book obj", json);
                Gson gson = new Gson();
                bookObj = gson.fromJson(json, BuyerList.class);
                if (json != null || json != "")
                    bookName.setText(bookObj.getBookName());
                bookPrice.setText(bookObj.getBookPrice());
                isbn.setText(bookObj.getIsbn());

                System.out.println("Selected book path is " + bookObj.getKey());
                Bitmap bookImageBitmap = SharedData.getDecodedImageFromString(bookObj.getBookImage());
                bookImage.setImageBitmap(bookImageBitmap);

            }
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        System.out.println("Buyer page fragment visible  OnResume");

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
            Toast.makeText(getContext(), "fetching buyer info", Toast.LENGTH_LONG).show();

            String buyernames = book.getBuyerDetails().getName();
            String buyeremails = book.getBuyerDetails().getEmail();
            SharedPreferences.Editor prefsEditor = myPrefs.edit();
            prefsEditor.putString("buyernames", buyernames);
            prefsEditor.putString("buyeremails", buyeremails);
            prefsEditor.commit();


            Intent intent = new Intent(getContext(), BuyerHistory.class);
            //     intent.putExtra("buyers", buyer1);
            startActivity(intent);
        } catch (NullPointerException npe) {
            Toast.makeText(getContext(), "No buyer found for your book yet", Toast.LENGTH_LONG).show();

            System.out.println("No buyer found");
        }

    }
}



