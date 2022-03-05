package nl.han.ica.datastructures.impl;

import nl.han.ica.datastructures.IHANLinkedList;

import java.util.ArrayList;

public class HANLinkedList<T> implements IHANLinkedList<T> {

    ArrayList<T> arrayList = new ArrayList<>();

    @Override
    public void addFirst(T value) {
        arrayList.add(0, value);
    }

    @Override
    public void clear() {
        arrayList.clear();
    }

    @Override
    public void insert(int index, T value) {
        arrayList.add(index, value);
    }

    @Override
    public void delete(int pos) {
        arrayList.remove(pos);
    }

    @Override
    public T get(int pos) {
        return arrayList.get(pos);
    }

    @Override
    public void removeFirst() {
        arrayList.remove(0);
    }

    @Override
    public T getFirst() {
        return arrayList.get(0);
    }

    @Override
    public int getSize() {
        return arrayList.size();
    }
}
