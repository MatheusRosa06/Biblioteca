package br.pucpr.authserver.library

import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class GeneroService(
    private val generoRepository: GeneroRepository
) {
    private val logger = LoggerFactory.getLogger(GeneroService::class.java)

    fun insert(genero: Genero): Genero {
        logger.info("A criar novo gênero: ${genero.nome}")
        if (generoRepository.findByNome(genero.nome) != null) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "Gênero '${genero.nome}' já existe!")
        }
        return generoRepository.save(genero)
    }

    fun findAll(): List<Genero> {
        logger.info("A listar todos os gêneros")
        return generoRepository.findAll()
    }

    fun findByIdOrNull(id: Long): Genero? {
        return generoRepository.findByIdOrNull(id)
    }

    fun deleteById(id: Long) {
        logger.warn("A apagar o gênero com ID: $id")
        if (!generoRepository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Gênero $id não encontrado!")
        }
        generoRepository.deleteById(id)
    }
}