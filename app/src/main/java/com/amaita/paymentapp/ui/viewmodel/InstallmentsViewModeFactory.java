package com.amaita.paymentapp.ui.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.amaita.paymentapp.data.PaymentRepository;

public class InstallmentsViewModeFactory extends ViewModelProvider.NewInstanceFactory {

    private final PaymentRepository mRepository;
    private final String paymentMethodID;
    private final String cardIssuer;
    private final double amount;

    public InstallmentsViewModeFactory(PaymentRepository repository, String paymentMethodID, String cardIssuer, double amount) {
        this.mRepository = repository;
        this.paymentMethodID = paymentMethodID;
        this.cardIssuer = cardIssuer;
        this.amount = amount;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new InstallmentsViewModel(mRepository, paymentMethodID, cardIssuer, amount);
    }

}