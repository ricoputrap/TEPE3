import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TP3 {
    private static InputReader in;
    private static PrintWriter out;

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        int banyakPos = in.nextInt(), banyakTerowongan = in.nextInt();
        ArrayList<ArrayList<AdjacentList>> graph = new ArrayList<>();

        // posA ke posB dihubungkan oleh terowongan sepanjang panjangTerowongan
        // dan terowongan tsb berukuran ukuranTerowongan
        for (int i = 0; i < banyakTerowongan; i++) {
            int posA = in.nextInt();
            int posB = in.nextInt();
            int panjangTerowongan = in.nextInt();
            int ukuranTerowongan = in.nextInt();
        }

        // Ada sebanyak banyakKurcaci di dalam sistem terowongan
        // setiap kurcaci ada di posKurcaci
        int banyakKurcaci = in.nextInt();
        for (int i = 0; i < banyakKurcaci; i++) {
            int posKurcaci = in.nextInt();
        }

        int banyakQuery = in.nextInt();
        for (int i = 0; i < banyakQuery; i++) {
            String aktivitas = in.next();
            if (aktivitas.equals("KABUR")) {
                kabur();
            }
            else if (aktivitas.equals("SIMULASI")) {
                simulasi();
            }
            else {
                querySuper();
            }
        }
        out.close();
    }

    static void kabur() {  
        int posAsal = in.nextInt();
        int posTujuan = in.nextInt();
    }

    static void simulasi() {  
        int banyakPosIstirahat = in.nextInt();

        for (int i = 0; i < banyakPosIstirahat; i++) {
            int posIstirahatkei = in.nextInt();
        }
        
    }

    static void querySuper() {  
        int posV1 = in.nextInt();
        int posV2 = in.nextInt();
        int posV3 = in.nextInt();
    }

    static class AdjacentList implements Comparable<AdjacentList>{
        int pos;
        Long panjangTerowongan;
        int size;
    
        AdjacentList(int pos, Long panjangTerowongan, int size) {
            this.pos = pos;
            this.panjangTerowongan = panjangTerowongan;
            this.size = size;
        }

        @Override
        public int compareTo(AdjacentList o) {
            // TODO Auto-generated method stub
            return this.panjangTerowongan.compareTo(o.panjangTerowongan) ;
        } 
    }

    public static long[] dijkstra(int V, ArrayList<ArrayList<AdjacentList>> graph, int src) {
        long[] distance = new long[V];
        for (int i = 0; i < V; i++)
            distance[i] = Integer.MAX_VALUE;
        distance[src] = 0;
 
        PriorityQueue<AdjacentList> pq = new PriorityQueue<>();
        pq.add(new AdjacentList(src+1, 0));
 
        while (pq.size() > 0) {
            AdjacentList current = pq.poll();
 
            for (AdjacentList n : graph.get(current.pos-1)) {
                if (distance[current.pos-1] + n.panjangTerowongan < distance[n.pos-1]) {
                    distance[n.pos-1] = n.panjangTerowongan + distance[current.pos-1];
                    pq.add(new AdjacentList(n.pos, distance[n.pos-1]));
                }
            }
        }

        // If you want to calculate distance from source to
        // a particular target, you can return
        // distance[target]
        return distance;
    }

    // Main class (MinHeap)
    static class GFG {
    
        // Member variables of this class
        private int[] Heap;
        private int size;
        private int maxsize;
    
        // Initializing front as static with unity
        private static final int FRONT = 1;
    
        // Constructor of this class
        public GFG(int maxsize)
        {
    
            // This keyword refers to current object itself
            this.maxsize = maxsize;
            this.size = 0;
    
            Heap = new int[this.maxsize + 1];
            Heap[0] = Integer.MIN_VALUE;
        }
    
        // Returning the position of
        // the parent for the node currently
        // at pos
        private int parent(int pos) { return pos / 2; }

        // Returning the position of the
        // left child for the node currently at pos
        private int leftChild(int pos) { return (2 * pos); }

        // Returning the position of
        // the right child for the node currently
        // at pos
        private int rightChild(int pos)
        {
            return (2 * pos) + 1;
        }

        // Returning true if the passed
        // node is a leaf node
        private boolean isLeaf(int pos)
        {
    
            if (pos > (size / 2)) {
                return true;
            }
    
            return false;
        }
    
        // To swap two nodes of the heap
        private void swap(int fpos, int spos) {

            int tmp;
            tmp = Heap[fpos];
    
            Heap[fpos] = Heap[spos];
            Heap[spos] = tmp;
        }
    
        // To heapify the node at pos
    private void minHeapify(int pos) {     
            if(!isLeaf(pos)) {
                int swapPos= pos;
                // swap with the minimum of the two children
                // to check if right child exists. Otherwise default value will be '0'
                // and that will be swapped with parent node.
                if(rightChild(pos)<=size)
                    swapPos = Heap[leftChild(pos)]<Heap[rightChild(pos)]?leftChild(pos):rightChild(pos);
                else
                    swapPos= Heap[leftChild(pos)];
                    
                if(Heap[pos]>Heap[leftChild(pos)] || Heap[pos]> Heap[rightChild(pos)]){
                    swap(pos,swapPos);
                    minHeapify(swapPos);
                }
            
            }      
        }
    
        // To insert a node into the heap
        public void insert(int element) {
            if (size >= maxsize) {
                return;
            }
    
            Heap[++size] = element;
            int current = size;
    
            while (Heap[current] < Heap[parent(current)]) {
                swap(current, parent(current));
                current = parent(current);
            }
        }
    
        // To remove and return the minimum
        // element from the heap
        public int remove() {
    
            int popped = Heap[FRONT];
            Heap[FRONT] = Heap[size--];
            minHeapify(FRONT);
    
            return popped;
        }
    }

    


    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }
    }
}