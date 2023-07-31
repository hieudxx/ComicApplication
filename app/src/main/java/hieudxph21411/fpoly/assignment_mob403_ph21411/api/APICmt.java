package hieudxph21411.fpoly.assignment_mob403_ph21411.api;

import static hieudxph21411.fpoly.assignment_mob403_ph21411.api.API.URL;

import hieudxph21411.fpoly.assignment_mob403_ph21411.model.Cmt;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface APICmt {

    APICmt apiCmt = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APICmt.class);
    @GET("cmt/comic/{id}")
    Call<Cmt> getOneCmt();
}
