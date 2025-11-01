package services;

import models.Pedido;
import models.Cliente;
import models.Produto;
import models.ItemPedido;
import repositories.RepositorioPedido;
import repositories.RepositorioCliente;
import repositories.RepositorioProduto;
import enums.StatusPedido;
import exceptions.PedidoInvalidoException;
import exceptions.ValidacaoException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PedidoService {
    private RepositorioPedido repositorioPedido;
    private RepositorioCliente repositorioCliente;
    private RepositorioProduto repositorioProduto;

    public PedidoService(RepositorioPedido repositorioPedido,
                        RepositorioCliente repositorioCliente,
                        RepositorioProduto repositorioProduto) {
        this.repositorioPedido = repositorioPedido;
        this.repositorioCliente = repositorioCliente;
        this.repositorioProduto = repositorioProduto;
    }

    public Pedido criarPedido(int clienteId, Map<Integer, Integer> produtosEQuantidades)
            throws PedidoInvalidoException {

        Cliente cliente = repositorioCliente.buscarPorId(clienteId);
        if (cliente == null) {
            throw new PedidoInvalidoException(
                "Cliente com ID " + clienteId + " nao encontrado"
            );
        }

        if (produtosEQuantidades == null || produtosEQuantidades.isEmpty()) {
            throw new PedidoInvalidoException(
                "O pedido deve conter pelo menos um produto"
            );
        }

        List<ItemPedido> itens = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : produtosEQuantidades.entrySet()) {
            int produtoId = entry.getKey();
            int quantidade = entry.getValue();

            Produto produto = repositorioProduto.buscarPorId(produtoId);
            if (produto == null) {
                throw new PedidoInvalidoException(
                    "Produto com ID " + produtoId + " nao encontrado"
                );
            }

            try {
                ItemPedido item = new ItemPedido(produto, quantidade);
                itens.add(item);
            } catch (ValidacaoException e) {
                throw new PedidoInvalidoException(
                    "Erro ao criar item do pedido: " + e.getMessage(), e
                );
            }
        }

        int novoId = repositorioPedido.obterProximoId();
        Pedido pedido = new Pedido(novoId, cliente, itens);
        repositorioPedido.salvar(pedido);

        return pedido;
    }

    public List<Pedido> listarPedidos() {
        return repositorioPedido.listarTodos();
    }

    public List<Pedido> listarPedidosPorStatus(StatusPedido status) {
        return repositorioPedido.buscarPorStatus(status);
    }

    public Pedido buscarPedidoPorId(int id) {
        return repositorioPedido.buscarPorId(id);
    }

    public List<Pedido> listarPedidosPorCliente(int clienteId) {
        Cliente cliente = repositorioCliente.buscarPorId(clienteId);
        if (cliente == null) {
            return new ArrayList<>();
        }
        return repositorioPedido.buscarPorCliente(cliente);
    }
    
    public boolean pedidoExiste(int id) {
        return repositorioPedido.existe(id);
    }

    public int contarPedidos() {
        return repositorioPedido.contarPedidos();
    }

    public void atualizarStatusPedido(int pedidoId, StatusPedido novoStatus)
            throws PedidoInvalidoException {
        Pedido pedido = repositorioPedido.buscarPorId(pedidoId);
        if (pedido == null) {
            throw new PedidoInvalidoException(
                "Pedido com ID " + pedidoId + " nao encontrado"
            );
        }
        pedido.mudarStatus(novoStatus);
        repositorioPedido.salvar(pedido);
    }
}
