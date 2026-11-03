package br.pucpr.authserver.library

data class AutorResponse(
    var id: Long,
    var nome: String,
    var nacionalidade: String
) {
    constructor(autor: Autor) : this(
        id = autor.id!!,
        nome = autor.nome,
        nacionalidade = autor.nacionalidade
    )
}