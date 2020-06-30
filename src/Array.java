import java.util.Arrays;

public class Array {
    private int arr[];
    private int size;
    private boolean isSorted;

    private Array() {
        this.isSorted = false;
    }

    public Array(int capacity) {
        this();
        arr = new int[capacity];
        this.size = 0;
    }

    public Array(int... args) {
        this();
        this.size = args.length;
        this.arr = args;
    }

    public int get(int index) {
        if (index >= size || index < 0)
            throw new ArrayIndexOutOfBoundsException(index);
        return arr[index];
    }

    public void set (int index, int value) {
        if (index >= size || index < 0)
            throw new ArrayIndexOutOfBoundsException(index);
        arr[index] = value;
    }

    public int length() {
        return size;
    }

    private void increaseCapacity() {
        int[] temp = arr;
        arr = new int[size * 2];
        System.arraycopy(temp, 0, arr, 0, size);
    }

    public void append(int value) {
        if (size >= arr.length) {
            increaseCapacity();
        }
        arr[size++] = value;
        isSorted = false;
    }

    public int deleteLast() {
        if (size == 0)
            throw new ArrayIndexOutOfBoundsException(-1);

        return arr[--size];
    }

    // homework
    // insert(index, value);
    public void insert(int index, int value) {
        if (index < 0 || index > size) {
            throw new ArrayIndexOutOfBoundsException("index " + index + " is out of bounds");
        }
        int[] temp = arr;
        arr = new int[++size];
        System.arraycopy(temp, 0, arr, 0, index);
        arr[index] = value;
        System.arraycopy(temp, index, arr, index + 1, temp.length - index);
    }
    // delete(val);
    //Удаляем первое найденное значение
    public void deleteVal(int val) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (arr[i] == val) {
                index = i;
                break;
            }
        }
        if (index < 0) {
            throw new RuntimeException("Value " + val + " is NOT found!");
        } else {
            deleteInd(index);
        }
    }
    // delete(index);
    public void deleteInd(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("index " + index + " is out of bounds");
        }
        int[] temp = arr;
        arr = new int[--size];
        System.arraycopy(temp, 0, arr, 0, index);
        System.arraycopy(temp, index + 1, arr, index, temp.length - index - 1);
    }
    // deleteAll();
    public void deleteAll() {
        if (arr == null) {
            throw new NullPointerException();
        }
        size = 0;
        arr = new int[size];
    }

    @Override
    public String toString() {
        if (arr == null) return "null";
        int iMax = size - 1;
        if (iMax == -1) return "[]";

        StringBuilder b = new StringBuilder();
        b.append('[');
        int i = 0;
        while (true) {
            b.append(arr[i]);
            if (i == iMax)
                return b.append(']').toString();
            b.append(", ");
            i++;
        }
    }

    public int find(int value) {
        for (int i = 0; i < size; i++) {
            if (arr[i] == value)
                return i;
        }
        return -1;
    }

    public boolean hasValue(int value) {
        if (!isSorted)
            throw new RuntimeException("try the 'find' method");

        int l = 0;
        int r = size;
        int m;
        while (l < r) {
            m = (l + r) >> 1; // (l + r) / 2
            if (value == arr[m])
                return true;
            else if (value < arr[m])
                r = m;
            else
                l = m + 1;
        }
        return false;
    }

    private void swap(int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    // сортировка пузырями как ни крути, имеет сложность O(n^2), что не эффективно для больших массивов
    public void sortBubble() {
        for (int i = 0; i < size - 1; i++) {         // Нет необходимости посещать
            for (int j = 0; j < size - i - 1; j++) { // уже отстортированные элементы
                if (arr[j] > arr[j + 1])
                    swap(j, j + 1);
            }
        }
        isSorted = true;
    }

    // сотрировка выбора имеет сложность O(n^2), что так же не эффективно для большиъ массивов
    public void sortSelect() {
        for (int flag = 0; flag < size; flag++) {
            int cMin = flag;
            for (int rem = flag + 1; rem < size; rem++)
                if (arr[rem] < arr[cMin])
                    cMin = rem;
            swap(flag, cMin);
        }
        isSorted = true;
    }

    // сортировка вставкой на мой взшляд так же имеет сложность O(n^2)
    public void sortInsert() {
        for (int out = 0; out < size; out++) {
            int temp = arr[out];
            int in = out;
            while (in > 0 && arr[in - 1] >= temp) {
                arr[in] = arr[in - 1];
                in--;
            }
            arr[in] = temp;
        }
        isSorted = true;
    }

    // сортировка подсчетом, самая простая версия которую смог изобразить
    // не работает с отрицательными числами в исходном массиве, нужно доработать
    // сложность алгоритма линейная и напрямую зависит от к-ва элементов в исходном массиве и диапазона данных
    // O(n + k).
    // да, ещё и память дополнительную требует, но это может и не проблема.
    public void countingSort() {
        if (arr == null) {
            throw new NullPointerException();
        }
        if (arr.length == 1) {
           return;
        }
        // найдем максимальное значение исходного массива, чтобы знать каким размером создать массив подсчетов
        int max = arr[0];
        for(int each : arr) {
            if (each > max) {
                max = each;
            }
        }
        // массив вначений подсчетов размерностью макс число + 1
        int[] counts = new int[max + 1];
        // заполняем массив подсчетов
        for (int each : arr) {
            counts[each]++;
        }
        int position = 0; // начинаем перезаписывать исхожный массив с начального индекса
        for (int number = 0; number < counts.length; number++) { // берём значение
            int count = counts[number]; // сколько раз в массиве встретилось это значение
            while (count > 0) {
                arr[position] = number; // столько раз записываем в исходный массив это значение
                position++;
                count--;
            }
        }
    }
}
