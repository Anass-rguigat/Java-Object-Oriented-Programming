import java.util.ArrayList;

class Timbre {

    public static final int ANNEE_COURANTE = 2016;
    public static final int VALEUR_TIMBRE_DEFAUT = 1;
    public static final String PAYS_DEFAUT = "Suisse";
    public static final String CODE_DEFAUT = "lambda";

    public static final int BASE_1_EXEMPLAIRES = 100;
    public static final int BASE_2_EXEMPLAIRES = 1000;
    public static final double PRIX_BASE_1 = 600;
    public static final double PRIX_BASE_2 = 400;
    public static final double PRIX_BASE_3 = 50;

    /*******************************************
     * Completez le programme a partir d'ici.
     *******************************************/
    private String code = CODE_DEFAUT;
    private int year = ANNEE_COURANTE;
    private String pays = PAYS_DEFAUT;
    private double valeur = VALEUR_TIMBRE_DEFAUT;

    public Timbre() {}
    public Timbre(String code) {
        this.code = code;
    }
    public Timbre(String code, int year) {
        this.code = code;
        this.year = year;
    }
    public Timbre(String code, int year, String pays) {
        this.code = code;
        this.year = year;
        this.pays = pays;
    }
    public Timbre(String code, int year, String pays, double valeur) {
        this.code = code;
        this.year = year;
        this.pays = pays;
        this.valeur = valeur;
    }

    public String getCode() { return this.code; }
    public int getAnnee() { return this.year; }
    public String getPays() { return this.pays; }
    public double getValeurFaciale() { return this.valeur; }
    public int age() { return ANNEE_COURANTE - this.getAnnee(); }

    public double vente() {
        if (this.age() < 5) {
            return this.valeur;
        } else {
            return this.age() * 2.5 * this.valeur;
        }
    }
    public String toString() {
        String str = String.format(
            "Timbre de code %s datant de  %d (provenance %s) ayant pour valeur faciale ",
            this.code, this.year, this.pays
        );
        str += this.valeur + " francs";
        return str;
    }
}

class Rare extends Timbre {
    private int nombreExemplaires;

    public Rare(int nombreExemplaires) {
        super();
        this.nombreExemplaires = nombreExemplaires;
    }
    public Rare(String code, int nombreExemplaires) {
        super(code);
        this.nombreExemplaires = nombreExemplaires;
    }
    public Rare(String code, int year, int nombreExemplaires) {
        super(code, year);
        this.nombreExemplaires = nombreExemplaires;
    }
    public Rare(String code, int year, String pays, int nombreExemplaires) {
        super(code, year, pays);
        this.nombreExemplaires = nombreExemplaires;
    }
    public Rare(String code, int year, String pays, double valeur, int nombreExemplaires) {
        super(code, year, pays, valeur);
        this.nombreExemplaires = nombreExemplaires;
    }

    public int getExemplaires() { return this.nombreExemplaires; }
    public String toString() {
        String str = super.toString();
        str += System.lineSeparator();
        str += "Nombre d’exemplaires -> " + this.nombreExemplaires;
        return str;
    }
    public double vente() {
        double basePrice;
        if (this.getExemplaires() < 100) {
            basePrice = PRIX_BASE_1;
        } else if (this.getExemplaires() < 1000) {
            basePrice = PRIX_BASE_2;
        } else {
            basePrice = PRIX_BASE_3;
        }
        return basePrice * (this.age() / 10.0);
    }
}

class Commemoratif extends Timbre {
    public Commemoratif() {
        super();
    }
    public Commemoratif(String code) {
        super(code);
    }
    public Commemoratif(String code, int year) {
        super(code, year);
    }
    public Commemoratif(String code, int year, String pays) {
        super(code, year, pays);
    }
    public Commemoratif(String code, int year, String pays, double valeur) {
        super(code, year, pays, valeur);
    }

    public String toString() {
        String str = super.toString();
        str += System.lineSeparator();
        str += "Timbre celebrant un evenement";
        return str;
    }
    public double vente() { return 2 * super.vente(); }
}

/*******************************************
 * Ne rien modifier apres cette ligne.
 *******************************************/

class Philatelie {

    public static void main(String[] args) {

        // ordre des parametres: nom, annee d'emission, pays, valeur faciale,
        // nombre d'exemplaires
        Rare t1 = new Rare("Guarana-4574", 1960, "Mexique", 0.2, 98);

        // ordre des parametres: nom, annee d'emission, pays, valeur faciale
        Commemoratif t2 = new Commemoratif("700eme-501", 2002, "Suisse", 1.5);
        Timbre t3 = new Timbre("Setchuan-302", 2004, "Chine", 0.2);

        Rare t4 = new Rare("Yoddle-201", 1916, "Suisse", 0.8, 3);


        ArrayList<Timbre> collection = new ArrayList<Timbre>();

        collection.add(t1);
        collection.add(t2);
        collection.add(t3);
        collection.add(t4);

        for (Timbre timbre : collection) {
            System.out.println(timbre);
            System.out.println("Prix vente : " + timbre.vente() + " francs");
            System.out.println();
        }
    }
}

