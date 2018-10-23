public interface Queue {
    // I: n >= 0 && a[n]

    // pre: element != null
    // post: n = n' + 1 && i=1..n' : a[i]' = a[i] && a[n] = element
    void enqueue(Object element);

    // pre: n > 0
    // post: R = a[1] && n = n' − 1 && i=2..n : a[i]' = a[i]
    Object  dequeue();

    // post: R = a[n] && n = n' && i=1..n : a[i]' = a[i]
    Object[] toArray();

    // pre: n > 0
    // post: R = a[1] && n = n' && i=1..n : a[i]' = a[i]
    Object element();

    // post: R = n && n = n' && i=1..n : a[i]' = a[i]
    int size();

    // post: R = n > 0 && n = n' && i=1..n : a[i]' = a[i]
    boolean isEmpty();

    // post: n = 0 && i = 0..elements.length - 1 : a[i]' = null
    void clear();
}
