package com.amaita.paymentapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
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
    private TextView txt_amount_payment, txt_payment_method, txt_payment_issuer;
    private CardIssuerAdapter mAdapter;

    private CardIssuersViewModel mViewModel;
    private PaymentInConstruction payment;

    private MaterialDialog popup;
    private Button btn_next;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_issuer);

        progressBar =  findViewById(R.id.progressBar);
        txt_amount_payment = findViewById(R.id.txt_amount_payment);
        txt_payment_method = findViewById(R.id.txt_payment_method);
        txt_payment_issuer = findViewById(R.id.txt_payment_issuer);
        btn_next = findViewById(R.id.btn_next);
        btn_next.setEnabled(false);

        double monto = getIntent().getDoubleExtra("amount",0);
        String paymentMethodId = getIntent().getStringExtra("payment_method_id");
        String paymentMethodName = getIntent().getStringExtra("payment_method_name");
        payment = getIntent().getParcelableExtra("payment");
        progressBar.setVisibility(View.VISIBLE);
        //get the viewModel from the factory
        CardIssuersViewModelFactory factory = InjectorUtils.provideCardIssuersViewModelFactory(this, paymentMethodId );
        mViewModel = ViewModelProviders.of(this,factory).get(CardIssuersViewModel.class);
        mViewModel.getIssuers().observe(this, new Observer<List<CardIssuer>>() {
            @Override
            public void onChanged(@Nullable List<CardIssuer> issuers) {
                progressBar.setVisibility(View.GONE);
                if (issuers != null) {
                    if (issuers.size() > 0) {
                        setAdapter(issuers);
                    } else {
                        goToInstallments(null);

                    }
                }

            }
        });

        txt_amount_payment.setText(String.valueOf(payment.getAmount()));
        txt_payment_method.setText(payment.getMethod_name());




    }

    public void setAdapter (final List<CardIssuer> issuers) {
        txt_payment_issuer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GridLayoutManager layoutManager = new GridLayoutManager(CardIssuersActivity.this,1,GridLayoutManager.VERTICAL,false);
                //mostrar popup para seleccionar un payment
                mAdapter = new CardIssuerAdapter(CardIssuersActivity.this, issuers,CardIssuersActivity.this);
                new MaterialDialog.Builder(CardIssuersActivity.this)
                        .title("Select")
                        .adapter(mAdapter, layoutManager)
                        .positiveText("OK")
                        .show();

            }
        });
    }


    public void goToInstallments (View view) {
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
        txt_payment_issuer.setText(item.getName());
        btn_next.setEnabled(true);
    }
}
