package com.examplewebsocket.websockets.Login.User;

<<<<<<< HEAD
import jakarta.persistence.*;
=======
<<<<<<< HEAD
import jakarta.persistence.*;
=======
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
>>>>>>> 917286f8dd3028398fe48ee3000059dd55494f32
>>>>>>> 8a9943e32de06103e4514bfa4ac1298625bee770
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> 8a9943e32de06103e4514bfa4ac1298625bee770
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Entity
<<<<<<< HEAD
=======
=======
>>>>>>> 917286f8dd3028398fe48ee3000059dd55494f32
>>>>>>> 8a9943e32de06103e4514bfa4ac1298625bee770
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
<<<<<<< HEAD
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
=======
<<<<<<< HEAD
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(name = "telefono", nullable = false)
    private Long telefono; // Cambiado a String si se prefiere almacenar con formato

    @Enumerated(EnumType.STRING)
    private Role role;

    // Constructor, getters y setters

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return null; 
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

=======
@Entity
@Table(name="user", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
>>>>>>> 8a9943e32de06103e4514bfa4ac1298625bee770
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(name = "telefono", nullable = false)
    private Long telefono; // Cambiado a String si se prefiere almacenar con formato

    @Enumerated(EnumType.STRING)
    private Role role;

    // Constructor, getters y setters

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return null; 
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
<<<<<<< HEAD

=======
>>>>>>> 917286f8dd3028398fe48ee3000059dd55494f32
>>>>>>> 8a9943e32de06103e4514bfa4ac1298625bee770
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
<<<<<<< HEAD

=======
<<<<<<< HEAD

=======
>>>>>>> 917286f8dd3028398fe48ee3000059dd55494f32
>>>>>>> 8a9943e32de06103e4514bfa4ac1298625bee770
    @Override
    public boolean isEnabled() {
        return true;
    }
}
<<<<<<< HEAD

=======
<<<<<<< HEAD

=======
>>>>>>> 917286f8dd3028398fe48ee3000059dd55494f32
>>>>>>> 8a9943e32de06103e4514bfa4ac1298625bee770
