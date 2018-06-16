package com.amaita.paymentapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amaita.paymentapp.R;
import com.amaita.paymentapp.data.network.response.PaymentMethod;
import com.amaita.paymentapp.ui.adapters.PaymentMethodsAdapter;
import com.amaita.paymentapp.ui.viewmodel.PaymentMethodsViewModel;
import com.amaita.paymentapp.ui.viewmodel.PaymentMethodsViewModelFactory;
import com.amaita.paymentapp.utils.GlobalCustom;
import com.amaita.paymentapp.utils.InjectorUtils;
import com.amaita.paymentapp.utils.PaymentInConstruction;

import java.util.List;

public class PaymentMethodsActivity extends AppCompatActivity implements  PaymentMethodsAdapter.ListItemClickListener {

    private RecyclerView rv_paymentmethods;
    private PaymentMethodsAdapter mAdapter;
    //view Model
    private PaymentMethodsViewModel mViewModel;
    private ProgressBar progressBar;
    private TextView txt_amount_payment;
    private PaymentInConstruction payment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_methods);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(GlobalCustom.PAYMENT_IN_CONSTRUCTION)) {
                payment = (PaymentInConstruction) savedInstanceState.get(GlobalCustom.PAYMENT_IN_CONSTRUCTION);
            } else {
                payment = new PaymentInConstruction();
            }
        } else {
            payment = getIntent().getParcelableExtra(GlobalCustom.PAYMENT_IN_CONSTRUCTION);
        }


        payment.setAmount(payment.getAmount());

        rv_paymentmethods =  findViewById(R.id.rv_paymentmethods);
        progressBar =  findViewById(R.id.progressBar);
        txt_amount_payment = findViewById(R.id.txt_amount_payment);

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
        txt_amount_payment.setText(String.valueOf (payment.getAmount()));
    }

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
        mAdapter = new PaymentMethodsAdapter(methods,this);
        rv_paymentmethods.setAdapter(mAdapter);
    }


    @Override
    public void onListItemClick(int itemPosition) {
        PaymentMethod method = mAdapter.getItem(itemPosition);
        payment.setMethod_id(method.getId());
        payment.setMethod_name(method.getName());
        payment.setMethod_url_thumbnail(method.getThumbnail());
        Intent intent = new Intent(this,CardIssuersActivity.class);
        intent.putExtra("payment_method_id",method.getId());
        intent.putExtra("payment_method_name",method.getName());
        intent.putExtra("amount",txt_amount_payment.getText());
        intent.putExtra("payment",payment);
        startActivity(intent);
    }
}


