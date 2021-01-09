package com.zup.orangeTalents.service;


import com.zup.orangeTalents.entity.Pessoa;
import com.zup.orangeTalents.repository.PessoaRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;

    public Pessoa cadastrarPessoa(Pessoa pessoa){
       return repository.save(pessoa);
    }


    public List<Pessoa> listarPessoas(){
        return repository.findAll();
    }

    public Pessoa listarPessoaId(Long id) throws Exception {

        return repository.findById(id).orElseThrow(Exception::new);
    }

    public Pessoa listarPessoaCPF(String cpf) throws Exception {
        return repository.findByCpf(cpf).orElseThrow(Exception::new);
    }

    public String excluirPessoa(Long id){
        repository.deleteById(id);
        return "Cadastro Excluido: " + id;
    }

    public Pessoa atualizarPessoa(Pessoa pessoa){
        Pessoa updatePessoa=repository.findById(pessoa.getId()).orElse(null);
        updatePessoa.setNome(pessoa.getNome());
        updatePessoa.setDtNascimento(pessoa.getDtNascimento());
        return repository.save(updatePessoa);
    }

}
