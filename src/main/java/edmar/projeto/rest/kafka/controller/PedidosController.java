package edmar.projeto.rest.kafka.controller;

import edmar.projeto.rest.kafka.adapters.out.mapper.PedidoDataToPedidoDataPagamento;
import edmar.projeto.rest.kafka.adapters.out.mapper.PedidoDataToPedidoDataSeparacao;
import edmar.projeto.rest.kafka.adapters.out.mapper.PedidoDataToPedidoEntityRedis;
import edmar.projeto.rest.kafka.adapters.out.mapper.PedidoDataToPedidoRepositoryMongo;
import edmar.projeto.rest.kafka.adapters.out.producer.PedidoProducer;
import edmar.projeto.rest.kafka.data.*;
import edmar.projeto.rest.kafka.data.PedidoData;
import edmar.projeto.rest.kafka.entity.PedidoEntityRedis;
import edmar.projeto.rest.kafka.entity.PedidoEntityMongo;
import edmar.projeto.rest.kafka.repository.PedidoRepositoryMongo;
import edmar.projeto.rest.kafka.services.*;
import edmar.projeto.rest.kafka.services.PedidoRedisService;
import edmar.projeto.rest.kafka.services.RegistraEventoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class PedidosController {

    private final RegistraEventoService eventoService;
    @Autowired
    private PedidoRedisService pedidoRedisService;

    @Autowired
    PedidoDataToPedidoDataPagamento enviaPedidoPagamento;
    @Autowired
    PedidoDataToPedidoDataSeparacao enviaPedidoSeparacao;

    @Autowired
    PedidoRepositoryMongo pedidoRepositoryMongo;

    @Autowired
    PedidoDataToPedidoRepositoryMongo pedidoDataToPedidoRepositoryMongo;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    PedidoDataToPedidoEntityRedis pedidoDataToPedidoEntityRedis;

    @Autowired
    PedidoProducer pedidoProducer;

    @PostMapping(path = "/salva-pedido")
    public ResponseEntity<String>  SalvarPedido(@RequestBody PedidoData pedidoData) {
        log.info("Inicio do fluxo de envio para kafka e salvar no redis. Id {}"+pedidoData.getId());
        pedidoProducer.enviaPagamento(pedidoData);
        pedidoProducer.enviaPedido(pedidoData);
        pedidoData.setStatus("Aguardando confirmacao de pagamento. Pedido em stattus de separacao");
        pedidoRepositoryMongo.save(pedidoDataToPedidoRepositoryMongo.toPedidoEntityMongo(pedidoData));
        pedidoRedisService.save(pedidoDataToPedidoEntityRedis.toPedidoEntityRedis(pedidoData));
        return ResponseEntity.ok("Pedido efetuado!");
    }

    @GetMapping("/buscar")
    public List<PedidoEntityMongo> getAllPedidosMongo() {
        return pedidoRepositoryMongo.findAll();
    }

    @GetMapping("/buscarV2")
    public List<PedidoEntityRedis> getAllKeys() {
        return pedidoRedisService.findAll();
    }

    @DeleteMapping("/delete")
    public String limparMongo() {
        // Lista todas as coleções no banco de dados
        mongoTemplate.getCollectionNames().forEach(collectionName -> {
            // Exclui cada coleção
            mongoTemplate.dropCollection(collectionName);
        });
        return "Mongo deletado";
    }

    @DeleteMapping("/deleteRedis")
    public String clearRedisDatabase() {
        Jedis jedis = new Jedis("localhost");
        jedis.flushDB();
        // jedis.flushAll();
        jedis.close();
        return "Redis deletado";
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Optional<PedidoEntityMongo>> findByIdMongo(@PathVariable final String id) {
        Optional<PedidoEntityMongo> getPedido = pedidoRepositoryMongo.findById(id);
        return ResponseEntity.ok().body(getPedido);
    }

    @GetMapping("/buscarV2/{id}")
    public ResponseEntity<?> findProduct3(@PathVariable String id) {

        Optional<PedidoEntityRedis> optionalProduct = Optional.ofNullable(pedidoRedisService.findProductById(id));

            if (optionalProduct.isPresent()) {
                //return ResponseEntity.ok(optionalProduct.get());
                PedidoEntityRedis pedidoEntityRedis = optionalProduct.orElse(null);
                return ResponseEntity.ok(pedidoEntityRedis);
            } else {
                return ResponseEntity.ok("Sem resultados");
            }
    }

}
