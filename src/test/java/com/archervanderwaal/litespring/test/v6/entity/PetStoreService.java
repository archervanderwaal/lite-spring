package com.archervanderwaal.litespring.test.v6.entity;

import com.archervanderwaal.litespring.beans.factory.annotation.Autowired;
import com.archervanderwaal.litespring.stereotype.Component;
import com.archervanderwaal.litespring.test.v5.util.MessageTracker;

/**
 * @author stormma stormmaybin@gmail.com
 */
@Component(value = "petStore")
public class PetStoreService implements IPetStoreService {

    @Override
    public void placeOrder() {
        MessageTracker.addMessage("place order");
    }
}
