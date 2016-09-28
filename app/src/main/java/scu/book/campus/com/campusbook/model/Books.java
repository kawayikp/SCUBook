package scu.book.campus.com.campusbook.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kushahuja on 5/23/16.
 */
public class Books {
    public String key;
    public String bookName;
    public String bookPrice;
    public String bookImage;
    public String sellerEmail;
    public String sellerPhone;
    public String sellerName;
    public String sellerLocation;
    public String isbn;
    public String sellerType;
    public BuyerDetails buyerDetails;


    public Books() {
    }

    public Books(String bookName, String bookPrice, String bookImage, String sellerEmail, String sellerName, String sellerLocation,
                 String isbn, String sellerType,BuyerDetails buyerDetails) {
        this.bookName = bookName;
        this.bookImage = bookImage;
        this.bookPrice = bookPrice;
        this.sellerEmail = sellerEmail;
        this.sellerName = sellerName;
        this.sellerLocation = sellerLocation;
        this.isbn = isbn;
        this.sellerType = sellerType;
        this.buyerDetails= buyerDetails;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;

    }


    public void setBookName(String bookName) {
        this.bookName = bookName;

    }

    public String getSellerPhone() {
        return sellerPhone;
    }

    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;

    }

    public String getBookName() {
        return bookName;
    }

    public void setBookPrice(String bookPrice) {
        this.bookPrice = bookPrice;

    }

    public String getBookPrice() {
        return bookPrice;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;

    }

    public String getBookImage() {
        return bookImage;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;

    }

    public String getIsbn() {
        return isbn;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;

    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerLocation(String sellerLocation) {
        this.sellerLocation = sellerLocation;

    }

    public String getSellerLocation() {
        return sellerLocation;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;

    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerType(String sellerType) {
        this.sellerType = sellerType;

    }

    public String getSellerType() {
        return sellerType;
    }

    public BuyerDetails getBuyers() {
        return buyerDetails;
    }

    public void setBuyers(BuyerDetails buyerDetails) {
        this.buyerDetails = buyerDetails;
    }


//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(bookImage);
//        dest.writeString(bookName);
//        dest.writeString(bookPrice);
//
//        dest.writeString(sellerEmail);
//        dest.writeString(sellerName);
//        dest.writeString(sellerLocation);
//        dest.writeString(sellerType);
//
//        dest.writeString(isbn);
//
//
//    }
//
//    public static final Parcelable.Creator<Books> CREATOR
//            = new Parcelable.Creator<Books>() {
//        public Books createFromParcel(Parcel in) {
//            return new Books(in);
//        }
//
//        public Books[] newArray(int size) {
//            return new Books[size];
//        }
//    };
//
//    private Books(Parcel in) {
//        bookImage = in.readString();
//        bookName = in.readString();
//        bookPrice = in.readString();
//
//        sellerEmail = in.readString();
//        sellerName = in.readString();
//        sellerLocation = in.readString();
//        sellerType = in.readString();
//
//        isbn = in.readString();
//
//
//    }
}

