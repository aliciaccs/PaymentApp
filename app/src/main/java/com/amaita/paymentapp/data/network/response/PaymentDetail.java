package com.amaita.paymentapp.data.network.response;

import com.google.gson.annotations.SerializedName;

public class PaymentDetail {

    @SerializedName("payment_method_id")
    private String paymentMethodId;


    @SerializedName("payment_type_id")
    private String paymentTypeId;

    @SerializedName("issuer")
    private CardIssuer issuer;

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(String paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }

    public CardIssuer getIssuer() {
        return issuer;
    }

    public void setIssuer(CardIssuer issuer) {
        this.issuer = issuer;
    }
}
