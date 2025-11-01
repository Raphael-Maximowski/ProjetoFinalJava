package models;

import enums.CategoriaProduto;
import exceptions.PrecoInvalidoException;
import exceptions.NomeInvalidoException;

public class Produto {
    private int id;
    private String nome;
    private float preco;
    private CategoriaProduto categoria;

    public Produto(int id, String nome, float preco, CategoriaProduto categoria)
            throws PrecoInvalidoException, NomeInvalidoException {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
        validarNome();
        validarPreco();
    }

    private void validarNome() throws NomeInvalidoException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new NomeInvalidoException("Nome do produto não pode ser vazio.");
        }
    }

    private void validarPreco() throws PrecoInvalidoException {
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
