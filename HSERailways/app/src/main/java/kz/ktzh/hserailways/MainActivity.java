package kz.ktzh.hserailways;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.List;

import kz.ktzh.hserailways.entity.Incidents;
import kz.ktzh.hserailways.network.NetworkServiceResource;
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static okhttp3.MediaType.*;
//import okhttp3.Response;

public class MainActivity extends AppCompatActivity { //  implements Callback

    /*public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpeg");*/
    public static final int ACCESS_TO_FILE_SYSTEM = 1;
    private static final String LOG_TAG = "OkHttp";

    TextView tvToken;
    String accessToken;
    Button btnSend;
    private Handler mHandler;
    private String mMessage;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvToken = findViewById(R.id.tvToken);
        btnSend = findViewById(R.id.btnSend);
        Intent intent = getIntent();
//        accessToken = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);
        accessToken = NetworkServiceResource.getInstance().getAccessToken();

//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);

       /* mHandler = new Handler(Looper.getMainLooper());
        OkHttpClient client = new OkHttpClient();
        // GET request
        Request request = new Request.Builder().url("http://10.64.2.156:9100/me/users")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mMessage = e.toString();
                Log.e(LOG_TAG, mMessage); // no need inside run()
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tvToken.setText(mMessage); // must be inside run()
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mMessage = response.body().string();
                Log.i(LOG_TAG, mMessage); // no need inside run()
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tvToken.setText(mMessage); // must be inside run()
                    }
                });
            }
        });*/
    }

/*    void doPost(String url, String accessToken, String json){
        mHandler = new Handler(Looper.getMainLooper());
//        RequestBody body = RequestBody.create(JSON, json);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        ACCESS_TO_FILE_SYSTEM);
            }
        }
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addPart(Headers.of("Content-Disposition", "form-data; name=\"incds\""),
                        RequestBody.create(JSON, json))
                .addPart(Headers.of("Content-Disposition", "form-data; name=\"mpfile\"; filename=\"storage/emulated/0/Download/bakary.png\""),
                        RequestBody.create(MEDIA_TYPE_PNG, new File("storage/emulated/0/Download/bakary.png")))
                .build();
        OkHttpClient client = new OkHttpClient();
        // POST request
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + accessToken)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mMessage = e.toString();
//                mMessage = "Ошибка, блять";
                Log.e(LOG_TAG, mMessage); // no need inside run()
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tvToken.setText(mMessage); // must be inside run()
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                mMessage = response.body().string();
                if (response.isSuccessful())
                    mMessage = response.body().string();
                else
                    mMessage = "ХУЙНЯ";
                Log.i(LOG_TAG, mMessage); // no need inside run()
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        tvToken.setText(mMessage); // must be inside run()
                    }
                });
            }
        });
    }*/

    public void onClickSend(View v) {
        Incidents incs  = new Incidents(1, "2019-09-05 00:00:00", "bla n", 1,"");
        String json = new Gson().toJson(incs);
//        doPost("http://10.64.2.156:9100/incidents/sendinc", accessToken, json);
//        NetworkRequest request = new NetworkRequest();
//        request.doPost("http://10.64.2.156:9100/incidents/viewall", accessToken, mSendPicCallback);
        NetworkServiceResource.getInstance()
                .getJSONApi()
                .getAllIncidents("Bearer " + accessToken)
                .enqueue(new Callback<List<Incidents>>() {
                    @Override
                    public void onResponse(Call<List<Incidents>> call, Response<List<Incidents>> response) {
                        List<Incidents> listIncidents = response.body();
                        for (Incidents inc : listIncidents)
                            Log.i(LOG_TAG, "id - " + inc.getInc_id());
                    }

                    @Override
                    public void onFailure(Call<List<Incidents>> call, Throwable t) {

                    }
                });
    }

    public void onClickSendPic(View v){
        Incidents incs  = new Incidents(1, "2019-09-06 00:00:00", "RETROFIT", 1,"");
        String json = new Gson().toJson(incs);
        NetworkServiceResource.getInstance()
                .getJSONApi()
                .addInc("Bearer " + accessToken,
                        MultipartBody.Part.createFormData("mpfile", "storage/emulated/0/Download/bakary.png",
                        RequestBody.create(parse("image/jpg"), new File("storage/emulated/0/Download/bakary.png"))),
                        RequestBody.create(parse("application/json"), json))
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            Log.i("CALLBACK", response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.i("CALLBACK", "ERROR");
                    }
                });
    }

    public void onClickMenu(View v){

    }

    /*private NetworkRequest.Callback<String> mSendPicCallback = new NetworkRequest.Callback<String>() {
        @Override
        public void onResponse(@NonNull String response) {
            tvToken.setText(response);
        }

        @Override
        public void onError(String error) {
            tvToken.setText(error);
        }

        @Override
        public Class<String> type() {
            return String.class;
        }

    };*/
}
