package models;

import enums.StatusPedido;
import exceptions.PedidoInvalidoException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id;
    private Cliente cliente;
    private List<ItemPedido> itens;
    private StatusPedido status;
    private LocalDateTime dataCriacao;

    public Pedido(int id, Cliente cliente, List<ItemPedido> itens) throws PedidoInvalidoException {
        this.id = id;
        this.cliente = cliente;
        this.itens = new ArrayList<>(itens); 
        this.status = StatusPedido.PENDENTE;
        this.dataCriacao = LocalDateTime.now();
        validar();
    }

    private void validar() throws PedidoInvalidoException {
        if (cliente == null) {
            throw new PedidoInvalidoException("O pedido deve ter um cliente associado");
        }
        if (itens == null || itens.isEmpty()) {
            throw new PedidoInvalidoException("O pedido deve conter pelo menos um item");
        }
    }

    public float calcularValorTotal() {
        float total = 0;
        for (ItemPedido item : itens) {
            total += item.calcularSubtotal();
        }
        return total;
    }

    public void adicionarItem(ItemPedido item) throws PedidoInvalidoException {
        if (item == null) {
            throw new PedidoInvalidoException("O item nao pode ser nulo");
        }
        if (status != StatusPedido.PENDENTE) {
            throw new PedidoInvalidoException(
                "Nao e possivel adicionar itens a um pedido com status: " + status
            );
        }
        itens.add(item);
    }

    public void mudarStatus(StatusPedido novoStatus) throws PedidoInvalidoException {
        if (novoStatus == null) {
            throw new PedidoInvalidoException("O novo status nao pode ser nulo");
        }
        if (!status.podeTransicionarPara(novoStatus)) {
            throw new PedidoInvalidoException(
                String.format("Transicao invalida: %s -> %s", status, novoStatus)
            );
        }
        this.status = novoStatus;
    }

    public int obterIdentificador() {
        return id;
    }

    public Cliente obterCliente() {
        return cliente;
    }

    public List<ItemPedido> obterItens() {
        return new ArrayList<>(itens);
    }

    public StatusPedido obterStatus() {
        return status;
    }

    public LocalDateTime obterDataCriacao() {
        return dataCriacao;
    }

    public int obterQuantidadeItens() {
        return itens.size();
    }

    public boolean podeSerModificado() {
        return status == StatusPedido.PENDENTE;
    }
}
