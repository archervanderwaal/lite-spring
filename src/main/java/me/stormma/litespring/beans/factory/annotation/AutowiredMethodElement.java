package me.stormma.litespring.beans.factory.annotation;

import me.stormma.litespring.beans.factory.config.AutowireCapableBeanFactory;

import java.lang.reflect.Member;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/24
 */
public class AutowiredMethodElement extends InjectionElement {

    public AutowiredMethodElement(Member member, AutowireCapableBeanFactory factory) {
        super(member, factory);
    }

    @Override
    void inject(Object target) {

    }
}
