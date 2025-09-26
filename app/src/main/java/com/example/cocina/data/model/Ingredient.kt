package com.example.cocina.data.model

data class Ingredient(
    val id: String,
    val subprepId: String,   // referencia a la sub-preparación
    val name: String,
    val qty: String,
    val note: String?,        // sustituciones / alergias (POI nivel 3)
    val image: String?
)
