package com.example.cocina.ui.subprep

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cocina.data.repository.AssetRepository
import com.example.cocina.databinding.ActivitySubprepBinding

class SubprepActivity : AppCompatActivity() {

    private lateinit var b: ActivitySubprepBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySubprepBinding.inflate(layoutInflater)
        setContentView(b.root)

        setSupportActionBar(b.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        b.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        // Transición de elemento compartido a nivel de ventana (ok mantenerla aquí)
        window.sharedElementEnterTransition =
            android.transition.TransitionInflater.from(this)
                .inflateTransition(android.R.transition.move)
        window.sharedElementReturnTransition =
            android.transition.TransitionInflater.from(this)
                .inflateTransition(android.R.transition.move)

        val dishId = intent.getStringExtra("dishId").orEmpty()
        val sharedName = intent.getStringExtra("sharedName") // se lo pasamos al fragment

        // título de la barra
        val dish = AssetRepository(this).getDishById(dishId)
        supportActionBar?.title = dish?.name ?: "Sub-preparaciones"

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    b.fragmentContainer.id,
                    SubprepListFragment.newInstance(dishId, sharedName)
                )
                .commit()
        }
    }
}
