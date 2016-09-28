package scu.book.campus.com.campusbook.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import scu.book.campus.com.campusbook.Buyer;
import scu.book.campus.com.campusbook.Constants.SharedData;
import scu.book.campus.com.campusbook.R;
import scu.book.campus.com.campusbook.model.Books;
import scu.book.campus.com.campusbook.model.BuyerList;

/**
 * Created by kushahuja on 5/17/16.
 */
public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.MyViewHolder> {
    private List<BuyerList> booksList = new ArrayList<>();
    Context context;
    Firebase firebaseRef;

    public ImageListAdapter(Context context) {
        Firebase.setAndroidContext(context);
        firebaseRef = new Firebase("https://flickering-torch-3960.firebaseio.com/Books");

//        Firebase booksRefs = firebaseRef.child("Books");

//        firebaseRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                System.out.println("booklist size under add value " + dataSnapshot.toString());
//                Books books = dataSnapshot.getValue(Books.class);
////                booksList.add(books);
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });
        booksList = new ArrayList<>();
        firebaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                System.out.println("Booklist size under child added " + dataSnapshot.getKey());

                BuyerList books = dataSnapshot.getValue(BuyerList.class);
//                books.setKey(dataSnapshot.getKey());

                books.setKey(dataSnapshot.getKey());
                booksList.add(books);
                System.out.println("Book key is  " + books.getKey());

                try {
                    System.out.println("And the email is " + books.getBuyerDetails().getEmail() + "And the name is " + books.getBuyerDetails().getName());
                } catch (NullPointerException npe) {
                    Log.d("Not found", "Email or name not found");
                }
                notifyDataSetChanged();
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
        this.context = context;
//        this.booksList= booksList;
        SharedData.booksList = booksList;

    }

    @Override
    public ImageListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_page_grid, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ImageListAdapter.MyViewHolder holder, int position) {

        BuyerList book = booksList.get(position);
        holder.bookName.setText(book.getBookName());
        holder.bookPrice.setText(book.getBookPrice());


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8; // Experiment with different sizes

        byte[] decodedImage = Base64.decode(book.getBookImage(), Base64.DEFAULT);
        Bitmap decodedImageByte = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);

        holder.bookImage.setImageBitmap(decodedImageByte);

    }

    @Override
    public int getItemCount() {
        System.out.println("Booklist size returned  under getItemCount is  " + booksList.size());

        return booksList.size();

    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView bookName;
        public TextView bookPrice;
        public ImageView bookImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            bookName = (TextView) itemView.findViewById(R.id.book_name);
            bookPrice = (TextView) itemView.findViewById(R.id.book_price);

            bookImage = (ImageView) itemView.findViewById(R.id.book_img);
        }
    }

    public BuyerList getImageObjectAtPosition(int position) {

        BuyerList image = booksList.get(position);
        return image;
    }


}
