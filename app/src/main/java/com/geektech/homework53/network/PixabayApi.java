package com.geektech.homework53.network;

import com.geektech.homework53.network.model.PixabayModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PixabayApi {
//https://pixabay.com/api/?key=27723662-43b1a48fa73b0ddcb14cd5408&q=animal
    @GET("api/")
    Call<PixabayModel> getImages(@Query("key") String key,
                                 @Query("q") String query,
                                 @Query("page") int page,
                                 @Query("per_page") int perPage);
}
