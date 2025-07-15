package co.istad.storeistad.utils;

import co.istad.storeistad.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Component
public class TokenUtils {

    private static final String ISSUER = "CSTAD-STORE";
    private final JwtEncoder encoder;
//    private JwtEncoder jwtRefreshTokenEncoder;

    public TokenUtils(JwtEncoder encoder) {
        this.encoder = encoder;
    }

//    @Autowired
//    public void setJwtRefreshTokenEncoder(JwtEncoder jwtRefreshTokenEncoder) {
//        this.jwtRefreshTokenEncoder = jwtRefreshTokenEncoder;
//    }

    public String generateToken(Authentication authentication) {
        return this.generateToken((UserPrincipal) authentication.getPrincipal());
    }

    public String generateToken(UserPrincipal userPrincipal) {
        Instant now = Instant.now();
        String scope = userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(ISSUER)
                .issuedAt(now)
                .expiresAt(now.plus(7, ChronoUnit.DAYS))
                .subject(userPrincipal.getUsername())
                .id(userPrincipal.getId().toString())

                .claim("scope", scope)

                .claim("id", userPrincipal.getId())
                .claim("username", userPrincipal.getUsername())
                .claim("name", emptyIfNull(userPrincipal.getName()))
                .claim("roleId", userPrincipal.getRoleId())
                .claim("email", emptyIfNull(userPrincipal.getEmail()))
                .claim("status", userPrincipal.getStatus())
                .claim("phone", emptyIfNull(userPrincipal.getPhone()))
                .claim("bio", emptyIfNull(userPrincipal.getBio()))
                .claim("address", emptyIfNull(userPrincipal.getAddress()))
                .claim("avatar", emptyIfNull(userPrincipal.getAvatar()))
                .claim("roleName", emptyIfNull(userPrincipal.getRoleName()))

                .build();

        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

//    public String generateRefreshToken(UserPrincipal userPrincipal) {
//        Instant now = Instant.now();
//        String scope = userPrincipal.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(" "));
//
//        JwtClaimsSet claims = JwtClaimsSet.builder()
//                .issuer(ISSUER)
//                .issuedAt(now)
//                .expiresAt(now.plus(30, ChronoUnit.DAYS))
//                .subject(userPrincipal.getUsername())
//                .id(userPrincipal.getId().toString())
//
//                .claim("scope", scope)
//
//                .claim("id", userPrincipal.getId())
//                .claim("username", userPrincipal.getUsername())
//                .claim("name", emptyIfNull(userPrincipal.getName()))
//                .claim("roleId", userPrincipal.getRoleId())
//                .claim("email", emptyIfNull(userPrincipal.getEmail()))
//                .claim("status", userPrincipal.getStatus())
//                .claim("phone", emptyIfNull(userPrincipal.getPhone()))
//                .claim("bio", emptyIfNull(userPrincipal.getBio()))
//                .claim("address", emptyIfNull(userPrincipal.getAddress()))
//                .claim("avatar", emptyIfNull(userPrincipal.getAvatar()))
//                .claim("roleName", emptyIfNull(userPrincipal.getRoleName()))
//
//                .build();
//
//        return this.jwtRefreshTokenEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
//    }
//


    private String emptyIfNull(String field) {
        if (field == null)
            return "";
        return field;
    }

}
