package hieudxph21411.fpoly.assignment_mob403_ph21411.api;

import java.util.ArrayList;
import java.util.List;

import hieudxph21411.fpoly.assignment_mob403_ph21411.model.Comic;
import hieudxph21411.fpoly.assignment_mob403_ph21411.model.Users;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface serviceUsers {
    @GET("users")
    Call<ArrayList<Users>> getAllUsers();

    @POST("register/users")
    Call<Users> postUsers(@Body Users data);

    @DELETE("users/{id}")
    Call<Users> deleteById(@Path("id") String id);
}
