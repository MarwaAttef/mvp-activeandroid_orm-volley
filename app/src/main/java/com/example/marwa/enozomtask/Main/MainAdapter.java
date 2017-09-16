package com.example.marwa.enozomtask.Main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.marwa.enozomtask.R;
import com.example.marwa.enozomtask.data.store.Store;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by marwa on 9/10/17.
 */

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Store> stores = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;
    private ArrayList<Store> arraylist = new ArrayList<>();
    private String TAG = "MainAdapter";


    // data is passed into the constructor
    public MainAdapter(Context context, ArrayList<Store> data) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        arraylist.addAll(data);
        this.stores = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            ((ViewHolder) holder).bindViewOnItem(position);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return stores.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtStoreName;
        private TextView txtStoreDesc;
        private ImageView imgStoreLogo;
        private ProgressBar imageProgress;

        public ViewHolder(View itemView) {
            super(itemView);
            txtStoreName = (TextView) itemView.findViewById(R.id.txt_store_name);
            txtStoreDesc = (TextView) itemView.findViewById(R.id.txt_store_desc);
            imgStoreLogo = (ImageView) itemView.findViewById(R.id.img_store_logo);
            imageProgress = (ProgressBar) itemView.findViewById(R.id.imageProgress);
        }

        void bindViewOnItem(final int position) throws ParseException {
            Store store = stores.get(position);
            txtStoreName.setText(store.getStoreName());
            txtStoreDesc.setText(store.getStoreDescription());

            Log.i(TAG,"bindViewOnItem:StoreLogo "+store.getStoreLogo().replace("http","https"));
            Picasso.with(context).
                    load(store.getStoreLogo().replace("http","https")).resize(350,300)
                    .into(imgStoreLogo, new Callback() {
                        @Override
                        public void onSuccess() {
                            imageProgress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onError() {
                            imageProgress.setVisibility(View.GONE);
                        }
                    });
        }
    }

    //--------------------------------- Filter -------------------------------------//
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        stores.clear();
        if (charText.length() == 0) {
            stores.addAll(arraylist);
        } else {
            for (Store wp : arraylist) {
                if (wp.getStoreName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    stores.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
