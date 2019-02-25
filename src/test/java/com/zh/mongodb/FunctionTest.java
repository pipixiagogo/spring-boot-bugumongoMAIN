package com.zh.mongodb;

import org.junit.Test;

import java.util.function.Function;

public class FunctionTest {
    @Test
    public void testFunction(){
        String s = doComput(5, x -> x + 2);
        Function<Integer, Integer> function = new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) {
                return integer - 2;
            }
        };
        Integer apply = function.apply(5);
        System.out.println(apply);

        System.out.println(s);
    }

    public String doComput(int i, Function<Integer,Integer> function){
        Object apply = function.apply(i);
        return  apply.toString();
    }





}
