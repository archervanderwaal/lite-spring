package com.archervanderwaal.litespring.test.v4.entity;

import java.util.ArrayList;

/**
 * @author stormma stormmaybin@gmail.com
 * @since 2018/7/16
 */
public class Test {

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        for (Integer integer : list) {
            System.out.print(integer);
            if (integer.equals(1)) {
                list.remove(integer);
            }
        }
    }
}
