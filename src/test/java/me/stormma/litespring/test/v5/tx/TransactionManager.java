package me.stormma.litespring.test.v5.tx;

import me.stormma.litespring.test.v5.util.MessageTracker;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class TransactionManager {

    public void start() {
        MessageTracker.addMessage("start tx");
    }

    public void commit() {
        MessageTracker.addMessage("commit tx");
    }

    public void rollback() {
        MessageTracker.addMessage("rollback tx");
    }
}
