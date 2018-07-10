package me.stormma.litespring.service.v2.entity;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class PetStoreService {

    private AccountDao accountDao;

    private ItemDao itemDao;

    private String test;

    private int version;

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

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String petStoreService() {
        return "petStoreService";
    }
}
