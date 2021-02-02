package co.com.csanvel.usecase.balancemovements;

import co.com.csanvel.model.balance.RqAccount;
import co.com.csanvel.model.balance.RqBalance;
import co.com.csanvel.model.balance.RqDataBalance;
import co.com.csanvel.model.balance.RsBalance;
import co.com.csanvel.model.balance.gateways.BalanceGateway;
import co.com.csanvel.model.balancemovements.*;
import co.com.csanvel.model.transaction.RqDataTransaction;
import co.com.csanvel.model.transaction.RqTransaction;
import co.com.csanvel.model.transaction.RsTransaction;
import co.com.csanvel.model.transaction.gateways.TransactionGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class BalancemovementsUseCase {

    private final BalanceGateway balanceGateway;
    private final TransactionGateway transactionGateway;

    public Mono<RsBalanceMovements> getBalanceMovements(RqBalanceMovements rqBalanceMovements){
        //construimos los request balance
        RqData rqData = rqBalanceMovements.getData().get(0);

        List<RqDataBalance> rqDataBalance = new ArrayList<>();
        rqDataBalance.add(RqDataBalance.builder()
                .account(rqData.getAccount())
                .build());

        RqBalance rqBalance = RqBalance.builder()
                .data(rqDataBalance)
                .build();

        //construimos request de transaction
        List<RqDataTransaction> rqDataTransactions = new ArrayList<>();
        rqDataTransactions.add(RqDataTransaction.builder()
                .account(rqData.getAccount())
                .office(rqData.getOffice())
                .pagination(rqData.getPagination())
                .transaction(rqData.getTransaction())
                .build());

        RqTransaction rqTransaction = RqTransaction.builder()
                .data(rqDataTransactions)
                .build();

        //se obtiene el reponse
        Mono<RsBalance> rsBalance = balanceGateway.getBalance(rqBalance);
        Mono<RsTransaction> rsTransaction = transactionGateway.getTransaction(rqTransaction);

        return Mono.zip(rsBalance, rsTransaction)
                .flatMap( response -> {
                    List<RsData> rsData = new ArrayList<>();
                    rsData.add(RsData.builder()
                            .responseSize(response.getT2().getData().get(0).getResponseSize())
                            .flagMoreRecords(response.getT2().getData().get(0).getFlagMoreRecords())
                            .account(response.getT1().getData().get(0).getAccount())
                            .balance(response.getT1().getData().get(0).getAccount().getBalances())
                            .transaction(response.getT2().getData().get(0).getTransaction())
                            .office(response.getT2().getData().get(0).getOffice())
                            .relatedTransferAccount(response.getT2().getData().get(0).getRelatedTransferAccount())
                            .customer(response.getT2().getData().get(0).getCustomer())
                            .build());

                    RsBalanceMovements rsBalanceMovements = RsBalanceMovements.builder()
                            .data(rsData)
                            .status(RsStatus.builder()
                                    .code("000")
                                    .title("TRANSACCION EXITOSA")
                                    .detail("TRANSACCION EXITOSA")
                                    .severity("INFO")
                                    .build())
                            .build();

                    return Mono.just(rsBalanceMovements);
                });
    }
}
