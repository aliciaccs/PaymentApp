package com.amaita.paymentapp.utils;

import android.os.Parcel;
import android.os.Parcelable;

public class PaymentInConstruction implements Parcelable {

    public String method_id;

    private String method_name;

    private String method_url_thumbnail;


    public String card_issuer_id;

    private String card_issuer_name;

    private String card_issuer_thumbnail;

    private String installment;

    private double amount;

    public PaymentInConstruction() {}

    protected PaymentInConstruction(Parcel in) {
        method_id = in.readString();
        method_name = in.readString();
        method_url_thumbnail = in.readString();
        card_issuer_id = in.readString();
        card_issuer_name = in.readString();
        card_issuer_thumbnail = in.readString();
        installment = in.readString();
        amount = in.readDouble();
    }

    public static final Creator<PaymentInConstruction> CREATOR = new Creator<PaymentInConstruction>() {
        @Override
        public PaymentInConstruction createFromParcel(Parcel in) {
            return new PaymentInConstruction(in);
        }

        @Override
        public PaymentInConstruction[] newArray(int size) {
            return new PaymentInConstruction[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(method_id);
        parcel.writeString(method_name);
        parcel.writeString(method_url_thumbnail);
        parcel.writeString(card_issuer_id);
        parcel.writeString(card_issuer_name);
        parcel.writeString(card_issuer_thumbnail);
        parcel.writeString(installment);
        parcel.writeDouble(amount);
    }


    public String getMethod_id() {
        return method_id;
    }

    public void setMethod_id(String method_id) {
        this.method_id = method_id;
    }

    public String getMethod_name() {
        return method_name;
    }

    public void setMethod_name(String method_name) {
        this.method_name = method_name;
    }

    public String getMethod_url_thumbnail() {
        return method_url_thumbnail;
    }

    public void setMethod_url_thumbnail(String method_url_thumbnail) {
        this.method_url_thumbnail = method_url_thumbnail;
    }

    public String getCard_issuer_id() {
        return card_issuer_id;
    }

    public void setCard_issuer_id(String card_issuer_id) {
        this.card_issuer_id = card_issuer_id;
    }

    public String getCard_issuer_name() {
        return card_issuer_name;
    }

    public void setCard_issuer_name(String card_issuer_name) {
        this.card_issuer_name = card_issuer_name;
    }

    public String getCard_issuer_thumbnail() {
        return card_issuer_thumbnail;
    }

    public void setCard_issuer_thumbnail(String card_issuer_thumbnail) {
        this.card_issuer_thumbnail = card_issuer_thumbnail;
    }

    public String getInstallment() {
        return installment;
    }

    public void setInstallment(String installment) {
        this.installment = installment;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
