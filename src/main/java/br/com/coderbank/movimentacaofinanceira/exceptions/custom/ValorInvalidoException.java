package br.com.coderbank.movimentacaofinanceira.exceptions.custom;

public class ValorInvalidoException extends RuntimeException {
    public ValorInvalidoException(String message) {
        super(message);
    }
}
