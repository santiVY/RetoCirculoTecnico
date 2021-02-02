package co.com.csanvel.model.balancemovements;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class RqBalanceMovements {
    private List<RqData> data;
    private RsStatus status;
}
