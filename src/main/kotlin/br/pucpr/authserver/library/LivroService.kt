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
    private val autorRepository: AutorRepository
) {
    private val logger = LoggerFactory.getLogger(LivroService::class.java)

    fun insert(request: LivroRequest): Livro {
        logger.info("A criar novo livro: ${request.titulo}")
        
       
        val autorId = request.autorId 
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "O ID do autor é obrigatório!")

        val autor = autorRepository.findByIdOrNull(autorId)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Autor $autorId não encontrado!")

        val livro = Livro(
            id = null,
            titulo = request.titulo!!,
            ano = request.ano!!,
            preco = request.preco!!,
            autor = autor
        )
        return livroRepository.save(livro)
    }

    // filtro
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
        return livroRepository.findByIdOrNull(id)
    }

    fun deleteById(id: Long) {
        logger.warn("A apagar o livro com ID: $id")
        if (!livroRepository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Livro $id não encontrado!")
        }
        livroRepository.deleteById(id)
    }
}