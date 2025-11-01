package exceptions;

public class EmailInvalidoException extends ValidacaoException {

    public EmailInvalidoException(String mensagem) {
        super(mensagem);
    }

    public EmailInvalidoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}

