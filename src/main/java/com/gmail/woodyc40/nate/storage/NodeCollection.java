package com.gmail.woodyc40.nate.storage;

import com.gmail.woodyc40.nate.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class NodeCollection implements Collection<Node> {
    private final List<Node> nodes;

    public NodeCollection(int size) {
        this.nodes = new ArrayList<>(size);
    }

    @Override
    public int size() {
        return this.nodes.size();
    }

    @Override
    public boolean isEmpty() {
        return this.nodes.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.nodes.contains(o);
    }

    @Override
    public Iterator<Node> iterator() {
        return this.nodes.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.nodes.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.nodes.toArray(a);
    }

    @Override
    public boolean add(Node node) {
        return this.nodes.add(node);
    }

    @Override
    public boolean remove(Object o) {
        return this.nodes.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.nodes.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Node> c) {
        return this.nodes.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.nodes.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.nodes.retainAll(c);
    }

    @Override
    public void clear() {
        this.nodes.clear();
    }
}