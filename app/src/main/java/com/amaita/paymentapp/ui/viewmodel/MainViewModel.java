package com.amaita.paymentapp.ui.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.amaita.paymentapp.data.PaymentRepository;
import com.amaita.paymentapp.data.database.Payment;

import java.util.List;

public class MainViewModel extends ViewModel {

    private LiveData<List<Payment>> payments;
    private final PaymentRepository mRepository;

    public MainViewModel (PaymentRepository repository) {
        this.mRepository = repository;
        this.payments = repository.getPayments();
    }

    public LiveData<List<Payment>> getPayments () {
        return payments;
    }


}

