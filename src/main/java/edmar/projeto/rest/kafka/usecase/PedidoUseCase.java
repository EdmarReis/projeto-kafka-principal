package edmar.projeto.rest.kafka.usecase;

import edmar.projeto.rest.kafka.entity.PedidoEntityMongo;
import edmar.projeto.rest.kafka.entity.PedidoEntityRedis;
import edmar.projeto.rest.kafka.repository.PedidoRepositoryMongo;
import edmar.projeto.rest.kafka.services.PedidoRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class PedidoUseCase {

    @Autowired
    PedidoRepositoryMongo pedidoRepositoryMongo;

    @Autowired
    PedidoRedisService pedidoRedisService;

    @Autowired
    PedidoEntityRedis pedidoEntityRedis;

    //Faz sync do banco mongo para o redis
    @Scheduled(fixedRate = 60000, initialDelay = 60000) // 60000 milissegundos = 1 minuto
    public void executeTask() {
        int a = 0;
        log.info("Inicio Sincronizacao mongo para redis...");
        List<PedidoEntityMongo> registros = pedidoRepositoryMongo.findAll();
        for (PedidoEntityMongo registro : registros) {
            pedidoRedisService.save(toPedidoEntityRedis(registro));
            a++;
        }
        log.info("Fim da sincronizacao. Registros enviados = "+ a);
    }

    public PedidoEntityRedis toPedidoEntityRedis(PedidoEntityMongo pedidoEntityMongo){
        pedidoEntityRedis.setId(pedidoEntityMongo.getId());
        pedidoEntityRedis.setPago(pedidoEntityMongo.isPago());
        pedidoEntityRedis.setProduto(pedidoEntityMongo.getProduto());
        pedidoEntityRedis.setValor(pedidoEntityMongo.getValor());
        pedidoEntityRedis.setSeparado(pedidoEntityMongo.isSeparado());
        pedidoEntityRedis.setCliente(pedidoEntityMongo.getCliente());
        pedidoEntityRedis.setStatus(pedidoEntityMongo.getStatus());
        return pedidoEntityRedis;
    }




}
