        package scu.book.campus.com.campusbook.model;

        import java.util.ArrayList;
        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

        public class BooksSchema {

    @SerializedName("sellerEmail")
    @Expose
    private String sellerEmail;
    @SerializedName("buyerDetails")
    @Expose
    private List<BuyerDetails> buyerDetails = new ArrayList<BuyerDetails>();
    @SerializedName("isbn")
    @Expose
    private String isbn;
    @SerializedName("sellerType")
    @Expose
    private String sellerType;
    @SerializedName("sellerPhone")
    @Expose
    private String sellerPhone;
    @SerializedName("sellerName")
    @Expose
    private String sellerName;
    @SerializedName("bookPrice")
    @Expose
    private String bookPrice;
    @SerializedName("sellerLocation")
    @Expose
    private String sellerLocation;
    @SerializedName("bookName")
    @Expose
    private String bookName;

    /**
     *
     * @return
     * The sellerEmail
     */
    public String getSellerEmail() {
        return sellerEmail;
    }

    /**
     *
     * @param sellerEmail
     * The sellerEmail
     */
    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }

    /**
     *
     * @return
     * The buyerDetails
     */
    public List<BuyerDetails> getBuyerDetails() {
        return buyerDetails;
    }

    /**
     *
     * @param buyerDetails
     * The buyerDetails
     */
    public void setBuyerDetails(List<BuyerDetails> buyerDetails) {
        this.buyerDetails = buyerDetails;
    }

    /**
     *
     * @return
     * The isbn
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     *
     * @param isbn
     * The isbn
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     *
     * @return
     * The sellerType
     */
    public String getSellerType() {
        return sellerType;
    }

    /**
     *
     * @param sellerType
     * The sellerType
     */
    public void setSellerType(String sellerType) {
        this.sellerType = sellerType;
    }

    /**
     *
     * @return
     * The sellerPhone
     */
    public String getSellerPhone() {
        return sellerPhone;
    }

    /**
     *
     * @param sellerPhone
     * The sellerPhone
     */
    public void setSellerPhone(String sellerPhone) {
        this.sellerPhone = sellerPhone;
    }

    /**
     *
     * @return
     * The sellerName
     */
    public String getSellerName() {
        return sellerName;
    }

    /**
     *
     * @param sellerName
     * The sellerName
     */
    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    /**
     *
     * @return
     * The bookPrice
     */
    public String getBookPrice() {
        return bookPrice;
    }

    /**
     *
     * @param bookPrice
     * The bookPrice
     */
    public void setBookPrice(String bookPrice) {
        this.bookPrice = bookPrice;
    }

    /**
     *
     * @return
     * The sellerLocation
     */
    public String getSellerLocation() {
        return sellerLocation;
    }

    /**
     *
     * @param sellerLocation
     * The sellerLocation
     */
    public void setSellerLocation(String sellerLocation) {
        this.sellerLocation = sellerLocation;
    }

    /**
     *
     * @return
     * The bookName
     */
    public String getBookName() {
        return bookName;
    }

    /**
     *
     * @param bookName
     * The bookName
     */
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

}