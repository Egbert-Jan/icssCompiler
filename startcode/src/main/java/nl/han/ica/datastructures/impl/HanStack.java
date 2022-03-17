package nl.han.ica.datastructures.impl;

import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.icss.ast.ASTNode;


public class HanStack<T> implements IHANStack<T> {

//    ArrayList<T> arrayList = new ArrayList<>();
    IHANLinkedList<T> linkedList = new HANLinkedList<>();

    @Override
    public void push(T value) {
        linkedList.insert(Integer.MAX_VALUE, value);
//        arrayList.add(value);
    }

    @Override
    public T pop() {
        var item = linkedList.get(linkedList.getSize());
        linkedList.delete(linkedList.getSize());
        return item;
//        return arrayList.remove(arrayList.size()-1);
    }

    @Override
    public T peek() {
        return linkedList.get(Integer.MAX_VALUE);
//        return arrayList.get(arrayList.size()-1);
    }
}

