package com.cita.coupons.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Coupon implements Parcelable {

    public static final Creator<Coupon> CREATOR
            = new Creator<Coupon>() {
        public Coupon createFromParcel(Parcel in) {
            return new Coupon(in);
        }

        public Coupon[] newArray(int size) {
            return new Coupon[size];
        }
    };

    // Members must be either primitives, primitive arrays or parcelables
    private String id;

    private String company;

    private String promoTitle;

    private String description;

    private int image;

    private int logo;

    public Coupon() {
    }

    public Coupon(String id, String company, String promoTitle, String description, int image,
            int logo) {
        this.id = id;
        this.company = company;
        this.promoTitle = promoTitle;
        this.description = description;
        this.image = image;
        this.logo = logo;
    }

    public Coupon(String id, String company, String promoTitle, String description, int image) {
        this.id = id;
        this.company = company;
        this.promoTitle = promoTitle;
        this.description = description;
        this.image = image;
    }

    private Coupon(Parcel in) {

        // Note: order is important - you must read in the same order
        // you write in writeToParcel!
        id = in.readString();
        company = in.readString();
        promoTitle = in.readString();
        description = in.readString();
        image = in.readInt();
        logo = in.readInt();

    }

    public String getPromoTitle() {
        return promoTitle;
    }

    public void setPromoTitle(String promoTitle) {
        this.promoTitle = promoTitle;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getId() {
        return id;
    }

    public String getCompany() {
        return company;
    }

    public String getDescription() {
        return description;
    }

    public int getImage() {
        return image;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        // TODO write your class members to the parcel
        // Note: order is important - you must write in the same order
        // you read in your private parcelable constructor!

        out.writeString(id);
        out.writeString(company);
        out.writeString(promoTitle);
        out.writeString(description);
        out.writeInt(image);
        out.writeInt(logo);

    }

    @Override
    public int describeContents() {
        // include a FileDescriptor, otherwise you can simply return 0
        return 0;
    }

}
