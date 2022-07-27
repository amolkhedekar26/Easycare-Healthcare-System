package com.example.easycare.api;

import com.example.easycare.dataclasses.ConsultDoctors;
import com.example.easycare.dataclasses.Doctors;
import com.example.easycare.dataclasses.Hospitals;
import com.example.easycare.dataclasses.Medicines;
import com.example.easycare.dataclasses.Post_Put_Response;
import com.example.easycare.dataclasses.Profile;
import com.example.easycare.dataclasses.Questions;
import com.example.easycare.dataclasses.ResetToken;
import com.example.easycare.dataclasses.Token;
import com.example.easycare.dataclasses.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface easycareapi {

    @POST("obtain_auth_token/")
    @FormUrlEncoded
    Call<Token> getToken(@Field("username") String username,
                         @Field("password") String password);

    @POST("signup/")
    @FormUrlEncoded
    Call<Post_Put_Response> doSignup(@Field("username") String username,
                                     @Field("password") String password,
                                     @Field("email") String email);

    @GET("doctors/")
    Call<List<Doctors>> getDoctors(@Query("location") String location, @Query("speciality") String speciality);

    @GET("hospitals/")
    Call<List<Hospitals>> getHospitals(@Query("location") String location, @Query("speciality") String speciality);

    @GET("medicines/{disease}/")
    Call<List<Medicines>> getMedicines(@Path("disease") String disease);

    @POST("verify_email/")
    @FormUrlEncoded
    Call<Post_Put_Response> verifyEmail(@Field("username") String username, @Field("email") String email);

    @PUT("reset_password/")
    @FormUrlEncoded
    Call<Post_Put_Response> resetPassword(@Field("token") String token, @Field("password") String password);


    @POST("verify_email_view/")
    @FormUrlEncoded
    Call<Post_Put_Response> verifyEmailView(@Field("username") String username,@Field("email") String email);

    @GET("profile/")
    Call<Profile> getProfile(@Header("Authorization") String token);

    @GET
    Call<ResponseBody> getProfileImage(@Url String url);

    @GET("consult_doctors/")
    Call<List<ConsultDoctors>> getConsultDoctors(@Header("Authorization") String token);

    @GET("get_consulted_doctors/")
    Call<List<ConsultDoctors>> getConsultedDoctors(@Header("Authorization") String token);

    @GET("patient_questions/{doc_name}")
    Call<List<Questions>> getQuestions(@Header("Authorization") String token, @Path("doc_name") String doctor);


    @POST("patient_questions/")
    @FormUrlEncoded
    Call<Post_Put_Response> submitQuery(@Header("Authorization") String token,@Field("query") String query,@Field("doc_name") String doc_name,@Field("tag") String tag);


    @GET("doctor_questions/{user_name}")
    Call<List<Questions>> getAskedQuestions(@Header("Authorization") String token,@Path("user_name") String username);

    @GET("get_asked_patients/")
    Call<List<User>> getaskedPatients(@Header("Authorization") String token);

    @PUT("doc_questions/{id}")
    @FormUrlEncoded
    Call<Post_Put_Response> postAnswer(@Header("Authorization") String token,@Path("id") int id,@Field("answer") String answer);

    @GET("get_disease_names/")
    Call<List<String>> getDiseaseNames();
}
