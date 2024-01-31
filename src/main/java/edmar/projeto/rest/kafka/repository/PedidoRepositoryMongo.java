package edmar.projeto.rest.kafka.repository;

import edmar.projeto.rest.kafka.entity.PedidoEntityMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PedidoRepositoryMongo extends MongoRepository<PedidoEntityMongo, String> {


}
