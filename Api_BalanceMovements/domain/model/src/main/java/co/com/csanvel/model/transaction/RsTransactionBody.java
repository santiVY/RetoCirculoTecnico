package co.com.csanvel.model.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class RsTransactionBody {
    private String id;
    private String postedDate;
    private String description;
    private int amount;
    private String type;
    private String reference1;
    private String reference2;
    private String reference3;
    private String checkNumber;
}
