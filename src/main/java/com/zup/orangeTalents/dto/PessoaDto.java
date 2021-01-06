package com.zup.orangeTalents.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaDto {

    private int id;
    private String nome;
    private String email;
    private String cpf;
    private LocalDate dtNascimento;
}
