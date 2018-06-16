package com.amaita.paymentapp.ui.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.amaita.paymentapp.data.PaymentRepository;

public class CardIssuersViewModelFactory  extends ViewModelProvider.NewInstanceFactory {

    private final PaymentRepository mRepository;
    private final String paymentMethodID;

    public CardIssuersViewModelFactory(PaymentRepository repository, String paymentMethodID) {
        this.mRepository = repository;
        this.paymentMethodID = paymentMethodID;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new CardIssuersViewModel(mRepository, paymentMethodID);
    }
}
