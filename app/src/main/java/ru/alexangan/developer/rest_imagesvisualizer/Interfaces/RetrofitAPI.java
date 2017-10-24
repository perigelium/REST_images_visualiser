package ru.alexangan.developer.rest_imagesvisualizer.Interfaces;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

import static ru.alexangan.developer.rest_imagesvisualizer.Models.GlobalConstants.API_HOST_URL;

public interface RetrofitAPI
{
    @POST(".")
    Call<List<String>> getImgUrls();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_HOST_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
