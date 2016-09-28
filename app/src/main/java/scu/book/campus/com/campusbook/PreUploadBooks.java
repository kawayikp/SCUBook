package scu.book.campus.com.campusbook;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;

import java.io.ByteArrayOutputStream;

import scu.book.campus.com.campusbook.model.Books;

/**
 * Created by qizhao on 5/23/16.
 */
public class PreUploadBooks extends AppCompatActivity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preupload);
        Firebase.setAndroidContext(this);
        Button upload_b = (Button) findViewById(R.id.upload);

        upload_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase myFirebaseRef = new Firebase("https://flickering-torch-3960.firebaseio.com/");
                Firebase bookRef = myFirebaseRef.child("Books");
                String pic = storeImageToFirebase();
                /*Books book_temp = new Books("The Sower", "23", pic, "sjan@gmail.com", "Sandy Jan", "Santa Clara University",
                        "0975361554", "Sell");*/
                /*Books book_temp = new Books("Secret Garden", "3", pic, "JsonChang@gmail.com", "Jason Chang", "Santa Clara University",
                        "0451528832", "Sell");*/
                /*Books book_temp = new Books("Memoirs of an Imaginary Friend: A Novel", "10", pic, "kkju@hotmail.com", "Kris Williams", "Santa Clara University",
                        "1250031850", "Sell");*/
                /*Books book_temp = new Books("KL: A History of the Nazi Concentration Camps", "25", pic, "ijazhq@hotmail.com", "Irian Megan", "Santa Clara University",
                        "0374118256", "Sell");*/
                /*Books book_temp = new Books("The Maker of Swans", "10", pic, "819237@hotmail.com", "Chuck Norris", "Santa Clara University",
                        "1474600360", "Rent");*/
//                Books book_temp = new Books("Adaptive Web Design: Crafting Rich Experiences with Progressive Enhancement, Edition 2", "16", pic, "18231@hotmail.com", "Jacky Cheng", "Santa Clara University",
//                        "9780134216201", "Sell");


//                bookRef.push().setValue(book_temp);

            }
        });
    }

    private String storeImageToFirebase() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8; // shrink it down otherwise we will use stupid amounts of memory
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.book_cover_5, options);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);
        return base64Image;

        // we finally have our base64 string version of the image, save it.
        //Log.d("image status", "Stored image with length: " + bytes.length);
    }
}
