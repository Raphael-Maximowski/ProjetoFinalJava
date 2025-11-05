package repositories;

import models.Produto;
import enums.CategoriaProduto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RepositorioProduto implements Repositorio<Produto> {
    private Map<Integer, Produto> produtos;
    private int proximoId;

    public RepositorioProduto() {
        this.produtos = new HashMap<>();
        this.proximoId = 1;
    }

    @Override
    public void salvar(Produto entidade) {
        if (entidade != null) {
            if(this.existe(entidade.obterIdentificador())) {
                throw new IllegalStateException("Não é possível adicionar: produto com o ID " + entidade.obterIdentificador() + " já existe.");
            }
            produtos.put(entidade.obterIdentificador(), entidade);
            if (entidade.obterIdentificador() >= proximoId) {
                proximoId = entidade.obterIdentificador() + 1;
            }
        }
    }

    @Override
    public Produto buscarPorId(int id) {
        return produtos.get(id);
    }

    @Override
    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos.values());
    }

    @Override
    public boolean existe(int id) {
        return produtos.containsKey(id);
    }

    public int obterProximoId() {
        return proximoId++;
    }

    public int contarProdutos() {
        return produtos.size();
    }

    public List<Produto> buscarPorCategoria(CategoriaProduto categoria) {
        return produtos.values().stream()
                .filter(p -> p.pertenceCategoria(categoria))
                .collect(Collectors.toList());
    }
}
