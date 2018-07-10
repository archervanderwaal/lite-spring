package me.stormma.litespring.service.v2.entity;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class PetStoreService {

    private AccountDao accountDao;

    private ItemDao itemDao;

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public String petStoreService() {
        return "petStoreService";
    }
}
