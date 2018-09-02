package com.example.teerasaksathu.productionjongtalad.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.teerasaksathu.productionjongtalad.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    // ALL COMMENT ARE FOR DISABLE UPLOAD IMAGE TO FIREBASE LOGIC

    private EditText etName;
    private EditText etSurname;
    private EditText etPhonenumber;
    private EditText etMarketName;
    private EditText etMarketAddress;
    private Button btnRegister;
    private Button btnUploadImage;
    private ImageView ivImg;
    private String filePath;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initInstances();

    }

    private void initInstances() {

        etName = findViewById(R.id.etName);
        etSurname = findViewById(R.id.etSurname);
        etPhonenumber = findViewById(R.id.etPhonenumber);
        etMarketName = findViewById(R.id.etMarketName);
        etMarketAddress = findViewById(R.id.etMarketAddress);
        btnRegister = findViewById(R.id.btnRegister);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        ivImg = findViewById(R.id.ivImg);

        btnRegister.setOnClickListener(this);
        btnUploadImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btnRegister) {
            if (registerChecking()) {
                String marketAdminName = etName.getText().toString().trim();
                String marketAdminSurname = etSurname.getText().toString().trim();
                String marketAdminPhonenumber = etPhonenumber.getText().toString().trim();
                String marketName = etMarketName.getText().toString().trim();
                String marketAddress = etMarketAddress.getText().toString().trim();

                Register register = new Register();
                register.execute(marketAdminName, marketAdminSurname, marketAdminPhonenumber, marketName, marketAddress);

//                if (filePath != null) {
//                    mStorageRef = FirebaseStorage.getInstance().getReference();
//                    Uri file = Uri.fromFile(new File(filePath));
//                    StorageReference riversRef = mStorageRef.child("MarketAdmins/" + marketName);
//
//                    riversRef.putFile(file)
//                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                                @Override
//                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                    // Get a URL to the uploaded content
//                                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
//
//                                    UploadProfileImage uploadProfileImage = new UploadProfileImage();
//                                    uploadProfileImage.execute(marketName, downloadUrl.toString());
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception exception) {
//                                    // Handle unsuccessful uploads
//                                    // ...
//                                }
//                            });
//
//                }
            }
        }
//        else if (view == btnUploadImage) {
//            Intent intent = new Intent();
//            intent.setType("image/*");
//            intent.setAction(Intent.ACTION_PICK);
//            startActivityForResult(Intent.createChooser(intent, "โปรดเลือกรูป"), 1);
//        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if ((requestCode == 1) && (resultCode == RESULT_OK)) {
//            Log.d("MyFrienfV1 ", "Result ==>OK");
//
//            //หา path รูป
//            Uri uri = data.getData();
//            filePath = myFindPathImage(uri);
//            Log.d("MyFrienfV1", "imagePathString ==>" + filePath);
//            //result Complete
//
//            //Setup Image to ImageView
//            try {
//                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
//                ivImg.setImageBitmap(bitmap);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }//try
//
//
//        }//if
//    }

//    private String myFindPathImage(Uri uri) {
//        String strResult = null;
//        String[] strings = {MediaStore.Images.Media.DATA};
//        Cursor cursor = getContentResolver().query(uri, strings, null, null, null);
//        if (cursor != null) {
//
//            cursor.moveToFirst();
//            int intIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            strResult = cursor.getString(intIndex);
//
//        } else {
//
//            strResult = uri.getPath();
//
//        }
//        return strResult;
//    }

    private boolean registerChecking() {
        int count = 0;
        if (etName.getText().toString().length() == 0) {
            count++;
        }

        if (etSurname.getText().toString().length() == 0) {
            count++;
        }

        if (etMarketName.getText().toString().length() == 0) {
            count++;
        }

        if (etMarketAddress.getText().toString().length() == 0) {
            count++;
        }

        if (count > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage("Please fill up the form")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        }

        if (etPhonenumber.getText().toString().length() == 0 || etPhonenumber.getText().toString().length() < 10) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage("Phonenumber must have 10 character")
                    .setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        }


        return true;
    }

    private class Register extends AsyncTask<String, Void, String> {
        public static final String URL = "https://jongtalad-web-api.herokuapp.com/auth/register/marketadmin";

        @Override
        protected String doInBackground(String... value) {
            OkHttpClient okHttpClient = new OkHttpClient();

            RequestBody requestBody = new FormBody.Builder()
                    .add("marketAdminName", value[0])
                    .add("marketAdminSurname", value[1])
                    .add("marketAdminPhonenumber", value[2])
                    .add("marketName", value[3])
                    .add("marketAddress", value[4])
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
            int resCode = 0;
            try {
                JSONObject obj = new JSONObject(s);
                resCode = obj.getInt("code");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (resCode == 201) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle("Thank you for your attention!")
                        .setMessage("We will contact you back in 24 hr.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                Toast.makeText(getApplicationContext(), "Something went wrong please contact admin", Toast.LENGTH_LONG).show();
            }


        }
    }

//    private class UploadProfileImage extends AsyncTask<String, Void, String> {
//        public static final String URL = "http://www.jongtalad.com/doc/phpNew/upload_profile_image.php";
//
//
//        @Override
//        protected String doInBackground(String... values) {
//            OkHttpClient okHttpClient = new OkHttpClient();
//            RequestBody requestBody = new FormBody.Builder()
//                    .add("marketName", values[0])
//                    .add("pictureUrl", values[1])
//                    .build();
//            Request request = new Request.Builder()
//                    .url(URL)
//                    .post(requestBody)
//                    .build();
//            try {
//                Response response = okHttpClient.newCall(request).execute();
//                if (response.isSuccessful()) {
//                    return response.body().string();
//                } else {
//                    return "Not Success - code : " + response.code();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                return "Error - " + e.getMessage();
//            }
//        }
//    }
}
