package co.com.csanvel.usecase.balancemovements;

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

    private RqBalance requestBalance(RqBalanceMovements rqBalanceMovements){

        List<RqDataBalance> rqDataBalance = new ArrayList<>();
        rqDataBalance.add(RqDataBalance.builder()
                .account(rqBalanceMovements.getData().get(0).getAccount())
                .build());

        return  RqBalance.builder()
                .data(rqDataBalance)
                .build();
    }

    private RqTransaction requestTransaction(RqBalanceMovements rqBalanceMovements){

        RqData rqData = rqBalanceMovements.getData().get(0);
        List<RqDataTransaction> rqDataTransactions = new ArrayList<>();
        rqDataTransactions.add(RqDataTransaction.builder()
                .account(rqData.getAccount())
                .office(rqData.getOffice())
                .pagination(rqData.getPagination())
                .transaction(rqData.getTransaction())
                .build());

        return RqTransaction.builder()
                .data(rqDataTransactions)
                .build();
    }

    public Mono<RsBalanceMovements> getBalanceMovements(RqBalanceMovements rqBalanceMovements){

        //se obtiene el reponse

        if(rqBalanceMovements.getData().get(0).getPagination().getKey() >= 2){
            Mono<RsTransaction> rsTransactionMoreTransaction = transactionGateway.getTransactionMoreMovements(requestTransaction(rqBalanceMovements));
            return rsTransactionMoreTransaction.flatMap(response -> {
                List<RsData> rsData = new ArrayList<>();
                rsData.add(RsData.builder()
                        .responseSize(response.getData().get(0).getResponseSize())
                        .flagMoreRecords(response.getData().get(0).getFlagMoreRecords())
                        .account(rqBalanceMovements.getData().get(0).getAccount())
                        .transaction(response.getData().get(0).getTransaction())
                        .office(response.getData().get(0).getOffice())
                        .relatedTransferAccount(response.getData().get(0).getRelatedTransferAccount())
                        .customer(response.getData().get(0).getCustomer())
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
        }else{
            Mono<RsBalance> rsBalance = balanceGateway.getBalance(requestBalance(rqBalanceMovements));
            Mono<RsTransaction> rsTransaction = transactionGateway.getTransaction(requestTransaction(rqBalanceMovements));
            return Mono.zip(rsBalance, rsTransaction)
                .flatMap( response -> {
                    List<RsData> rsData = new ArrayList<>();
                    rsData.add(RsData.builder()
                            .responseSize(response.getT2().getData().get(0).getResponseSize())
                            .flagMoreRecords(response.getT2().getData().get(0).getFlagMoreRecords())
                            .account(rqBalanceMovements.getData().get(0).getAccount())
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
}
