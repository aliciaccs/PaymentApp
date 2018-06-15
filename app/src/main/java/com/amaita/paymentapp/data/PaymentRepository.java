package com.amaita.paymentapp.data;

import android.arch.lifecycle.LiveData;

import com.amaita.paymentapp.data.database.PaymentDao;
import com.amaita.paymentapp.data.network.PaymentNetworkDataSource;
import com.amaita.paymentapp.data.network.response.PaymentMethod;

import java.util.ArrayList;
import java.util.List;

public class PaymentRepository {

    private LiveData<ArrayList<PaymentMethod>> methods;

    private static final Object LOCK = new Object();

    private static PaymentRepository sInstance;

    private PaymentNetworkDataSource webClient;

    private PaymentDao paymentDao;

    private PaymentRepository (PaymentDao paymentDao, PaymentNetworkDataSource webClient) {
        this.paymentDao = paymentDao;
        this.webClient = webClient;
    }


    public synchronized static PaymentRepository getInstance (PaymentDao paymentDao, PaymentNetworkDataSource webClient) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new PaymentRepository(paymentDao,webClient);
            }
        }
        return sInstance;
    }

    public LiveData<List<PaymentMethod>> getPaymentMethods () {
        LiveData<List<PaymentMethod>> methods = webClient.getDowloadedMethods();
        return methods;
    }

}
