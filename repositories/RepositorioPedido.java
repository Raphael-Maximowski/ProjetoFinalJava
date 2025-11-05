package repositories;

import models.Pedido;
import models.Cliente;
import enums.StatusPedido;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class RepositorioPedido implements Repositorio<Pedido> {
    private Map<Integer, Pedido> pedidos;
    private int proximoId;

    public RepositorioPedido() {
        this.pedidos = new HashMap<>();
        this.proximoId = 1;
    }

    @Override
    public void salvar(Pedido entidade) {
        if (entidade != null) {
            if(this.existe(entidade.obterIdentificador())) {
                throw new IllegalStateException("Não é possível adicionar: pedido com o ID " + entidade.obterIdentificador() + " já existe.");
            }

            pedidos.put(entidade.obterIdentificador(), entidade);

            if (entidade.obterIdentificador() >= proximoId) {
                proximoId = entidade.obterIdentificador() + 1;
            }
        }
    }

    @Override
    public Pedido buscarPorId(int id) {
        return pedidos.get(id);
    }

    @Override
    public List<Pedido> listarTodos() {
        return new ArrayList<>(pedidos.values());
    }

    @Override
    public boolean existe(int id) {
        return pedidos.containsKey(id);
    }

    public int obterProximoId() {
        return proximoId++;
    }

    public int contarPedidos() {
        return pedidos.size();
    }

    public List<Pedido> buscarPorCliente(Cliente cliente) {
        if (cliente == null) {
            return new ArrayList<>();
        }
        return pedidos.values().stream()
                .filter(p -> p.obterCliente().obterId() == cliente.obterId())
                .collect(Collectors.toList());
    }

    public List<Pedido> buscarPorStatus(StatusPedido status) {
        if (status == null) {
            return new ArrayList<>();
        }
        return pedidos.values().stream()
                .filter(p -> p.obterStatus() == status)
                .collect(Collectors.toList());
    }
}
