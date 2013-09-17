package org.accountservice.server.service;

import org.accountservice.server.dao.AccountDao;
import org.accountservice.server.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Pavel Karpukhin
 */
@Service
public class AccountServiceImpl implements AccountService {

    private Map<Integer, Long> cache = new HashMap<Integer, Long>();
    private AccountDao accountDao;

    @Autowired
    public AccountServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Long getAmount(Integer id) {
        synchronized (cache) {
            Long amount = cache.get(id);
            if (amount == null) {
                Account account = accountDao.getAccountById(id);
                if (account == null) {
                    return 0L;
                }
                amount = account.getAmount();
                cache.put(id, amount);
            }
            return amount;
        }
    }

    @Override
    @Transactional
    public void addAmount(Integer id, Long value) {
        synchronized (cache) {
            Long amount = cache.get(id);
            if (amount == null) {
                Account account = accountDao.getAccountById(id);
                if (account == null) {
                    amount = value;
                    account = new Account();
                    account.setId(id);
                    account.setAmount(amount);
                    accountDao.createAccount(account);
                    cache.put(id, value);
                } else {
                    amount = account.getAmount() + value;
                    account.setAmount(amount);
                    accountDao.updateAccount(account);
                }
                cache.put(id, amount);
                return;
            }
            amount += value;
            Account account = new Account();
            account.setId(id);
            account.setAmount(amount);
            accountDao.updateAccount(account);
            cache.put(id, amount);
        }
    }
}
