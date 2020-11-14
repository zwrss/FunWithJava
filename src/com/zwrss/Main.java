package com.zwrss;

import com.zwrss.collections.immutable.Array;
import com.zwrss.collections.mutable.HashMap;
import com.zwrss.collections.mutable.LinkedList;

public class Main {

    public static void main(String[] args) {

        System.out.println("Immutable Array:");
        System.out.println("");

        Array<Integer> intArray = new Array<>(1, 2);

        System.out.println(intArray.size());
        intArray.foreach(System.out::println);

        Array<String> strArray = intArray.map(String.class, e -> "element: " + e);

        System.out.println("");
        System.out.println(strArray.size());
        strArray.foreach(System.out::println);

        Array<String> strArray2 = strArray.add("element: x");

        System.out.println("");
        System.out.println(strArray2.size());
        strArray2.foreach(System.out::println);

        System.out.println("");
        System.out.println("Linked List");

        LinkedList<String> list = new LinkedList<>();
        list.add("one");
        list.add("two");
        list.add("three");

        System.out.println(list.size());
        list.foreach(System.out::println);

        System.out.println("");
        System.out.println("HashMap:");

        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        System.out.println(map.get(0));
        System.out.println(map.get(1));
        System.out.println(map.get(2));
        System.out.println(map.get(3));
        System.out.println(map.get(4));

    }
}
