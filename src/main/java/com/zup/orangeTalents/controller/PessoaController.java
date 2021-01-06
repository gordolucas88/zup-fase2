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
    @PostMapping("/pessoas")
    public List<Pessoa> adicionarPessoas(@RequestBody List<Pessoa> pessoas){
        return  service.cadastrarPessoas(pessoas);

    }

    @GetMapping("/pessoas")
    public List<Pessoa> listarPessoas(){
        return service.listarPessoas();
    }
    @GetMapping("/pessoas/{id}")
    public Pessoa listarPessoaId(@PathVariable int id){
        return service.listarPessoaId(id);
    }
    @GetMapping("/pessoas/{cpf}")
    public Pessoa listarPessoaCpf(@PathVariable String cpf){
        return service.listarPessoaCPF(cpf);
    }
    @GetMapping("/pessoas/{nome}")
    public Pessoa listarPessoaNome(@PathVariable String nome){
        return service.listarPessoaNome(nome);
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
