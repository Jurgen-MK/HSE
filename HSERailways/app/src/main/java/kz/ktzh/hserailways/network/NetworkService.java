package kz.ktzh.hserailways.network;

import kz.ktzh.hserailways.controller.HSEApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class NetworkService {

    private static NetworkService mInstance;



    private static final String BASE_URL = "http://10.64.2.156:9100";
    private Retrofit mRetrofit;
    private String accessToken;

    private NetworkService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public HSEApi getJSONApi() {
        return mRetrofit.create(HSEApi.class);
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

}
