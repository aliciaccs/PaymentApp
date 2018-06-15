package com.amaita.paymentapp.utils;

import android.content.Context;

import com.amaita.paymentapp.data.PaymentRepository;
import com.amaita.paymentapp.data.database.PaymentDatabase;
import com.amaita.paymentapp.data.network.PaymentNetworkDataSource;
import com.amaita.paymentapp.data.network.PaymentWebClient;
import com.amaita.paymentapp.ui.viewmodel.PaymentMethodsViewModelFactory;

public class InjectorUtils {

    public static PaymentRepository provideRepository(Context context) {
        PaymentDatabase database = PaymentDatabase.getInstance(context.getApplicationContext());
        PaymentWebClient paymentWebClient = PaymentWebClient.getInstance();
        PaymentNetworkDataSource networkDataSource =
                PaymentNetworkDataSource.getInstance(context.getApplicationContext(), paymentWebClient);
        return PaymentRepository.getInstance(database.paymentDao(), networkDataSource);
    }


    public static PaymentMethodsViewModelFactory providePaymentMethodsViewModelFactory(Context context) {
        PaymentRepository repository = provideRepository(context.getApplicationContext());
        return new PaymentMethodsViewModelFactory(repository);
    }
}
