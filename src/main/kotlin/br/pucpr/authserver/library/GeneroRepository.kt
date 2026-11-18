package br.pucpr.authserver.library

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GeneroRepository : JpaRepository<Genero, Long> {
    fun findByNome(nome: String): Genero?
}