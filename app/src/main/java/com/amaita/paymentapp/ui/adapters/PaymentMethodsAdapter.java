package com.amaita.paymentapp.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amaita.paymentapp.R;
import com.amaita.paymentapp.data.network.response.PaymentMethod;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PaymentMethodsAdapter extends RecyclerView.Adapter<PaymentMethodsAdapter.PaymentMethodsViewHolder> {

    private static final String TAG = PaymentMethodsAdapter.class.getSimpleName();

    final private ListItemClickListener mOnClickListener;

    private List<PaymentMethod> methods;

    public interface ListItemClickListener  {
        void onListItemClick (int itemPosition);
    }


    public PaymentMethodsAdapter (List<PaymentMethod> methods, ListItemClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
        this.methods = methods;
    }

    @NonNull
    @Override
    public PaymentMethodsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.item_payment_method;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        PaymentMethodsViewHolder viewHolder = new PaymentMethodsViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentMethodsViewHolder holder, int position) {
        PaymentMethod methodItem = methods.get(position);
            holder.bind(methodItem);
    }



    @Override
    public int getItemCount() {
        return methods.size();
    }

    public PaymentMethod getItem (int position) {
        return methods.get(position);
    }

    class PaymentMethodsViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {


        TextView title;
        ImageView thumbnail;

        public PaymentMethodsViewHolder(View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.img_payment_method);
            title = (TextView) itemView.findViewById(R.id.tvt_payment_method_title);
            itemView.setOnClickListener(this);

        }

        void bind(PaymentMethod method) {
            title.setText(method.getName());
            Picasso.get().load(method.getThumbnail()).placeholder(R.drawable.img_noimg).into(thumbnail);
        }

        @Override
        public void onClick(View view) {
            int itemPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(itemPosition);

        }
    }
}
