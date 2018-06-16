package com.amaita.paymentapp.utils;

import android.content.Context;

import com.amaita.paymentapp.data.PaymentRepository;
import com.amaita.paymentapp.data.database.PaymentDatabase;
import com.amaita.paymentapp.data.network.PaymentNetworkDataSource;
import com.amaita.paymentapp.data.network.PaymentWebClient;
import com.amaita.paymentapp.ui.viewmodel.CardIssuersViewModelFactory;
import com.amaita.paymentapp.ui.viewmodel.InstallmentsViewModeFactory;
import com.amaita.paymentapp.ui.viewmodel.InstallmentsViewModel;
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

    public static CardIssuersViewModelFactory provideCardIssuersViewModelFactory (Context context, String paymentMethodId) {
        PaymentRepository repository = provideRepository(context.getApplicationContext());
        return new CardIssuersViewModelFactory(repository, paymentMethodId);
    }

    public static InstallmentsViewModeFactory provideInstallmentViewModelFactory (Context context, String paymentMethodId, String cardIssuer, double amount) {
        PaymentRepository repository = provideRepository(context.getApplicationContext());
        return new InstallmentsViewModeFactory(repository, paymentMethodId, cardIssuer,amount);
    }
}
