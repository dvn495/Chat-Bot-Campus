package com.examplewebsocket.websockets.config;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import com.examplewebsocket.websockets.Login.Jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomWebSocketInterceptor extends HttpSessionHandshakeInterceptor {

    

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String query = request.getURI().getQuery();
        if (query != null && query.contains("token=")) {
            String token = query.split("token=")[1];
            // Valida el token JWT aquí
            if (isValidToken(token)) {
                // Token es válido, permite la conexión
                return true;
            } else {
                // Token no es válido, rechaza la conexión
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return false;
            }
        }
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return false;
    }

    private final JwtService jwtService;

    private boolean isValidToken(String token) {
        // Lógica de validación del token usando JwtService
        return jwtService.isTokenValid(token);
    }
}
