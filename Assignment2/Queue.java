package Assignment2;

import java.util.Iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Queue<E> implements AbstractQueue<E> {
    private Node<E> head;
    private Node<E> tail;

    private int size;
    public Queue( ) {
        head = null;
        tail = null;
        size = 0;
    }
    @Override
    public void offer( E data ) {
        Node<E> newNode = new Node<>( data );
        if ( isEmpty( ) ) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }
    @Override
    public E poll( ) {
        if ( isEmpty( ) ) {
            throw new NoSuchElementException( );
        }
        E element = head.element;
        if ( size == 1 ) {
            head = null;
            tail = null;
        } else {
            Node<E> next = head.next;
            head.next = null;
            head = next;
        }
        size--;
        return element;
    }
    @Override
    public E peek( ) {
        if ( isEmpty( ) ) {
            throw new NoSuchElementException( );
        }
        return head.element;
    }
    @Override
    public int size( ) {
        return size;
    }
    @Override
    public boolean isEmpty( ) {
        return ( head == null && tail == null ); // or size == 0
    }
    @Override
    public Iterator<E> iterator( ) {
        return new Iterator<>( ) {
            private Node<E> current = head;
            public boolean hasNext( ) {
                return current != null;
            }
            public E next( ) {
                if ( !hasNext( ) ) {
                    throw new NoSuchElementException( );
                }
                E element = current.element;
                current = current.next;
                return element;
            }
        };
    }
    // toString method to represent the queue as a string
    @Override
    public String toString( ) {
        StringBuilder result = new StringBuilder( "[" );
        Node<E> current = head;

        while ( current != null ) {
            result.append( current.element );
            if ( current.next != null ) {
                result.append( ", " );
            }
            current = current.next;
        }
        result.append( "]" );
        return result.toString( );
    }
    private static class Node<E> {
        private E element;
        private Node<E> next;
        public Node( E element ) {
            this.element = element;
            this.next = null;
        }
    }
}
