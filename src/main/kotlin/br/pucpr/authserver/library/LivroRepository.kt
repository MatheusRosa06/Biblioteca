package br.pucpr.authserver.library

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LivroRepository : JpaRepository<Livro, Long> {
    fun findByTitulo(titulo: String): Livro?
}