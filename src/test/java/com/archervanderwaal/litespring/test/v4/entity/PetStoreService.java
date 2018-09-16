package com.archervanderwaal.litespring.test.v4.entity;

import com.archervanderwaal.litespring.beans.factory.annotation.Autowired;
import com.archervanderwaal.litespring.stereotype.Component;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/13
 */
@Component(value = "petStore")
public class PetStoreService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private ItemDao itemDao;

    public AccountDao getAccountDao() {
        return this.accountDao;
    }

    public ItemDao getItemDao() {
        return this.itemDao;
    }
}
