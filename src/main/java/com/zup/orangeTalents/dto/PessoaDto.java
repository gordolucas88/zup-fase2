package com.zup.orangeTalents.dto;

import com.zup.orangeTalents.entity.Pessoa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.UniqueConstraint;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaDto {

    private Long id;
    @NotBlank(message = "Nome nao pode ser Branco")
    @NotNull(message = "Nome nao pode ser Nulo")
    @Size(min = 5, max = 250)
    private String nome;

    @NotEmpty(message = "E-mail nao pode ser vazio")
    @Email(message = "E-mail Invalido")
    private String email;

    @NotEmpty(message = "CPF nao pode ser em Vazio")
    @CPF(message = "CPF Invalido")
    @Size(min = 11, max = 11, message = "Tamanho Invalido")
    private String cpf;

    //@NotEmpty(message = "Data de nascimento nao pode ser em Vazio")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dtNascimento;

    public Pessoa toPessoa() {
        Pessoa pessoa = new Pessoa();

        /*if (this.getId() > 0) {
            pessoa.setId(id);
        }*/
        pessoa.getId();
        pessoa.setNome(nome);
        pessoa.setEmail(email);
        pessoa.setCpf(cpf);
        pessoa.setDtNascimento(dtNascimento);

        return pessoa;
    }
}

