package com.anioncode.spojrzyj.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.anioncode.spojrzyj.Model.Data;
import com.anioncode.spojrzyj.Model.StoreModel;
import com.anioncode.spojrzyj.R;

import java.util.ArrayList;

public class AdapterStore extends RecyclerView.Adapter<AdapterStore.ViewHolder> {

    private ArrayList<StoreModel> mData;
    private LayoutInflater mInflater;
    private Context context;
    public ListenersInterface listener;

    public interface ListenersInterface{
        void itemDelete(StoreModel data);
    }
    // data is passed into the constructor
    public AdapterStore(Context context, ArrayList<StoreModel> data,ListenersInterface listener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
        this.listener = listener;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_store, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StoreModel data = mData.get(position);
        if (data.getTyp()==1){
            holder.show1.setVisibility(View.VISIBLE);
            holder.show2.setVisibility(View.GONE);
            holder.leweOko.setText( context.getString(R.string.lewe_oko)+" : "+data.getLeweOko());
            holder.praweOko.setText( context.getString(R.string.prawe_oko)+" : "+data.getPraweOko());
            holder.typ.setText( context.getString(R.string.typ)+" : "+data.getRodzaj());
            holder.trash0.setOnClickListener(v -> {
                listener.itemDelete(data);
                mData.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            });
        }else if(data.getTyp()==2){

            holder.show1.setVisibility(View.GONE);
            holder.show2.setVisibility(View.VISIBLE);
            holder.size.setText(data.getPojemnosc()+" ml ");
            holder.trash.setOnClickListener(v -> {
                listener.itemDelete(data);
                mData.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());

            });
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout show1;
        RelativeLayout show2;
        TextView leweOko;
        TextView praweOko;
        TextView typ;
        TextView size;
        ImageButton trash0;
        ImageButton trash;


        ViewHolder(View itemView) {
            super(itemView);
            show1 = itemView.findViewById(R.id.show1);

            leweOko = itemView.findViewById(R.id.leweOko);
            praweOko = itemView.findViewById(R.id.praweOko);
            typ = itemView.findViewById(R.id.typ);
            trash0 = itemView.findViewById(R.id.trash0);
            trash = itemView.findViewById(R.id.trash);

            show2 = itemView.findViewById(R.id.show2);
            size = itemView.findViewById(R.id.size);
           // myTextView2 = itemView.findViewById(R.id.date);
        }


    }

    // convenience method for getting data at click position

}