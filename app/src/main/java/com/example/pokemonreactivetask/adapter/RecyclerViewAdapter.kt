package com.example.pokemonreactivetask.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemonreactivetask.R
import com.example.pokemonreactivetask.model.Result
import com.example.pokemonreactivetask.ui.SinglePokemonDetail
import kotlinx.android.synthetic.main.pokemon_list.view.*

/**
 * RecyclerView Adapter for the Main Activity Pokemon List
 */
class RecyclerViewAdapter(val context: Context) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>(){
    var pokemon : List<com.example.pokemonreactivetask.model.Result> = listOf()

    fun setDataList(pokemon: List<com.example.pokemonreactivetask.model.Result>){
        this.pokemon = pokemon
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_list, parent, false)
        return MyViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(pokemon[position], context)
    }

    override fun getItemCount(): Int {
        return pokemon.size
    }

    class MyViewHolder (view: View) : RecyclerView.ViewHolder(view){

        fun bind(pokemon: Result, context: Context){
            itemView.poke_name.text = pokemon.name.capitalize()
            val url = pokemon.url
            val id = url.substring(34, url.lastIndex).toInt()
            itemView.theId.text = id.toString()
            val pokemonPosterURL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png"
            Glide.with(itemView.context)
                .load(pokemonPosterURL)
                .into(itemView.imageView)
            itemView.cardView.setOnClickListener {
                val intent = Intent(context, SinglePokemonDetail::class.java)
                intent.putExtra("id", id.toString())
                context.startActivity(intent)
            }
        }
    }
}
