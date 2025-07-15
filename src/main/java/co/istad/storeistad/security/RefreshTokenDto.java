package co.istad.storeistad.security;

import jakarta.validation.constraints.NotBlank;

/**
 * @author Sattya
 * create at 3/2/2024 11:34 AM
 */
public record RefreshTokenDto(
        @NotBlank String refreshToken
) {
}
