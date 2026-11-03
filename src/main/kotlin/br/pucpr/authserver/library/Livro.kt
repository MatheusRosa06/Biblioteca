package br.pucpr.authserver.library

import jakarta.persistence.*

@Entity
@Table(name = "tbl_livro")
data class Livro(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var titulo: String,

    @Column(nullable = false)
    var ano: Int,

    @Column(nullable = false)
    var preco: Double,

    // Relacionamento Many-to-One: Vários livros pertencem a um autor
    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    var autor: Autor
)