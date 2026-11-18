package br.pucpr.authserver.users

import br.pucpr.authserver.roles.RoleRepository
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import org.springframework.security.crypto.password.PasswordEncoder

@Service
class UserService(
    val repository: UserRepository,
    val roleRepository: RoleRepository,
    val passwordEncoder: PasswordEncoder
) {
    fun insert(user: User): User {
        if (repository.findByEmail(user.email) != null) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "User already exists")
        }
        val password = user.password
        user.password = passwordEncoder.encode(password)!!
        return repository.save(user)
    }

    fun update(id: Long, name: String): User? {
        val user = repository.findByIdOrNull(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User $id not found!")
        if (user.name == name) return null
        user.name = name
        return repository.save(user)
    }

    fun findAll(dir: SortDir) =
        if (dir == SortDir.ASC)
            repository.findAll(Sort.by("name"))
        else
            repository.findAll(Sort.by("name").descending())

    fun findByRole(role: String) = repository.findByRole(role.uppercase())
    fun findByIdOrNull(id: Long) = repository.findByIdOrNull(id)
    fun deleteById(id: Long) = repository.deleteById(id)
    fun addRole(id: Long, roleName: String): Boolean {
        val upperRole = roleName.uppercase()
        val user = findByIdOrNull(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User $id not found!")
        if (user.roles.any { it.name ==  upperRole }) return false
        val role = roleRepository.findByName(upperRole) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Role $upperRole not found!")

        user.roles.add(role)
        repository.save(user)
        return true
    }
}