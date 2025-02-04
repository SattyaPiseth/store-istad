package co.istad.storeistad.security;


import co.istad.storeistad.db.entity.UserEntity;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Map;
import java.util.Optional;

/**
 * @author Sattya
 * create at 13/02/24 11:55 PM
 */

class SpringSecurityAuditorAware implements AuditorAware<UserEntity> {

    @Override
    public Optional<UserEntity> getCurrentAuditor() {
        Authentication authToken = SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> attributes;
        if (authToken instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            attributes = jwtAuthenticationToken.getTokenAttributes();
            UserEntity user = new UserEntity();

            user.setId((Long) attributes.get("id"));
            user.setUsername((String) attributes.get("username"));
            user.setEmail((String) attributes.get("email"));
            user.setPhone((String) attributes.get("phone"));
            user.setStatus((Boolean) attributes.get("status"));

            return Optional.of(user);
        }
        return Optional.empty();
    }

}


