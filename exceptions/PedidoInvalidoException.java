package exceptions;

public class PedidoInvalidoException extends ValidacaoException {

    public PedidoInvalidoException(String mensagem) {
        super(mensagem);
    }

    public PedidoInvalidoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
