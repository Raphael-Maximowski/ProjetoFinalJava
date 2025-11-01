package models;

import exceptions.ValidacaoException;

public class ItemPedido {
    private Produto produto;
    private int quantidade;

    public ItemPedido(Produto produto, int quantidade) throws ValidacaoException {
        this.produto = produto;
        this.quantidade = quantidade;
        validar();
    }

    private void validar() throws ValidacaoException {
        if (produto == null) {
            throw new ValidacaoException("O produto nao pode ser nulo");
        }
        if (quantidade <= 0) {
            throw new ValidacaoException(
                "A quantidade deve ser maior que zero. Valor informado: " + quantidade
            );
        }
    }

    public float calcularSubtotal() {
        return produto.obterPreco() * quantidade;
    }

    public Produto obterProduto() {
        return produto;
    }

    public int obterQuantidade() {
        return quantidade;
    }

    public boolean contemProdutoCategoria(enums.CategoriaProduto categoria) {
        return produto.pertenceCategoria(categoria);
    }
}
