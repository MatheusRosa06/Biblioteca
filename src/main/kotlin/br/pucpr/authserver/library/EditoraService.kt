package br.pucpr.authserver.library

import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class EditoraService(
    private val editoraRepository: EditoraRepository
) {
    private val logger = LoggerFactory.getLogger(EditoraService::class.java)

    fun findAll(): List<Editora> {
        logger.info("A listar todas as editoras")
        return editoraRepository.findAll()
    }

    fun findByIdOrNull(id: Long): Editora? = editoraRepository.findByIdOrNull(id)
}