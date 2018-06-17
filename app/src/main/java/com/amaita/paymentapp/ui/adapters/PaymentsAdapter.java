package com.amaita.paymentapp.ui.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amaita.paymentapp.R;
import com.amaita.paymentapp.data.database.Payment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PaymentsAdapter  extends RecyclerView.Adapter<PaymentsAdapter.PaymentsViewHolder> {

    private static final String TAG = PaymentsAdapter.class.getSimpleName();


    private List<Payment> payments;


    private Context context;



    public PaymentsAdapter (Context context, List<Payment> payments) {
        this.payments = payments;
        this.context = context;
    }

    @NonNull
    @Override
    public PaymentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.payment_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        PaymentsViewHolder viewHolder = new PaymentsViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentsViewHolder holder, int position) {
        Payment paymentItem = payments.get(position);
        holder.bind(paymentItem);
    }



    @Override
    public int getItemCount() {
        return payments.size();
    }

    public Payment getItem (int position) {
        return payments.get(position);
    }

    class PaymentsViewHolder extends RecyclerView.ViewHolder  {


        TextView method_title;
        ImageView method_thumbnail;
        TextView issuer_title;
        ImageView issuer_thumbnail;
        TextView tvt_installment;
        TextView total;

        public PaymentsViewHolder(View itemView) {
            super(itemView);
            method_thumbnail = (ImageView) itemView.findViewById(R.id.img_method);
            method_title = (TextView) itemView.findViewById(R.id.tvt_method_name);
            issuer_thumbnail = (ImageView) itemView.findViewById(R.id.img_issuer);
            issuer_title = (TextView) itemView.findViewById(R.id.tvt_issuer_name);
            tvt_installment = (TextView) itemView.findViewById(R.id.tvt_installment);
            total = (TextView) itemView.findViewById(R.id.total);


        }

        void bind(Payment payment) {
            Resources res = context.getResources();
            String text = String.format(res.getString(R.string.total_value),  payment.getAmount());
            total.setText(text);
            method_title.setText(payment.getMethod_name());
            Picasso.get().load(payment.getMethod_url_thumbnail()).placeholder(R.drawable.img_noimg).into(method_thumbnail);
            if (payment.getCard_issuer_id() != null) {
                issuer_thumbnail.setVisibility(View.VISIBLE);
                issuer_title.setVisibility(View.VISIBLE);
                issuer_title.setText(payment.getCard_issuer_name());
                Picasso.get().load(payment.getCard_issuer_thumbnail()).placeholder(R.drawable.img_noimg).into(issuer_thumbnail);
            } else {
                issuer_thumbnail.setVisibility(View.GONE);
                issuer_title.setVisibility(View.GONE);
            }
            tvt_installment.setText(payment.getInstallment());
        }

    }
}
