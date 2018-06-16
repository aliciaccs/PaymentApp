package com.amaita.paymentapp.data.network.api;

import com.amaita.paymentapp.data.network.response.CardIssuer;
import com.amaita.paymentapp.data.network.response.Installment;
import com.amaita.paymentapp.data.network.response.PaymentMethod;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PaymentServiceApi {


    @GET("payment_methods")
    Call<List<PaymentMethod>> getPaymentMethods();


    @GET("payment_methods/card_issuers")
    Call<List<CardIssuer>> getCardIssuers(@Query("payment_method_id") String paymentMethodId);


    @GET("payment_methods/installments")
    Call<List<Installment>> getInstallments(@Query("amount") double amount, @Query("payment_method_id") String paymentMethodId, @Query("issuer.id") String issuer_id);


    @GET("payment_methods/installments")
    Call<List<Installment>> getInstallmentsWithoutIssuer(@Query("amount") double amount, @Query("payment_method_id") String paymentMethodId);

}
