package edmar.projeto.rest.kafka.adapters.out.mapper;

import edmar.projeto.rest.kafka.data.PedidoData;
import edmar.projeto.rest.kafka.entity.PedidoEntityMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoDataToPedidoRepositoryMongo {

    @Autowired
    PedidoEntityMongo pedidoEntityMongo;

    public PedidoEntityMongo toPedidoEntityMongo(PedidoData pedidoData){
        pedidoEntityMongo.setId(pedidoData.getId());
        pedidoEntityMongo.setCliente(pedidoData.getCliente());
        pedidoEntityMongo.setProduto(pedidoData.getProduto());
        pedidoEntityMongo.setValor(pedidoData.getValor());
        pedidoEntityMongo.setPago(pedidoData.isPago());
        pedidoEntityMongo.setSeparado(pedidoData.isSeparado());
        pedidoEntityMongo.setStatus(pedidoData.getStatus());
        return pedidoEntityMongo;
    }

}
