/**
 * @author:Leo
 * @create 2018/7/1
 * @desc
 */
package com.litespring.service.v2;

import com.litespring.dao.v2.AccountDao;
import com.litespring.dao.v2.ItemDao;

public class PetStoreService {
    private AccountDao accountDao;
    private ItemDao itemDao;
    private String owner;

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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
