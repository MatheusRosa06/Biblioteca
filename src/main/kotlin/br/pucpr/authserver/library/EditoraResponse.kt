package br.pucpr.authserver.library

data class EditoraResponse(
    var id: Long,
    var nome: String,
    var cidade: String,
    var quantidadeLivros: Int
) {
    constructor(editora: Editora) : this(
        id = editora.id!!,
        nome = editora.nome,
        cidade = editora.cidade,
        quantidadeLivros = editora.livros.size
    )
}