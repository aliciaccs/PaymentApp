package com.amaita.paymentapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.amaita.paymentapp.ui.adapters.CardIssuerAdapter;
import com.amaita.paymentapp.ui.viewmodel.CardIssuersViewModel;
import com.amaita.paymentapp.ui.viewmodel.CardIssuersViewModelFactory;
import com.amaita.paymentapp.utils.GlobalCustom;
import com.amaita.paymentapp.utils.InjectorUtils;
import com.amaita.paymentapp.utils.PaymentInConstruction;

import java.util.List;

public class CardIssuersActivity extends AppCompatActivity implements CardIssuerAdapter.ListItemClickListener {

    private ProgressBar progressBar;
    private TextView title;
    private CardIssuerAdapter mAdapter;

    private CardIssuersViewModel mViewModel;
    private PaymentInConstruction payment;

    private RecyclerView rv_issuers;
    private Button btn_next;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        progressBar =  findViewById(R.id.progressBar);
        rv_issuers =  findViewById(R.id.rv_list);
        title = findViewById(R.id.title);
        btn_next = findViewById(R.id.btn_next);
        btn_next.setEnabled(false);
        title.setText(R.string.issuer_explanation);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(GlobalCustom.PAYMENT_IN_CONSTRUCTION)) {
                payment = (PaymentInConstruction) savedInstanceState.get(GlobalCustom.PAYMENT_IN_CONSTRUCTION);
            } else {
                payment = new PaymentInConstruction();
            }
        } else {
            payment = getIntent().getParcelableExtra(GlobalCustom.PAYMENT_IN_CONSTRUCTION);
        }

        //get the viewModel from the factory
        CardIssuersViewModelFactory factory = InjectorUtils.provideCardIssuersViewModelFactory(this, payment.getMethod_id() );
        mViewModel = ViewModelProviders.of(this,factory).get(CardIssuersViewModel.class);
        mViewModel.getIssuers().observe(this, new Observer<List<CardIssuer>>() {
            @Override
            public void onChanged(@Nullable List<CardIssuer> issuers) {
                progressBar.setVisibility(View.GONE);
                if (issuers != null) {
                        setAdapter(issuers);
                }

            }
        });

        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mViewModel.clearIssuers();
    }

    public void setAdapter (final List<CardIssuer> issuers) {
        GridLayoutManager layoutManager = new GridLayoutManager(this,1,GridLayoutManager.VERTICAL,false);
        rv_issuers.setLayoutManager(layoutManager);
        mAdapter = new CardIssuerAdapter(this, issuers,this);
        rv_issuers.setAdapter(mAdapter);
    }


    public void goToNextActivity (View view) {
        Intent intent = new Intent(CardIssuersActivity.this,InstallmentsActivity.class);
        intent.putExtra(GlobalCustom.PAYMENT_IN_CONSTRUCTION,payment);
        startActivity(intent);

    }

    @Override
    public void onListItemClick(int itemPosition) {
        CardIssuer item = mAdapter.getItem(itemPosition);
        payment.setCard_issuer_id(item.getId());
        payment.setCard_issuer_name(item.getName());
        payment.setCard_issuer_thumbnail(item.getThumbnail());
        btn_next.setEnabled(true);
    }
}
