package kz.ktzh.hserailways.network;

import kz.ktzh.hserailways.controller.HSEAuthApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NetworkServiceAuth {
    private static NetworkServiceAuth mInstance;

    private static final String BASE_URL_REG = "http://10.64.2.156:9000";
    private Retrofit mRetrofit;
    private String accessToken;

    public NetworkServiceAuth() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_REG)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    public static NetworkServiceAuth getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkServiceAuth();
        }
        return mInstance;
    }

    public HSEAuthApi getJSONAuthApi() {
        return mRetrofit.create(HSEAuthApi.class);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
