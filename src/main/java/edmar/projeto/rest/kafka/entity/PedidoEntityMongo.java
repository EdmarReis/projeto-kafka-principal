package edmar.projeto.rest.kafka.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Document(collection = "pedidoPrincipal")
@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
public class PedidoEntityMongo {

    @Id
    private String id;
    private String cliente;
    private String produto;
    private BigDecimal valor;
    private boolean pago;
    private boolean separado;
    private String status;

}
