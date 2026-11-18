package br.pucpr.authserver.library

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "tbl_editora")
class Editora(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    var nome: String,

    @Column(nullable = false)
    var cidade: String,

    @JsonIgnore
    @OneToMany(mappedBy = "editora")
    val livros: MutableSet<Livro> = mutableSetOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Editora) return false
        return id != null && id == other.id
    }

    override fun hashCode(): Int = id?.hashCode() ?: 0
}