package com.archervanderwaal.litespring.test.v5.entity;

import com.archervanderwaal.litespring.beans.factory.annotation.Autowired;
import com.archervanderwaal.litespring.stereotype.Component;
import com.archervanderwaal.litespring.test.v5.util.MessageTracker;

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
        MessageTracker.addMessage("place order");
    }

    public void placeOrderWithException() {
        MessageTracker.addMessage("place order with exception");
        throw new RuntimeException();
    }
}
