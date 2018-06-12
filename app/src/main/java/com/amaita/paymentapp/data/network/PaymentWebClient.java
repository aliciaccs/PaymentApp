package com.amaita.paymentapp.data.network;

import com.amaita.paymentapp.data.network.api.PaymentServiceApi;
import com.amaita.paymentapp.utils.GlobalCustom;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaymentWebClient {

    private PaymentServiceApi apiService;

    public PaymentWebClient () {
        Retrofit retrofit = createRetrofit();
        apiService =  retrofit.create(PaymentServiceApi.class);
    }

    /**
     * Este cliente agregara el apiKey en cada solicitud al servicio
     */
    private OkHttpClient createOkHttpClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        final OkHttpClient.Builder httpClient =
                new OkHttpClient.Builder();
        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                final Request original = chain.request();
                final HttpUrl originalHttpUrl = original.url();

                final HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("public_key", GlobalCustom.API_KEY_WS)
                        .build();

                // Request customization: add request headers
                final Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                final Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        return httpClient.build();
    }

    public PaymentServiceApi getApiService() {
        return apiService;
    }


    private Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(GlobalCustom.BASE_URL_WS)
                .addConverterFactory(GsonConverterFactory.create())
                .client(createOkHttpClient())
                .build();
    }
}
