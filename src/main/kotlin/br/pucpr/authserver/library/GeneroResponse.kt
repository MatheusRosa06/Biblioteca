package br.pucpr.authserver.library

data class GeneroResponse(
    var id: Long,
    var nome: String,
    var descricao: String?,
    var quantidadeLivros: Int 
) {
    constructor(genero: Genero) : this(
        id = genero.id!!,
        nome = genero.nome,
        descricao = genero.descricao,
        quantidadeLivros = genero.livros.size 
    )
}