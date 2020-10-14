package cmtech.soft.equipment.utils.commonUtil.listUtil;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentReverseList<T> implements Iterable<T> {
    private List rawList;
    AtomicInteger size = new AtomicInteger(0);

    public ConcurrentReverseList(List list) {
        synchronized (this) {
            rawList = list;
            size.set(list.size());
        }
    }

    public synchronized void add(T t) {
        if (rawList != null) {
            rawList.add(t);
            size.incrementAndGet();
        }
    }

    public synchronized void remove(T t) {
        if (rawList != null && rawList.contains(t)) {
            rawList.remove(t);
            size.decrementAndGet();
        }
    }

    public Iterator iterator() {
        return new ReverseIterator(size.get());
    }

    class ReverseIterator implements Iterator {
        private AtomicInteger index;

        public ReverseIterator(int size) {
            index = new AtomicInteger(size);
        }

        @Override
        public boolean hasNext() {
            return index.get() > 0;
        }

        @Override
        public Object next() {
            return rawList.get(index.decrementAndGet());
        }
    }
}
