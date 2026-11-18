package br.pucpr.authserver.library

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DadosBase(
    private val autorRepository: AutorRepository,
    private val generoRepository: GeneroRepository,
    private val livroRepository: LivroRepository,
    private val editoraRepository: EditoraRepository
) {

    @Bean
    fun initDatabase() = CommandLineRunner {
        if (autorRepository.count() == 0L) {
            // 1. Criar Editoras e guardar as referências
            val ed1 = editoraRepository.save(Editora(null, "Editora Letrado", "São Paulo"))
            val ed2 = editoraRepository.save(Editora(null, "Editora Pochita", "Rio de Janeiro"))
            val ed3 = editoraRepository.save(Editora(null, "Editora Quinta Série", "Curitiba"))

            // 2. Criar Autores Inventados
            val autor1 = autorRepository.save(Autor(null, "Astrogildo Silva", "Brasileira"))
            val autor2 = autorRepository.save(Autor(null, "Beatriz Coden", "Portuguesa"))

            // 3. Criar Gêneros
            val genero1 = generoRepository.save(Genero(null, "Ficção Científica", "Viagens espaciais e tecnologia"))
            val genero2 = generoRepository.save(Genero(null, "Mistério", "Investigações e enigmas"))
            val genero3 = generoRepository.save(Genero(null, "Comédia", "Histórias divertidas e caóticas"))

            // 4. Criar Livros Inventados (associando Autor e Editora)
            val livro1 = Livro(
                id = null,
                titulo = "O Bug do Milênio",
                ano = 2024,
                preco = 59.90,
                autor = autor1,
                editora = ed1
            )
            livro1.generos.add(genero1)
            livroRepository.save(livro1)

            val livro2 = Livro(
                id = null,
                titulo = "As Crônicas do Teclado Quebrado",
                ano = 2025,
                preco = 79.90,
                autor = autor2,
                editora = ed2
            )
            livro2.generos.add(genero2)
            livro2.generos.add(genero3) // Livro com dois gêneros!
            livroRepository.save(livro2)
        }
    }
}