package br.com.coderbank.movimentacaofinanceira.exceptions.custom;

public class ContaException extends RuntimeException {
    public ContaException(String message) {
        super(message);
    }
}
