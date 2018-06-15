package com.amaita.paymentapp.data.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "payment")
public class Payment {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String method_name;

    private String method_url_thumbnail;

    private String card_issuer_name;

    private String card_issuer_thumbnail;


    public Payment(String method_name, String method_url_thumbnail, String card_issuer_name, String card_issuer_thumbnail) {
        this.method_name = method_name;
        this.method_url_thumbnail = method_url_thumbnail;
        this.card_issuer_name = card_issuer_name;
        this.card_issuer_thumbnail = card_issuer_thumbnail;
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
}
