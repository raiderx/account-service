package org.accountservice.server.model;

/**
 * @author Pavel Karpukhin
 */
public class Account {

    private int id = -1;
    private long amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
