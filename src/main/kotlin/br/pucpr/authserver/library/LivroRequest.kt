package br.pucpr.authserver.library

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class LivroRequest(
    @field:NotBlank(message = "O título não pode estar em branco")
    var titulo: String?,

    @field:NotNull(message = "O ano é obrigatório")
    var ano: Int?,

    @field:NotNull(message = "O preço é obrigatório")
    @field:Positive(message = "O preço deve ser maior que zero")
    var preco: Double?,

    @field:NotNull(message = "O ID do autor é obrigatório")
    var autorId: Long?,

    @field:NotNull(message = "O ID da editora é obrigatório")
    var editoraId: Long? 
)