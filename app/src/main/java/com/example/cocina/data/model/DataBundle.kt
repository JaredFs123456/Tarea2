package com.example.cocina.data.model

data class DataBundle(
    val dishes: List<Dish>,
    val subpreps: List<Subprep>,
    val ingredients: List<Ingredient>
)
