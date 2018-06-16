package com.amaita.paymentapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.amaita.paymentapp.R;
import com.amaita.paymentapp.data.network.response.PayerCost;
import com.amaita.paymentapp.ui.adapters.InstallmentAdapter;
import com.amaita.paymentapp.ui.viewmodel.InstallmentsViewModeFactory;
import com.amaita.paymentapp.ui.viewmodel.InstallmentsViewModel;
import com.amaita.paymentapp.utils.GlobalCustom;
import com.amaita.paymentapp.utils.InjectorUtils;
import com.amaita.paymentapp.utils.PaymentInConstruction;

import java.util.List;

public class InstallmentsActivity extends AppCompatActivity  implements  InstallmentAdapter.ListItemClickListener {


    private RecyclerView rv_list;
    private PaymentInConstruction payment;
    private ProgressBar progressBar;
    private InstallmentAdapter mAdapter;

    private InstallmentsViewModel mViewModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("paymentInConstruction")) {
                payment = (PaymentInConstruction) savedInstanceState.get("paymentInConstruction");
            } else {
                payment = new PaymentInConstruction();
            }
        } else {
            payment = getIntent().getParcelableExtra(GlobalCustom.PAYMENT_IN_CONSTRUCTION);
        }


        rv_list = findViewById(R.id.rv_list);
        progressBar =  findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        //get the viewModel from the factory
        InstallmentsViewModeFactory factory = InjectorUtils.provideInstallmentViewModelFactory(this,payment.getMethod_id(),payment.getCard_issuer_id(),payment.getAmount());
        mViewModel = ViewModelProviders.of(this,factory).get(InstallmentsViewModel.class);
        mViewModel.getInstallments().observe(this, new Observer<List<PayerCost>>() {
            @Override
            public void onChanged(@Nullable List<PayerCost> installments) {
                progressBar.setVisibility(View.GONE);
                if (installments != null)
                    setInstallmentAdapter(installments);
            }
        });
    }


    public void setInstallmentAdapter (List<PayerCost> installments) {
        GridLayoutManager layoutManager = new GridLayoutManager(this,1,GridLayoutManager.VERTICAL,false);
        rv_list.setLayoutManager(layoutManager);
        mAdapter = new InstallmentAdapter(this, installments,this);
        rv_list.setAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(int itemPosition) {

    }

}
