package com.example.webshop.models.dto;

import com.example.webshop.models.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtUser implements UserDetails {
    private Integer id;
    private String korisnickoIme;
    private String lozinka;
    private Role rola;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(rola.name()));
    }

    @Override
    public String getPassword() {
        return lozinka;
    }

    @Override
    public String getUsername() {
        return korisnickoIme;
    }
    public Role getRole()
    {
        return rola;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
