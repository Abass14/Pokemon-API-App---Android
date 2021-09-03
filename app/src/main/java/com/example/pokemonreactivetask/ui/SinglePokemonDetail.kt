package com.example.pokemonreactivetask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.pokemonreactivetask.R
import com.example.pokemonreactivetask.connectivity.ConnectivityLiveData
import com.example.pokemonreactivetask.model.PokemonData
import com.example.pokemonreactivetask.model.SinglePokeDetails
import com.example.pokemonreactivetask.vm.SinglePokemonViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_single_pokemon_detail.*

class SinglePokemonDetail : AppCompatActivity() {
    private lateinit var connectivityLiveData: ConnectivityLiveData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_pokemon_detail)
        connectivityLiveData = ConnectivityLiveData(application)
        val getId = intent.getStringExtra("id")

        val id = getId?.toInt()
        makeAPICall(id.toString())
    }

    private fun bindUI(it: SinglePokeDetails?) {
        if (it != null) {
            pokName.text = it.name.capitalize()
        }
        if (it != null) {
            heightTxt.text = "${it.height}ft"
        }
        if (it != null) {
            weightTxt.text = "${it.weight}kg"
        }

        if (it != null) {
            AbilitiesTxt1.text = "${it.abilities[0].ability.name}"
        }
        if (it != null) {
            movesTxt.text = "${it.moves[0].move.name}"
        }
        if (it != null) {
            for (i in it.stats){
                statTxt.text = "${i.stat.name} ${i.base_stat} ${i.effort} \n"
            }
        }

        val urlBackDefault = it?.sprites?.other?.official_artwork?.front_default
        val urlFrontDefault = it?.sprites?.front_default
        val urlBackShiny = it?.sprites?.back_shiny
        val urlFrontShiny = it?.sprites?.front_shiny

        Glide.with(this).load(urlBackDefault).into(imageView1)
        Glide.with(this).load(urlFrontDefault).into(imageView2)
        Glide.with(this).load(urlFrontShiny).into(imageView3)
        Glide.with(this).load(urlBackShiny).into(imageView4)

    }

    fun makeAPICall(id: String) : SinglePokemonViewModel {
        val viewModel = ViewModelProvider(this)[SinglePokemonViewModel::class.java]
        viewModel.pokemonDetails.observe(this, Observer {
            singlePokProgress.visibility = View.GONE
            singlePokErrorTxt.visibility = View.GONE
            if (it != null){
                bindUI(it)
                singlePokErrorTxt.visibility = View.GONE
            }else{
                singlePokErrorTxt.visibility = View.VISIBLE
            }
        })

        connectivityLiveData.observe(this, Observer { isAvailable ->
            //2
            when (isAvailable) {
                true -> {
                    //3
                    viewModel.makeAPICall(id)
                    singlePokErrorTxt.visibility = View.GONE
                }
                false -> {
                    singlePokErrorTxt.visibility = View.VISIBLE
                }
            }
        })
        return viewModel
    }

}