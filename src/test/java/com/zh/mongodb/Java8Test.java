package com.zh.mongodb;

import com.zh.mongodb.common.MyFunction;
import com.zh.mongodb.entity.People;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class Java8Test {
    List<People> peoples= Arrays.asList(
            new People("张三",22,9999.99),
            new People("李四",52,8888.99),
            new People("王五",42,7777.99),
            new People("赵六",32,3333.99),
            new People("田七",12,5555.99)
    );

    public List<People> filterPeople(List<People> peoples, MyFunction<People> function) {
       List<People> peopleList=new ArrayList<>();
       for(People people:peoples){
           if(function.filterPeoples(people)){
               peopleList.add(people);
           }
       }
       return peopleList;
    }

    @Test
    public void testLambda(){
        List<People> peopleList = filterPeople(peoples, new MyFunction<People>() {
            @Override
            public boolean filterPeoples(People o) {
                return o.getAge() >= 23;
            }
        });
        for(People people:peopleList){
            System.out.println(people);
        }
    }

    @Test
    public void test2(){
        List<People> peopleList = filterPeople(peoples, (e) -> e.getAge() >= 30);
        peopleList.forEach(System.out::println);
    }

    @Test
    public void test3(){
        peoples.stream()
                .filter((e) -> e.getAge()>=25)
                .forEach(System.out::println);

        peoples.stream()
                .filter((e)->e.getAge()>=35)
                .map(people -> people.getName())
                .limit(1)
                .forEach(System.out::println);

        peoples.stream()
                .filter((e)->e.getAge()>=35)
                .map(People::getName)
                .limit(1)
                .forEach(System.out::println);
    }

    @Test
    public void test4(){
        List<People> peopleList = filterPeople(peoples, (e) -> e.getName().equals("张三"));
        peopleList.forEach(System.out::print);
        peopleList.stream().map(x->x.getName()).forEach(System.out::print);
    }
    // 语法格式1:无参数 无返回值Runnable
    @Test
    public void test5(){
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                System.out.println("runnable");
            }
        };
        runnable.run();

        System.out.println("--------------");
        Runnable r1=()-> System.out.println("r1");
        r1.run();
    }

    //语法格式2:有参数 无返回值Consumer
    @Test
    public void test6(){
        Consumer <String>consumer=(e)-> System.out.println(e);
        consumer.accept("中国牛逼");
    }

    //语法格式3：有参数 有返回值
    @Test
    public void test7(){
        Comparator<Integer> comparator =(x,y)->{
            System.out.println(x + y);
            return Integer.compare(x,y);
        };
        int compare = comparator.compare(5, 6);
        System.out.println(compare);
    }

    //语法格式4：有参数 有返回值
    @Test
    public void test8(){
        Comparator <Integer>comparator=(x,y)->Integer.compare(x,y);
        int compare = comparator.compare(9, 6);
        System.out.println(compare);
    }
    //需求：对一个数进行运算
    //四个函数接口 BiFunction /Consumer/Function/ Supplier
    @Test
    public void test9(){
        Consumer<Integer> consumer =(x)-> System.out.println(x+10);
        consumer.accept(5);
    }
    //运算操作
    @Test
    public void test10(){
        Function<Integer,Integer> function=(x)->x*x;
        System.out.println(function.apply(5));
        function=(x)->x+10;
        System.out.println(function.apply(10));
        function=(x)->x-10;
        System.out.println(function.apply(10));
        function=(x)->x/2;
        System.out.println(function.apply(10));

    }


}
