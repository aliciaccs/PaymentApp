package com.amaita.paymentapp.data;

import android.arch.lifecycle.LiveData;

import com.amaita.paymentapp.data.database.Payment;
import com.amaita.paymentapp.data.database.PaymentDao;
import com.amaita.paymentapp.data.network.PaymentNetworkDataSource;
import com.amaita.paymentapp.data.network.response.CardIssuer;
import com.amaita.paymentapp.data.network.response.PayerCost;
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

    public LiveData<List<PaymentMethod>> getPaymentMethods (double amount) {
        LiveData<List<PaymentMethod>> methods = webClient.getDowloadedMethods(amount);
        return methods;
    }

    public LiveData<List<CardIssuer>> getIssuers (String paymentMethodID) {
        LiveData<List<CardIssuer>> issuers = webClient.getDowloadedCardIssuers(paymentMethodID);
        return issuers;
    }

    public LiveData<List<PayerCost>> getInstalmments (String paymentMethodID, String cardIssuer, double amount) {
        LiveData<List<PayerCost>> installments = webClient.getDowloadedInstallments(paymentMethodID,cardIssuer,amount);
        return installments;
    }

    public void clearIssuer () {
        webClient.clearIssuer();
    }

    public void clearMethods () {
        webClient.clearMethods();
    }

    public List<CardIssuer> getIssuersForValidation (String paymentMethodID) {
        return webClient.getIssuers(paymentMethodID);
    }

    public LiveData<List<Payment>> getPayments () {
        return paymentDao.getPayments();
    }

    public void savePayment (Payment payment) {
        paymentDao.bulkInsert(payment);
    }
}
