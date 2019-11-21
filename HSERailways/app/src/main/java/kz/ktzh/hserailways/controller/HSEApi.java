package kz.ktzh.hserailways.controller;

import java.util.List;

import kz.ktzh.hserailways.entity.Incidents;
import kz.ktzh.hserailways.entity.UserCreationRequest;
import kz.ktzh.hserailways.entity.UserInfo;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HSEApi {
//    @POST("/incidents/viewall")
//    public Call<Incidents> getIncidentWithID(@Path("id") int id);

    @POST("/incidents/viewall")
    public Call<List<Incidents>> getAllIncidents(@Header("Authorization") String token);

    @Multipart
    @POST("/incidents/sendinc")
    public Call<ResponseBody> addInc(@Header("Authorization") String token, @Part MultipartBody.Part file, @Part("incds") RequestBody incds);

    @POST("/user/userinfo")
    public Call<UserInfo> getUserInfo(@Header("Authorization") String token, @Query("username") String username);

    @POST("/incidents/viewbyid")
    public Call<List<Incidents>> getIncidentsByUserId(@Header("Authorization") String token, @Query("userid") int username);

    @POST("/user/register")
    public Call<ResponseBody> doRetister(@Header("Content-Type") String token, @Body UserCreationRequest userCreationRequest);
}
