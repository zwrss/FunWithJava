package com.zwrss.collections.mutable;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Very simple Linked List. It is not supposed to be optimal neither 100% valid. Educational means only.
 * @param <T> type of elements
 */
public class LinkedList<T> implements Iterable<T> {

    private class Link {
        T element;
        Link next;

        Link(T element) {
            this.element = element;
        }
    }

    private Link next = null;

    @Override
    public void add(T element) {
        if (next == null) next = new Link(element);
        else {
            Link link = next;
            while (link.next != null) {
                link = link.next;
            }
            link.next = new Link(element);
        }
    }

    @Override
    public void foreach(Consumer<T> callback) {
        Link link = next;
        while(link != null) {
            callback.accept(link.element);
            link = link.next;
        }
    }

    @Override
    public T find(Predicate<T> predicate) {
        Link link = next;
        while(link != null) {
            if (predicate.test(link.element)) return link.element;
            link = link.next;
        }
        return null;
    }

    public int size() {
        int size = 0;
        Link link = next;
        while(link != null) {
            size += 1;
            link = link.next;
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        return next == null;
    }
}
