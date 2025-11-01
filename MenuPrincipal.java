import services.*;
import models.*;
import enums.*;
import exceptions.*;
import processors.ProcessadorPedidos;
import java.util.*;

public class MenuPrincipal {
    private ClienteService clienteService;
    private ProdutoService produtoService;
    private PedidoService pedidoService;
    private ProcessadorPedidos processador;
    private Scanner scanner;

    public MenuPrincipal(ClienteService clienteService,
                        ProdutoService produtoService,
                        PedidoService pedidoService,
                        ProcessadorPedidos processador) {
        this.clienteService = clienteService;
        this.produtoService = produtoService;
        this.pedidoService = pedidoService;
        this.processador = processador;
        this.scanner = new Scanner(System.in);
    }

    public void exibir() {
        boolean continuar = true;

        while (continuar) {
            exibirMenu();
            int opcao = lerInteiro("Escolha uma opcao: ");

            switch (opcao) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    cadastrarProduto();
                    break;
                case 3:
                    criarPedido();
                    break;
                case 4:
                    listarClientes();
                    break;
                case 5:
                    listarProdutos();
                    break;
                case 6:
                    listarPedidos();
                    break;
                case 7:
                    listarPedidosPorStatus();
                    break;
                case 8:
                    System.out.println("\n=== Encerrando sistema ===");
                    continuar = false;
                    break;
                default:
                    System.out.println("\nOpcao invalida! Tente novamente.");
            }
        }

