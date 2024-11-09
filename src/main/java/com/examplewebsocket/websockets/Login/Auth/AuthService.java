package com.examplewebsocket.websockets.Login.Auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
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

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .telefono(request.getTelefono())
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
            .token(jwtService.getToken(user))
            .build();
    }
}
