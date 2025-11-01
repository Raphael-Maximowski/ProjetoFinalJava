package services;

import models.Produto;
import repositories.RepositorioProduto;
import enums.CategoriaProduto;
import exceptions.PrecoInvalidoException;
import exceptions.NomeInvalidoException;
import java.util.List;

public class ProdutoService {
    private RepositorioProduto repositorioProduto;

    public ProdutoService(RepositorioProduto repositorioProduto) {
        this.repositorioProduto = repositorioProduto;
    }

    public Produto cadastrarProduto(String nome, float preco, CategoriaProduto categoria)
            throws PrecoInvalidoException, NomeInvalidoException {
        int novoId = repositorioProduto.obterProximoId();
        Produto produto = new Produto(novoId, nome, preco, categoria);
        repositorioProduto.salvar(produto);
        return produto;
    }

    public List<Produto> listarProdutos() {
        return repositorioProduto.listarTodos();
    }

    public List<Produto> listarProdutosPorCategoria(CategoriaProduto categoria) {
        return repositorioProduto.buscarPorCategoria(categoria);
    }

    public Produto buscarProdutoPorId(int id) {
        return repositorioProduto.buscarPorId(id);
    }

    public boolean produtoExiste(int id) {
        return repositorioProduto.existe(id);
    }

    public int contarProdutos() {
        return repositorioProduto.contarProdutos();
    }
}
