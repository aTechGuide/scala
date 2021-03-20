package in.kamranali.algorithms.heap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Java by Default is Min Heap
 */
public class PriorityQueueUsageJava {

    public static void main(String[] args) {


        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        minHeap.addAll(Arrays.asList(5,1,3,2,4));

        System.out.println("Printing Min Heap");
        System.out.println(minHeap.poll());
        System.out.println(minHeap.poll());
        System.out.println(minHeap.poll());
        System.out.println(minHeap.poll());
        System.out.println(minHeap.poll());

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        // PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b.compareTo(a));
        maxHeap.addAll(Arrays.asList(5,1,3,2,4));

        System.out.println("\nPrinting Max Heap");
        System.out.println(maxHeap.poll());
        System.out.println(maxHeap.poll());
        System.out.println(maxHeap.poll());
        System.out.println(maxHeap.poll());
        System.out.println(maxHeap.poll());
    }
}
