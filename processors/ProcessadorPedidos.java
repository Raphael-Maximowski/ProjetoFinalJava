package processors;

import models.Pedido;

public interface ProcessadorPedidos {

    void processarPedido(Pedido pedido);

    void iniciar();

    void parar();
}
