package com.idwall.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idwall.entity.Pessoa;
import com.idwall.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
    @Autowired
    private PessoaRepository pessoaRepository;

    @GetMapping
    public ResponseEntity<List<Pessoa>> listarPessoas() {
        List<Pessoa> pessoas = pessoaRepository.findAll();
        if (pessoas.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(pessoas);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> getPessoa(@PathVariable Long id) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        if (pessoa.isPresent()) {
            return ResponseEntity.ok(pessoa.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/filtro")
    public ResponseEntity<List<Pessoa>> filtrarPessoas(
            @RequestParam(required = false) String crime,
            @RequestParam(required = false) String nacionalidade,
            @RequestParam(required = false) Integer idade,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String sobrenome,
            @RequestParam(required = false) String passaporte,
            @RequestParam(required = false) Boolean comCrimes,
            @RequestParam(required = false) Boolean semCrimes
    ) {
        List<Pessoa> pessoasFiltradas = new ArrayList<>();

        List<Pessoa> todasPessoas = pessoaRepository.findAll();

        for (Pessoa pessoa : todasPessoas) {
            if (comCrimes != null && comCrimes && (pessoa.getCrimes() == null || pessoa.getCrimes().isEmpty())) {
                continue;
            }
            if (semCrimes != null && semCrimes && (pessoa.getCrimes() != null && !pessoa.getCrimes().isEmpty())) {
                continue;
            }
            if (crime != null && !pessoa.getCrimes().contains(crime)) {
                continue;
            }
            if (nacionalidade != null && !pessoa.getNacionalidade().equals(nacionalidade)) {
                continue;
            }
            if (idade != null && pessoa.getIdade() != idade) {
                continue;
            }
            if (nome != null && !pessoa.getNome().equals(nome)) {
                continue;
            }
            if (sobrenome != null && !pessoa.getSobrenome().equals(sobrenome)) {
                continue;
            }
            if (passaporte != null && !pessoa.getPassaporte().equals(passaporte)) {
                continue;
            }
            pessoasFiltradas.add(pessoa);
        }

        if (pessoasFiltradas.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(pessoasFiltradas);
        }
    }


    @PostMapping
    public ResponseEntity<Object> adicionarPessoa(@RequestBody Pessoa pessoa) {
        if (pessoaRepository.existsByPassaporte(pessoa.getPassaporte())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe uma pessoa com o mesmo número de passaporte. Tente Novamente.");
        }

        Pessoa novaPessoa = pessoaRepository.save(pessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body("Pessoa adicionada com sucesso. ID: " + novaPessoa.getId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarPessoa(@PathVariable Long id, @RequestBody Pessoa pessoaAtualizada) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        if (pessoa.isPresent()) {
            pessoaAtualizada.setId(id);
            pessoaRepository.save(pessoaAtualizada);
            return ResponseEntity.ok("Pessoa atualizada com sucesso. ID: " + id);
        } else {
            return ResponseEntity.notFound().build();
        }
        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarPessoa(@PathVariable Long id) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        if (pessoa.isPresent()) {
            pessoaRepository.delete(pessoa.get());
            return ResponseEntity.ok("Pessoa deletada com sucesso. ID: " + id);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

