package com.amaita.paymentapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amaita.paymentapp.R;
import com.amaita.paymentapp.utils.GlobalCustom;
import com.amaita.paymentapp.utils.PaymentInConstruction;

import java.text.DecimalFormat;

/**
 * Activity AmountActivity permitira obtener el monto a pagar
 */
public class AmountActivity extends AppCompatActivity {

    private EditText txt_amount;
    private Button btn_next;
    private PaymentInConstruction payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount);
        txt_amount = findViewById(R.id.txt_amount);
        btn_next = findViewById(R.id.btn_next);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("paymentInConstruction")) {
                payment = (PaymentInConstruction) savedInstanceState.get(GlobalCustom.PAYMENT_IN_CONSTRUCTION);
            } else {
                payment = new PaymentInConstruction();
            }
        } else {

            payment = new PaymentInConstruction();
        }


        btn_next.setEnabled(false);
        txt_amount.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(11,2)});
        txt_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                btn_next.setEnabled(editable.length() > 0);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        double amount = Double.parseDouble(txt_amount.getText().toString());
        payment.setAmount(amount);
        outState.putParcelable(GlobalCustom.PAYMENT_IN_CONSTRUCTION,payment);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        payment = savedInstanceState.getParcelable("paymentInConstruction");
    }

    public void goToPaymentMethods (View view) {
        double amount = Double.parseDouble(txt_amount.getText().toString());
        payment.setAmount(amount);
        Intent intent = new Intent(AmountActivity.this,PaymentMethodsActivity.class);
        intent.putExtra(GlobalCustom.PAYMENT_IN_CONSTRUCTION, payment);
        startActivity(intent);
    }


}
