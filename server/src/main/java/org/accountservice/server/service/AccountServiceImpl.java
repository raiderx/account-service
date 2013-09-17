package org.accountservice.server.service;

import org.accountservice.server.dao.AccountDao;
import org.accountservice.server.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Pavel Karpukhin
 */
@Service
public class AccountServiceImpl implements AccountService {

    private AccountDao accountDao;

    @Autowired
    public AccountServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Long getAmount(Integer id) {
        Account account = accountDao.getAccountById(id);
        if (account == null) {
            return 0L;
        }
        return account.getAmount();
    }

    @Override
    @Transactional
    public void addAmount(Integer id, Long value) {
        Account account = accountDao.getAccountById(id);
        if (account == null) {
            account = new Account();
            account.setId(id);
            account.setAmount(value);
            accountDao.createAccount(account);
        } else {
            account.setAmount(account.getAmount() + value);
            accountDao.updateAccount(account);
        }
    }
}
