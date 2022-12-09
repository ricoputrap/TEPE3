import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
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
        int nomorPosTujuanAkhir = in.nextInt(); 
        
        ArrayList<Integer> daftarNoPosTerkunjungi = new ArrayList<Integer>();
        ArrayList<Stack<Edge>> daftarJalur = new ArrayList<Stack<Edge>>();
        // kunjungi(nomorPosAsal, nomorPosTujuan, daftarNoPosTerkunjungi, Integer.MAX_VALUE);

        // 1. Cari seluruh jalur yang tersedia dari pos F ke pos E -> daftarJalur
        // sebuah jalur adalah sekumpulan terowongan -> ArrayList<Edge> jalur
        // seluruh jalur -> ArrayList<ArrayList<Edge>> semuaJalur
        ArrayList<Stack<Edge>> semuaJalur = getSemuaJalur(nomorPosAsal, nomorPosTujuanAkhir, daftarNoPosTerkunjungi, daftarJalur);
        System.out.println("semuaJalur: " + semuaJalur);
    
            // cari jalur2nya
            // add ke dalam daftar jalur
       // sistemTerowongan.getPosByNomor(posAsal).daftarTerowongan.get(posTujuan)

        // 2. Dapatkan ukuran terowongan terkecil di setiap jalur -> daftarUkuranTerkecil
        ArrayList<Integer> daftarUkuranTerowonganTerkecilDiSetiapJalur = new ArrayList<Integer>();

        // 3. Dapatkan ukuran terowongan terbesar di dalam `daftarUkuranTerkecil` -> ukuranTerowonganTerbesar
        int jumlahLogistikDapatDibawaTerbanyak = 0;

        // 4. Return `ukuranTerowonganTerbesar` sebagai nilai untuk jumlah logistik terbanyak
        //     yang dapat dibawa kabur dari pos F ke pos E
        System.out.println(jumlahLogistikDapatDibawaTerbanyak);
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
            int ukuranTerowonganSekarang = terowonganSekarang.getLuas();
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
    
    /**
     * Mendapatkan daftar semua jalur yang dapat dilalui dari pos sekarang hingga ke pos tujuan akhir
     * @param noPosSekarang nomor pos saat ini di mana penyusup berada
     * @param noPosTujuanAkhir nomor pos yang dituju
     * @param daftarNoPosTerkunjungi daftar nomor pos yang telah dikunjungi sebelum tiba di pos sekarang
     * @param daftarJalur daftar jalur
     * @return
     */
    static ArrayList<Stack<Edge>> getSemuaJalur(
        int noPosSekarang,
        int noPosTujuanAkhir,
        ArrayList<Integer> daftarNoPosTerkunjungi,
        ArrayList<Stack<Edge>> daftarJalur
    ) {
        System.out.println("====== MASUK POS " + noPosSekarang + " =====");
        // base case
        if (noPosSekarang == noPosTujuanAkhir) {
            System.out.println("Riwayat kunjungan pos: " + daftarNoPosTerkunjungi);
            System.out.println("Seluruh jalur: " + daftarJalur);
            System.out.println("===== POS " + noPosSekarang + " SELESAI!!!");
            return daftarJalur;
        }

        // Update visited pos
        ArrayList<Integer> daftarNoPosTerkunjungiSekarang = new ArrayList<Integer>(daftarNoPosTerkunjungi);
        daftarNoPosTerkunjungiSekarang.add(noPosSekarang);
        System.out.println("Daftar pos terkunjungi: " + daftarNoPosTerkunjungiSekarang);

        // Mendapatkan daftar terowongan milik pos sekarang
        Vertex posSekarang = sistemTerowongan.getPosByNomor(noPosSekarang);
        ArrayList<Edge> daftarTerowonganPosSekarang = posSekarang.getDaftarTerowongan();
        System.out.println("Daftar terowongan: " + daftarTerowonganPosSekarang);

        ArrayList<Stack<Edge>> daftarJalurSekarang = new ArrayList<Stack<Edge>>(daftarJalur);

        // kunjungi setiap terowongan
        for (int i = 0; i < daftarTerowonganPosSekarang.size(); i++) {
            Edge terowongan = daftarTerowonganPosSekarang.get(i);
            int noPosTujuanTerowongan = terowongan.getNomorPosTujuan();

            System.out.println("NEMU TEROWONGAN dari POS " + noPosSekarang + " ke POS " + noPosTujuanTerowongan);
            System.out.println("DAFTAR JALUR SEBELUMNYA: " + daftarJalurSekarang);


            // skip (jangan melalui terowongan ini) jika pos tujuannya sudah pernah dikunjungi
            if (daftarNoPosTerkunjungiSekarang.contains(noPosTujuanTerowongan)) {
                System.out.println("STOP! POS " + noPosTujuanTerowongan + " sudah pernah dikunjungi.");
                continue;
            }

            // mencari sebuah jalur (sekumpulan terowongan) yang sebelumnya telah dilalui
            // hingga bisa berada di pos sekarang
            Stack<Edge> jalur = null;
            int indexJalur = -1;
            
            for (int j = 0; j < daftarJalurSekarang.size(); j++) {
                if (daftarJalurSekarang.get(j).peek().getNomorPosTujuan() == noPosSekarang) {
                    jalur = daftarJalurSekarang.get(j);
                    indexJalur = j;
                    break;
                }
            }

            // buat objek jalur jika terowongan ini adalah 
            // terowongan pertama yang dapat dilalui
            if (jalur == null) {
                jalur = new Stack<Edge>();
            }
            
            // tambahkan terowongan ini ke jalur yang dapat dilalui menuju pos tujuan akhir
            jalur.add(terowongan);

            // update jalur ini ke dalam daftar jalur
            if (indexJalur == -1) {
                daftarJalurSekarang.add(jalur);
            }
            else {
                daftarJalurSekarang.set(indexJalur, jalur);
            }

            System.out.println("JALUR: " + jalur);
            System.out.println("DAFTAR JALU SEKARARANG: " + daftarJalurSekarang);

            daftarJalurSekarang = getSemuaJalur(noPosTujuanTerowongan, noPosTujuanAkhir, daftarNoPosTerkunjungiSekarang, daftarJalurSekarang);
            System.out.println("Daftar jalur setelah penelusuran dari pos " + noPosSekarang + ": " + daftarJalurSekarang);

            System.out.println("====== SAATNYA CEK TEROWONGAN SELANJUTNYA ======");
        }

        // semuaJalur: [
        //     [Terowongan menuju pos 1, Terowongan menuju pos 3],
        //     [Terowongan menuju pos 4, Terowongan menuju pos 3],
        //     [Terowongan menuju pos 4, Terowongan menuju pos 1, Terowongan menuju pos 3],
        //     [Terowongan menuju pos 3]
        // ]

        return daftarJalurSekarang;
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

        @Override
        public String toString() {
            return "Terowongan menuju pos " + posTujuan.getNomorPos();
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
        }

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