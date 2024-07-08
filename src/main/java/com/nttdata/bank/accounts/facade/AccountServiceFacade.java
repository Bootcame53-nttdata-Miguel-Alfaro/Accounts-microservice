package com.nttdata.bank.accounts.facade;

import com.nttdata.bank.accounts.mapper.*;
import com.nttdata.bank.accounts.service.AccountService;
import com.nttdata.bank.accounts.service.TransactionService;
import lombok.Getter;

@Getter
public class AccountServiceFacade {
    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final BalanceMapper balanceMapper;
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;
    private final DepositMapper depositMapper;
    private final WithdrawMapper withdrawMapper;
    private final DailyBalanceSummaryMapper dailyBalanceSummaryMapper;

    public AccountServiceFacade(AccountService accountService, AccountMapper accountMapper,
                                BalanceMapper balanceMapper, TransactionService transactionService,
                                TransactionMapper transactionMapper, DepositMapper depositMapper,
                                WithdrawMapper withdrawMapper, DailyBalanceSummaryMapper dailyBalanceSummaryMapper) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
        this.balanceMapper = balanceMapper;
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
        this.depositMapper = depositMapper;
        this.withdrawMapper = withdrawMapper;
        this.dailyBalanceSummaryMapper = dailyBalanceSummaryMapper;
    }
}
