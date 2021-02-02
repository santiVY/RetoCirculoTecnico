package co.com.csanvel.model.transaction;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class RsCustomer {
    private String name;
}
