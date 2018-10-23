public abstract class AbstractQueue implements Queue {
    protected int size;

    public void enqueue(Object element) {
        assert element != null;
        enqueueImpl(element);
        size++;
    }

    protected abstract void enqueueImpl(Object element);

    public Object dequeue() {
        assert size > 0;
        Object result = element();
        size--;
        remove();
        return result;
    }

    protected abstract void remove();

    public Object element() {
        assert size > 0;
        return elementImpl();
    }

    protected abstract Object elementImpl();

    public Object[] toArray() {
        if(size==0) {
            Object[] newElements = new Object[0];
            return newElements;
        } else {
            Object[] newElements = new Object[size];
            newElements = toArrayImpl(newElements);
            return newElements;
        }
    }

    protected abstract Object[] toArrayImpl(Object[] newElements);

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        clearImpl();
        size = 0;
    }

    protected abstract void clearImpl();
}
