package br.com.coderbank.movimentacaofinanceira.controller;

import br.com.coderbank.movimentacaofinanceira.dtos.request.ContaClienteRequestDTO;
import br.com.coderbank.movimentacaofinanceira.dtos.response.ContaClienteResponseDTO;
import br.com.coderbank.movimentacaofinanceira.entities.ContaCliente;
import br.com.coderbank.movimentacaofinanceira.services.ContaClienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/criarconta")
public class ContaClienteControllerV1 {
    private final ContaClienteService service;

    public ContaClienteControllerV1(ContaClienteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ContaClienteResponseDTO> criarConta(@RequestBody @Valid ContaClienteRequestDTO dto) {
        ContaCliente conta = service.criarConta(dto.titular(), dto.cpf());

        ContaClienteResponseDTO response = new ContaClienteResponseDTO(
                conta.getId(),
                conta.getTitular(),
                conta.getCpf(),
                conta.getNumeroConta(),
                conta.getAgencia(),
                conta.getSaldo()
        );

        return ResponseEntity.ok(response);
    }
}
