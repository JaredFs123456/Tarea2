package com.example.cocina.data.repository

import android.content.Context
import com.example.cocina.data.model.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class AssetRepository(private val context: Context) {

    // Carga perezosa del JSON de assets/data.json
    private val bundle: DataBundle by lazy {
        val json = context.assets.open("data.json").bufferedReader().use { it.readText() }
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter = moshi.adapter(DataBundle::class.java)
        adapter.fromJson(json) ?: DataBundle(emptyList(), emptyList(), emptyList())
    }

    fun getDishes(): List<Dish> = bundle.dishes
    fun getDishById(id: String): Dish? = bundle.dishes.find { it.id == id }
    fun getSubpreps(dishId: String): List<Subprep> = bundle.subpreps.filter { it.dishId == dishId }
    fun getIngredients(subprepId: String): List<Ingredient> = bundle.ingredients.filter { it.subprepId == subprepId }
}
