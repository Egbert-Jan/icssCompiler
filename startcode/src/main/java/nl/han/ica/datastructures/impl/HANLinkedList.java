package nl.han.ica.datastructures.impl;

import nl.han.ica.datastructures.IHANLinkedList;

class Node<T> {
    T data;
    Node<T> next;

    public Node(T data, Node<T> next) {
        this.data = data;
    }
}

public class HANLinkedList<T> implements IHANLinkedList<T> {

    Node<T> head;


    @Override
    public void addFirst(T value) {
        var oldHead = head;
        head = new Node<>(value, oldHead);
    }

    @Override
    public void clear() {
        head = null;
    }

    @Override
    public void insert(int index, T value) {
        if (index == 0) {
            //new head node
            addFirst(value);
        } else {
            //New node in between
            var beginNode = getNode(index-1);
            if (beginNode == null) {
                head = new Node<>(value, null);
                return;
            }

            var endNode = beginNode.next;
            beginNode.next = new Node<>(value, endNode);

//            var beginNode = getNode(index-1);
//            var endNode = beginNode.next;
//            beginNode.next = new Node<>(value, endNode);
        }
    }

    @Override
    public void delete(int pos) {
        if(pos == 0) {
            removeFirst();
        } else {
//            Node<T> nodeBefore = getNode(pos - 1);
//            nodeBefore.next = nodeBefore.next.next;

            Node<T> nodeBefore = getNode(pos - 1);
            if(nodeBefore == null) { return; } //|| nodeBefore.next == null

            nodeBefore.next = nodeBefore.next == null ? null : nodeBefore.next.next;
        }
    }

    @Override
    public T get(int pos) {
        return getNode(pos).data;
    }

    @Override
    public void removeFirst() {
        if(head != null) {
            head = head.next;
        }
    }

    @Override
    public T getFirst() {
        return head.data;
    }

    @Override
    public int getSize() {
        Node<T> current = head;
        int count = 0;

        while (current.next != null) {
            current = current.next;
            count++;
        }

        return count;
    }

    //Get node on position or last available node
    private Node<T> getNode(int pos) {
        Node<T> current = head;

        for (int x = 0; x < pos; x++) {
            if (current == null || current.next == null) {
                break;
            } else {
                current = current.next;
            }
        }

        return current;
    }
}
