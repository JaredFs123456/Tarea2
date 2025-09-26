package com.example.cocina.ui.ingredient

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cocina.R
import com.example.cocina.databinding.ActivityIngredientBinding

class IngredientActivity : AppCompatActivity() {

    private lateinit var b: ActivityIngredientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityIngredientBinding.inflate(layoutInflater)
        setContentView(b.root)

        setSupportActionBar(b.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        b.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        val subprepId   = intent.getStringExtra("subprepId").orEmpty()
        val subprepName = intent.getStringExtra("subprepName").orEmpty()
        val dishName    = intent.getStringExtra("dishName").orEmpty()
        supportActionBar?.title = subprepName

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(b.fragmentContainer.id,
                    IngredientListFragment.newInstance(subprepId, dishName, subprepName))
                .commit()
        }
    }

    // ← aplica la animación de salida (Etapa 5)
    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out_right)
    }

    // (Opcional) para que el botón "Up" use la misma lógica
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
