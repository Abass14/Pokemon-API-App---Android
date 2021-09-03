package com.example.pokemonreactivetask.model

data class PokemonData(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>

)

data class Result(
    val name: String,
    val url: String
)



