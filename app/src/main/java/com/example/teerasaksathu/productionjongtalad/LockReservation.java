package com.example.teerasaksathu.productionjongtalad;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class LockReservation extends AppCompatActivity implements View.OnClickListener {


    private Button btnReserve;
    private EditText etName;
    private EditText etPhonenumber;
    private Spinner spProductType;
    private Spinner spLock;


    private String lockNameForDelete;

    private String marketName = "RMUTT Walking Street";
    private String dataLock;
    private String market_id = "1";
    private String timeDate;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_reservation);

        initInstances();

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


    }

    private void initInstances() {

         timeDate =String.valueOf(timeDate());
//        Log.d("Daet", timeDate);



        setDataToTV setDataToTV = new setDataToTV();
        setDataToTV.execute();

        loadLockname loadLockname = new loadLockname();
        loadLockname.execute();

        loadProductType loadProductType = new loadProductType();
        loadProductType.execute();



        etName = (EditText) findViewById(R.id.etName);
        etPhonenumber = (EditText) findViewById(R.id.etPhonenumber);
        btnReserve = (Button) findViewById(R.id.btnReserve);
        spProductType = (Spinner) findViewById(R.id.spProductType);
        spLock = (Spinner) findViewById(R.id.spLock);
        tvDate = (TextView) findViewById(R.id.date);
        tvDate.setText(timeDate);
        tvA1 = (TextView) findViewById(R.id.tvA1);
        tvA2 = (TextView) findViewById(R.id.tvA2);
        tvA3 = (TextView) findViewById(R.id.tvA3);
        tvB1 = (TextView) findViewById(R.id.tvB1);
        tvB2 = (TextView) findViewById(R.id.tvB2);
        tvB3 = (TextView) findViewById(R.id.tvB3);
        tvC1 = (TextView) findViewById(R.id.tvC1);
        tvC2 = (TextView) findViewById(R.id.tvC2);
        tvC3 = (TextView) findViewById(R.id.tvC3);
        tvC4 = (TextView) findViewById(R.id.tvC4);
        tvC5 = (TextView) findViewById(R.id.tvC5);
        tvD1 = (TextView) findViewById(R.id.tvD1);
        tvD2 = (TextView) findViewById(R.id.tvD2);
        tvD3 = (TextView) findViewById(R.id.tvD3);
        tvD4 = (TextView) findViewById(R.id.tvD4);
        tvD5 = (TextView) findViewById(R.id.tvD5);
        tvD6 = (TextView) findViewById(R.id.tvD6);
        tvD7 = (TextView) findViewById(R.id.tvD7);
        tvD8 = (TextView) findViewById(R.id.tvD8);
        tvD9 = (TextView) findViewById(R.id.tvD9);


    }
    @Override
    public void onClick(View view) {
        if (view == btnReserve) {
            if (valuesChecking()) {
                String name = etName.getText().toString().trim();
//                String surname = etSurname.getText().toString().trim();
                String phonenumber = etPhonenumber.getText().toString().trim();
                String lockName = spLock.getSelectedItem().toString().trim();
                String productType = spProductType.getSelectedItem().toString().trim();
//                Log.d("ooooo", name);
//                Log.d("ooooo", phonenumber);
//                Log.d("ooooo", marketName);
//                Log.d("ooooo", lockName);
//                Log.d("ooooo", productType);
//                Log.d("ooooo", timeDate);

                if (lockName.equals("None")) {
                    Toast.makeText(LockReservation.this, "Please select lock", Toast.LENGTH_LONG).show();
                } else {
                    ReserveLock reserveLock = new ReserveLock();
                    reserveLock.execute(name,phonenumber,marketName,lockName,productType,timeDate);

                    loadLockname loadLockname = new loadLockname();
                    loadLockname.execute();

                    setLockStatus(spLock.getSelectedItem().toString().trim(), R.color.lockStatusOuccupied);
                }


            }
        } else if (view == tvA1) {
            showLockStatus("A1");
        } else if (view == tvA2) {
            showLockStatus("A2");
        } else if (view == tvA3) {
            showLockStatus("A3");
        } else if (view == tvB1) {
            showLockStatus("B1");
        } else if (view == tvB2) {
            showLockStatus("B2");
        } else if (view == tvB3) {
            showLockStatus("B3");
        } else if (view == tvC1) {
            showLockStatus("C1");
        } else if (view == tvC2) {
            showLockStatus("C2");
        } else if (view == tvC3) {
            showLockStatus("C3");
        } else if (view == tvC4) {
            showLockStatus("C4");
        } else if (view == tvC5) {
            showLockStatus("C5");
        } else if (view == tvD1) {
            showLockStatus("D1");
        } else if (view == tvD2) {
            showLockStatus("D2");
        } else if (view == tvD3) {
            showLockStatus("D3");
        } else if (view == tvD4) {
            showLockStatus("D4");
        } else if (view == tvD5) {
            showLockStatus("D5");
        } else if (view == tvD6) {
            showLockStatus("D6");
        } else if (view == tvD7) {
            showLockStatus("D7");
        } else if (view == tvD8) {
            showLockStatus("D8");
        } else if (view == tvD9) {
            showLockStatus("D9");
        }
    }

    private void setLockStatus(String lockName, Integer colorID) {
        switch (lockName) {
            case "A1":
                tvA1.setBackgroundResource(colorID);
                break;
            case "A2":
                tvA2.setBackgroundResource(colorID);
                break;
            case "A3":
                tvA3.setBackgroundResource(colorID);
                break;
            case "B1":
                tvB1.setBackgroundResource(colorID);
                break;
            case "B2":
                tvB2.setBackgroundResource(colorID);
                break;
            case "B3":
                tvB3.setBackgroundResource(colorID);
                break;
            case "C1":
                tvC1.setBackgroundResource(colorID);
                break;
            case "C2":
                tvC2.setBackgroundResource(colorID);
                break;
            case "C3":
                tvC3.setBackgroundResource(colorID);
                break;
            case "C4":
                tvC4.setBackgroundResource(colorID);
                break;
            case "C5":
                tvC5.setBackgroundResource(colorID);
                break;
            case "D1":
                tvD1.setBackgroundResource(colorID);
                break;
            case "D2":
                tvD2.setBackgroundResource(colorID);
                break;
            case "D3":
                tvD3.setBackgroundResource(colorID);
                break;
            case "D4":
                tvD4.setBackgroundResource(colorID);
                break;
            case "D5":
                tvD5.setBackgroundResource(colorID);
                break;
            case "D6":
                tvD6.setBackgroundResource(colorID);
                break;
            case "D7":
                tvD7.setBackgroundResource(colorID);
                break;
            case "D8":
                tvD8.setBackgroundResource(colorID);
                break;
            case "D9":
                tvD9.setBackgroundResource(colorID);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(LockReservation.this);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(LockReservation.this);
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

    private void showLockStatus(String lockName) {
        lockNameForDelete = lockName;
        loadLockInformation lockInformation = new loadLockInformation();
        lockInformation.execute(lockName, marketName,timeDate);
    }

    private class ReserveLock extends AsyncTask<String, Void,String> {
        public static final String URL = "http://www.jongtalad.com/doc/phpNew/lock_reservation.php";

        @Override
        protected String doInBackground(String... values) {
            OkHttpClient okHttpClient = new OkHttpClient();

            RequestBody requestBody = new FormBody.Builder()
                    .add("merchantName", values[0])
                    .add("merchantPhonenumber", values[1])
                    .add("merchantSurname","")
                    .add("marketAdmin_username","")
                    .add("marketName", values[2])
                    .add("lockName", values[3])
                    .add("productTypeName",values[4])
                    .add("saleDate",values[5])
                    .build();
            Request request = new Request.Builder()
                    .url(URL)
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
//            Log.d("onPostSS", s);
            if (s.equals("1")) {

                Toast.makeText(LockReservation.this, "จองล็อคสำเร็จ", Toast.LENGTH_SHORT).show();
                etName.setText("");
                etPhonenumber.setText("");
            } else {
                Toast.makeText(LockReservation.this, "เกิดข้อผิดพลาด", Toast.LENGTH_LONG).show();

            }
        }
    }

    private class setDataToTV extends AsyncTask<Void, Void, String> {

        private static final String URLstatusLock = "http://www.jongtalad.com/doc/phpNew/load_lock_status.php";


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

//            Log.d("Json", "=>" + s);
            try {

                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {


                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                        dataLock = jsonObject.getString("name");
                        setLockStatus(dataLock, R.color.lockStatusOuccupied);
//                        Log.d("dataLock", dataLock);


                }


            } catch (Exception e) {
//                Log.d("Erorr", "e onPost ==>" + e.toString());
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {


            try {


                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                RequestBody requestBody = new FormBody.Builder()
                        .add("reservationStatus","2")
                        .add("saleDate",timeDate)
                        .add("marketName",marketName)
                        .build();
                Request request = builder.url(URLstatusLock).post(requestBody).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {

//                Log.d("Dpost", "=>" + e);

                return null;
            }


        }
    }

    private class loadLockname extends AsyncTask<Void, Void, String> {
        public static final String URL = "http://www.jongtalad.com/doc/phpNew/load_lock_status.php";

        @Override
        protected String doInBackground(Void... voids) {
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("reservationStatus","1")
                    .add("saleDate",timeDate)
                    .add("marketName",marketName)
                    .build();
            Request request = new Request.Builder()
                    .url(URL)
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
            String[] lockList;
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    lockList = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        lockList[i] = (jsonObject.getString("name"));
                    }

                } catch (JSONException e) {
                    lockList = new String[1];
                    lockList[0] = "None";

                    e.printStackTrace();
                }




            ArrayAdapter<String> adapter = new ArrayAdapter<>(LockReservation.this, R.layout.custom_spinner_view, lockList);
            adapter.setDropDownViewResource(R.layout.custom_spinner_drop_down);
            spLock.setAdapter(adapter);
        }
    }

    private class loadProductType extends AsyncTask<Void, Void, String> {
        public static final String URL = "http://www.jongtalad.com/doc/phpNew/loadProductType.php";

        @Override
        protected String doInBackground(Void... voids) {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(URL)
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
            String[] productTypeList;
            try {


                JSONArray jsonArray = new JSONArray(s);

                productTypeList = new String[jsonArray.length()];

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    productTypeList[i] = jsonObject.getString("name");

                }
            } catch (JSONException e) {
                productTypeList = null;
                e.printStackTrace();
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(LockReservation.this, R.layout.custom_spinner_view, productTypeList);
            adapter.setDropDownViewResource(R.layout.custom_spinner_drop_down);
            spProductType.setAdapter(adapter);
        }
    }

    private class loadLockInformation extends AsyncTask<String, Void, String> {
        public static final String URL = "http://www.jongtalad.com/doc/phpNew/load_lock_information.php";


        @Override
        protected String doInBackground(String... values) {
            OkHttpClient okHttpClient = new OkHttpClient();

            RequestBody requestBody = new FormBody.Builder()
                    .add("lockName", values[0])
                    .add("marketName", values[1])
                    .add("saleDate",values[2])
                    .build();

            Request request = new Request.Builder()
                    .url(URL)
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
//             Log.d("data", s);
            String lockName = null;
            String name = "null";

            String phonenumber = "null";
            String productType = "null";
            try {
                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    lockName = jsonObject.getString("lockName");
                    name = jsonObject.getString("merchantName");
                    phonenumber = jsonObject.getString("phonenumber");
                    productType = jsonObject.getString("productType");

                }

            } catch (JSONException e) {
                e.printStackTrace();
//                Log.d("Error -->", e.getMessage());
            }
            if (lockName != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LockReservation.this);
                builder.setTitle("รายละเอียดล็อค")
                        .setMessage(lockName + "\n" +
                                name + " " +
                                phonenumber + "\n" +
                                productType + "\n")
                        .setPositiveButton("ยกเลิกการจอง", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                                Log.d("lockNameForDelete", lockNameForDelete);
                                cancelLockReservation cancelLockReservation = new cancelLockReservation();
                                cancelLockReservation.execute(lockNameForDelete, marketName,timeDate);

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
        public static final String URL = "http://www.jongtalad.com/doc/phpNew/cancel_lock_reservation.php";

        @Override
        protected String doInBackground(String... values) {
            OkHttpClient okHttpClient = new OkHttpClient();

            RequestBody requestBody = new FormBody.Builder()
                    .add("lockName", values[0])
                    .add("marketName", values[1])
                    .add("saleDate",values[2])
                    .build();

            Request request = new Request.Builder()
                    .url(URL)
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
            setLockStatus(lockNameForDelete, R.color.lockStatausNotOuccupied);
            loadLockname loadLockname = new loadLockname();
            loadLockname.execute();
        }
    }

    private Date timeDate() {

        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
        return date;

    }

    }



