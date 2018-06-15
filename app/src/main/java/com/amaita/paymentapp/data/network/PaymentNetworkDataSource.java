package com.amaita.paymentapp.data.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.amaita.paymentapp.data.network.response.PaymentMethod;
import com.amaita.paymentapp.utils.APIError;
import com.amaita.paymentapp.utils.ErrorUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentNetworkDataSource {

    private static final String LOG_TAG = PaymentNetworkDataSource.class.getSimpleName();
    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static PaymentNetworkDataSource sInstance;
    private final Context mContext;
    private PaymentWebClient mExecutors;
    private final MutableLiveData<List<PaymentMethod>> mDownloadedMethods;

    private static String STATUS_ACTIVE = "active";
    private static String CREDIT_CARD_TYPE ="credit_card";


    private PaymentNetworkDataSource(Context context, PaymentWebClient executors) {
        mContext = context;
        mExecutors = executors;
        mDownloadedMethods = new MutableLiveData<List<PaymentMethod>>();
    }


    public static PaymentNetworkDataSource getInstance(Context context, PaymentWebClient executors) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new PaymentNetworkDataSource(context.getApplicationContext(), executors);
            }
        }
        return sInstance;
    }

    public void startFetchMethods () {
        mExecutors.getApiService().getPaymentMethods().enqueue(new Callback<List<PaymentMethod>>() {
            @Override
            public void onResponse(Call<List<PaymentMethod>> call, Response<List<PaymentMethod>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<PaymentMethod> methods = response.body();
                        List<PaymentMethod> onlyActiveAndCreditCard = new ArrayList<PaymentMethod>();
                        for (PaymentMethod method : methods) {
                            if (method.getPayment_type_id().equalsIgnoreCase(CREDIT_CARD_TYPE) && method.getStatus().equalsIgnoreCase(STATUS_ACTIVE)) {
                                onlyActiveAndCreditCard.add(method);
                            }
                        }
                            mDownloadedMethods.postValue(onlyActiveAndCreditCard);
                    }
                } else {
                    APIError error = ErrorUtils.parseError(response);
                    Log.d(LOG_TAG, error.message());
                    mDownloadedMethods.postValue(new ArrayList<PaymentMethod>());
                }
            }

            @Override
            public void onFailure(Call<List<PaymentMethod>> call, Throwable t) {
                mDownloadedMethods.postValue(new ArrayList<PaymentMethod>());
            }
        });
    }


    public LiveData<List<PaymentMethod>> getDowloadedMethods() {
        startFetchMethods ();
        return mDownloadedMethods;
    }
}
