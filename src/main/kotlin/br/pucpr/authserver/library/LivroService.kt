package br.pucpr.authserver.library

import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class LivroService(
    private val livroRepository: LivroRepository,
    private val autorRepository: AutorRepository,
    private val generoRepository: GeneroRepository,
    private val editoraRepository: EditoraRepository 
) {
    private val logger = LoggerFactory.getLogger(LivroService::class.java)

    fun insert(request: LivroRequest): Livro {
        logger.info("A criar novo livro: ${request.titulo}")
        
  
        val autorId = request.autorId ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "O ID do autor é obrigatório!")
        val autor = autorRepository.findByIdOrNull(autorId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Autor $autorId não encontrado!")

     
        val editoraId = request.editoraId ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "O ID da editora é obrigatório!")
        val editora = editoraRepository.findByIdOrNull(editoraId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Editora $editoraId não encontrada!")

        val livro = Livro(
            id = null,
            titulo = request.titulo!!,
            ano = request.ano!!,
            preco = request.preco!!,
            autor = autor,
            editora = editora 
        )
        return livroRepository.save(livro)
    }

    fun findAllFiltered(titulo: String?, sortBy: String): List<Livro> {
        logger.info("A listar livros com filtro de título: $titulo e ordenados por: $sortBy")
        val sort = Sort.by(sortBy)
        val livros = if (!titulo.isNullOrBlank()) {
            livroRepository.findAll().filter { it.titulo.contains(titulo, ignoreCase = true) }
        } else {
            livroRepository.findAll(sort)
        }
        return livros
    }

    fun findByIdOrNull(id: Long): Livro? {
        return livroRepository.findByIdWithAssociations(id)
    }

    fun deleteById(id: Long) {
        logger.warn("A apagar o livro com ID: $id")
        val livro = findByIdOrNull(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Livro $id não encontrado!")

        livro.generos.clear()
        livroRepository.flush()
        livroRepository.delete(livro)
        livroRepository.flush()
    }

    fun addGenero(livroId: Long, generoId: Long): Boolean {
        logger.info("A associar gênero $generoId ao livro $livroId")
        val livro = findByIdOrNull(livroId) 
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Livro $livroId não encontrado!")
        
        val genero = generoRepository.findByIdOrNull(generoId) 
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Gênero $generoId não encontrado!")

        if (livro.generos.any { it.id == generoId }) return false

        livro.generos.add(genero)
        livroRepository.save(livro)
        return true
    }
}