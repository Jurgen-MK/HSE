package kz.ktzh.hserailways.controller;

import kz.ktzh.hserailways.entity.UserCreationRequest;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface HSEAuthApi {

    @POST("/user/register")
    public Call<ResponseBody> doRegistration(@Header("Content-Type") String token, @Body UserCreationRequest userCreationRequest);

}
