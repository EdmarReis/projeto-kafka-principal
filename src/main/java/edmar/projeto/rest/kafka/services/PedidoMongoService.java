package edmar.projeto.rest.kafka.services;

import edmar.projeto.rest.kafka.adapters.in.mapper.PedidoEntityMongoToPedidoEntityRedis;
import edmar.projeto.rest.kafka.entity.PedidoEntityMongo;
import edmar.projeto.rest.kafka.repository.PedidoRepositoryMongo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class PedidoMongoService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    PedidoRedisService pedidoRedisService;

    @Autowired
    PedidoEntityMongoToPedidoEntityRedis pedidoEntityMongoToPedidoEntityRedis;

    @Autowired
    PedidoRepositoryMongo pedidoRepositoryMongo;

    public void updatePedidoPagamento(PedidoEntityMongo pedidoEntityMongo) {
        //seta o status pagamento para true
        log.info("Atualizando pago para true(updatePagamento)...");
        Query query = new Query(Criteria.where("_id").is(pedidoEntityMongo.getId()));
        Update update = new Update().set("pago", true);
        mongoTemplate.updateFirst(query, update, PedidoEntityMongo.class);
        log.info("status pagamento alterado(updatePagamento)");

        //checa o status pagamento e separacao para setar o campo status
        log.info("checando status pagamento e separacao para atualizar o campo status(updatePagamento)...");
        Optional<PedidoEntityMongo> getPedido = pedidoRepositoryMongo.findById(pedidoEntityMongo.getId());
        PedidoEntityMongo pedido = getPedido.orElse(null);
        Query query2 = new Query(Criteria.where("_id").is(pedidoEntityMongo.getId()));
        Update update2 = new Update().set("status", setarStatus(pedido));
        mongoTemplate.updateFirst(query2, update2, PedidoEntityMongo.class);
        log.info("status checado e alterado(updatePagamento)");

        //consulta mongo para atualiza redis
        log.info("enviando atualizacao de status de pagamento para redis...");
        Optional<PedidoEntityMongo> getPedido2 = pedidoRepositoryMongo.findById(pedidoEntityMongo.getId());
        PedidoEntityMongo pedido2 = getPedido2.orElse(null);
        pedidoRedisService.save(pedidoEntityMongoToPedidoEntityRedis.toPedidoEntityRedis(pedido2));
        log.info("mongo e redis atualizados - fluxo pagamento. {}",pedido2);
    }

    public void updatePedidoSeparacao(PedidoEntityMongo pedidoEntityMongo) {
        //seta o status separacao para true
        log.info("Atualizando separado para true(updateSeparacao)...");
        Query query = new Query(Criteria.where("_id").is(pedidoEntityMongo.getId()));
        Update update = new Update().set("separado", true);
        mongoTemplate.updateFirst(query, update, PedidoEntityMongo.class);
        log.info("status separacao alterado(updateSeparacao)");

        //checa o status pagamento e separacao para setar o campo status
        log.info("checando status pagamento e separacao para atualizar o campo status(updateSeparacao)...");
        Optional<PedidoEntityMongo> getPedido = pedidoRepositoryMongo.findById(pedidoEntityMongo.getId());
        PedidoEntityMongo pedido = getPedido.orElse(null);
        Query query2 = new Query(Criteria.where("_id").is(pedidoEntityMongo.getId()));
        Update update2 = new Update().set("status", setarStatus(pedido));
        mongoTemplate.updateFirst(query2, update2, PedidoEntityMongo.class);
        log.info("status checado e alterado(updateSeparacao)");

        //consulta mongo para atualiza redis
        log.info("enviando atualizacao de status de separacao para redis...");
        Optional<PedidoEntityMongo> getPedido2 = pedidoRepositoryMongo.findById(pedidoEntityMongo.getId());
        PedidoEntityMongo pedido2 = getPedido2.orElse(null);
        pedidoRedisService.save(pedidoEntityMongoToPedidoEntityRedis.toPedidoEntityRedis(pedido2));
        log.info("mongo e redis atualizados - fluxo separacao. {}",pedido2);
    }

    public String setarStatus(PedidoEntityMongo pedidoEntityMongo){
        if(pedidoEntityMongo.isSeparado() && pedidoEntityMongo.isPago()){
            return "Pagamento confirmado. Pedido em etapa de entrega.";
        }else if(pedidoEntityMongo.isSeparado()){
            return "Aguardando confirmacao de pagamento. O Pedido ja foi separado.";
        }else if(pedidoEntityMongo.isPago()){
            return "Pagamento confirmado. Separacao de produto em andamento";
        }
        return "Status desconhecido";
    }

}
