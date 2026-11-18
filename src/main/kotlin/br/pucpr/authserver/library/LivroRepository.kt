package br.pucpr.authserver.library

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface LivroRepository : JpaRepository<Livro, Long> {
    fun findByTitulo(titulo: String): Livro?

    @EntityGraph(attributePaths = ["autor", "editora", "generos"])
    @Query("select l from Livro l where l.id = :id")
    fun findByIdWithAssociations(id: Long): Livro?
}