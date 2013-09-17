package org.accountservice.server.dao;

import org.accountservice.server.model.Account;
import org.accountservice.server.util.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author Pavel Karpukhin
 */
@Repository
public class AccountDaoImpl implements AccountDao {

    public static final String SQL_SELECT_ACCOUNT_BY_ID =
            "SELECT ID, AMOUNT FROM ACCOUNTS " +
            "WHERE ID = ?";
    public static final String SQL_INSERT_ACCOUNT =
            "INSERT INTO ACCOUNTS (ID, AMOUNT) " +
            "VALUES (?, ?)";
    public static final String SQL_UPDATE_ACCOUNT =
            "UPDATE ACCOUNTS SET AMOUNT = ? " +
            "WHERE ID = ?";

    private final Logger logger = LoggerFactory.getLogger(AccountDaoImpl.class);

    private JdbcTemplate jdbcTemplate;
    private final AccountRowMapper rowMapper = new AccountRowMapper();

    @Autowired
    public AccountDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void createAccount(Account account) {
        Object[] values = {
                account.getId(),
                account.getAmount()
        };
        int[] types = {
                Types.INTEGER,
                Types.BIGINT
        };
        int rows = jdbcTemplate.update(SQL_INSERT_ACCOUNT, values, types);
        if (rows != 1) {
            throw new ApplicationException(String.format("%d rows were inserted but 1 was expected only", rows));
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void updateAccount(Account account) {
        Object[] values = {
                account.getAmount(),
                account.getId()
        };
        int[] types = {
                Types.BIGINT,
                Types.INTEGER
        };
        int rows = jdbcTemplate.update(SQL_UPDATE_ACCOUNT, values, types);
        if (rows != 1) {
            throw new ApplicationException(String.format("%d rows were updated but 1 was expected only", rows));
        }
    }

    @Override
    public Account getAccountById(int id) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_ACCOUNT_BY_ID,
                    new Object[] {id}, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public static final class AccountRowMapper implements RowMapper<Account> {
        @Override
        public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
            Account user = new Account();
            user.setId(rs.getInt("ID"));
            user.setAmount(rs.getLong("AMOUNT"));
            return user;
        }
    }
}
