package com.zup.orangeTalents.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pessoa")

public class Pessoa {

    @Id
    @GeneratedValue
    private int id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String email;
    @Column(unique = true)
    private String cpf;
    @Column(nullable = false)
    private LocalDate dtNascimento;


}