package com.amaita.paymentapp.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Resources;
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

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.amaita.paymentapp.R;
import com.amaita.paymentapp.data.network.response.Installment;
import com.amaita.paymentapp.data.network.response.PayerCost;
import com.amaita.paymentapp.ui.adapters.InstallmentAdapter;
import com.amaita.paymentapp.ui.viewmodel.InstallmentsViewModeFactory;
import com.amaita.paymentapp.ui.viewmodel.InstallmentsViewModel;
import com.amaita.paymentapp.utils.AppExecutors;
import com.amaita.paymentapp.utils.GlobalCustom;
import com.amaita.paymentapp.utils.InjectorUtils;
import com.amaita.paymentapp.utils.PaymentInConstruction;

import java.util.List;

public class InstallmentsActivity extends AppCompatActivity  implements  InstallmentAdapter.ListItemClickListener {


    private RecyclerView rv_list;
    private PaymentInConstruction payment;
    private ProgressBar progressBar;
    private Button btn_next;
    private InstallmentAdapter mAdapter;
    private TextView title;

    private InstallmentsViewModel mViewModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("paymentInConstruction")) {
                payment = (PaymentInConstruction) savedInstanceState.get(GlobalCustom.PAYMENT_IN_CONSTRUCTION);
            } else {
                payment = getIntent().getParcelableExtra(GlobalCustom.PAYMENT_IN_CONSTRUCTION);
            }
        } else {
            payment = getIntent().getParcelableExtra(GlobalCustom.PAYMENT_IN_CONSTRUCTION);
        }

        title = findViewById(R.id.title);
        rv_list = findViewById(R.id.rv_list);
        progressBar =  findViewById(R.id.progressBar);
        btn_next = findViewById(R.id.btn_next);

        progressBar.setVisibility(View.VISIBLE);
        title.setText(R.string.installment_explanation);
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


    public void setInstallmentAdapter (List<PayerCost> installments) {
        GridLayoutManager layoutManager = new GridLayoutManager(this,1,GridLayoutManager.VERTICAL,false);
        rv_list.setLayoutManager(layoutManager);
        mAdapter = new InstallmentAdapter(this, installments,this);
        rv_list.setAdapter(mAdapter);
    }

    public void goToNextActivity(View view) {
        progressBar.setVisibility(View.VISIBLE);
        //guardar payment en db y limpiar stack de pantallas y mostrar popup con el total
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                mViewModel.savePayment(payment);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);

                        Resources res = getResources();
                        String text = String.format(res.getString(R.string.payment_created), payment.getMethod_name(), payment.getCard_issuer_name(), payment.getInstallment(), payment.getAmount());
                        new MaterialDialog.Builder(InstallmentsActivity.this)
                                .title("Pago Creado")
                                .content(text)
                                .positiveText("OK")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        Intent intent = new Intent (InstallmentsActivity.this,MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);

                                    }
                                })
                                .show();
                    }
                });
            }
        });


    }

    @Override
    public void onListItemClick(int itemPosition) {
        PayerCost cost = mAdapter.getItem(itemPosition);
        payment.setInstallment(cost.getRecommendedMessage());
        btn_next.setEnabled(true);

    }

}
