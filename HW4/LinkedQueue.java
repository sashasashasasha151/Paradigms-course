public class LinkedQueue extends AbstractQueue implements Queue {

    private Node head;
    private Node tail;

    protected void enqueueImpl(Object element) {
        Node key = tail;
        tail = new Node(element, null);
        if (size == 0) {
            head = tail;
        } else {
            key.next = tail;
        }
    }

    protected Object[] toArrayImpl(Object[] newElements) {
        newElements[0] = head.value;
        Node key = head.next;
        for(int i=1;i<size;i++) {
            newElements[i] = key.value;
            key = key.next;
        }
        return newElements;
    }

    protected void remove() {
        head = head.next;
    }

    protected Object elementImpl() {
        return head.value;
    }

    protected void clearImpl() {};

}
