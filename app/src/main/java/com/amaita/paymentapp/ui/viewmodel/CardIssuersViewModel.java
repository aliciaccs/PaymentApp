package com.amaita.paymentapp.ui.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.amaita.paymentapp.data.PaymentRepository;
import com.amaita.paymentapp.data.network.response.CardIssuer;

import java.util.List;

public class CardIssuersViewModel extends ViewModel {

    private LiveData<List<CardIssuer>> issuers;
    private final PaymentRepository mRepository;
    private final String paymentMethodID;

    public CardIssuersViewModel (PaymentRepository repository, String paymentMethodID) {
        this.mRepository = repository;
        this.paymentMethodID = paymentMethodID;
        this.issuers = mRepository.getIssuers(paymentMethodID);
    }

    public LiveData<List<CardIssuer>> getIssuers () {
        return issuers;
    }

    public void clearIssuers () {
        mRepository.clearIssuer();
    }

}
