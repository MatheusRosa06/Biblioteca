package br.pucpr.authserver.library

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "tbl_genero")
class Genero(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    var nome: String,

    @Column(nullable = true)
    var descricao: String?,

    @JsonIgnore
    @ManyToMany(mappedBy = "generos")
    val livros: MutableSet<Livro> = mutableSetOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Genero) return false
        return id != null && id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}