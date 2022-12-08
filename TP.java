import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TP3 {
    private static InputReader in;
    private static PrintWriter out;
    private static Graph sistemTerowongan;

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        in = new InputReader(inputStream);
        OutputStream outputStream = System.out;
        out = new PrintWriter(outputStream);

        int banyakPos = in.nextInt(), banyakTerowongan = in.nextInt();

        // Inisiasi sistem terowongan berupa graf
        sistemTerowongan = new Graph();

        // Inisiasi vertex2 (pos-pos di sistem terowongan) 
        for (int i = 0; i < banyakPos; i++) {
            sistemTerowongan.addPos(i+1);
        }

        // posA ke posB dihubungkan oleh terowongan sepanjang panjangTerowongan
        // dan terowongan tsb berukuran ukuranTerowongan
        for (int i = 0; i < banyakTerowongan; i++) {
            int nomorPosAsal = in.nextInt();
            int nomorPosTujuan = in.nextInt();
            int panjangTerowongan = in.nextInt();
            int sizeTerowongan = in.nextInt();

            // Membuat terowongan dan dimasukkan ke dalam sistem 
            sistemTerowongan.addTerowongan(nomorPosAsal, nomorPosTujuan, panjangTerowongan, sizeTerowongan);
        }

        // Ada sebanyak banyakKurcaci di dalam sistem terowongan
        // setiap kurcaci ada di posKurcaci
        int banyakKurcaci = in.nextInt();
        for (int i = 0; i < banyakKurcaci; i++) {
            int nomorPosKurcaci = in.nextInt();

           // Set pos hasKurcaci => true
            sistemTerowongan.getPosByNomor(nomorPosKurcaci).setHasKurcaci();
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
        int nomorPosAsal = in.nextInt();
        int nomorPosTujuan = in.nextInt(); 
        
        ArrayList<Integer> daftarNoPosTerkunjungi = new ArrayList<Integer>();
        kunjungi(nomorPosAsal, nomorPosTujuan, daftarNoPosTerkunjungi, Integer.MAX_VALUE);

        // 1. Cari seluruh jalur yang tersedia dari pos F ke pos E -> daftarJalur
    
            // cari jalur2nya
            // add ke dalam daftar jalur
       // sistemTerowongan.getPosByNomor(posAsal).daftarTerowongan.get(posTujuan)
        // 2. Dapatkan ukuran terowongan terkecil di setiap jalur -> daftarUkuranTerkecil
        // 3. Dapatkan ukuran terowongan terbesar di dalam `daftarUkuranTerkecil` -> ukuranTerowonganTerbesar
        // 4. Return `ukuranTerowonganTerbesar` sebagai nilai untuk jumlah logistik terbanyak
        //     yang dapat dibawa kabur dari pos F ke pos E
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

        ArrayList<Integer> daftarX = new ArrayList<Integer>();
        kunjungi(1, 10, daftarX, 0);
    }

    // rekursif
    static int kunjungi(int noPosSekarang, int noPosTujuan, ArrayList<Integer> daftarNoPosTerkunjungi, int ukuranTerowonganTerkecilSejauhIni) {

        if (noPosSekarang == noPosTujuan) {
            return ukuranTerowonganTerkecilSejauhIni;
        }
        
        // pos sekarang masuk ke daftar pos yg sudah terkunjungi
        ArrayList<Integer> daftarNoPosTerkunjungiUpdated = new ArrayList<Integer>(daftarNoPosTerkunjungi);
        daftarNoPosTerkunjungiUpdated.add(noPosSekarang);

        ArrayList<Integer> daftarUkuranTerowonganTerkecilDariSemuaJalur = new ArrayList<Integer>();
        int ukuranTerowonganTerbesar = 0;

        Vertex posSekarang = sistemTerowongan.getPosByNomor(noPosSekarang);
        ArrayList<Edge> daftarTerowonganPosSekarang = posSekarang.getDaftarTerowongan();
        

        for (int i = 0; i < daftarTerowonganPosSekarang.size(); i++) {
            Edge terowonganSekarang = daftarTerowonganPosSekarang.get(i);
            int noPosTujuanTerowongan = terowonganSekarang.getNomorPosTujuan();

            // Jika pos tujuan dari pos sekarang sudah pernah dikunjungi => skip
            // supaya tidak mengunjungi ulang
            if (daftarNoPosTerkunjungi.contains(noPosTujuan)) {
                continue;
            }

            // Membandingkan ukuran terowongan ini dengan yang sudah dilalui -> ambil yg terkecil 
            int ukuranTerowonganTerkecil = ukuranTerowonganTerkecilSejauhIni;
            if (ukuranTerowonganSekarang < ukuranTerowonganTerkecil)
                ukuranTerowonganTerkecil = ukuranTerowonganSekarang;
            
            // Jika penyusup sudah sampai di pos tujuan 
            if (noPosTujuanTerowongan == noPosTujuan) {
                daftarUkuranTerowonganTerkecilDariSemuaJalur.add(ukuranTerowonganTerkecil);
                // berhenti = true;
            }

            ukuranTerowonganTerbesar = kunjungi(noPosTujuanTerowongan, noPosTujuan, daftarNoPosTerkunjungiUpdated, ukuranTerowonganTerkecil);
        }

        
        for (int i = 0; i < daftarUkuranTerowonganTerkecilDariSemuaJalur.size(); i++) {
            if (ukuranTerowonganTerbesar > daftarUkuranTerowonganTerkecilDariSemuaJalur.get(i)) {
                ukuranTerowonganTerbesar = daftarUkuranTerowonganTerkecilDariSemuaJalur.get(i);
            }
        }

        return ukuranTerowonganTerbesar;
    }

    // Edge = Terowongan antarpos
    static class Edge {
        private Vertex posTujuan;
        private int panjang;
        private int luas;
      
        public Edge(Vertex posTujuan, int panjang, int luas) {
          this.posTujuan = posTujuan;
          this.panjang = panjang;
          this.luas = luas;
        }

        public int getNomorPosTujuan() {
            return this.posTujuan.getNomorPos();
        }

        public int getLuas() {
            return this.luas;
        }
    }

    // Vertex = pos
    static class Vertex {
        private int nomorPos;
        private ArrayList<Edge> daftarTerowongan;
        private boolean hasKurcaci;

        public Vertex(int nomorPos) {
            this.nomorPos = nomorPos;
            this.daftarTerowongan = new ArrayList<Edge>();
            this.hasKurcaci = false;
        }

        public int getNomorPos() {
            return this.nomorPos;
        }
        public boolean getHasKurcaci() {
            return this.hasKurcaci;
        }
        public boolean setHasKurcaci() {
            return this.hasKurcaci = true;
        }
        public ArrayList<Edge> getDaftarTerowongan() {
            return this.daftarTerowongan;
        }
        public void addTerowongan(Edge terowongan) {
            this.daftarTerowongan.add(terowongan);
        }
    }

    static class Graph {
        private ArrayList<Vertex> daftarPos;
    
        public Graph() {
            this.daftarPos = new ArrayList<Vertex>();
        }

        // addPos untuk menambahkan pos ke sistem terowongan
        // hasKurcaci untuk cek apakah pos baru tersebut punya seorang kurcaci
        public void addPos(int nomorPos) {
            Vertex posBaru = new Vertex(nomorPos);
            this.daftarPos.add(posBaru);
        }

        // addTerowongan untuk membuat sebuah terowongan 
        // yang menghubungkan dua pos
        public void addTerowongan(int nomorPosAsal, int nomorPosTujuan, int panjangTerowongan, int sizeTerowongan) {
            Vertex posAsal = this.getPosByNomor(nomorPosAsal);
            Vertex posTujuan = this.getPosByNomor(nomorPosTujuan);

            Edge terowonganKePosTujuan = new Edge(posTujuan, panjangTerowongan, sizeTerowongan);
            Edge terowonganKePosAsal = new Edge(posAsal, panjangTerowongan, sizeTerowongan);

            // menghubungkan terowongan asal dan tujuan
            posAsal.addTerowongan(terowonganKePosTujuan);
            posTujuan.addTerowongan(terowonganKePosAsal);

            // update posAsal dan posTujuan ke dalam `daftarPos`
            this.daftarPos.set(nomorPosAsal - 1, posAsal);
            this.daftarPos.set(nomorPosTujuan - 1, posTujuan);

            // mendapatkan sebuah objek pos (Vertex)
            // yang telah terdaftar dalam sistem terowongan bawah tanah (Graph)
            // berdasarkan nomornya
            public Vertex getPosByNomor(int nomorPos) {
                return this.daftarPos.get(nomorPos - 1);
            }

            public void cetakDaftarPos() {
                for (int i = 0; i < this.daftarPos.size(); i++) {
                  System.out.println(this.daftarPos.get(i));
                }
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