package com.amaita.paymentapp.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "payment")
public class Payment  {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public String method_id;

    private String method_name;

    private String method_url_thumbnail;


    public String card_issuer_id;

    private String card_issuer_name;

    private String card_issuer_thumbnail;

    private String installment;

    private double amount;

    public Payment( String method_name, String method_url_thumbnail, String card_issuer_id, String card_issuer_name, String card_issuer_thumbnail, String installment, double amount) {
        this.method_name = method_name;
        this.method_url_thumbnail = method_url_thumbnail;
        this.card_issuer_id = card_issuer_id;
        this.card_issuer_name = card_issuer_name;
        this.card_issuer_thumbnail = card_issuer_thumbnail;
        this.installment = installment;
        this.amount = amount;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getMethod_id() {
        return method_id;
    }

    public void setMethod_id(String method_id) {
        this.method_id = method_id;
    }

    public String getCard_issuer_id() {
        return card_issuer_id;
    }

    public void setCard_issuer_id(String card_issuer_id) {
        this.card_issuer_id = card_issuer_id;
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
