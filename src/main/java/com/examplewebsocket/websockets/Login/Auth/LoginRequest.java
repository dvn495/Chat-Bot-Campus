package com.examplewebsocket.websockets.Login.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    String username;
<<<<<<< HEAD
    Long telefono; 
=======
    String password; 
>>>>>>> 917286f8dd3028398fe48ee3000059dd55494f32
    String role;
}
