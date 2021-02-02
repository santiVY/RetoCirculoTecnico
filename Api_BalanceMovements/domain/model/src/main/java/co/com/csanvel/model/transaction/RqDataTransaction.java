package co.com.csanvel.model.transaction;

import co.com.csanvel.model.balance.RqAccount;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class RqDataTransaction {
    private RqAccount account;
    private RqTransactionBody transaction;
    private RqPagination pagination;
    private RqOffice office;
}
