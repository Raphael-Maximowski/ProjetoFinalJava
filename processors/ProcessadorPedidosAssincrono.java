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
            System.out.println("[PROCESSADOR] Iniciando processamento do pedido ID: "
                + pedido.obterIdentificador());

            pedido.mudarStatus(StatusPedido.PROCESSANDO);
            System.out.println("[PROCESSADOR] Pedido " + pedido.obterIdentificador()
                + " -> Status: PROCESSANDO");

            int tempoProcessamento = 2000 + random.nextInt(3001); 
            System.out.println("[PROCESSADOR] Processando pedido "
                + pedido.obterIdentificador()
                + " (tempo estimado: " + (tempoProcessamento/1000.0) + "s)");
            Thread.sleep(tempoProcessamento);

            pedido.mudarStatus(StatusPedido.ENVIADO);
            System.out.println("[PROCESSADOR] Pedido " + pedido.obterIdentificador()
                + " -> Status: ENVIADO");

            System.out.println("[PROCESSADOR] Pedido " + pedido.obterIdentificador()
                + " processado com sucesso!");

        } catch (PedidoInvalidoException e) {
            System.err.println("[PROCESSADOR] Erro ao processar pedido "
                + pedido.obterIdentificador() + ": " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("[PROCESSADOR] Processamento interrompido para pedido "
                + pedido.obterIdentificador());
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void run() {
        System.out.println("[PROCESSADOR] Thread iniciada. Aguardando pedidos...");

        while (executando) {
            try {
                Pedido pedido = fila.obterProximoPedido();
                processarPedido(pedido);

            } catch (InterruptedException e) {
                System.out.println("[PROCESSADOR] Thread interrompida.");
                Thread.currentThread().interrupt();
                break;
            }
        }

        System.out.println("[PROCESSADOR] Thread finalizada.");
    }

    @Override
    public void iniciar() {
        if (executando) {
            System.out.println("[PROCESSADOR] Processador ja esta em execucao.");
            return;
        }

        executando = true;
        threadProcessadora = new Thread(this, "Thread-Processador-Pedidos");
        threadProcessadora.start();
        System.out.println("[PROCESSADOR] Processador iniciado com sucesso!");
    }

    @Override
    public void parar() {
        if (!executando) {
            System.out.println("[PROCESSADOR] Processador ja esta parado.");
            return;
        }

        System.out.println("[PROCESSADOR] Parando processador...");
        executando = false;

        if (threadProcessadora != null && threadProcessadora.isAlive()) {
            threadProcessadora.interrupt();

            try {
                threadProcessadora.join(5000);
                System.out.println("[PROCESSADOR] Processador parado com sucesso!");
            } catch (InterruptedException e) {
                System.err.println("[PROCESSADOR] Erro ao aguardar thread terminar.");
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
