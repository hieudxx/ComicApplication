package hieudxph21411.fpoly.assignment_mob403_ph21411.api;

import static hieudxph21411.fpoly.assignment_mob403_ph21411.api.API.URL;

import java.util.ArrayList;

import hieudxph21411.fpoly.assignment_mob403_ph21411.model.Comic;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIComic {

    APIComic apiComic = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIComic.class);
    @GET("comic")
    Call<ArrayList<Comic>> getAllComic();
    @GET("comic/detail/{id}")
    Call<Comic> getOneComic(@Path("id") String id);





}
