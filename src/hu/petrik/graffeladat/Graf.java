package hu.petrik.graffeladat;

import java.util.*;

/**
 * Irányítatlan, egyszeres gráf.
 */
public class Graf {
    private final int csucsokSzama;
    /**
     * A gráf élei.
     * Ha a lista tartalmaz egy (A,B) élt, akkor tartalmaznia kell
     * a (B,A) vissza irányú élt is.
     */
    private final ArrayList<El> elek = new ArrayList<>();
    /**
     * A gráf csúcsai.
     * A gráf létrehozása után új csúcsot nem lehet felvenni.
     */
    private final ArrayList<Csucs> csucsok = new ArrayList<>();
    
    /**
     * Létehoz egy úgy, N pontú gráfot, élek nélkül.
     * 
     * @param csucsok A gráf csúcsainak száma
     */
    public Graf(int csucsok) {
        this.csucsokSzama = csucsok;
        
        // Minden csúcsnak hozzunk létre egy új objektumot
        for (int i = 0; i < csucsok; i++) {
            this.csucsok.add(new Csucs(i));
        }
    }

    /**
     * Hozzáad egy új élt a gráfhoz.
     * Mindkét csúcsnak érvényesnek kell lennie:
     * 0 &lt;= cs &lt; csúcsok száma.
     * 
     * @param cs1 Az él egyik pontja
     * @param cs2 Az él másik pontja
     */
    public void hozzaad(int cs1, int cs2) {
        if (cs1 < 0 || cs1 >= csucsokSzama ||
            cs2 < 0 || cs2 >= csucsokSzama) {
            throw new IndexOutOfBoundsException("Hibas csucs index");
        }
        
        // Ha már szerepel az él, akkor nem kell felvenni
        for (El el: elek) {
            if (el.getCsucs1() == cs1 && el.getCsucs2() == cs2) {
                return;
            }
        }
        
        elek.add(new El(cs1, cs2));
        elek.add(new El(cs2, cs1));
    }
    public void szelessegiBejaras(int start){

        List<Integer> alreadyLooked = new ArrayList<>();
        Queue<Integer> nextOnes = new LinkedList<>();

        nextOnes.add(start);
        alreadyLooked.add(start);

        while (nextOnes.size() > 0) {
            var k = nextOnes.poll();
            System.out.println(this.csucsok.get(k));
            for (var el : this.elek) {
                if (el.getCsucs1() == k && !alreadyLooked.contains(el.getCsucs2())) {
                    nextOnes.add(el.getCsucs2());
                    alreadyLooked.add(el.getCsucs2());
                }
            }
        }
    }
    
//    public void melysegiBejaras(int start){
//        List<Integer> alreadyLooked = new ArrayList<>();
//        Stack<Integer> nextOnes = new Stack<>();
//
//        nextOnes.add(start);
//        alreadyLooked.add(start);
//
//        while (nextOnes.size() > 0) {
//            var k = nextOnes.pop();
//            System.out.println(this.csucsok.get(k));
//            for (var el : this.elek) {
//                if (el.getCsucs1() == k && !alreadyLooked.contains(el.getCsucs2())) {
//                    nextOnes.add(el.getCsucs2());
//                    alreadyLooked.add(el.getCsucs2());
//                }
//            }
//        }
//    }

    public void melysegiBejar2(int start){
        List<Integer> alreadyLooked = new ArrayList<>();
        alreadyLooked.add(start);
        this.melysegiBejarRekurziv(start,alreadyLooked);

    }
    public void melysegiBejarRekurziv(int k, List<Integer> alreadyLooked){
        System.out.println(this.csucsok.get(k));
        for (El el: elek) {
            if (el.getCsucs1()==k&&!(alreadyLooked.contains(el.getCsucs2()))){
                alreadyLooked.add(el.getCsucs2());
                melysegiBejarRekurziv(el.getCsucs2(),alreadyLooked);
            }
        }
    }
    public boolean osszefuggosegEldontese(){
        List<Integer> alreadyLooked = new ArrayList<>();
        Queue<Integer> nextOnes = new LinkedList<>();

        nextOnes.add(0);
        alreadyLooked.add(0);

        while (nextOnes.size() > 0) {
            var k = nextOnes.poll();
            for (var el : this.elek) {
                if (el.getCsucs1() == k && !alreadyLooked.contains(el.getCsucs2())) {
                    nextOnes.add(el.getCsucs2());
                    alreadyLooked.add(el.getCsucs2());
                }
            }
        }
        return alreadyLooked.size() == this.csucsokSzama;
    }
    
    public Graf feszitofaKeszitese(){
        Graf fa = new Graf(this.csucsokSzama);
        List<Integer> alreadyLooked = new ArrayList<>();
        Queue<Integer> nextOnes = new LinkedList<>();

        nextOnes.add(0);
        alreadyLooked.add(0);

        while (nextOnes.size() > 0) {
            var k = nextOnes.poll();
            for (var el : this.elek) {
                if (el.getCsucs1() == k && !alreadyLooked.contains(el.getCsucs2())) {
                    nextOnes.add(el.getCsucs1());
                    alreadyLooked.add(el.getCsucs2());
                    fa.hozzaad(el.getCsucs1(), el.getCsucs2());
                }
            }
        }
        return fa;
    }
    
    public HashMap csucsSzinezesMohoAlgoritmussal(){
        var colouring = new HashMap<Integer, Integer>();
        var maxColour = this.csucsokSzama;

        for (int i = 0; i < this.csucsokSzama; i++) {
            List<Integer> colourOptions = new ArrayList<>();
            for (int j = 0; j < maxColour; j++) {
                colourOptions.add(j);
            }

            for (var el : this.elek) {
               if (el.getCsucs1() == this.csucsok.get(i).getId() && colouring.containsKey(el.getCsucs2())) {
                    var szin = colouring.get(el.getCsucs2());
                    colourOptions.remove(szin);
                }
            }
            var selectedColour = Collections.min(colourOptions);
            colouring.put(i, selectedColour);
        }
        return colouring;
    }
    
    @Override
    public String toString() {
        String str = "Csucsok:\n";
        for (Csucs cs: csucsok) {
            str += cs + "\n";
        }
        str += "Elek:\n";
        for (El el: elek) {
            str += el + "\n";
        }
        return str;
    }
}
