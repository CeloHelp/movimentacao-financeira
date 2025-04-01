package br.com.coderbank.movimentacaofinanceira.controller;

import br.com.coderbank.movimentacaofinanceira.dtos.request.ContaClienteRequestDTO;
import br.com.coderbank.movimentacaofinanceira.dtos.request.DepositoRequestDTO;
import br.com.coderbank.movimentacaofinanceira.dtos.request.SaqueRequestDTO;
import br.com.coderbank.movimentacaofinanceira.dtos.response.ContaClienteResponseDTO;
import br.com.coderbank.movimentacaofinanceira.dtos.response.SaldoResponseDTO;
import br.com.coderbank.movimentacaofinanceira.entities.ContaCliente;
import br.com.coderbank.movimentacaofinanceira.services.ContaClienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/criarconta")
public class ContaClienteControllerV1 {
    private final ContaClienteService service;
    private final ContaClienteService contaClienteService;

    public ContaClienteControllerV1(ContaClienteService service, ContaClienteService contaClienteService) {
        this.service = service;
        this.contaClienteService = contaClienteService;
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

    @PatchMapping("/{id}/deposito")
    public ResponseEntity<ContaClienteResponseDTO> depositar(
            @PathVariable UUID id,
            @RequestBody @Valid DepositoRequestDTO depositoRequestDTO) {
        ContaCliente contaAtualizada = contaClienteService.depositar(id, depositoRequestDTO.valor());

        ContaClienteResponseDTO response = new ContaClienteResponseDTO(
                contaAtualizada.getId(),
                contaAtualizada.getTitular(),
                contaAtualizada.getCpf(),
                contaAtualizada.getNumeroConta(),
                contaAtualizada.getAgencia(),
                contaAtualizada.getSaldo()
        );
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/saque")
    public ResponseEntity<ContaClienteResponseDTO> sacar(
            @PathVariable UUID id,
            @RequestBody @Valid SaqueRequestDTO saqueRequestDTO) {

        ContaCliente contaAtualizada = contaClienteService.sacar(id, saqueRequestDTO.valor());

        ContaClienteResponseDTO response = new ContaClienteResponseDTO(
                contaAtualizada.getId(),
                contaAtualizada.getTitular(),
                contaAtualizada.getCpf(),
                contaAtualizada.getNumeroConta(),
                contaAtualizada.getAgencia(),
                contaAtualizada.getSaldo()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/saldo")
    public ResponseEntity<SaldoResponseDTO> consultarSaldo(@PathVariable UUID id) {
        ContaCliente conta = contaClienteService.consultarSaldo(id);

        SaldoResponseDTO response = new SaldoResponseDTO(conta.getTitular(), conta.getSaldo());

        return ResponseEntity.ok(response);
    }




}
