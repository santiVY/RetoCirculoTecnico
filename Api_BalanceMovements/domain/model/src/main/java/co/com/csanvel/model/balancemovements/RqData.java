package co.com.csanvel.model.balancemovements;

import co.com.csanvel.model.balance.RqAccount;
import co.com.csanvel.model.transaction.RqOffice;
import co.com.csanvel.model.transaction.RqPagination;
import co.com.csanvel.model.transaction.RqTransactionBody;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class RqData {
    private RqAccount account;
    private RqTransactionBody transaction;
    private RqPagination pagination;
    private RqOffice office;
}
