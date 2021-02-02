package co.com.csanvel.model.balancemovements;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class RsBalanceMovements {
    private List<RsData> data;
}
