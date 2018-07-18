package me.stormma.litespring.test.v4.entity;

import me.stormma.litespring.beans.factory.annotation.Autowired;
import me.stormma.litespring.stereotype.Component;
import me.stormma.litespring.test.v2.entity.ItemDao;

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
