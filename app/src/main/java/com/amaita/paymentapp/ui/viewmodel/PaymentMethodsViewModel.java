package com.amaita.paymentapp.ui.viewmodel;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.amaita.paymentapp.data.PaymentRepository;
import com.amaita.paymentapp.data.network.response.PaymentMethod;

import java.util.List;

public class PaymentMethodsViewModel extends ViewModel {

    private LiveData<List<PaymentMethod>> methods;
    private final PaymentRepository mRepository;

    public PaymentMethodsViewModel (PaymentRepository repository) {
        this.mRepository = repository;
        this.methods = repository.getPaymentMethods();
    }

    public LiveData<List<PaymentMethod>> getMethods () {
        return methods;
    }



}
