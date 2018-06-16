package com.amaita.paymentapp.data.network.response;

import com.google.gson.annotations.SerializedName;

public class PayerCost {

    @SerializedName("recommended_message")
    private String recommendedMessage;


    public String getRecommendedMessage() {
        return recommendedMessage;
    }

    public void setRecommendedMessage(String recommendedMessage) {
        this.recommendedMessage = recommendedMessage;
    }
}
