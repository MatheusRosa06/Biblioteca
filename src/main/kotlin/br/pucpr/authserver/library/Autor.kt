package br.pucpr.authserver.library

import jakarta.persistence.*

@Entity
@Table(name = "tbl_autor")
data class Autor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var nome: String,

    @Column(nullable = false)
    var nacionalidade: String,

    // Relacionamento One-to-Many: Um autor tem vários livros
    @OneToMany(mappedBy = "autor", cascade = [CascadeType.ALL], orphanRemoval = true)
    val livros: MutableList<Livro> = mutableListOf()
)