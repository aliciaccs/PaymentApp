package com.amaita.paymentapp.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amaita.paymentapp.R;
import com.amaita.paymentapp.data.network.response.PayerCost;

import java.util.List;

public class InstallmentAdapter extends RecyclerView.Adapter<InstallmentAdapter.InstallmentViewHolder> {

    private static final String TAG = InstallmentAdapter.class.getSimpleName();

    final private ListItemClickListener mOnClickListener;

    private List<PayerCost> items;

    public Context context;

    private int lastSelected = -1;

    public interface ListItemClickListener {
        void onListItemClick(int itemPosition);
    }


    public InstallmentAdapter(Context context, List<PayerCost> items, ListItemClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public InstallmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.item_row;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        InstallmentViewHolder viewHolder = new InstallmentViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InstallmentViewHolder holder, int position) {
        PayerCost installment = items.get(position);
        holder.bind(installment);
        holder.view.setBackgroundColor(lastSelected == position ? ContextCompat.getColor(context, R.color.light_gray) : ContextCompat.getColor(context, R.color.white));

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public PayerCost getItem(int position) {
        return items.get(position);
    }

    class InstallmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView title;
        ImageView thumbnail;
        View view;


        public InstallmentViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            thumbnail = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            itemView.setOnClickListener(this);

        }

        void bind(PayerCost installment) {
            title.setText(installment.getRecommendedMessage());
            thumbnail.setVisibility(View.GONE);
        }

        @Override
        public void onClick(View view) {
            int itemPosition = getAdapterPosition();
            lastSelected = itemPosition;
            notifyDataSetChanged();
            mOnClickListener.onListItemClick(itemPosition);

        }
    }
}
