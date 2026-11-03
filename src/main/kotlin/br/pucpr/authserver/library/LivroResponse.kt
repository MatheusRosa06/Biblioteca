package br.pucpr.authserver.library

data class LivroResponse(
    var id: Long,
    var titulo: String,
    var ano: Int,
    var preco: Double,
    var autorNome: String
) {
    constructor(livro: Livro) : this(
        id = livro.id!!,
        titulo = livro.titulo,
        ano = livro.ano,
        preco = livro.preco,
        autorNome = livro.autor.nome
    )
}