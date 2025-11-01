package processors;

import models.Pedido;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FilaProcessamento {
    private BlockingQueue<Pedido> fila;

    public FilaProcessamento() {
        this.fila = new LinkedBlockingQueue<>();
    }

    public FilaProcessamento(int capacidade) {
        this.fila = new LinkedBlockingQueue<>(capacidade);
    }

    public void adicionarPedido(Pedido pedido) throws InterruptedException {
        if (pedido != null) {
            fila.put(pedido);
        }
    }

    public Pedido obterProximoPedido() throws InterruptedException {
        return fila.take();
    }

    public int tamanho() {
        return fila.size();
    }

    public boolean estaVazia() {
        return fila.isEmpty();
    }

    public void limpar() {
        fila.clear();
    }
}
