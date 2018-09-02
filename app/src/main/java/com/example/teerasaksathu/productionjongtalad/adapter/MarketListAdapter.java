package com.example.teerasaksathu.productionjongtalad.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teerasaksathu.productionjongtalad.R;
import com.squareup.picasso.Picasso;


public class MarketListAdapter extends BaseAdapter {


    private Context ojdContext;
    private int[] marketId;
    private String[] marketName;
    private String[] pictureUrl;
    private String[] marketAddress;


    public MarketListAdapter(Context ojdContext, int[] marketId, String[] marketName, String[] pictureUrl, String[] marketAddress) {
        this.ojdContext = ojdContext;
        this.marketId = marketId;
        this.marketName = marketName;
        this.pictureUrl = pictureUrl;
        this.marketAddress = marketAddress;

    }

    @Override
    public int getCount() {
        return this.marketId.length;
    }

    @Override
    public String getItem(int i) {
        return this.marketName[i];
    }

    @Override
    public long getItemId(int i) {
        return this.marketId[i];
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) ojdContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View ojdView = layoutInflater.inflate(R.layout.card_actvity, viewGroup, false);

        TextView tvNameMarket = ojdView.findViewById(R.id.tvNameMarket);
        tvNameMarket.setText(marketName[i]);

        TextView tvAddress = ojdView.findViewById(R.id.tvAddress);
        tvAddress.setText(marketAddress[i]);

        ImageView imageView = ojdView.findViewById(R.id.imageView);

        try {
            Picasso.with(viewGroup.getContext())
                    .load(pictureUrl[i])
                    .into(imageView);
        } catch (IllegalArgumentException e) {
            Picasso.with(viewGroup.getContext())
                    .load(R.drawable.mock_market)
                    .into(imageView);
        }
        return ojdView;
    }
}
