package edmar.projeto.rest.kafka.adapters.out.mapper;

import edmar.projeto.rest.kafka.data.PedidoData;
import edmar.projeto.rest.kafka.entity.PedidoEntityRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoDataToPedidoEntityRedis {

    @Autowired
    PedidoEntityRedis pedidoEntityRedis;

    public PedidoEntityRedis toPedidoEntityRedis(PedidoData pedidoData){
        pedidoEntityRedis.setId(pedidoData.getId());
        pedidoEntityRedis.setCliente(pedidoData.getCliente());
        pedidoEntityRedis.setProduto(pedidoData.getProduto());
        pedidoEntityRedis.setValor(pedidoData.getValor());
        pedidoEntityRedis.setSeparado(pedidoData.isSeparado());
        pedidoEntityRedis.setPago(pedidoData.isPago());
        pedidoEntityRedis.setStatus(pedidoData.getStatus());
        return pedidoEntityRedis;
    }

}
