package Assignment2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Stack<E> implements AbstractStack<E> {
    private Node<E> top; // Top of the stack
    private int size; // Track the size of stackS
    // Constructor
    public Stack( ) {
        top = null;
        size = 0;
    }
    // Push an element onto the stack
    @Override
    public void push( E element ) {
        Node<E> newNode = new Node<>( element );
        if ( isEmpty( ) ) {
            top = newNode;
        } else {
            newNode.next = top;
            top = newNode;
        }
        size++;
    }
    // Pop an element from the stack
    @Override
    public E pop( ) {
        if ( isEmpty( ) ) {
            throw new NoSuchElementException( );
        }
        E element = top.element;
        Node<E> next = top.next;
        top.next = null;
        top = next;
        size--;
        return element;
    }
    // Peek at the top element without removing it
    @Override
    public E peek( ) {
        if ( isEmpty( ) ) {
            throw new NoSuchElementException( );
        }
        return top.element;
    }
    @Override
    public int size( ) {
        return size;
    }
    // Check if the stack is empty
    @Override
    public boolean isEmpty( ) {
        return top == null; // or size == 0
    }

    public Iterator<E> iterator( ) {
        return new Iterator<E>( ) {
            Node<E> current = top;
            @Override
            public boolean hasNext( ) {
                return current != null;
            }

            @Override
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
    @Override
    public String toString( ) {
        StringBuilder result = new StringBuilder( "[" );
        Node<E> current = top;

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
        }
    }
}


