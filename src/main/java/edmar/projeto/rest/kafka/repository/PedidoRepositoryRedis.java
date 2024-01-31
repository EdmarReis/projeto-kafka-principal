package edmar.projeto.rest.kafka.repository;

import edmar.projeto.rest.kafka.entity.PedidoEntityRedis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepositoryRedis extends CrudRepository<PedidoEntityRedis, Integer> {

}
