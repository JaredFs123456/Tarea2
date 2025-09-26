package com.example.cocina.ui.dish

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cocina.databinding.ActivityDishBinding

class DishActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = "Platillos"
        binding.title.text = "Aquí irá la lista de platillos"
    }
}
