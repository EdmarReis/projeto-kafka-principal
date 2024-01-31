package edmar.projeto.rest.kafka.services;

import edmar.projeto.rest.kafka.entity.PedidoEntityRedis;
import edmar.projeto.rest.kafka.repository.PedidoRepositoryRedis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class PedidoRedisService {

    public static final String HASH_KEY = "Pedido";
    @Autowired
    private RedisTemplate template;

    @Autowired
    private RedisTemplate<String, PedidoEntityRedis> redisTemplate;

    @Autowired
    private PedidoRepositoryRedis pedidoRepositoryRedis;

    public PedidoEntityRedis save(PedidoEntityRedis pedidoEntityRedis){
        template.opsForHash().put(HASH_KEY, pedidoEntityRedis.getId(), pedidoEntityRedis);
        log.info("Evanto salvo no Redis. Id {}",pedidoEntityRedis.getId());
        return pedidoEntityRedis;
    }

    public List<PedidoEntityRedis> findAll(){

        return template.opsForHash().values(HASH_KEY);
    }

    public PedidoEntityRedis findProductById(String id){

        return (PedidoEntityRedis) template.opsForHash().get(HASH_KEY,id);
    }


    public String deleteProduct(int id){
        template.opsForHash().delete(HASH_KEY,id);
        return "product removed !!";
    }

    public Optional<PedidoEntityRedis> findById(int id) {
        return pedidoRepositoryRedis.findById(id);
    }

    public PedidoEntityRedis find2(String id) {
        return redisTemplate.opsForValue().get(id);
    }

}
