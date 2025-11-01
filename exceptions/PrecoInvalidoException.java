package exceptions;


public class PrecoInvalidoException extends ValidacaoException {

    public PrecoInvalidoException(String mensagem) {
        super(mensagem);
    }

    public PrecoInvalidoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
