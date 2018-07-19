import edu.princeton.cs.algs4.StdIn;

public class Permutation {

    public static void main(String[] args) {
        int k = Integer.valueOf(args[0]);

        RandomizedQueue<String> rq = new RandomizedQueue<>();
        String str = StdIn.readString();

        rq.enqueue(str);

        while (!StdIn.isEmpty()) {
            str = StdIn.readString();
            rq.enqueue(str);
        }

        for (int i = 0; i < k; i++) {
            System.out.println(rq.dequeue());
        }

    }
}
