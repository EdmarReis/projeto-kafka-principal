package edmar.projeto.rest.kafka.adapters.out.producer;

import edmar.projeto.rest.kafka.adapters.out.mapper.PedidoDataToPedidoDataPagamento;
import edmar.projeto.rest.kafka.adapters.out.mapper.PedidoDataToPedidoDataSeparacao;
import edmar.projeto.rest.kafka.data.PedidoData;
import edmar.projeto.rest.kafka.services.RegistraEventoService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data
public class PedidoProducer {

    private final RegistraEventoService eventoService;

    @Autowired
    PedidoDataToPedidoDataPagamento enviaPedidoPagamento;

    @Autowired
    PedidoDataToPedidoDataSeparacao enviaPedidoSeparacao;

    public void enviaPagamento (PedidoData pedidoData){
        eventoService.adicionarEventoPagamento("SalvarPedidoPagamento", enviaPedidoPagamento.toPedidoDataPagamento(pedidoData));
    }

    public void enviaPedido (PedidoData pedidoData){
        eventoService.adicionarEventoSeparacao("SalvarPedidoSeparacao", enviaPedidoSeparacao.toPedidoDataSeparacao(pedidoData));
    }

}
