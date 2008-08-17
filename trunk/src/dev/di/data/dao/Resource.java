package dev.di.data.dao;

/**
 * Created by IntelliJ IDEA.
 * User: Thilina
 * Date: Oct 25, 2007
 * Time: 2:48:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class Resource extends AbstractDao{
    String id;
    String amount;
    String rate;
    String description;

    public Resource(String fullStr) {
        numOfAttribs=4;
        String[] s=getAttributes(fullStr);
        id=s[0];
        amount=s[1];
        rate=s[2];
        description=s[3];
    }

    public Resource(String id, String amount, String rate, String description) {
        this.id = id;
        this.amount = amount;
        this.rate = rate;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAmount() {
        return Integer.parseInt(amount);
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getRate() {
        return Integer.parseInt(rate);
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return id+"|"+amount+"|"+rate+"|"+description+"|";
    }
}
