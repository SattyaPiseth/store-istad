package co.istad.storeistad.security;


import co.istad.storeistad.db.entity.PermissionEntity;
import co.istad.storeistad.db.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class UserPrincipal implements UserDetails, Principal {

    private Long id;
    private String username;
    private String name;
    private String email;
    private String avatar;
    private String phone;
    private String bio;
    private String address;
    private String roleName;
    private Long roleId;
    private Boolean status;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(UserEntity u) {
        this.id = u.getId();
        this.username = u.getUsername();
        this.name = u.getName();
        this.email = u.getEmail();
        this.phone = u.getPhone();
        this.bio = u.getBio();
        this.address = u.getAddress();
        this.avatar = u.getAvatar();
        this.roleId = u.getRoleEntity().getId();
        this.roleName = u.getRoleEntity().getName();
        this.status = u.getStatus();
        this.password = u.getPassword();
        this.authorities = Collections.emptyList();

        Set<String> permissions = u.getRoleEntity().getPermissionEntities().stream().map(PermissionEntity::getName).collect(Collectors.toSet());
        this.authorities = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

    public UserPrincipal(JwtAuthenticationToken jwt) {

        Map<String, Object> claims = jwt.getTokenAttributes();

        this.id = (Long) claims.get("id");
        this.username = (String) claims.get("username");
        this.name = (String) claims.get("name");
        this.email = (String) claims.get("email");
        this.phone = (String) claims.get("phone");
        this.bio = (String) claims.get("bio");
        this.address = (String) claims.get("address");
        this.avatar = (String) claims.get("avatar");
        this.roleName = (String) claims.get("roleName");
        this.roleId = (Long) claims.get("roleId");
        this.status = (Boolean) claims.get("status");

    }

    public static UserPrincipal build(UserEntity user) {
        return new UserPrincipal(user);
    }

    public static UserPrincipal build(JwtAuthenticationToken jwt) {
        return new UserPrincipal(jwt);
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return this.status;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return this.status;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return this.status;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return this.status;
    }

    @JsonIgnore
    @Override
    public String getName() {
        return this.name;
    }
}
