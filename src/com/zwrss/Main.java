package com.zwrss;

import com.zwrss.collections.immutable.Array;
import com.zwrss.collections.immutable.Stream;
import com.zwrss.collections.mutable.HashMap;
import com.zwrss.collections.mutable.LinkedList;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static void testArray() {

        System.out.println("Immutable Array:");
        System.out.println();

        Array<Integer> intArray = new Array<>(1, 2);

        System.out.println(intArray.size());
        intArray.foreach(System.out::println);

        Array<String> strArray = intArray.map(String.class, e -> "element: " + e);

        System.out.println();
        System.out.println(strArray.size());
        strArray.foreach(System.out::println);

        Array<String> strArray2 = strArray.add("element: x");

        System.out.println();
        System.out.println(strArray2.size());
        strArray2.foreach(System.out::println);

    }

    private static void testList() {


        System.out.println();
        System.out.println("Linked List");

        LinkedList<String> list = new LinkedList<>();
        list.add("one");
        list.add("two");
        list.add("three");

        System.out.println(list.size());
        list.foreach(System.out::println);
    }

    private static void testHashMap() {


        System.out.println();
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

        System.out.println();
        System.out.println("HashMap resizing:");

        HashMap<Integer, Integer> intsMap = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            System.out.println("Iteration: " + i);
            intsMap.put(i, i);
            System.out.println("Buckets: " + intsMap.getBucketsCount());
        }
    }

    private static void testStream() {

        System.out.println();
        System.out.println("Stream:");

        AtomicInteger counter = new AtomicInteger(0);
        Stream<String> stream = Stream.apply(() -> {
            int value = counter.getAndIncrement();
            System.out.println("Calculated value " + value);
            if (value < 50) return "Element: " + value;
            else return null;
        });
        for (int i = 0; i < 100; i++) {
            System.out.println("Try " + i);
            if (stream.isNonEmpty()) {
                System.out.println(stream.head());
                stream = stream.tail();
            }
        }

        System.out.println();
        System.out.println("Merged Stream:");

        AtomicInteger leftCounter = new AtomicInteger(0);
        Stream<String> leftStream = Stream.apply(() -> {
            int value = leftCounter.getAndIncrement();
            System.out.println("LEFT Calculated value " + value);
            if (value < 50) return "LEFT Element: " + value;
            else return null;
        });

        AtomicInteger rightCounter = new AtomicInteger(0);
        Stream<String> rightStream = Stream.apply(() -> {
            int value = rightCounter.getAndIncrement();
            System.out.println("RIGHT Calculated value " + value);
            if (value < 50) return "RIGHT Element: " + value;
            else return null;
        });

        Stream<String> mergedStream = leftStream.add(rightStream);

        for (int i = 0; i < 200; i++) {
            System.out.println("Try " + i);
            if (mergedStream.isNonEmpty()) {
                System.out.println(mergedStream.head());
                mergedStream = mergedStream.tail();
            }
        }

        System.out.println();
        System.out.println("Stream from array:");

        Stream<String> streamFromArray = Stream.apply("one", "two", "three");
        while(streamFromArray.isNonEmpty()) {
            System.out.println(streamFromArray.head());
            streamFromArray = streamFromArray.tail();
        }
    }

    public static void main(String[] args) {

        testArray();
        testList();
        testHashMap();
        testStream();

    }
}