        scanner.close();
    }

    private void exibirMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("       SISTEMA DE GESTAO DE PEDIDOS");
        System.out.println("=".repeat(50));
        System.out.println("1. Cadastrar Cliente");
        System.out.println("2. Cadastrar Produto");
        System.out.println("3. Criar Pedido");
        System.out.println("4. Listar Clientes");
        System.out.println("5. Listar Produtos");
        System.out.println("6. Listar Pedidos");
        System.out.println("7. Listar Pedidos por Status");
        System.out.println("8. Sair");
        System.out.println("=".repeat(50));
    }

    private void cadastrarCliente() {
        System.out.println("\n--- CADASTRAR CLIENTE ---");
        String nome = lerString("Nome: ");
        String email = lerString("Email: ");

        try {
            Cliente cliente = clienteService.cadastrarCliente(nome, email);
            System.out.println("\n[SUCESSO] Cliente cadastrado!");
            System.out.println("  ID: " + cliente.obterId());
            System.out.println("  Nome: " + cliente.obterNome());
            System.out.println("  Email: " + cliente.obterEmail());
        } catch (EmailInvalidoException | NomeInvalidoException e) {
            System.out.println("\n[ERRO] " + e.getMessage());
        }
    }

    private void cadastrarProduto() {
        System.out.println("\n--- CADASTRAR PRODUTO ---");
        String nome = lerString("Nome: ");
        float preco = lerFloat("Preco: R$ ");
        CategoriaProduto categoria = selecionarCategoria();

        try {
            Produto produto = produtoService.cadastrarProduto(nome, preco, categoria);
            System.out.println("\n[SUCESSO] Produto cadastrado!");
            System.out.println("  ID: " + produto.obterIdentificador());
            System.out.println("  Nome: " + produto.obterNome());
            System.out.println("  Preco: R$ " + String.format("%.2f", produto.obterPreco()));
            System.out.println("  Categoria: " + produto.obterCategoria());
        } catch (PrecoInvalidoException | NomeInvalidoException e) {
            System.out.println("\n[ERRO] " + e.getMessage());
        }
    }

    private void criarPedido() {
        System.out.println("\n--- CRIAR PEDIDO ---");

        List<Cliente> clientes = clienteService.listarClientes();
        if (clientes.isEmpty()) {
            System.out.println("\n[AVISO] Nenhum cliente cadastrado. Cadastre um cliente primeiro.");
            return;
        }

        System.out.println("\nClientes disponiveis:");
        for (Cliente c : clientes) {
            System.out.println("  ID " + c.obterId() + ": " + c.obterNome());
        }

        int clienteId = lerInteiro("\nID do Cliente: ");

        List<Produto> produtos = produtoService.listarProdutos();
        if (produtos.isEmpty()) {
            System.out.println("\n[AVISO] Nenhum produto cadastrado. Cadastre produtos primeiro.");
            return;
        }

        System.out.println("\nProdutos disponiveis:");
        for (Produto p : produtos) {
            System.out.println("  ID " + p.obterIdentificador() + ": " + p.obterNome()
                + " - R$ " + String.format("%.2f", p.obterPreco()));
        }

        Map<Integer, Integer> produtosEQuantidades = new HashMap<>();
        boolean adicionarMais = true;

        while (adicionarMais) {
            System.out.println("\n--- Adicionar Produto ao Pedido ---");
            int produtoId = lerInteiro("ID do Produto (0 para finalizar): ");

            if (produtoId == 0) {
                adicionarMais = false;
            } else {
                int quantidade = lerInteiro("Quantidade: ");
                produtosEQuantidades.put(produtoId, quantidade);
                System.out.println("[OK] Produto adicionado ao pedido.");
            }
        }

        if (produtosEQuantidades.isEmpty()) {
            System.out.println("\n[AVISO] Pedido cancelado. Nenhum produto foi adicionado.");
            return;
        }

        try {
            Pedido pedido = pedidoService.criarPedido(clienteId, produtosEQuantidades);
            System.out.println("\n[SUCESSO] Pedido criado!");
            System.out.println("  ID: " + pedido.obterIdentificador());
            System.out.println("  Cliente: " + pedido.obterCliente().obterNome());
            System.out.println("  Itens: " + pedido.obterQuantidadeItens());
            System.out.println("  Valor Total: R$ " + String.format("%.2f", pedido.calcularValorTotal()));
            System.out.println("  Status: " + pedido.obterStatus());
            System.out.println("\n[INFO] Pedido enviado para processamento em segundo plano!");
        } catch (PedidoInvalidoException e) {
            System.out.println("\n[ERRO] " + e.getMessage());
        }
    }

    private void listarClientes() {
        System.out.println("\n--- LISTA DE CLIENTES ---");
        List<Cliente> clientes = clienteService.listarClientes();

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            System.out.println("Total: " + clientes.size() + " cliente(s)\n");
            for (Cliente c : clientes) {
                System.out.println("ID: " + c.obterId());
                System.out.println("Nome: " + c.obterNome());
                System.out.println("Email: " + c.obterEmail());
                System.out.println("-".repeat(40));
            }
        }
    }

    private void listarProdutos() {
        System.out.println("\n--- LISTA DE PRODUTOS ---");
        List<Produto> produtos = produtoService.listarProdutos();

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
        } else {
            System.out.println("Total: " + produtos.size() + " produto(s)\n");
            for (Produto p : produtos) {
                System.out.println("ID: " + p.obterIdentificador());
                System.out.println("Nome: " + p.obterNome());
                System.out.println("Preco: R$ " + String.format("%.2f", p.obterPreco()));
                System.out.println("Categoria: " + p.obterCategoria());
                System.out.println("-".repeat(40));
            }
        }
    }

    private void listarPedidos() {
        System.out.println("\n--- LISTA DE PEDIDOS ---");
        List<Pedido> pedidos = pedidoService.listarPedidos();

        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido cadastrado.");
        } else {
            System.out.println("Total: " + pedidos.size() + " pedido(s)\n");
            for (Pedido p : pedidos) {
                exibirPedido(p);
            }
        }
    }

    private void listarPedidosPorStatus() {
        System.out.println("\n--- LISTAR PEDIDOS POR STATUS ---");
        StatusPedido status = selecionarStatus();

        List<Pedido> pedidos = pedidoService.listarPedidosPorStatus(status);

        if (pedidos.isEmpty()) {
            System.out.println("\nNenhum pedido com status: " + status);
        } else {
            System.out.println("\nTotal: " + pedidos.size() + " pedido(s) com status " + status + "\n");
            for (Pedido p : pedidos) {
                exibirPedido(p);
            }
        }
    }

    private void exibirPedido(Pedido pedido) {
        System.out.println("ID: " + pedido.obterIdentificador());
        System.out.println("Cliente: " + pedido.obterCliente().obterNome());
        System.out.println("Status: " + pedido.obterStatus());
        System.out.println("Itens: " + pedido.obterQuantidadeItens());
        System.out.println("Valor Total: R$ " + String.format("%.2f", pedido.calcularValorTotal()));
        System.out.println("Data: " + pedido.obterDataCriacao());
        System.out.println("-".repeat(40));
    }

    private CategoriaProduto selecionarCategoria() {
        System.out.println("\nCategorias:");
        CategoriaProduto[] categorias = CategoriaProduto.values();
        for (int i = 0; i < categorias.length; i++) {
            System.out.println((i + 1) + ". " + categorias[i]);
        }

        int opcao;
        do {
            opcao = lerInteiro("Escolha a categoria: ");
            if (opcao < 1 || opcao > categorias.length) {
                System.out.println("Opcao invalida! Escolha um numero entre 1 e " + categorias.length);
            }
        } while (opcao < 1 || opcao > categorias.length);

        return categorias[opcao - 1];
    }

    private StatusPedido selecionarStatus() {
        System.out.println("\nStatus:");
        StatusPedido[] status = StatusPedido.values();
        for (int i = 0; i < status.length; i++) {
            System.out.println((i + 1) + ". " + status[i]);
        }

        int opcao;
        do {
            opcao = lerInteiro("Escolha o status: ");
            if (opcao < 1 || opcao > status.length) {
                System.out.println("Opcao invalida! Escolha um numero entre 1 e " + status.length);
            }
        } while (opcao < 1 || opcao > status.length);

        return status[opcao - 1];
    }

    private int lerInteiro(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Valor invalido. " + prompt);
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }

    private float lerFloat(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextFloat()) {
            System.out.print("Valor invalido. " + prompt);
            scanner.next();
        }
        float valor = scanner.nextFloat();
        scanner.nextLine();
        return valor;
    }

    private String lerString(String prompt) {
        String valor;
        do {
            System.out.print(prompt);
            valor = scanner.nextLine();
            if (valor.trim().isEmpty()) {
                System.out.println("Valor não pode ser vazio. Tente novamente.");
            }
        } while (valor.trim().isEmpty());
        return valor;
    }
}
