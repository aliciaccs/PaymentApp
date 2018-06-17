package com.amaita.paymentapp.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amaita.paymentapp.R;
import com.amaita.paymentapp.data.network.response.CardIssuer;
import com.amaita.paymentapp.data.network.response.PaymentMethod;
import com.amaita.paymentapp.ui.adapters.PaymentMethodsAdapter;
import com.amaita.paymentapp.ui.viewmodel.PaymentMethodsViewModel;
import com.amaita.paymentapp.ui.viewmodel.PaymentMethodsViewModelFactory;
import com.amaita.paymentapp.utils.AppExecutors;
import com.amaita.paymentapp.utils.GlobalCustom;
import com.amaita.paymentapp.utils.InjectorUtils;
import com.amaita.paymentapp.utils.PaymentInConstruction;

import java.util.List;

public class PaymentMethodsActivity extends AppCompatActivity implements  PaymentMethodsAdapter.ListItemClickListener {

    private RecyclerView rv_paymentmethods;
    private PaymentMethodsAdapter mAdapter;
    private TextView title;
    private Button btn_next;
    //view Model
    private PaymentMethodsViewModel mViewModel;

    private ProgressBar progressBar;
    private PaymentInConstruction payment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(GlobalCustom.PAYMENT_IN_CONSTRUCTION)) {
                payment = (PaymentInConstruction) savedInstanceState.get(GlobalCustom.PAYMENT_IN_CONSTRUCTION);
            } else {
                payment = new PaymentInConstruction();
            }
        } else {
            payment = getIntent().getParcelableExtra(GlobalCustom.PAYMENT_IN_CONSTRUCTION);
        }

        rv_paymentmethods =  findViewById(R.id.rv_list);
        progressBar =  findViewById(R.id.progressBar);
        title = findViewById(R.id.title);
        btn_next = findViewById(R.id.btn_next);

        title.setText(R.string.method_explanation);
        progressBar.setVisibility(View.VISIBLE);
        //get the viewModel from the factory
        PaymentMethodsViewModelFactory factory = InjectorUtils.providePaymentMethodsViewModelFactory(this );
        mViewModel = ViewModelProviders.of(this,factory).get(PaymentMethodsViewModel.class);
        mViewModel.getMethods(payment.getAmount()).observe(this, new Observer<List<PaymentMethod>>() {
            @Override
            public void onChanged(@Nullable List<PaymentMethod> methods) {
                progressBar.setVisibility(View.GONE);
                if (methods != null)
                    setMethodsAdapter(methods);
            }
        });
    }

    /**
     * onSaveInstanceState y onRestoreInstanceState se utilizan para no perder
     * la informacion del pago a crear si la aplicacion es cerrada por el SO
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(GlobalCustom.PAYMENT_IN_CONSTRUCTION,payment);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        payment = savedInstanceState.getParcelable(GlobalCustom.PAYMENT_IN_CONSTRUCTION);
    }


    public void setMethodsAdapter (List<PaymentMethod> methods) {
        GridLayoutManager layoutManager = new GridLayoutManager(this,1,GridLayoutManager.VERTICAL,false);
        rv_paymentmethods.setLayoutManager(layoutManager);
        mAdapter = new PaymentMethodsAdapter(this,methods,this);
        rv_paymentmethods.setAdapter(mAdapter);
    }

    public void goToNextActivity (View view) {

        progressBar.setVisibility(View.VISIBLE);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (!payment.getMethod_id().isEmpty()) {
                    final boolean methodHasIssuers = mViewModel.validateIssuers(payment.getMethod_id());
                        AppExecutors.getInstance().mainThread().execute(new Runnable() {
                            @Override
                            public void run() {

                                progressBar.setVisibility(View.GONE);
                                if (methodHasIssuers) {
                                    Intent intent = new Intent(PaymentMethodsActivity.this, CardIssuersActivity.class);
                                    intent.putExtra(GlobalCustom.PAYMENT_IN_CONSTRUCTION, payment);
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(PaymentMethodsActivity.this, InstallmentsActivity.class);
                                    intent.putExtra(GlobalCustom.PAYMENT_IN_CONSTRUCTION, payment);
                                    startActivity(intent);

                                }
                            }
                        });

                }
            }
        });
    }


    @Override
    public void onListItemClick(int itemPosition) {
        PaymentMethod method = mAdapter.getItem(itemPosition);
        payment.setMethod_id(method.getId());
        payment.setMethod_name(method.getName());
        payment.setMethod_url_thumbnail(method.getThumbnail());
        btn_next.setEnabled(true);

    }



}


