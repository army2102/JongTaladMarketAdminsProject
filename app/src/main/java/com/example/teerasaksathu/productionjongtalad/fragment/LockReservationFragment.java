package com.example.teerasaksathu.productionjongtalad.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teerasaksathu.productionjongtalad.R;
import com.example.teerasaksathu.productionjongtalad.activity.LockReservationActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LockReservationFragment extends Fragment implements View.OnClickListener {

    private Button btnReserve;
    private EditText etName;
    private EditText etPhonenumber;
    private Spinner spProductType;
    private Spinner spLock;

    private ImageView ivRefresh;

    private ProgressDialog loadingMarketData;
    private ProgressDialog loadingMarketLockDetail;
    private ProgressDialog loadingRefreshMarketLock;

    // To use with spinner
    private int[] marketLockId = {0};
    private String[] marketLockName = {"none"};
    private int[] productTypeId = {0};
    private String[] productTypeName = {"none"};

    // To use with cancel lock
    private int cancelMarketLockId;


    private int marketAdminId;
    private int marketId;

    private final int price = 100;
    private String timeDate;

    private String getMarketName;
    private TextView tvMarketName;

    private TextView tvA1;
    private TextView tvA2;
    private TextView tvA3;
    private TextView tvB1;
    private TextView tvB2;
    private TextView tvB3;
    private TextView tvC1;
    private TextView tvC2;
    private TextView tvC3;
    private TextView tvC4;
    private TextView tvC5;
    private TextView tvD1;
    private TextView tvD2;
    private TextView tvD3;
    private TextView tvD4;
    private TextView tvD5;
    private TextView tvD6;
    private TextView tvD7;
    private TextView tvD8;
    private TextView tvD9;
    private TextView tvDate;


    public LockReservationFragment() {
        super();
    }

    public static LockReservationFragment newInstance() {
        LockReservationFragment fragment = new LockReservationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lock_reservation, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        timeDate = String.valueOf(timeDate());
        marketAdminId = LockReservationActivity.intent.getIntExtra("marketAdminId", 0);
        marketId = LockReservationActivity.intent.getIntExtra("marketId", 0);
        getMarketName = LockReservationActivity.intent.getStringExtra("marketName");

        etName = rootView.findViewById(R.id.etName);
        etPhonenumber = rootView.findViewById(R.id.etPhonenumber);
        btnReserve = rootView.findViewById(R.id.btnReserve);
        spProductType = rootView.findViewById(R.id.spProductType);
        spLock = rootView.findViewById(R.id.spLock);
        tvDate = rootView.findViewById(R.id.date);
        tvDate.setText(timeDate);
        tvMarketName = rootView.findViewById(R.id.tvMarketName);
        tvMarketName.setText(getMarketName);
        ivRefresh = rootView.findViewById(R.id.ivRefresh);
        ivRefresh.setOnClickListener(this);
        tvA1 = rootView.findViewById(R.id.tvA1);
        tvA2 = rootView.findViewById(R.id.tvA2);
        tvA3 = rootView.findViewById(R.id.tvA3);
        tvB1 = rootView.findViewById(R.id.tvB1);
        tvB2 = rootView.findViewById(R.id.tvB2);
        tvB3 = rootView.findViewById(R.id.tvB3);
        tvC1 = rootView.findViewById(R.id.tvC1);
        tvC2 = rootView.findViewById(R.id.tvC2);
        tvC3 = rootView.findViewById(R.id.tvC3);
        tvC4 = rootView.findViewById(R.id.tvC4);
        tvC5 = rootView.findViewById(R.id.tvC5);
        tvD1 = rootView.findViewById(R.id.tvD1);
        tvD2 = rootView.findViewById(R.id.tvD2);
        tvD3 = rootView.findViewById(R.id.tvD3);
        tvD4 = rootView.findViewById(R.id.tvD4);
        tvD5 = rootView.findViewById(R.id.tvD5);
        tvD6 = rootView.findViewById(R.id.tvD6);
        tvD7 = rootView.findViewById(R.id.tvD7);
        tvD8 = rootView.findViewById(R.id.tvD8);
        tvD9 = rootView.findViewById(R.id.tvD9);

        tvA1.setOnClickListener(this);
        tvA2.setOnClickListener(this);
        tvA3.setOnClickListener(this);
        tvB1.setOnClickListener(this);
        tvB2.setOnClickListener(this);
        tvB3.setOnClickListener(this);
        tvC1.setOnClickListener(this);
        tvC2.setOnClickListener(this);
        tvC3.setOnClickListener(this);
        tvC4.setOnClickListener(this);
        tvC5.setOnClickListener(this);
        tvD1.setOnClickListener(this);
        tvD2.setOnClickListener(this);
        tvD3.setOnClickListener(this);
        tvD4.setOnClickListener(this);
        tvD5.setOnClickListener(this);
        tvD6.setOnClickListener(this);
        tvD7.setOnClickListener(this);
        tvD8.setOnClickListener(this);
        tvD9.setOnClickListener(this);
        btnReserve.setOnClickListener(this);

        setDataToTV setDataToTV = new setDataToTV();
        setDataToTV.execute();

        loadLockname loadLockname = new loadLockname();
        loadLockname.execute();

        loadProductType loadProductType = new loadProductType();
        loadProductType.execute();
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

    @Override
    public void onClick(View view) {
        if (view == btnReserve) {
            if (valuesChecking()) {
                String marketAdminMerchantName = etName.getText().toString().trim();
                String marketAdminMerchantPhonenumber = etPhonenumber.getText().toString().trim();
                String productTypeName = spProductType.getSelectedItem().toString().trim();
                String marketLockName = spLock.getSelectedItem().toString().trim();

                int getProductTypeId = productTypeId[find(this.productTypeName, productTypeName)];
                int getMarketLockId = marketLockId[find(this.marketLockName, marketLockName)];
                if (marketLockName.equals("None")) {
                    Toast.makeText(getActivity(), "Please select lock", Toast.LENGTH_LONG).show();
                } else {
                    ReserveLock reserveLock = new ReserveLock();
                    reserveLock.execute(String.valueOf(marketAdminId),
                            String.valueOf(getMarketLockId),
                            marketAdminMerchantName,
                            marketAdminMerchantPhonenumber,
                            String.valueOf(getProductTypeId),
                            String.valueOf(price),
                            timeDate);

                    loadLockname loadLockname = new loadLockname();
                    loadLockname.execute();
                }
            }
        } else if (view == ivRefresh) {
            RefreshMarketLock refreshMarketLock = new RefreshMarketLock();
            refreshMarketLock.execute();
        } else if (view == tvA1) {
            showLockStatus(1);
        } else if (view == tvA2) {
            showLockStatus(2);
        } else if (view == tvA3) {
            showLockStatus(3);
        } else if (view == tvB1) {
            showLockStatus(4);
        } else if (view == tvB2) {
            showLockStatus(5);
        } else if (view == tvB3) {
            showLockStatus(6);
        } else if (view == tvC1) {
            showLockStatus(7);
        } else if (view == tvC2) {
            showLockStatus(8);
        } else if (view == tvC3) {
            showLockStatus(9);
        } else if (view == tvC4) {
            showLockStatus(10);
        } else if (view == tvC5) {
            showLockStatus(11);
        } else if (view == tvD1) {
            showLockStatus(12);
        } else if (view == tvD2) {
            showLockStatus(13);
        } else if (view == tvD3) {
            showLockStatus(14);
        } else if (view == tvD4) {
            showLockStatus(15);
        } else if (view == tvD5) {
            showLockStatus(16);
        } else if (view == tvD6) {
            showLockStatus(17);
        } else if (view == tvD7) {
            showLockStatus(18);
        } else if (view == tvD8) {
            showLockStatus(19);
        } else if (view == tvD9) {
            showLockStatus(20);
        }
    }

    private void setLockStatus(int marketLockId, Integer colorID) {
        switch (marketLockId) {
            case 1:
                tvA1.setBackgroundResource(colorID);
                tvA1.setClickable(true);
                break;
            case 2:
                tvA2.setBackgroundResource(colorID);
                tvA2.setClickable(true);
                break;
            case 3:
                tvA3.setBackgroundResource(colorID);
                tvA3.setClickable(true);
                break;
            case 4:
                tvB1.setBackgroundResource(colorID);
                tvB1.setClickable(true);
                break;
            case 5:
                tvB2.setBackgroundResource(colorID);
                tvB2.setClickable(true);
                break;
            case 6:
                tvB3.setBackgroundResource(colorID);
                tvB3.setClickable(true);
                break;
            case 7:
                tvC1.setBackgroundResource(colorID);
                tvC1.setClickable(true);
                break;
            case 8:
                tvC2.setBackgroundResource(colorID);
                tvC2.setClickable(true);
                break;
            case 9:
                tvC3.setBackgroundResource(colorID);
                tvC3.setClickable(true);
                break;
            case 10:
                tvC4.setBackgroundResource(colorID);
                tvC4.setClickable(true);
                break;
            case 11:
                tvC5.setBackgroundResource(colorID);
                tvC5.setClickable(true);
                break;
            case 12:
                tvD1.setBackgroundResource(colorID);
                tvD1.setClickable(true);
                break;
            case 13:
                tvD2.setBackgroundResource(colorID);
                tvD2.setClickable(true);
                break;
            case 14:
                tvD3.setBackgroundResource(colorID);
                tvD3.setClickable(true);
                break;
            case 15:
                tvD4.setBackgroundResource(colorID);
                tvD4.setClickable(true);
                break;
            case 16:
                tvD5.setBackgroundResource(colorID);
                tvD5.setClickable(true);
                break;
            case 17:
                tvD6.setBackgroundResource(colorID);
                tvD6.setClickable(true);
                break;
            case 18:
                tvD7.setBackgroundResource(colorID);
                tvD7.setClickable(true);
                break;
            case 19:
                tvD8.setBackgroundResource(colorID);
                tvD8.setClickable(true);
                break;
            case 20:
                tvD9.setBackgroundResource(colorID);
                tvD9.setClickable(true);
                break;
        }
    }

    private boolean valuesChecking() {
        int count = 0;
        if (etName.getText().toString().trim().equals("")) {
            count++;
        }
//        if (etSurname.getText().toString().trim().equals("")) {
//            count++;
//        }

        if (etPhonenumber.getText().length() < 10) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("กรุณากรอกเบอร์โทรศัพท์ให้ครบ 10 หลัก")
                    .setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
            Dialog dialog = builder.create();
            dialog.show();
            return false;
        }
        if (count > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("โปรดกรอกข้อมูลให้ครบทุกช่อง")
                    .setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
            Dialog dialog = builder.create();
            dialog.show();
            return false;
        } else {
            return true;
        }
    }

    private void showLockStatus(int marketLockId) {
        loadLockInformation lockInformation = new loadLockInformation();
        lockInformation.execute(String.valueOf(marketLockId));
    }

    private class ReserveLock extends AsyncTask<String, Void, String> {
        public static final String URL = "https://jongtalad-web-api.herokuapp.com/marketadmins/";

        @Override
        protected String doInBackground(String... values) {
            String newUrl = URL + values[0] + "/markets/locks/" + values[1] + "/reserve";

            Log.d("debugnaenaenae", "doInBackground: " + newUrl);
            OkHttpClient okHttpClient = new OkHttpClient();

            RequestBody requestBody = new FormBody.Builder()
                    .add("marketAdminMerchantName", values[2])
                    .add("marketAdminMerchantPhonenumber", values[3])
                    .add("productTypeId", values[4])
                    .add("price", values[5])
                    .add("saleDate", values[6])
                    .build();

            Request request = new Request.Builder()
                    .url(newUrl)
                    .post(requestBody)
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
            int resCode;
            int marketLockId;
            try {
                JSONObject obj = new JSONObject(s);
                resCode = obj.getInt("code");
                JSONObject res = obj.getJSONObject("response");
                marketLockId = res.getInt("reservedMarketLockId");
            } catch (JSONException e) {
                resCode = 0;
                marketLockId = 0;
                e.printStackTrace();
            }
            if (resCode == 200) {
                Toast.makeText(getActivity(), "Reserve Success", Toast.LENGTH_SHORT).show();
                etName.setText("");
                etPhonenumber.setText("");
                setLockStatus(marketLockId, R.color.lockStatusOuccupied);
            } else {
                Toast.makeText(getActivity(), "Reserve Failed", Toast.LENGTH_LONG).show();

            }
        }
    }

    private class setDataToTV extends AsyncTask<Void, Void, String> {

        private static final String URL = "https://jongtalad-web-api.herokuapp.com/markets/";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingMarketData = ProgressDialog.show(getContext(), "Fetch market data", "Loading...", true, false);
            setMarketLockClickable(false);
        }

        @Override
        protected String doInBackground(Void... voids) {
            String newUrl = URL + marketId + "/locks/types/" + 1 + "/?saleDate=" + timeDate;

            OkHttpClient okHttpClient = new OkHttpClient();
            Request.Builder builder = new Request.Builder();
            Request request = builder
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
            int resCode = 404;
            int[] marketLockId = {0};
            try {
                JSONObject obj = new JSONObject(s);
                resCode = obj.getInt("code");
                JSONArray marketLockList = obj.getJSONArray("response");
                marketLockId = new int[marketLockList.length()];
                for (int i = 0; i < marketLockList.length(); i++) {
                    marketLockId[i] = marketLockList.getJSONObject(i).getInt("marketLockId");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            if (resCode == 200) {
                for (int i = 0; i < marketLockId.length; i++) {
                    setLockStatus(marketLockId[i], R.color.lockStatusOuccupied);
                }
            }
        }
    }

    private class RefreshMarketLock extends AsyncTask<Void, Void, String> {

        private static final String URL = "https://jongtalad-web-api.herokuapp.com/markets/";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingRefreshMarketLock = ProgressDialog.show(getContext(), "Fetch market lock", "Loading...", true, false);
            for (int i = 0; i < 20; i++) {
                setLockStatus(i, R.color.lockStatausNotOuccupied);
            }
            setMarketLockClickable(false);
            loadLockname loadLockname = new loadLockname();
            loadLockname.execute();
        }


        @Override
        protected String doInBackground(Void... voids) {
            String newUrl = URL + marketId + "/locks/types/" + 1 + "/?saleDate=" + timeDate;

            OkHttpClient okHttpClient = new OkHttpClient();
            Request.Builder builder = new Request.Builder();
            Request request = builder
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
            int resCode = 404;
            int[] marketLockId = {0};
            try {
                JSONObject obj = new JSONObject(s);
                resCode = obj.getInt("code");
                JSONArray marketLockList = obj.getJSONArray("response");
                marketLockId = new int[marketLockList.length()];
                for (int i = 0; i < marketLockList.length(); i++) {
                    marketLockId[i] = marketLockList.getJSONObject(i).getInt("marketLockId");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (resCode == 200) {
                for (int i = 0; i < marketLockId.length; i++) {
                    setLockStatus(marketLockId[i], R.color.lockStatusOuccupied);
                }
            }

            loadingRefreshMarketLock.dismiss();
        }
    }

    private class loadLockname extends AsyncTask<Void, Void, String> {
        public static final String URL = "https://jongtalad-web-api.herokuapp.com/markets/";

        @Override
        protected String doInBackground(Void... voids) {
            String newUrl = URL + marketId + "/locks/types/" + 0 + "/?saleDate=" + timeDate;
            Log.d("marketLockName", "doInBackground: " + newUrl);

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
            int resCode;

            try {
                JSONObject obj = new JSONObject(s);
                resCode = obj.getInt("code");
                JSONArray marketLockList = obj.getJSONArray("response");
                marketLockId = new int[marketLockList.length()];
                marketLockName = new String[marketLockList.length()];
                for (int i = 0; i < marketLockList.length(); i++) {
                    marketLockId[i] = (marketLockList.getJSONObject(i).getInt("marketLockId"));
                    marketLockName[i] = (marketLockList.getJSONObject(i).getString("marketLockName"));
                }
            } catch (JSONException e) {
                resCode = 404;
                marketLockId = new int[1];
                marketLockId[0] = 0;

                marketLockName = new String[1];
                marketLockName[0] = "None";

                e.printStackTrace();
            }

            if (resCode != 200) {
                marketLockId = new int[1];
                marketLockId[0] = 0;

                marketLockName = new String[1];
                marketLockName[0] = "None";
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.custom_spinner_view, marketLockName);
            adapter.setDropDownViewResource(R.layout.custom_spinner_drop_down);
            spLock.setAdapter(adapter);
        }
    }

    private class loadProductType extends AsyncTask<Void, Void, String> {
        public static final String URL = "https://jongtalad-web-api.herokuapp.com/producttypes/";

        @Override
        protected String doInBackground(Void... voids) {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(URL)
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
            int resCode = 404;

            try {
                JSONObject obj = new JSONObject(s);
                resCode = obj.getInt("code");
                JSONArray productTypeList = obj.getJSONArray("response");
                productTypeId = new int[productTypeList.length()];
                productTypeName = new String[productTypeList.length()];
                for (int i = 0; i < productTypeList.length(); i++) {
                    productTypeId[i] = productTypeList.getJSONObject(i).getInt("productTypeId");
                    productTypeName[i] = productTypeList.getJSONObject(i).getString("productTypeName");
                }
            } catch (JSONException e) {
                resCode = 404;
                productTypeId = new int[1];
                productTypeName = new String[1];

                productTypeId[0] = 0;
                productTypeName[0] = "None";
                e.printStackTrace();
            }
            if (resCode != 200) {
                productTypeId = new int[1];
                productTypeId[0] = 0;

                productTypeName = new String[1];
                productTypeName[0] = "None";
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.custom_spinner_view, productTypeName);
            adapter.setDropDownViewResource(R.layout.custom_spinner_drop_down);
            spProductType.setAdapter(adapter);
            loadingMarketData.dismiss();
        }
    }

    private class loadLockInformation extends AsyncTask<String, Void, String> {
        public static final String URL = "https://jongtalad-web-api.herokuapp.com/markets";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingMarketLockDetail = ProgressDialog.show(getContext(), "Fetch market lock detail", "Loading...", true, false);
        }

        @Override
        protected String doInBackground(String... values) {
            String newUrl = URL + "/locks/" + values[0] + "/detail/?saleDate=" + timeDate;
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
            String marketLockName = "none";
            String reserverType = "none";
            String merchantFullname = "none";
            String merchantPhonenumber = "none";
            String productTypeName = "none";

            try {
                JSONObject obj = new JSONObject(s);
                JSONArray marketLockDetail = obj.getJSONArray("response");
                for (int i = 0; i < marketLockDetail.length(); i++) {

                    cancelMarketLockId = marketLockDetail.getJSONObject(i).getInt("marketLockId");
                    marketLockName = marketLockDetail.getJSONObject(i).getString("marketLockName");
                    merchantFullname = marketLockDetail.getJSONObject(i).getString("marketAdminMerchantName");
                    if (merchantFullname != "null") {
                        reserverType = "Market Admin";
                        merchantPhonenumber = marketLockDetail.getJSONObject(i).getString("marketAdminMerchantPhonenumber");
                    } else {
                        String merchantFirstName;
                        String merchantLastName;
                        reserverType = "Merchant";
                        merchantFirstName = marketLockDetail.getJSONObject(i).getString("merchantName");
                        merchantLastName = marketLockDetail.getJSONObject(i).getString("merchantSurname");
                        merchantFullname = merchantFirstName + " " + merchantLastName;
                        merchantPhonenumber = marketLockDetail.getJSONObject(i).getString("merchantPhonenumber");
                    }
                    productTypeName = marketLockDetail.getJSONObject(i).getString("productTypeName");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            loadingMarketLockDetail.dismiss();

            if (marketLockName != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Market Lock Details")
                        .setMessage("" + marketLockName + "\n" +
                                "Reserved by: " + reserverType + "\n" +
                                merchantFullname + "\n" +
                                merchantPhonenumber + "\n" +
                                productTypeName)
                        .setPositiveButton("ยกเลิกการจอง", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                cancelLockReservation cancelLockReservation = new cancelLockReservation();
                                cancelLockReservation.execute(String.valueOf(cancelMarketLockId), timeDate);
                            }
                        })
                        .setNegativeButton("ปิดหน้าจอ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }

    private class cancelLockReservation extends AsyncTask<String, Void, String> {
        public static final String URL = "https://jongtalad-web-api.herokuapp.com/markets/";

        @Override
        protected String doInBackground(String... values) {
            String newUrl = URL + "locks/" + values[0] + "/cancel";
            OkHttpClient okHttpClient = new OkHttpClient();

            RequestBody requestBody = new FormBody.Builder()
                    .add("saleDate", values[1])
                    .build();

            Request request = new Request.Builder()
                    .url(newUrl)
                    .post(requestBody)
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
            int cancelMarketLockId = 0;
            try {
                JSONObject obj = new JSONObject(s);
                cancelMarketLockId = obj.getJSONObject("response").getInt("cancelMarketLockId");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            loadLockname loadLockname = new loadLockname();
            loadLockname.execute();
            setLockStatus(cancelMarketLockId, R.color.lockStatausNotOuccupied);
        }
    }

    private void setMarketLockClickable(boolean clickable) {
        tvA1.setClickable(clickable);
        tvA2.setClickable(clickable);
        tvA3.setClickable(clickable);
        tvB1.setClickable(clickable);
        tvB2.setClickable(clickable);
        tvB3.setClickable(clickable);
        tvC1.setClickable(clickable);
        tvC2.setClickable(clickable);
        tvC3.setClickable(clickable);
        tvC4.setClickable(clickable);
        tvC5.setClickable(clickable);
        tvD1.setClickable(clickable);
        tvD2.setClickable(clickable);
        tvD3.setClickable(clickable);
        tvD4.setClickable(clickable);
        tvD5.setClickable(clickable);
        tvD6.setClickable(clickable);
        tvD7.setClickable(clickable);
        tvD8.setClickable(clickable);
        tvD9.setClickable(clickable);
    }

    private Date timeDate() {

        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        return date;

    }

    // Function to find the index of an element in a primitive array in Java
    public static int find(String[] a, String target) {
        for (int i = 0; i < a.length; i++)
            if (a[i] == target)
                return i;

        return -1;
    }
}
