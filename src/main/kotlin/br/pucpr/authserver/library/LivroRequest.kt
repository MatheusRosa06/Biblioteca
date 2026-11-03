package br.pucpr.authserver.library

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class LivroRequest(
    @NotBlank(message = "O título não pode estar em branco")
    var titulo: String?,

    @NotNull(message = "O ano é obrigatório")
    var ano: Int?,

    @NotNull(message = "O preço é obrigatório")
    @Min(value = 0, message = "O preço deve ser maior ou igual a zero")
    var preco: Double?,

    @NotNull(message = "O ID do autor é obrigatório")
    @Min(value = 1)
    var autorId: Long?
)