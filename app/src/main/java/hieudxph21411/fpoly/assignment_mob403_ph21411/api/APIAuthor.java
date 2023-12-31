package hieudxph21411.fpoly.assignment_mob403_ph21411.api;

import static hieudxph21411.fpoly.assignment_mob403_ph21411.api.API.URL;

import java.util.ArrayList;
import java.util.List;

import hieudxph21411.fpoly.assignment_mob403_ph21411.model.Author;
import hieudxph21411.fpoly.assignment_mob403_ph21411.model.Users;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIAuthor {

    APIAuthor apiAuthor = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIAuthor.class);

    @GET("author")
    Call<List<Author>> getAllAuthor();

    @POST("author")
    Call<Author> postAuthor(@Body Author data);

    @DELETE("users/{id}")
    Call<Users> deleteById(@Path("id") String id);

    @PUT("users/edit/{id}")
    Call<Users> editById(@Path("id") String id, @Body Users data);
}
