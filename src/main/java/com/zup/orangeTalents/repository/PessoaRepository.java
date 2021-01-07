package com.zup.orangeTalents.repository;

import com.zup.orangeTalents.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Pessoa findByNome(String name);
    Pessoa findByCpf(String cpf);
}
