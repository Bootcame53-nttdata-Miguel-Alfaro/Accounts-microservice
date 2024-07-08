package com.nttdata.bank.accounts.configuration;

import com.nttdata.bank.accounts.facade.AccountServiceFacade;
import com.nttdata.bank.accounts.mapper.*;
import com.nttdata.bank.accounts.service.AccountService;
import com.nttdata.bank.accounts.service.TransactionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountConfiguration {

    @Bean
    public AccountServiceFacade accountServiceFacade(AccountService accountService, AccountMapper accountMapper,
                                                     BalanceMapper balanceMapper, TransactionService transactionService,
                                                     TransactionMapper transactionMapper, DepositMapper depositMapper,
                                                     WithdrawMapper withdrawMapper, DailyBalanceSummaryMapper dailyBalanceSummaryMapper) {
        return new AccountServiceFacade(accountService, accountMapper, balanceMapper, transactionService,
                transactionMapper, depositMapper, withdrawMapper, dailyBalanceSummaryMapper);
    }
}