package br.pucpr.authserver.library

import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class AutorService(
    private val autorRepository: AutorRepository
) {
    private val logger = LoggerFactory.getLogger(AutorService::class.java)

    fun insert(autor: Autor): Autor {
        logger.info("A criar novo autor: ${autor.nome}")
        return autorRepository.save(autor)
    }

    fun findAll(): List<Autor> {
        logger.info("A listar todos os autores")
        return autorRepository.findAll()
    }

    fun findByIdOrNull(id: Long): Autor? {
        return autorRepository.findByIdOrNull(id)
    }

    fun update(id: Long, request: AutorRequest): Autor {
        logger.info("A atualizar o autor com ID: $id")
        val autor = findByIdOrNull(id) 
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Autor $id não encontrado!")
        
        autor.nome = request.nome!!
        autor.nacionalidade = request.nacionalidade!!
        return autorRepository.save(autor)
    }

    fun deleteById(id: Long) {
        logger.warn("A apagar o autor com ID: $id")
        if (!autorRepository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Autor $id não encontrado!")
        }
        autorRepository.deleteById(id)
    }
}