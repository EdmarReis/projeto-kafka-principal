package edmar.projeto.rest.kafka.adapters.in.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edmar.projeto.rest.kafka.entity.PedidoEntityMongo;
import edmar.projeto.rest.kafka.services.PedidoMongoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RecebimentoPagamentoListener {

    @Autowired
    PedidoEntityMongo pedidoEntityMongo;

    @Autowired
    PedidoMongoService pedidoMongoService;

    @KafkaListener(topics = "devolvePedidoPagamento", groupId = "MicrosservicoSalvaPedido")
    private void executar(ConsumerRecord<String, String> record) {

        log.info("Inicio fluxo de resposta PedidoPagamento");

        String strDados = record.value();

        ObjectMapper mapper = new ObjectMapper();

        try {
            pedidoEntityMongo = mapper.readValue(strDados, PedidoEntityMongo.class);
        } catch (JsonProcessingException ex) {
            log.error("Falha converter evento [dado={}}]", strDados, ex);
            return;
        }
        log.info("Evento Recebido no topico devolvePedidoPagamento = {}", pedidoEntityMongo);
        pedidoMongoService.updatePedidoPagamento(pedidoEntityMongo);
        log.info("Fim do fluxo de recebimento do status do pagamento.  {}", pedidoEntityMongo);
    }

}
