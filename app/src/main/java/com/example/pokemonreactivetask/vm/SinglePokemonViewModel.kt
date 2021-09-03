package com.example.pokemonreactivetask.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemonreactivetask.model.SinglePokeDetails
import com.example.pokemonreactivetask.network.RetroService
import com.example.pokemonreactivetask.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * SinglePokemon Details View Model that calls the retroService API to make request from the server
 */
class SinglePokemonViewModel : ViewModel() {
    //pokemonDetails live data
    private val _pokemonDetails: MutableLiveData<SinglePokeDetails?> = MutableLiveData()
    val pokemonDetails : LiveData<SinglePokeDetails?> = _pokemonDetails

    fun makeAPICall(id: String){
        val retrofitInstance = RetrofitInstance.getRetrofitInstance().create(RetroService::class.java)
        val call = retrofitInstance.getSinglePokemon(id)
        call.enqueue(object : Callback<SinglePokeDetails> {
            override fun onResponse(call: Call<SinglePokeDetails>, response: Response<SinglePokeDetails>) {
                if (response.isSuccessful){
                    _pokemonDetails.value = response.body()
                }else{
                    _pokemonDetails.value = null
                }
            }

            override fun onFailure(call: Call<SinglePokeDetails>, t: Throwable) {
                _pokemonDetails.value = null
            }

        })
    }

    var v = MainActivityViewModel()

}