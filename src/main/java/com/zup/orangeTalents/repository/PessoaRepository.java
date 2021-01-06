package com.zup.orangeTalents.repository;

import com.zup.orangeTalents.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
    Pessoa findByNome(String name);
    Pessoa findByCpf(String cpf);
}
