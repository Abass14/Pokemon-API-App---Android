package com.example.pokemonreactivetask.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    //https://pokeapi.co/api/v2/pokemon?limit=100&offset=0
    //https://pokeapi.co/api/v2/pokemon/1

    companion object{
        val BASE_URL = "https://pokeapi.co/api/v2/"

        val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient: OkHttpClient.Builder = OkHttpClient.Builder().addInterceptor(logger)


        fun getRetrofitInstance() : Retrofit{
            return Retrofit.Builder()
                .client(okHttpClient.build())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}