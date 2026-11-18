package br.pucpr.authserver.library

import jakarta.persistence.*

@Entity
@Table(name = "tbl_livro")
class Livro(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var titulo: String,

    @Column(nullable = false)
    var ano: Int,

    @Column(nullable = false)
    var preco: Double,

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    var autor: Autor,

    @ManyToOne
    @JoinColumn(name = "editora_id", nullable = true)
    var editora: Editora? = null,

    @ManyToMany
    @JoinTable(
        name = "tbl_livro_genero",
        joinColumns = [JoinColumn(name = "livro_id")],
        inverseJoinColumns = [JoinColumn(name = "genero_id")]
    )
    val generos: MutableSet<Genero> = mutableSetOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Livro) return false
        return id != null && id == other.id
    }

    override fun hashCode(): Int = id?.hashCode() ?: 0
}