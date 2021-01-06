package com.zup.orangeTalents.service;


import com.zup.orangeTalents.entity.Pessoa;
import com.zup.orangeTalents.repository.PessoaRepository;
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

    public List<Pessoa> cadastrarPessoas(List<Pessoa> pessoas){
        return repository.saveAll(pessoas);
    }

    public List<Pessoa> listarPessoas(){
        return repository.findAll();
    }

    public Pessoa listarPessoaId(int id){
        return repository.findById(id).orElse(  null);
    }

    public Pessoa listarPessoaCPF(String cpf){
        return repository.findByCpf(cpf);
    }

    public Pessoa listarPessoaNome(String nome){
        return repository.findByNome(nome);
    }

    public String excluirPessoa(int id){
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