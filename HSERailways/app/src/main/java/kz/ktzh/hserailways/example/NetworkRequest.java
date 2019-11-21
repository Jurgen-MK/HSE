package kz.ktzh.hserailways.example;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.IOException;

import ca.mimic.oauth2library.OAuthError;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class NetworkRequest {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpeg");
    public static final int ACCESS_TO_FILE_SYSTEM = 1;
    public static final String URL_AUTH_SERVER = "";
    public static final String URL_RESOURCE_SERVER = "";

    private Callback mCallback;

    private OkHttpClient mClient;
    private OAuthError error;

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public NetworkRequest() {
        mClient = new OkHttpClient();
    }

    public void doPostPicture(String url, String accessToken, String json, Callback callback){
        setCallback(callback);
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
        doRequest(request, callback);
    }

    public void doPost(String url, String token, Callback callback){
        setCallback(callback);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + token)
                .post(RequestBody.create(null, new byte[0]))
                .build();
        doRequest(request, callback);
    }

    private void doRequest(@NonNull Request request, final Callback callback) {
        mClient.newCall(request)
            .enqueue(new okhttp3.Callback() {
                Handler mainHandler = new Handler(Looper.getMainLooper());
                @Override
                public void onFailure(Call call, final IOException e) {
                    if (callback != null) {
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                callback.onError(e.toString());
                            }
                        });
                    }
                }

                @Override
                public void onResponse(final Call call, final Response response) {
                    if(callback != null) {
                        try {
                            final String stringResponse = response.body().string();
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Object res = buildObjectFromResponse(stringResponse,
                                            callback.type());
                                    if (res != null) {
                                        callback.onResponse(res);
                                    } else {
                                        callback.onError(stringResponse);
                                    }
                                }
                            });
                        } catch (final IOException ioe) {
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    callback.onError(ioe.toString());
                                }
                            });
                        }
                    }
                }
            });
    }

    private Object buildObjectFromResponse(String response, Class cls) {
        if (cls == String.class) {
            return response;
        } else {
            try {
                return new Gson().fromJson(response, cls);
            } catch (JsonSyntaxException jse) {
                return null;
            }
        }
    }

    private String buildJsonFromObject(Class cls) {
        try {
            return new Gson().toJson(cls);
        } catch (JsonSyntaxException jse) {
            return null;
        }
    }

    public interface Callback<T> {
        void onResponse(@NonNull T response);
        void onError(String error);
        Class<T> type();
    }
}
