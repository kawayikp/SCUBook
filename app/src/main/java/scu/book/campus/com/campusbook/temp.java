//package scu.book.campus.com.campusbook;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Base64;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//
//import com.firebase.client.ChildEventListener;
//import com.firebase.client.DataSnapshot;
//import com.firebase.client.Firebase;
//import com.firebase.client.FirebaseError;
//import com.firebase.client.Query;
//import com.firebase.client.ValueEventListener;
//
//import java.io.ByteArrayOutputStream;
//
///**
// * Created by qizhao on 5/22/16.
// */
//public class Uplaod extends AppCompatActivity{
//    Firebase myFirebaseRef;
//    ImageView show;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.uploadbook);
//        Firebase.setAndroidContext(this);
//        Button upload = (Button) findViewById(R.id.button3);
//        Button showimage = (Button) findViewById(R.id.button8);
//        Button queryBooks = (Button) findViewById(R.id.query_books);
//        Button editData= (Button) findViewById(R.id.edit_data);
//
//        show = (ImageView) findViewById(R.id.imageView2);
//        upload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myFirebaseRef = new Firebase("https://flickering-torch-3960.firebaseio.com/");
//                Firebase alanRef = myFirebaseRef.child("Books-Kush2").child("Book1");
//                Books alan1 = new Books("115", "book1","ll");
//                alanRef.setValue(alan1);
//                storeImageToFirebase(alanRef);
//
//                Firebase alanRef2 = myFirebaseRef.child("Books-Kush2").child("Book2");
//                Books alan = new Books("15", "book2","kk");
//                alanRef2.setValue(alan);
//                storeImageToFirebase(alanRef2);
//
//                alanRef.child("price").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot snapshot) {
//
//                    }
//                    @Override public void onCancelled(FirebaseError error) { }
//                });
//            }
//        });
//        showimage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                previewStoredFirebaseImage();
//            }
//        });
//        editData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                myFirebaseRef = new Firebase("https://flickering-torch-3960.firebaseio.com/");
//                Firebase alanRef2 = myFirebaseRef.child("Books-Kush2").child("Book1").child("buyers");
////                Books alan = new Books("15", "book2","kk");
//
////                alanRef2.
////                System.out.println("Buyer infor is "+ alanRef2.);
////                alanRef2.setValue("kkk");
////                storeImageToFirebase(alanRef2);
//
//
//            }
//        });
//
//
//        queryBooks.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myFirebaseRef = new Firebase("https://flickering-torch-3960.firebaseio.com/");
//
//                Firebase alanRef = myFirebaseRef.child("Books-Kush2");
//                Log.d("Snapshot data is ","Querying databse");
//
//                Query query = alanRef.orderByChild("buyers");
//                query.addChildEventListener(new ChildEventListener() {
//                    @Override
//                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                        Log.d("Snapshot data is ", dataSnapshot.child("buyers").toString());
////                        System.out.println("Snapshot being covertied is "+dataSnapshot.child("price").toString());  //prints "Do you have data? You'll love Firebase."
////                        System.out.println("Snapshot being covertied is "+dataSnapshot.child(dataSnapshot.getKey()).getValue().toString());
////                        System.out.println("Snapshot being covertied is "+dataSnapshot.child(dataSnapshot.getKey()).getValue().toString());
////                        Books books = (Books) dataSnapshot.child(dataSnapshot.getKey()).getValue();
////                        System.out.print("JHello "+ books.getName());
//
//                    }
//
//                    @Override
//                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//                    }
//
//                    @Override
//                    public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//                    }
//
//                    @Override
//                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                    }
//
//                    @Override
//                    public void onCancelled(FirebaseError firebaseError) {
//
//                    }
//                });
//            }
//        });
//    }
//
//    private void storeImageToFirebase(Firebase myFirebaseRef ) {
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 8; // shrink it down otherwise we will use stupid amounts of memory
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
//                R.drawable.book_cover_1, options);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] bytes = baos.toByteArray();
//        String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);
//
//        // we finally have our base64 string version of the image, save it.
//        myFirebaseRef.child("pic").setValue(base64Image);
//        System.out.println("Stored image with length: " + bytes.length);
//    }
//    private void previewStoredFirebaseImage() {
//        myFirebaseRef.child("pic").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                String base64Image = (String) snapshot.getValue();
//                byte[] imageAsBytes = Base64.decode(base64Image.getBytes(), Base64.DEFAULT);
//                show.setImageBitmap(
//                        BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length)
//                );
//                System.out.println("Downloaded image with length: " + imageAsBytes.length);
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError error) {}
//        });
//    }
//}