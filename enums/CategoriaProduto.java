package enums;

public enum CategoriaProduto {
    ALIMENTOS("Alimentos"),
    ELETRONICOS("Eletronicos"),
    LIVROS("Livros");

    private String descricao;

    CategoriaProduto(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
