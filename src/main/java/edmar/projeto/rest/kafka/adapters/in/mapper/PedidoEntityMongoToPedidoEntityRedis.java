package edmar.projeto.rest.kafka.adapters.in.mapper;

import edmar.projeto.rest.kafka.entity.PedidoEntityMongo;
import edmar.projeto.rest.kafka.entity.PedidoEntityRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoEntityMongoToPedidoEntityRedis {

    @Autowired
    PedidoEntityRedis pedidoEntityRedis;

    public PedidoEntityRedis toPedidoEntityRedis (PedidoEntityMongo pedidoEntityMongo){
        pedidoEntityRedis.setId(pedidoEntityMongo.getId());
        pedidoEntityRedis.setPago(pedidoEntityMongo.isPago());
        pedidoEntityRedis.setSeparado(pedidoEntityMongo.isSeparado());
        pedidoEntityRedis.setCliente(pedidoEntityMongo.getCliente());
        pedidoEntityRedis.setProduto(pedidoEntityMongo.getProduto());
        pedidoEntityRedis.setValor(pedidoEntityMongo.getValor());
        pedidoEntityRedis.setStatus(pedidoEntityMongo.getStatus());
        return pedidoEntityRedis;
    }

}
