package edmar.projeto.rest.kafka.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoData {

    private String id;
    private String cliente;
    private String produto;
    private BigDecimal valor;
    private boolean pago;
    private boolean separado;
    private String status;

}
