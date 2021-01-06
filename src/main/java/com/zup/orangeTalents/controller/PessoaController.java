package com.zup.orangeTalents.controller;

import com.zup.orangeTalents.entity.Pessoa;
import com.zup.orangeTalents.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PessoaController {


    @Autowired
    private PessoaService service;

    @PostMapping("/pessoa")
    public Pessoa adicionarPessoa(@RequestBody Pessoa pessoa){
        return  service.cadastrarPessoa(pessoa);

    }

    @GetMapping("/pessoas")
    public List<Pessoa> listarPessoas(){
        return service.listarPessoas();
    }
    @GetMapping("/pessoaId/{id}")
    public Pessoa listarPessoaId(@PathVariable int id){
        return service.listarPessoaId(id);
    }
    @GetMapping("/pessoaCPF/{cpf}")
    public Pessoa listarPessoaCpf(@PathVariable String cpf){
        return service.listarPessoaCPF(cpf);
    }


    @PutMapping("/atualizar")
    public Pessoa atualizarPessoa(@RequestBody Pessoa pessoa) {
        return  service.atualizarPessoa(pessoa);
    }


    @DeleteMapping("/excluir/{id}")
    public  String excluirPessoa(@PathVariable int id) {
        return  service.excluirPessoa(id);

    }



}
