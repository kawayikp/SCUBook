package scu.book.campus.com.campusbook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import scu.book.campus.com.campusbook.model.Books;
import scu.book.campus.com.campusbook.model.User;

/**
 * Created by qizhao on 5/18/16.
 */
public class Seller extends Fragment {
    private Uri imageUri;
    private String pictureImagePath = "";
    private String pic;
    private String book_name_s;
    private String isbn_s;
    private String price_s;
    private String location_s;
    private String type_s;
    private String seller_email;
    private String seller_phone;
    Firebase myFirebaseRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(
                R.layout.seller_page, container, false);
        final Books new_book = new Books();
        Gson gson = new Gson();
        SharedPreferences myPrefs = this.getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        /*SharedPreferences.Editor editor = myPrefs.edit();
        editor.remove("User");
        editor.apply();*/
        final String json = myPrefs.getString("User", "");
        Log.d("User obj", json);
        final User user_obj = gson.fromJson(json, User.class);
        Button page1Next = (Button) rootView.findViewById(R.id.button_seller1_1);
        Button page2Next = (Button) rootView.findViewById(R.id.button_seller2_1);
        Button page3Next = (Button) rootView.findViewById(R.id.button_seller3_1);
        Button page2Back = (Button) rootView.findViewById(R.id.button4);
        Button page3Back = (Button) rootView.findViewById(R.id.button7);
        Button takePhoto = (Button) rootView.findViewById(R.id.take_photo);
        Button selectPhoto = (Button) rootView.findViewById(R.id.select_photo);
        final CheckBox sell = (CheckBox)rootView.findViewById(R.id.seller_sell_checkbox);
        final CheckBox rent = (CheckBox)rootView.findViewById(R.id.seller_rent_checkbox);
        final EditText book_name  = (EditText) rootView.findViewById(R.id.seller_bookName_et);
        final EditText isbn  = (EditText) rootView.findViewById(R.id.seller_isbn_et);
        final EditText price  = (EditText) rootView.findViewById(R.id.seller_price_et);
        final EditText location = (EditText) rootView.findViewById(R.id.seller_contactlocation_et);
        final LinearLayout page1 = (LinearLayout) rootView.findViewById(R.id.seller_page1);
        final LinearLayout page2 = (LinearLayout) rootView.findViewById(R.id.seller_page2);
        final LinearLayout page3 = (LinearLayout) rootView.findViewById(R.id.seller_page3);
        final LinearLayout soldPage = (LinearLayout) rootView.findViewById(R.id.seller_page_sold);
        final ImageView mImg =  (ImageView) rootView.findViewById(R.id.imageView_seller2_1);

        page1Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (pictureImagePath == null || pictureImagePath.length()== 0) {
                    Toast.makeText(getContext(), "Please take or select photo", Toast.LENGTH_SHORT).show();
                } else {
                    page1.setVisibility(View.GONE);
                    page2.setVisibility(View.VISIBLE);
                    page3.setVisibility(View.GONE);
                    Bitmap picView = convertToBitmap();


                    mImg.setImageBitmap(picView);

                    if (json != null && json.length() != 0){
                        new_book.sellerName = user_obj.name;
                    }

                    new_book.bookImage = pic;

                }


            }
        });
        page2Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book_name_s = book_name.getText().toString();
                isbn_s = isbn.getText().toString();
                price_s = price.getText().toString();

                if (sell.isChecked() && rent.isChecked()) {
                    type_s = "Sell or rent";
                } else if(sell.isChecked()) {
                    type_s = "Sell";
                } else {
                    type_s = "rent";
                }

                if (book_name_s == null || book_name_s.length() == 0){
                    Toast.makeText(getContext(), "Your book name is invalid, please re-enter!", Toast.LENGTH_SHORT).show();
                } else if (isbn_s == null || isbn_s.length() == 0) {
                    Toast.makeText(getContext(), "Your isbn is invalid, please re-enter!", Toast.LENGTH_SHORT).show();
                } else if (price_s == null || price_s.length() == 0) {
                    Toast.makeText(getContext(), "Your price is invalid, please re-enter!", Toast.LENGTH_SHORT).show();
                } else if (!sell.isChecked() && !rent.isChecked()) {
                    Toast.makeText(getContext(), "Please choose to rent or sell!", Toast.LENGTH_SHORT).show();
                } else {
                    page1.setVisibility(View.GONE);
                    page2.setVisibility(View.GONE);
                    page3.setVisibility(View.VISIBLE);
                    new_book.bookName = book_name_s;
                    new_book.isbn = isbn_s;
                    new_book.bookPrice = price_s;
                    new_book.sellerType = type_s;
                }

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
                    location_s = location.getText().toString();
                    if (location_s == null || location_s.length() == 0){
                        Toast.makeText(getContext(), "Your book location is invalid, please re-enter!", Toast.LENGTH_SHORT).show();
                    } else {
                        myFirebaseRef = new Firebase("https://flickering-torch-3960.firebaseio.com/");
                        Firebase booksRef = myFirebaseRef.child("Books");
                        if (json != null && json.length() != 0) {
                            seller_phone = user_obj.phoneNumber;
                            new_book.sellerPhone = seller_phone;
                            seller_email = user_obj.email;
                            new_book.sellerEmail = seller_email;
                            new_book.sellerLocation = location_s;
                        }

                        booksRef.push().setValue(new_book);




                        page3.setVisibility(View.GONE);
                        soldPage.setVisibility(View.VISIBLE);
                }

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

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                try {
                    File photoFile = createImFile();
                } catch (IOException ex) {
                    Log.e("Error", "Error occurred");
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                startActivityForResult(intent, 1);
            }
        });


        selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create intent to Open Image applications like Gallery, Google Photos
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // Start the Intent
                startActivityForResult(galleryIntent, 1);
            }
        });




        return rootView;
    }

    private File createImFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + ".jpg";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        pictureImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;

        File im = new File(pictureImagePath);
        imageUri = Uri.fromFile(im);


        return im;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Here we need to check if the activity that was triggers was the Image Gallery.
        // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
        // If the resultCode is RESULT_OK and there is some data we know that an image was picked.
        if (requestCode == 1 && data != null) {
            // Let's read picked image data - its URI
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContext().getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            pictureImagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            // Now we need to set the GUI ImageView data with data read from the picked file.


            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();
        }
    }

    private Bitmap convertToBitmap(){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8; // Experiment with different sizes
        Bitmap pic_bitmap = BitmapFactory.decodeFile(pictureImagePath, options);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        pic_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        pic = Base64.encodeToString(bytes, Base64.DEFAULT);
        Log.d("base64", pic);
        return pic_bitmap;
    }

}