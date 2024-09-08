package dev.ipsych0.myrinnia.shops;

import java.io.Serializable;

public class Stock implements Serializable {

    private static final long serialVersionUID = 6349466499289524304L;
    private int id;
    private int amount;

    public Stock(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
