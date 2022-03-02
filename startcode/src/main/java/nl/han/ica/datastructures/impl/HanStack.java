package nl.han.ica.datastructures.impl;

import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.icss.ast.ASTNode;

import java.util.ArrayList;

public class HanStack<T extends ASTNode> implements IHANStack<T> {

    ArrayList<T> arrayList = new ArrayList<>();

    @Override
    public void push(T value) {
        arrayList.add(value);
    }

    @Override
    public T pop() {
        return arrayList.remove(arrayList.size()-1);
    }

    @Override
    public T peek() {
        return arrayList.get(arrayList.size()-1);
    }
}

