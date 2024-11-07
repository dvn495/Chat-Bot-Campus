package com.examplewebsocket.websockets.Login.Auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
<<<<<<< HEAD
=======
import org.springframework.security.crypto.password.PasswordEncoder;
>>>>>>> 917286f8dd3028398fe48ee3000059dd55494f32
import org.springframework.stereotype.Service;

import com.examplewebsocket.websockets.Login.Jwt.JwtService;
import com.examplewebsocket.websockets.Login.User.Role;
import com.examplewebsocket.websockets.Login.User.User;
import com.examplewebsocket.websockets.Login.User.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
<<<<<<< HEAD
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        // Autenticación sin contraseña
        UserDetails user = userRepository.findByUsername(request.getUsername())
                                         .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    
        // Puedes agregar lógica adicional para verificar el teléfono si es necesario
        if (!request.getTelefono().equals(request.getTelefono())) {
            throw new RuntimeException("Teléfono incorrecto");
        }
    
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }
    
=======
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user=userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token=jwtService.getToken(user);
        return AuthResponse.builder()
            .token(token)
            .build();

    }
>>>>>>> 917286f8dd3028398fe48ee3000059dd55494f32

    public AuthResponse register(RegisterRequest request) {
        User user;
        user = User.builder()
                .username(request.getUsername())
<<<<<<< HEAD
                .telefono(request.getTelefono())
=======
                .password(passwordEncoder.encode( request.getPassword()))
>>>>>>> 917286f8dd3028398fe48ee3000059dd55494f32
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
            .token(jwtService.getToken(user))
            .build();
        
    }

}
