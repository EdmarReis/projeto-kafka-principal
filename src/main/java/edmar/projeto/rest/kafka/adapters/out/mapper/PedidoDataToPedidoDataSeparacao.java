package edmar.projeto.rest.kafka.adapters.out.mapper;

import edmar.projeto.rest.kafka.data.PedidoData;
import edmar.projeto.rest.kafka.data.PedidoDataSeparacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoDataToPedidoDataSeparacao {

    @Autowired
    PedidoDataSeparacao pedidoDataSeparacao;

    public PedidoDataSeparacao toPedidoDataSeparacao(PedidoData pedidoData){
        pedidoDataSeparacao.setId(pedidoData.getId());
        pedidoDataSeparacao.setSeparado(pedidoData.isSeparado());
        pedidoDataSeparacao.setValor(pedidoData.getValor());
        pedidoDataSeparacao.setProduto(pedidoData.getProduto());
        pedidoDataSeparacao.setCliente(pedidoData.getCliente());
        return pedidoDataSeparacao;
    }

}
