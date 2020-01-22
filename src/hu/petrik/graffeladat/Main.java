package hu.petrik.graffeladat;

public class Main {
    public static void main(String[] args) {
        Graf graf = new Graf(6);

        graf.hozzaad(0, 1);
        graf.hozzaad(1, 2);
        graf.hozzaad(0, 2);
        graf.hozzaad(2, 3);
        graf.hozzaad(3, 4);
        graf.hozzaad(4, 5);
        graf.hozzaad(2, 4);
        
        System.out.println(graf);
        graf.szelessegiBejaras(2);
//        System.out.println();
//        graf.melysegiBejaras(4);
        System.out.println();
        graf.melysegiBejar2(4);
        System.out.println();
        graf.osszefuggosegEldontese();
        System.out.println();
        graf.feszitofaKeszitese();
        System.out.println();
        graf.csucsSzinezesMohoAlgoritmussal();

    }
}
