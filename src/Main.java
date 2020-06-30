
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] arr; // int arr[]
        arr = new int[5];
        int[] arr2 = {1, 2, 3, 4};

        Array a0 = new Array(1, -2, -3, -4, 5);
        a0.sortBubble();
        System.out.println(a0);

        a0.insert(4, 10);
        System.out.println(a0);

        a0.deleteVal(10);
        System.out.println(a0);

        a0.deleteInd(0);
        System.out.println(a0);

        a0.deleteAll();
        System.out.println(a0);

        Array a1 = new Array(1, 9, 2, 4, 5, 4, 5);
        a1.countingSort();
        System.out.println(a1);
    }
}
