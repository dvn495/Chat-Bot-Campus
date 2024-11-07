package com.examplewebsocket.websockets.Login.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username); 
<<<<<<< HEAD
    Optional<User> findByTelefono(Long telefono);
=======
>>>>>>> 917286f8dd3028398fe48ee3000059dd55494f32
}
