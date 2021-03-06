package com.zup.orangeTalents.controller;

import com.zup.orangeTalents.dto.PessoaDto;
import com.zup.orangeTalents.entity.Pessoa;
import com.zup.orangeTalents.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import com.zup.orangeTalents.responses.Response;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class PessoaController {


    @Autowired
    private PessoaService service;


    @PostMapping("/pessoa")
    public ResponseEntity<Pessoa> adicionarPessoa(@Valid @RequestBody PessoaDto pessoaDto, BindingResult result) {
        Response<PessoaDto> response = new Response<PessoaDto>();

        if (result.hasErrors()) {
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            List<String> errors = response.getErrors();
            System.out.println(errors);
            return ResponseEntity.badRequest().build();

        }
        Pessoa pessoa = service.cadastrarPessoa(pessoaDto.toPessoa());


        return ResponseEntity.created(URI.create("htttp://localhost:3000/pessoa/" + pessoa.getId())).body(pessoa);

    }

    @GetMapping("/pessoas")
    public ResponseEntity<List<Pessoa>> listarPessoas(){

        return ResponseEntity.ok().body(service.listarPessoas());
    }

    @GetMapping("/pessoaId/{id}")
    public ResponseEntity<Pessoa> listarPessoaId(@PathVariable("id") Long id){
        try {
            return ResponseEntity.ok().body(service.listarPessoaId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }
    @GetMapping("/pessoaCPF/{cpf}")
    public ResponseEntity<Pessoa> listarPessoaCpf(@PathVariable("cpf") String cpf) {

        try {
           return ResponseEntity.ok().body(service.listarPessoaCPF(cpf));

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }



    @PutMapping("/atualizar")
    public ResponseEntity<Pessoa> atualizarPessoa(@RequestBody Pessoa pessoa) {
       try {
           service.atualizarPessoa(pessoa);
           return  ResponseEntity.ok().build();

       } catch (NullPointerException error){
           return ResponseEntity.notFound().build();
       }

    }


    @DeleteMapping("/excluir/{id}")
    public  ResponseEntity<String> excluirPessoa(@PathVariable("id") Long id) {

        try {
            service.excluirPessoa(id);
            return ResponseEntity.ok().body("Excluido id:" + id) ;
        } catch (EmptyResultDataAccessException error) {
            return ResponseEntity.notFound().build();
        }



    }



}
