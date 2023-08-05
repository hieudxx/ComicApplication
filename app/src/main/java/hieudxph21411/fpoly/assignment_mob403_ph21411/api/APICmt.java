package hieudxph21411.fpoly.assignment_mob403_ph21411.api;

import static hieudxph21411.fpoly.assignment_mob403_ph21411.api.API.URL;

import hieudxph21411.fpoly.assignment_mob403_ph21411.model.Cmt;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APICmt {
    APICmt apiCmt = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APICmt.class);

    @POST("cmt/{comicId}/{usersId}")
    Call<Cmt> postCmt(@Body Cmt data, @Path("comicId") String comicId, @Path("usersId") String usersId);

    @PUT("cmt/{cmtId}/{usersId}")
    Call<Cmt> updateCmt(@Body Cmt data, @Path("cmtId") String cmtId, @Path("usersId") String usersId);

    @DELETE("cmt/{cmtId}/{usersId}")
    Call<Cmt> deleteCmt(@Path("cmtId") String cmtId, @Path("usersId") String usersId);


}

