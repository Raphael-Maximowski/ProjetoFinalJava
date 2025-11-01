import repositories.*;
import services.*;
import processors.*;
import models.*;
import enums.CategoriaProduto;
import exceptions.*;

public class Main {

    public static void main(String[] args) {
        RepositorioCliente repositorioCliente = new RepositorioCliente();
        RepositorioProduto repositorioProduto = new RepositorioProduto();
        RepositorioPedido repositorioPedido = new RepositorioPedido();

        FilaProcessamento filaProcessamento = new FilaProcessamento();
        ProcessadorPedidosAssincrono processador =
            new ProcessadorPedidosAssincrono(filaProcessamento);
        processador.iniciar();

        ClienteService clienteService = new ClienteService(repositorioCliente);
        ProdutoService produtoService = new ProdutoService(repositorioProduto);
        PedidoService pedidoService = new PedidoService(
            repositorioPedido,
            repositorioCliente,
            repositorioProduto,
            filaProcessamento
        );

        try {
            Cliente cliente = clienteService.cadastrarCliente("Joao Silva", "joao@email.com");

            Produto produto1 = produtoService.cadastrarProduto("Notebook Dell", 3500.00f, CategoriaProduto.ELETRONICOS);

            Produto produto2 = produtoService.cadastrarProduto("Mouse Logitech", 150.00f, CategoriaProduto.ELETRONICOS);
        } catch (EmailInvalidoException | PrecoInvalidoException | NomeInvalidoException e) {
            System.err.println("      ERRO ao cadastrar dados mockados: " + e.getMessage());
        }

        MenuPrincipal menu = new MenuPrincipal(
            clienteService,
            produtoService,
            pedidoService,
            processador
        );


        menu.exibir();
    }
}
