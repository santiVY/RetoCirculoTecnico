package co.com.csanvel.model.transaction;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
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
