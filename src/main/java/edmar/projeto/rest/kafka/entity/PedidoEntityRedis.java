package edmar.projeto.rest.kafka.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@RedisHash("Pedido")
public class PedidoEntityRedis implements Serializable {

    private static final long serialVersionUID = -1188668945286995754L;

    @Id
    private String id;
    private String cliente;
    private String produto;
    private BigDecimal valor;
    private boolean pago;
    private boolean separado;
    private String status;

}
