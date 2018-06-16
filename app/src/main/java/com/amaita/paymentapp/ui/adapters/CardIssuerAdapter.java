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
import com.amaita.paymentapp.data.network.response.CardIssuer;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CardIssuerAdapter extends RecyclerView.Adapter<CardIssuerAdapter.CardIssuerViewHolder> {

    private static final String TAG = PaymentMethodsAdapter.class.getSimpleName();

    final private ListItemClickListener mOnClickListener;

    private List<CardIssuer> issuers;

    public Context context;

    private int lastSelected = -1;

    public interface ListItemClickListener  {
        void onListItemClick (int itemPosition);
    }


    public CardIssuerAdapter (Context context, List<CardIssuer> issuers, ListItemClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
        this.issuers = issuers;
        this.context = context;
    }

    @NonNull
    @Override
    public CardIssuerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.item_row;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        CardIssuerViewHolder viewHolder = new CardIssuerViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardIssuerViewHolder holder, int position) {
        CardIssuer cardItem = issuers.get(position);
        holder.bind(cardItem);
        holder.view.setBackgroundColor(lastSelected == position ? ContextCompat.getColor(context, R.color.light_gray) : ContextCompat.getColor(context,R.color.white));

    }



    @Override
    public int getItemCount() {
        return issuers.size();
    }

    public CardIssuer getItem (int position) {
        return issuers.get(position);
    }

    class CardIssuerViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {


        TextView title;
        ImageView thumbnail;
        View view;


        public CardIssuerViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            thumbnail = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            itemView.setOnClickListener(this);

        }

        void bind(CardIssuer issuer) {
            title.setText(issuer.getName());
            Picasso.get().load(issuer.getThumbnail()).placeholder(R.drawable.img_noimg).into(thumbnail);
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