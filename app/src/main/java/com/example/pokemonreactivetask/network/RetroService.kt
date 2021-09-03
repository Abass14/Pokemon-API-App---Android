package com.example.pokemonreactivetask.network

import com.example.pokemonreactivetask.model.PokemonData
import com.example.pokemonreactivetask.model.SinglePokeDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetroService {
    //https://pokeapi.co/api/v2/pokemon/1
    //pokemon?limit=100&offset=0
    @GET("pokemon")
    fun getDataFromApi(@Query("limit") limit: String, @Query("offset") offset: Int ) : Call<PokemonData>

    @GET("pokemon/{pok_id}")
    fun getSinglePokemon(@Path("pok_id") id: String) : Call<SinglePokeDetails>
}