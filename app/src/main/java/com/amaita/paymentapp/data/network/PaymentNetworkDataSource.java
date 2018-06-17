package com.amaita.paymentapp.data.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.amaita.paymentapp.data.network.response.CardIssuer;
import com.amaita.paymentapp.data.network.response.Installment;
import com.amaita.paymentapp.data.network.response.PayerCost;
import com.amaita.paymentapp.data.network.response.PaymentMethod;
import com.amaita.paymentapp.utils.APIError;
import com.amaita.paymentapp.utils.ErrorUtils;

import java.io.IOException;
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

    private static String STATUS_ACTIVE = "active";
    private static String CREDIT_CARD_TYPE ="credit_card";


    /** datos que deben descargarse del servidor **/
    private final MutableLiveData<List<PaymentMethod>> mDownloadedMethods;
    private final MutableLiveData<List<CardIssuer>> mDownloadedCardIssuers;
    private final MutableLiveData<List<PayerCost>> mDownloadedInstallments;


    private PaymentNetworkDataSource(Context context, PaymentWebClient executors) {
        mContext = context;
        mExecutors = executors;
        mDownloadedMethods = new MutableLiveData<List<PaymentMethod>>();
        mDownloadedCardIssuers = new MutableLiveData<List<CardIssuer>>();
        mDownloadedInstallments = new MutableLiveData<>();
    }


    public static PaymentNetworkDataSource getInstance(Context context, PaymentWebClient executors) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new PaymentNetworkDataSource(context.getApplicationContext(), executors);
            }
        }
        return sInstance;
    }

    /**
     * Descarga del servidor todos los metodos de pago para luego validara que sean tipo credit_card y
     * esten en status activo y que el monto introducido sea permitido
     */
    public void startFetchMethods (final double amount) {
        mExecutors.getApiService().getPaymentMethods().enqueue(new Callback<List<PaymentMethod>>() {
            @Override
            public void onResponse(Call<List<PaymentMethod>> call, Response<List<PaymentMethod>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<PaymentMethod> methods = response.body();
                        List<PaymentMethod> onlyActiveAndCreditCard = new ArrayList<PaymentMethod>();
                        for (PaymentMethod method : methods) {
                            if (method.getPayment_type_id().equalsIgnoreCase(CREDIT_CARD_TYPE) && method.getStatus().equalsIgnoreCase(STATUS_ACTIVE)
                                    && method.getMax_allowed_amount() >= amount && method.getMin_allowed_amount() <= amount) {
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

    public void startFetchCardsIssuers (String paymentMethodId) {
        mExecutors.getApiService().getCardIssuers(paymentMethodId).enqueue(new Callback<List<CardIssuer>>() {
            @Override
            public void onResponse(Call<List<CardIssuer>> call, Response<List<CardIssuer>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mDownloadedCardIssuers.postValue(response.body());
                    }
                } else {
                    APIError error = ErrorUtils.parseError(response);
                    Log.d(LOG_TAG, error.message());
                    mDownloadedCardIssuers.postValue(new ArrayList<CardIssuer>());
                }
            }

            @Override
            public void onFailure(Call<List<CardIssuer>> call, Throwable t) {
                mDownloadedCardIssuers.postValue(new ArrayList<CardIssuer>());

            }
        });
    }

    public void startFetchInstallments (String paymentMethodId, String cardIssuer, double amount) {
        Callback<List<Installment>> callback = new Callback<List<Installment>>() {
            @Override
            public void onResponse(Call<List<Installment>> call, Response<List<Installment>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<Installment> body = response.body();
                        if (body.size() == 1)
                        mDownloadedInstallments.postValue(body.get(0).getPayerCost());
                    }
                } else {
                    APIError error = ErrorUtils.parseError(response);
                    Log.d(LOG_TAG, error.message());
                    mDownloadedCardIssuers.postValue(new ArrayList<CardIssuer>());
                }
            }

            @Override
            public void onFailure(Call<List<Installment>> call, Throwable t) {

                Log.d(LOG_TAG, t.getMessage());
            }
        };
        if (cardIssuer == null) {
            mExecutors.getApiService().getInstallmentsWithoutIssuer(amount,paymentMethodId).enqueue(callback);
        } else {
            mExecutors.getApiService().getInstallments(amount,paymentMethodId,cardIssuer).enqueue(callback);
        }

    }

    public LiveData<List<CardIssuer>> getDowloadedCardIssuers(String paymentMethodID) {
        startFetchCardsIssuers (paymentMethodID);
        return mDownloadedCardIssuers;
    }

    public LiveData<List<PaymentMethod>> getDowloadedMethods(double amount) {
        startFetchMethods (amount);
        return mDownloadedMethods;
    }

    public LiveData<List<PayerCost>> getDowloadedInstallments(String paymentMethodId, String cardIssuer, double amount) {
        startFetchInstallments (paymentMethodId, cardIssuer, amount);
        return mDownloadedInstallments;
    }

    public void  clearIssuer () {
        mDownloadedInstallments.postValue(new ArrayList<PayerCost>());
        mDownloadedCardIssuers.postValue(new ArrayList<CardIssuer>());
    }

    public List<CardIssuer> getIssuers (String paymentMethodId) {
        List<CardIssuer> issuers = null;
        try {
        Call<List<CardIssuer>> call = mExecutors.getApiService().getCardIssuers(paymentMethodId);
        issuers = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return issuers;
    }
}
