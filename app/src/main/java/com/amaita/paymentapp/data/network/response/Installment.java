package com.amaita.paymentapp.data.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Installment {

    @SerializedName("payer_costs")
    private List<PayerCost> payerCost;


    public List<PayerCost> getPayerCost() {
        return payerCost;
    }

    public void setPayerCost(List<PayerCost> payerCost) {
        this.payerCost = payerCost;
    }
}
