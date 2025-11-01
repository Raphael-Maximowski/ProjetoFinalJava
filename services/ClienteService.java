package services;

import models.Cliente;
import repositories.RepositorioCliente;
import exceptions.EmailInvalidoException;
import exceptions.NomeInvalidoException;
import java.util.List;

public class ClienteService {
    private RepositorioCliente repositorioCliente;

    public ClienteService(RepositorioCliente repositorioCliente) {
        this.repositorioCliente = repositorioCliente;
    }

    public Cliente cadastrarCliente(String nome, String email) throws EmailInvalidoException, NomeInvalidoException {
        int novoId = repositorioCliente.obterProximoId();
        Cliente cliente = new Cliente(novoId, nome, email);
        repositorioCliente.salvar(cliente);
        return cliente;
    }

    public List<Cliente> listarClientes() {
        return repositorioCliente.listarTodos();
    }

    public Cliente buscarClientePorId(int id) {
        return repositorioCliente.buscarPorId(id);
    }

    public boolean clienteExiste(int id) {
        return repositorioCliente.existe(id);
    }

    public int contarClientes() {
        return repositorioCliente.contarClientes();
    }
}
