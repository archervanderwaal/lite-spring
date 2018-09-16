package com.archervanderwaal.litespring.beans.factory.annotation;

import com.archervanderwaal.litespring.beans.factory.config.AutowireCapableBeanFactory;

import java.lang.reflect.Member;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/24
 */
public abstract class InjectionElement {

    protected Member member;

    protected AutowireCapableBeanFactory factory;

    public InjectionElement(Member member, AutowireCapableBeanFactory factory) {
        this.member = member;
        this.factory = factory;
    }

    abstract void inject(Object target);
}
