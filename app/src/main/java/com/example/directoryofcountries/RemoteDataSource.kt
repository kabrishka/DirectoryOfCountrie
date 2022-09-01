package com.example.directoryofcountries

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.converter.gson.GsonConverterFactory


interface RestCountriesApi {
    //здесь указывается список endpoint-ов, которые нас интересуют
    @GET("name/{name}")
    //модификатором suspend помечаются функции, работющ. долго
    suspend fun getCountryByName(@Path("name") cityName: String): List<Country>
}

var retrofit = Retrofit.Builder()
    .baseUrl("https://restcountries.com/v2/")
    //добавляет конвертер
    .addConverterFactory(GsonConverterFactory.create())
    .build()

var restCountriesApi = retrofit.create(RestCountriesApi::class.java)