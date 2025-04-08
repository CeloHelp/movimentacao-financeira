package br.com.coderbank.movimentacaofinanceira.exceptions.errormensage;

public final class MensagensErro {

    private MensagensErro() {}

    public static final String CONTA_NAO_ENCONTRADA = "Conta não encontrada.";
    public static final String SALDO_INSUFICIENTE = "Saldo insuficiente para realizar a operação.";
    public static final String VALOR_INVALIDO = "O valor informado deve ser maior que zero.";
    public static final String CONTAS_IGUAIS = "As contas de origem e destino devem ser diferentes.";

    public static final String CPF_INVALIDO = "CPF inválido.";
    public static final String CONTA_JA_EXISTE = "Já existe uma conta com esses dados.";

    public static final String ERRO_INTERNO = "Ocorreu um erro inesperado. Tente novamente mais tarde.";
}
