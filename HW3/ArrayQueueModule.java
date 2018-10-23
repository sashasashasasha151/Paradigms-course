public class ArrayQueueModule {
    // I: size >= 0 && head >= 0 && i = 0..elements.length - 1 : a[i] != null
    private static int size = 0;
    private static int head = 0;
    private static Object[] elements = new Object[5];

    // pre: element != null
    // post: elements.length && head = head' && size = size' + 1 && i = 0..elements.length - 1' : a[i]' = a[i] && a[(head + size) % elements.length] = element
    public static void enqueue(Object element) {
        assert element != null;
        ensureCapacity(size + 1);
        elements[(head + size) % elements.length] = element;
        size++;
    }

    private static void ensureCapacity(int capacity) {
        if (capacity <= elements.length) {
            return;
        }
        Object[] newElements = new Object[2 * capacity];
        if (head == 0) {
            for (int i = 0; i < size; i++) {
                newElements[i] = elements[i];
            }
            elements = newElements;
        } else {
            for (int i = head; i < elements.length; i++) {
                newElements[i - head] = elements[i];
            }
            for (int i = 0; i <= (head + size) % elements.length; i++) {
                newElements[i + elements.length - head] = elements[i];
            }
            head = 0;
            elements = newElements;
        }
    }

    // pre: head > 0
    // post: R = a[head] && head = (head' + 1) % elements.length && size = size' - 1 && i = 0..elements.length - 1 : a[i]' = a[i]
    public static Object dequeue() {
        assert size > 0;
        Object x = elements[head];
        head = (head + 1) % elements.length;
        size--;
        return x;
    }

    // pre: head > 0
    // peek: R = a[head] && head = head' && size = size' && i = 0..elements.length - 1 : a[i]' = a[i]
    public static Object element() {
        assert size > 0;
        return elements[head];
    }

    // post: R = size && size = size' && head = head' && i = 0..elements.length - 1 : a[i]' = a[i]
    public static int size() {
        return size;
    }

    // post: R = size > 0 && size = size' && head = head' && i = 0..elements.length - 1 : a[i]' = a[i]
    public static boolean isEmpty() {
        return size == 0;
    }

    // post: head = 0 && size = 0 && i = 0..elements.length - 1 : a[i]' = null
    public static void clear() {
        Object[] newElements = new Object[5];
        elements = newElements;
        size = 0;
        head = 0;
    }

    // post: R = String ans && size = size' && head = head' && i = 0..elements.length - 1 : a[i]' = a[i]
    public static String toStr() {
        StringBuilder str = new StringBuilder("[");
        if (size == 0) return "[]";
        if (head < (head + size) % elements.length) {
            for (int i = head; i < (head + size) % elements.length; i++) {
                str.append(elements[i].toString());
                str.append(", ");
            }
        } else {
            for (int i = head; i < elements.length; i++) {
                str.append(elements[i].toString());
                str.append(", ");
            }
            for (int i = 0; i < (head + size) % elements.length; i++) {
                str.append(elements[i].toString());
                str.append(", ");
            }
        }
        str.delete(str.length() - 2, str.length());
        str.insert(str.length(), ']');
        return str.toString();
    }

    public static void main(String[] args) {
        //input
        for (int i = 0; i < 10; i++) {
            ArrayQueueModule.enqueue(i);
        }
        System.out.println(toStr());

        //pop half
        for (int i = 0; i < 5; i++) {
            ArrayQueueModule.dequeue();
        }
        System.out.println(toStr());

        //input half
        for (int i = 0; i < 5; i++) {
            ArrayQueueModule.enqueue(i);
        }
        System.out.println(toStr());

        //input more
        for (int i = 100; i < 108; i++) {
            ArrayQueueModule.enqueue(i);
        }
        System.out.println(toStr());
    }
}