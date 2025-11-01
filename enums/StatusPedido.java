package enums;

public enum StatusPedido {
    PENDENTE("Pendente"),
    PROCESSANDO("Processando"),
    ENVIADO("Enviado"),
    ENTREGUE("Entregue"),
    CANCELADO("Cancelado");

    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean podeTransicionarPara(StatusPedido novoStatus) {
        if (this == novoStatus) {
            return false; 
        }

        switch (this) {
            case PENDENTE:
                return novoStatus == PROCESSANDO || novoStatus == CANCELADO;
            case PROCESSANDO:
                return novoStatus == ENVIADO || novoStatus == CANCELADO;
            case ENVIADO:
                return novoStatus == ENTREGUE;
            case ENTREGUE:
                return false; 
            case CANCELADO:
                return false;
            default:
                return false;
        }
    }
}
