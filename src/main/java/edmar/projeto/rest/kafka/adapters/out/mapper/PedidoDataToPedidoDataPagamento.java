package edmar.projeto.rest.kafka.adapters.out.mapper;

import edmar.projeto.rest.kafka.data.PedidoData;
import edmar.projeto.rest.kafka.data.PedidoDataPagamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoDataToPedidoDataPagamento {

    @Autowired
    PedidoDataPagamento pedidoDataPagamento;

    public PedidoDataPagamento toPedidoDataPagamento(PedidoData pedidoData){
        pedidoDataPagamento.setId(pedidoData.getId());
        pedidoDataPagamento.setPago(pedidoData.isPago());
        pedidoDataPagamento.setValor(pedidoData.getValor());
        pedidoDataPagamento.setProduto(pedidoData.getProduto());
        pedidoDataPagamento.setCliente(pedidoData.getCliente());
        return pedidoDataPagamento;
    }

}
