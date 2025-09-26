package com.example.cocina.data.model

data class Subprep(
    val id: String,
    val dishId: String,
    val name: String,
    val description: String,
    val tips: List<String> = emptyList(),
    val kind: String = "prep",  // "info" (descripción) | "prep" (sub-preparación)
    val order: Int = 0,         // para ordenar capas/pasos
    val mediaUrl: String? = null
)
