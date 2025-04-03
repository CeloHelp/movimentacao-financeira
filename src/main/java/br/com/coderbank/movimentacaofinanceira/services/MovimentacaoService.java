package br.com.coderbank.movimentacaofinanceira.services;

import br.com.coderbank.movimentacaofinanceira.dtos.response.MovimentacaoResponseDTO;
import br.com.coderbank.movimentacaofinanceira.entities.Movimentacao;
import br.com.coderbank.movimentacaofinanceira.repositories.MovimentacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MovimentacaoService {
    private MovimentacaoRepository movimentacaoRepository;

    public MovimentacaoService(MovimentacaoRepository movimentacaoRepository) {
        this.movimentacaoRepository = movimentacaoRepository;
    }

    public List<MovimentacaoResponseDTO> buscarPorConta(UUID contaId) {
        List<Movimentacao> movimentacoes= movimentacaoRepository.findByContaIdOrderByDataHora(contaId);

        return movimentacoes.stream()
                .map(m -> new MovimentacaoResponseDTO(
                        m.getDataHora(),
                        m.getTipo(),
                        m.getValor(),
                        m.getContaDestino()
                ))
                .collect(Collectors.toList());


    }
}
