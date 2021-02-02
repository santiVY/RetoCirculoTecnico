package co.com.csanvel.model.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class RsDataTransaction {
    @JsonProperty("_responseSize")
    private int responseSize;

    @JsonProperty("_flagMoreRecords")
    private Boolean flagMoreRecords;

    private List<RsTransactionBody> transaction;
    private RqOffice office;
    private RsRelatedTransferAccount relatedTransferAccount;
    private RsCustomer customer;
}
