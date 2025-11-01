package models;

import enums.CategoriaProduto;
import exceptions.PrecoInvalidoException;

public class Produto {
    private int id;
    private String nome;
    private float preco;
    private CategoriaProduto categoria;

    public Produto(int id, String nome, float preco, CategoriaProduto categoria)
            throws PrecoInvalidoException {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
        validarPreco();
    }

    private void validarPreco() throws PrecoInvalidoException {
        if (preco == null) {
            throw new PrecoInvalidoException("O preco nao pode ser nulo");
        }
        if (preco <= 0) {
            throw new PrecoInvalidoException(
                "O preco deve ser maior que zero. Valor informado: " + preco
            );
        }
    }


    public int obterIdentificador() {
        return id;
    }

    public String obterNome() {
        return nome;
    }
    
    public float obterPreco() {
        return preco;
    }

    public CategoriaProduto obterCategoria() {
        return categoria;
    }

    public boolean pertenceCategoria(CategoriaProduto categoriaEspecifica) {
        return this.categoria == categoriaEspecifica;
    }
}
