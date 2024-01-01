package Assignment2;

public interface AbstractStack<E> {
    // Push an element onto the stack
    void push(E element);

    // Pop an element from the stack
    E pop();

    // Peek at the top element without removing it
    E peek();

    int size();

    // Check if the stack is empty
    boolean isEmpty();


}
