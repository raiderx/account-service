package org.accountservice.server.dao;

import org.accountservice.server.model.Account;

/**
 * @author Pavel Karpukhin
 */
public interface AccountDao {

    void createAccount(Account account);

    void updateAccount(Account account);

    Account getAccountById(int id);
}
