package org.example.lab5_20202132.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "customer", schema = "lab6")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    @NotBlank(message = "El nombre no puede estar en blanco")
    private String name;

    @Size(max = 11)
    @NotNull
    @Column(name = "document", nullable = false, length = 11)
    @Size(min = 8, message = "El documento debe tener al menos 8 caracteres")

    private String document;

    @Size(max = 10)
    @NotNull
    @Column(name = "document_type", nullable = false, length = 10)
    @NotBlank(message = "El documento no puede estar en blanco")
    private String documentType;


}