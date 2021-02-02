package co.com.csanvel.model.balance;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class RqBalance {
    private List<RqDataBalance> data;

}
