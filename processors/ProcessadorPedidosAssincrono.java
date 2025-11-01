package processors;

import models.Pedido;
import enums.StatusPedido;
import exceptions.PedidoInvalidoException;
import java.util.Random;

public class ProcessadorPedidosAssincrono implements ProcessadorPedidos, Runnable {
    private FilaProcessamento fila;
    private Thread threadProcessadora;
    private volatile boolean executando;
    private Random random;

    public ProcessadorPedidosAssincrono(FilaProcessamento fila) {
        this.fila = fila;
        this.executando = false;
        this.random = new Random();
    }

    @Override
    public void processarPedido(Pedido pedido) {
        try {
            pedido.mudarStatus(StatusPedido.PROCESSANDO);

            int tempoProcessamento = 2000 + random.nextInt(3001);
            Thread.sleep(tempoProcessamento);

            pedido.mudarStatus(StatusPedido.ENVIADO);

        } catch (PedidoInvalidoException e) {

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void run() {
        while (executando) {
            try {
                Pedido pedido = fila.obterProximoPedido();
                processarPedido(pedido);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    @Override
    public void iniciar() {
        if (executando) {
            return;
        }

        executando = true;
        threadProcessadora = new Thread(this, "Thread-Processador-Pedidos");
        threadProcessadora.start();
    }

    @Override
    public void parar() {
        if (!executando) {
            return;
        }

        executando = false;

        if (threadProcessadora != null && threadProcessadora.isAlive()) {
            threadProcessadora.interrupt();

            try {
                threadProcessadora.join(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public boolean estaExecutando() {
        return executando;
    }

    public int tamanhoFila() {
        return fila.tamanho();
    }
}
