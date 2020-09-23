package cmtech.soft.equipment.utils;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class ReverseList<T> implements Iterable<T> {
    private List<T> rwaList;

    public ReverseList(List list) {
        rwaList = list;
    }

    public void add(T t) {
        Optional.ofNullable(rwaList).ifPresent(l -> l.add(t));
    }

    public void remove(T t) {
        Optional.ofNullable(rwaList).ifPresent(l -> l.remove(t));
    }

    @Override
    public Iterator<T> iterator() {
        return new ReverseIterator(rwaList.size());
    }

    class ReverseIterator implements Iterator<T> {
        int index;

        public ReverseIterator(int size) {
            this.index = size;
        }

        @Override
        public boolean hasNext() {
            return index > 0;
        }

        @Override
        public T next() {
            index--;
            return rwaList.get(index);
        }
    }
}
