package br.pucpr.authserver.users

import jakarta.validation.constraints.NotBlank

data class PatchUserRequest(
    @NotBlank
    var name: String?
)
