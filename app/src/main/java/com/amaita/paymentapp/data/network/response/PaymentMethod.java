package com.amaita.paymentapp.data.network.response;

import com.google.gson.annotations.SerializedName;

public class PaymentMethod {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("status")
    private String status;

    @SerializedName("secureThumbnail")
    private String secureThumbnail;

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("payment_type_id")
    private String payment_type_id;

    @SerializedName("min_allowed_amount")
    private double min_allowed_amount;

    @SerializedName("max_allowed_amount")
    private double max_allowed_amount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSecureThumbnail() {
        return secureThumbnail;
    }

    public void setSecureThumbnail(String secureThumbnail) {
        this.secureThumbnail = secureThumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPayment_type_id() {
        return payment_type_id;
    }

    public void setPayment_type_id(String payment_type_id) {
        this.payment_type_id = payment_type_id;
    }

    public double getMin_allowed_amount() {
        return min_allowed_amount;
    }

    public void setMin_allowed_amount(double min_allowed_amount) {
        this.min_allowed_amount = min_allowed_amount;
    }

    public double getMax_allowed_amount() {
        return max_allowed_amount;
    }

    public void setMax_allowed_amount(double max_allowed_amount) {
        this.max_allowed_amount = max_allowed_amount;
    }
}
