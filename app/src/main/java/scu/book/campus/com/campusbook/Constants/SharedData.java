package scu.book.campus.com.campusbook.Constants;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.util.ArrayList;
import java.util.List;

import scu.book.campus.com.campusbook.model.BuyerList;

/**
 * Created by kushahuja on 5/25/16.
 */
public class SharedData {

    public static List<BuyerList> booksList= new ArrayList<>();


    public static Bitmap getDecodedImageFromString(String imageStringBase64){

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8; // Experiment with different sizes

        byte[] decodedImage = Base64.decode(imageStringBase64, Base64.DEFAULT);
        Bitmap decodedImageByte = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);

        return decodedImageByte;
    }
}
