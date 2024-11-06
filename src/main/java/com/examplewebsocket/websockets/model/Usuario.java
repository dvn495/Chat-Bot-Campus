package com.examplewebsocket.websockets.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombreUsuario", nullable = false, length = 100)
    private String nombreUsuario;

    @Column(name = "telefono", nullable = false)
    private Integer telefono;


}
