package com.example.teerasaksathu.productionjongtalad.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.teerasaksathu.productionjongtalad.R;
import com.example.teerasaksathu.productionjongtalad.activity.LockReservationActivity;
import com.example.teerasaksathu.productionjongtalad.activity.MainActivity;
import com.example.teerasaksathu.productionjongtalad.adapter.MarketListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MarketListFragment extends Fragment {
    ListView marketList;
    int marketAdminId;
    ProgressDialog loadingMarketListDialog;

    public MarketListFragment() {
        super();
    }

    public static MarketListFragment newInstance() {
        MarketListFragment fragment = new MarketListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maketlist, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here

        marketAdminId = MainActivity.intent.getIntExtra("marketAdminId", 0);

        LoadMerketList loadMarketList = new LoadMerketList();
        loadMarketList.execute(String.valueOf(marketAdminId));

        marketList = rootView.findViewById(R.id.marketList);
        marketList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), LockReservationActivity.class);
                intent.putExtra("marketAdminId", marketAdminId);
                intent.putExtra("marketId", (int) l);
                intent.putExtra("marketName", "" + adapterView.getItemAtPosition(i));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }

    private class LoadMerketList extends AsyncTask<String, Void, String> {
        public static final String URL = "https://jongtalad-web-api.herokuapp.com/marketadmins/";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingMarketListDialog =  ProgressDialog.show(getContext(), "Fetch market list", "Loading...", true, false);
        }

        @Override
        protected String doInBackground(String... values) {
            String newUrl = URL + values[0] + "/markets";
            Log.d("marketAdminGetMarkets", "=>" + newUrl);
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(newUrl)
                    .get()
                    .build();

            try {
                Response response = okHttpClient.newCall(request).execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                } else {
                    return "Not Success - code : " + response.code();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Error - " + e.getMessage();
            }
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            int[] marketId = {0};
            String[] marketName = {"None"};
            String[] pictureUrl = {"None"};
            String[] marketAddress = {"None"};

            try {
                JSONObject obj = new JSONObject(s);
                JSONArray marketList = obj.getJSONArray("response");
                marketId = new int[marketList.length()];
                marketName = new String[marketList.length()];
                pictureUrl = new String[marketList.length()];
                marketAddress = new String[marketList.length()];
                for (int i = 0; i < marketList.length(); i++) {
                    marketId[i] = marketList.getJSONObject(i).getInt("marketId");
                    marketName[i] = marketList.getJSONObject(i).getString("marketName");
                    pictureUrl[i] = marketList.getJSONObject(i).getString("pictureUrl");
                    marketAddress[i] = marketList.getJSONObject(i).getString("marketAddress");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MarketListAdapter marketListAdapter = new MarketListAdapter(getActivity(), marketId, marketName, pictureUrl, marketAddress);
            marketList.setAdapter(marketListAdapter);

            loadingMarketListDialog.dismiss();
        }
    }
}
