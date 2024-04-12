class Souris {

    public static final int ESPERANCE_VIE_DEFAUT = 36;

    /*******************************************
     * Completez le programme a partir d'ici.
     *******************************************/
    private int poids;
    private String couleur;
    private int age;
    private int esperanceVie;
    private boolean clonee = false;

    public Souris(int p, String c, int a, int e) {
        poids = p;
        couleur = c;
        age = a;
        esperanceVie = e;
        System.out.println("Une nouvelle souris !");
    }
    public Souris(int p, String c, int a) {
        this(p, c, a, ESPERANCE_VIE_DEFAUT);
    }
    public Souris(int p, String c) {
        this(p, c, 0, ESPERANCE_VIE_DEFAUT);
    }
    public Souris(Souris s) {
        poids = s.poids;
        couleur = s.couleur;
        age = s.age;
        esperanceVie = s.esperanceVie * 4 / 5;
        clonee = true;
        System.out.println("Clonage d'une souris !");
    }
    public String toString() {
        String s = String.format("Une souris %s", couleur);
        if (clonee) {
            s += ", clonee,";
        }
        s += String.format(" de %d mois et pesant %d grammes", age, poids);
        return s;
    }
    public void vieillir() {
        age += 1;
        if (clonee && age > (esperanceVie / 2)) {
            couleur = "verte";
        }
    }
    public void evolue() {
        while (age < esperanceVie) {
            vieillir();
        }
    }
}
/*******************************************
 * Ne rien modifier apres cette ligne.
 *******************************************/

public class Labo {

    public static void main(String[] args) {
        Souris s1 = new Souris(50, "blanche", 2);
        Souris s2 = new Souris(45, "grise");
        Souris s3 = new Souris(s2);

        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        s1.evolue();
        s2.evolue();
        s3.evolue();
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
    }
}
