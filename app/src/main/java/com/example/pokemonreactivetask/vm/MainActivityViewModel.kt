package com.example.pokemonreactivetask.vm

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokemonreactivetask.adapter.RecyclerViewAdapter
import com.example.pokemonreactivetask.model.PokemonData
import com.example.pokemonreactivetask.model.Result
import com.example.pokemonreactivetask.network.RetroService
import com.example.pokemonreactivetask.network.RetrofitInstance
import com.example.pokemonreactivetask.ui.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * MainActivity's ViewModel
 */
class MainActivityViewModel : ViewModel(){
    //LiveData that is observed in main activity
    private val _recyclerListLiveData: MutableLiveData<PokemonData?> = MutableLiveData()
    val recyclerListLiveData: LiveData<PokemonData?> = _recyclerListLiveData


    lateinit var recyclerViewAdapter: RecyclerViewAdapter



    fun init(context: Context){
        recyclerViewAdapter = RecyclerViewAdapter(context)
    }

    fun setAdapterData(data: List<Result>){
        recyclerViewAdapter.setDataList(data)
        recyclerViewAdapter.notifyDataSetChanged()
    }

    //Function that calls the retrofit service to trigger request from the server
    fun makeAPICall(limit: String, offset: Int){
        val retrofitInstance = RetrofitInstance.getRetrofitInstance().create(RetroService::class.java)
        val call = retrofitInstance.getDataFromApi(limit, offset)

        call.enqueue(object : Callback<PokemonData> {
            override fun onResponse(call: Call<PokemonData>, response: Response<PokemonData>) {
                if (response.isSuccessful){
                    //Response from the server
                    _recyclerListLiveData.postValue(response.body())
                }else{
                    _recyclerListLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<PokemonData>, t: Throwable) {
                _recyclerListLiveData.postValue(null)
            }

        })
    }
}
