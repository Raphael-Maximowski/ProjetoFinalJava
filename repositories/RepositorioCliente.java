package repositories;

import models.Cliente;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositorioCliente implements Repositorio<Cliente> {
    private Map<Integer, Cliente> clientes;
    private int proximoId;

    public RepositorioCliente() {
        this.clientes = new HashMap<>();
        this.proximoId = 1;
    }

    @Override
    public void salvar(Cliente entidade) {
        if (entidade != null) {
            clientes.put(entidade.obterId(), entidade);
            if (entidade.obterId() >= proximoId) {
                proximoId = entidade.obterId() + 1;
            }
        }
    }

    @Override
    public Cliente buscarPorId(int id) {
        return clientes.get(id);
    }

    @Override
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes.values());
    }

    @Override
    public boolean existe(int id) {
        return clientes.containsKey(id);
    }

    public int obterProximoId() {
        return proximoId++;
    }

    public int contarClientes() {
        return clientes.size();
    }
}
