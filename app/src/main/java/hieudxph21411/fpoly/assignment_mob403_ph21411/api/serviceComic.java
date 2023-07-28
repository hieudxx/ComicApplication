package hieudxph21411.fpoly.assignment_mob403_ph21411.api;

import hieudxph21411.fpoly.assignment_mob403_ph21411.model.Comic;
import retrofit2.Call;
import retrofit2.http.GET;

public interface serviceComic {
    @GET("comic")
    Call<Comic> getAllComic();


}
