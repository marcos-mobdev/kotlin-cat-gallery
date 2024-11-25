package br.com.appforge.catchallenge.models

data class CatImagesResponse(
    val `data`: List<Data>,
    val status: Int,
    val success: Boolean
)