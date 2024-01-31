package edmar.projeto.rest.kafka.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistraEventoService {

    private final KafkaTemplate<Object, Object> template;

    public <T> void adicionarEventoPagamento(String topico, T dados) {

        template.send(topico, dados);
        log.info("evento enviado para o topico SalvarPedidoPagamento. {}",dados);
    }

    public <T> void adicionarEventoSeparacao(String topico, T dados) {

        template.send(topico, dados);
        log.info("evento enviado para o topico SalvarPedidoSeparacao. {}",dados);
    }


}
