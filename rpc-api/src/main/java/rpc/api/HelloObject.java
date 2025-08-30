package rpc.api;

import java.io.Serializable;
import lombok.Data;
import lombok.AllArgsConstructor;



@Data
@AllArgsConstructor
public class HelloObject implements Serializable {
    private Integer id;
    private String message;
}
