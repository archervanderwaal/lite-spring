package me.stormma.litespring.test.v5.entity;

import me.stormma.litespring.beans.factory.annotation.Autowired;
import me.stormma.litespring.stereotype.Component;
import me.stormma.litespring.test.v5.util.MessageTracker;

/**
 * @author stormma stormmaybin@gmail.com
 */
@Component(value = "petStore")
public class PetStoreService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private ItemDao itemDao;

    public PetStoreService() {

    }

    public AccountDao getAccountDao() {
        return this.accountDao;
    }

    public ItemDao getItemDao() {
        return this.itemDao;
    }


    public void placeOrder() {
        System.out.println("place order");
        MessageTracker.addMessage("place order");
    }

    public void placeOrderWithException() {
        System.out.println("place order with exception");
        MessageTracker.addMessage("place order with exception");
        throw new RuntimeException();
    }
}
