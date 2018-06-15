package com.amaita.paymentapp.data.network.api;

import com.amaita.paymentapp.data.network.response.PaymentMethod;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PaymentServiceApi {


    @GET("payment_methods")
    Call<List<PaymentMethod>> getPaymentMethods();


}
