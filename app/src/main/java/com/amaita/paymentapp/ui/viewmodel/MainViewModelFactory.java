package com.amaita.paymentapp.ui.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.amaita.paymentapp.data.PaymentRepository;

public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final PaymentRepository mRepository;

    public MainViewModelFactory (PaymentRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MainViewModel (mRepository);
    }
}