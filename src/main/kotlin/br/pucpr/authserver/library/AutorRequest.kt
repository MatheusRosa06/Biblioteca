package br.pucpr.authserver.library

import jakarta.validation.constraints.NotBlank

data class AutorRequest(
    @NotBlank(message = "O nome não pode estar em branco")
    var nome: String?,

    @NotBlank(message = "A nacionalidade não pode estar em branco")
    var nacionalidade: String?
) {
    fun toAutor() = Autor(
        id = null,
        nome = nome!!,
        nacionalidade = nacionalidade!!
    )
}