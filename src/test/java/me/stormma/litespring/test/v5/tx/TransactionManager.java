package me.stormma.litespring.test.v5.tx;

import me.stormma.litespring.test.v5.util.MessageTracker;

/**
 * @author stormma stormmaybin@gmail.com
 */
public class TransactionManager {

    public void start() {
        System.out.println("start tx");
        MessageTracker.addMessage("start tx");
    }

    public void commit(){
        System.out.println("commit tx");
        MessageTracker.addMessage("commit tx");
    }

    public void rollback(){
        System.out.println("rollback tx");
        MessageTracker.addMessage("rollback tx");
    }
}
