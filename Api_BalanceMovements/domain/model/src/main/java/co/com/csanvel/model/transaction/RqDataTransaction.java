package co.com.csanvel.model.transaction;

import co.com.csanvel.model.balance.RqAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class RqDataTransaction {
    private RqAccount account;
    private RqTransactionBody transaction;
    private RqPagination pagination;
    private RqOffice office;
}
