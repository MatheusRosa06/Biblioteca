package br.pucpr.authserver.library

import jakarta.validation.constraints.NotBlank

data class GeneroRequest(
    @NotBlank(message = "O nome do gênero não pode estar em branco")
    var nome: String?,

    var descricao: String?
) {
    fun toGenero() = Genero(
        id = null,
        nome = nome!!,
        descricao = descricao
    )
}