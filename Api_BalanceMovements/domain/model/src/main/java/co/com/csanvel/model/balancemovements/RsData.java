package co.com.csanvel.model.balancemovements;

import co.com.csanvel.model.balance.RsAccount;
import co.com.csanvel.model.balance.RsBalances;
import co.com.csanvel.model.transaction.RqOffice;
import co.com.csanvel.model.transaction.RsCustomer;
import co.com.csanvel.model.transaction.RsRelatedTransferAccount;
import co.com.csanvel.model.transaction.RsTransactionBody;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class RsData {
    @JsonProperty("_responseSize")
    private int responseSize;

    @JsonProperty("_flagMoreRecords")
    private Boolean flagMoreRecords;

    private RsAccount account;
    private RsBalances balance;
    private List<RsTransactionBody> transaction;
    private RqOffice office;
    private RsRelatedTransferAccount relatedTransferAccount;
    private RsCustomer customer;

}
