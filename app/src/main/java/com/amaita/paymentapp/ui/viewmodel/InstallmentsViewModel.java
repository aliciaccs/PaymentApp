package com.amaita.paymentapp.ui.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.amaita.paymentapp.data.PaymentRepository;
import com.amaita.paymentapp.data.database.Payment;
import com.amaita.paymentapp.data.network.response.PayerCost;
import com.amaita.paymentapp.utils.PaymentInConstruction;

import java.util.List;

public class InstallmentsViewModel extends ViewModel {

    private LiveData<List<PayerCost>> installments;
    private final PaymentRepository mRepository;
    private final String paymentMethodID;
    private final String issuerID;
    private final double amount;

    public InstallmentsViewModel (PaymentRepository repository, String paymentMethodID, String issuerID, double amount) {
        this.mRepository = repository;
        this.paymentMethodID = paymentMethodID;
        this.issuerID = issuerID;
        this.installments = new MutableLiveData<>();
        this.amount = amount;
    }

    public LiveData<List<PayerCost>> getInstallments () {
        installments = mRepository.getInstalmments(paymentMethodID,issuerID,amount);
        return installments;
    }

    public void savePayment (PaymentInConstruction paymentInConstruction) {
        Payment payment = new Payment(paymentInConstruction.getMethod_name(),paymentInConstruction.getMethod_url_thumbnail(),
                paymentInConstruction.getCard_issuer_id(),paymentInConstruction.getCard_issuer_name(),paymentInConstruction.getCard_issuer_thumbnail(),
                paymentInConstruction.getInstallment(),paymentInConstruction.getAmount());

        mRepository.savePayment(payment);
    }
}
