package br.com.coderbank.movimentacaofinanceira.controller;

import br.com.coderbank.movimentacaofinanceira.dtos.request.ContaClienteRequestDTO;
import br.com.coderbank.movimentacaofinanceira.dtos.request.DepositoRequestDTO;
import br.com.coderbank.movimentacaofinanceira.dtos.request.SaqueRequestDTO;
import br.com.coderbank.movimentacaofinanceira.dtos.request.TransferenciaRequestDTO;
import br.com.coderbank.movimentacaofinanceira.dtos.response.ContaClienteResponseDTO;
import br.com.coderbank.movimentacaofinanceira.dtos.response.DepositoResponseDTO;
import br.com.coderbank.movimentacaofinanceira.dtos.response.MovimentacaoResponseDTO;
import br.com.coderbank.movimentacaofinanceira.dtos.response.SaldoResponseDTO;
import br.com.coderbank.movimentacaofinanceira.entities.ContaCliente;
import br.com.coderbank.movimentacaofinanceira.repositories.ContaClienteRepository;
import br.com.coderbank.movimentacaofinanceira.services.ContaClienteService;
import br.com.coderbank.movimentacaofinanceira.services.MovimentacaoService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;



@RestController
@RequestMapping("/api/v1/contas")
public class ContaClienteControllerV1 {
    private final ContaClienteService service;
    private final ContaClienteService contaClienteService;
    private final MovimentacaoService movimentacaoService;

    public ContaClienteControllerV1(ContaClienteService service, ContaClienteService contaClienteService, MovimentacaoService movimentacaoService) {
        this.service = service;
        this.contaClienteService = contaClienteService;
        this.movimentacaoService = movimentacaoService;
    }






    @PostMapping
    public ResponseEntity<ContaClienteResponseDTO> criarConta(@RequestBody @Valid ContaClienteRequestDTO contaClienteRequestDTO) {

        ContaClienteResponseDTO response = service.criarConta(contaClienteRequestDTO);

        URI location = URI.create("api/v1/contas" + response.id());

        return ResponseEntity.created(location).body(response);


    }

    @PatchMapping("/{id}/deposito")
    public ResponseEntity<ContaClienteResponseDTO> depositar(
            @PathVariable UUID id,
            @RequestBody @Valid DepositoRequestDTO depositoRequestDTO) {
        ContaCliente contaAtualizada = contaClienteService.depositar(id,depositoRequestDTO.valor());

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

    @PostMapping("/transferencia")
    public ResponseEntity<ContaClienteResponseDTO> transferenciaEntreContas(@RequestBody @Valid TransferenciaRequestDTO transferenciaRequestDTO) {

        ContaCliente contaAtualizada = contaClienteService.transferirEntreContas(
                transferenciaRequestDTO.contaorigem(),
                transferenciaRequestDTO.contadestino(),
                transferenciaRequestDTO.valor()
        );

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

    @GetMapping("/{id}/movimentacoes")
    public  ResponseEntity<List<MovimentacaoResponseDTO>> listarMovimentacoes( @PathVariable UUID id) {
        List<MovimentacaoResponseDTO> buscarConta = movimentacaoService.buscarPorConta(id);

        for (MovimentacaoResponseDTO movimentacao : buscarConta) {
            System.out.println(movimentacao.dataHora());
        }

       return ResponseEntity.ok(buscarConta);







    }




}
