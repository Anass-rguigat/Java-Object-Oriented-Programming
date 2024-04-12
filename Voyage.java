/*******************************************
 * Completez le programme a partir d'ici.
 *******************************************/
import java.util.ArrayList;

class OptionVoyage {
    private String nom;
    private double prixForfaitaire;

    public OptionVoyage(String nom, double prixForfaitaire) {
        this.nom = nom;
        this.prixForfaitaire = prixForfaitaire;
    }

    public String getNom() { return nom; }
    public double prix() { return prixForfaitaire; }
    public String toString() {
        String str = this.getNom() + " ->  " + this.prix() + " CHF";
        return str;
    }
}

class Transport extends OptionVoyage {
    public static final double TARIF_LONG = 1500;
    public static final double TARIF_BASE = 200;
    private boolean isLong;

    public Transport(String nom, double prixForfaitaire, Boolean isLong) {
        super(nom, prixForfaitaire);
        this.isLong = isLong;
    }
    public Transport(String nom, double prixForfaitaire) {
        this(nom, prixForfaitaire, false);
    }
    public double prix() {
        double basePrice;
        if (isLong) {
            basePrice = TARIF_LONG;
        } else {
            basePrice = TARIF_BASE;
        }
        return basePrice + super.prix();
    }
}

class Sejour extends OptionVoyage {
    private int nombreNuit;
    private double prixNuit;

    public Sejour(String nom, double prixForfaitaire, int nombreNuit, double prixNuit) {
        super(nom, prixForfaitaire);
        this.nombreNuit = nombreNuit;
        this.prixNuit = prixNuit;
    }
    public double prix() {
        return (this.nombreNuit * this.prixNuit) + super.prix();
    }
}

class KitVoyage {
    private ArrayList<OptionVoyage> options;
    private String depart;
    private String destination;
    
    public KitVoyage(String depart, String destination) {
        this.depart = depart;
        this.destination = destination;
        this.options = new ArrayList<OptionVoyage>();
    }

    public double prix() {
        double prix = 0;
        for (OptionVoyage o : this.options) {
            prix += o.prix();
        }
        return prix;
    }

    public String toString() {
        String str = String.format("Voyage de %s à %s, avec pour options :\n", this.depart, this.destination);
        for (OptionVoyage o : this.options) {
            str += "   - " + o.toString() + "\n";
        }
        str += "Prix total : " + this.prix() + " CHF\n";
        return str;
    }

    public void ajouterOption(OptionVoyage option) {
        if (option != null) {
            this.options.add(option);
        }
    }

    public void annuler() {
        this.options.clear();
    }

    public int getNbOptions() { return this.options.size(); }
}
/*******************************************
 * Ne pas modifier apres cette ligne
 * pour pr'eserver les fonctionnalit'es et
 * le jeu de test fourni.
 * Votre programme sera test'e avec d'autres
 * donn'ees.
 *******************************************/

public class Voyage {
    public static void main(String args[]) {

        // TEST 1
        System.out.println("Test partie 1 : ");
        System.out.println("----------------");
        OptionVoyage option1 = new OptionVoyage("Séjour au camping", 40.0);
        System.out.println(option1.toString());

        OptionVoyage option2 = new OptionVoyage("Visite guidée : London by night" , 50.0);
        System.out.println(option2.toString());
        System.out.println();

        // FIN TEST 1


        // TEST 2
        System.out.println("Test partie 2 : ");
        System.out.println("----------------");

        Transport transp1 = new Transport("Trajet en car ", 50.0);
        System.out.println(transp1.toString());

        Transport transp2 = new Transport("Croisière", 1300.0);
        System.out.println(transp2.toString());

        Sejour sejour1 = new Sejour("Camping les flots bleus", 20.0, 10, 30.0);
        System.out.println(sejour1.toString());
        System.out.println();

        // FIN TEST 2


        // TEST 3
        System.out.println("Test partie 3 : ");
        System.out.println("----------------");

        KitVoyage kit1 = new KitVoyage("Zurich", "Paris");
        kit1.ajouterOption(new Transport("Trajet en train", 50.0));
        kit1.ajouterOption(new Sejour("Hotel 3* : Les amandiers ", 40.0, 5, 100.0));
        System.out.println(kit1.toString());
        System.out.println();
        kit1.annuler();

        KitVoyage kit2 = new KitVoyage("Zurich", "New York");
        kit2.ajouterOption(new Transport("Trajet en avion", 50.0, true));
        kit2.ajouterOption(new Sejour("Hotel 4* : Ambassador Plazza  ", 100.0, 2, 250.0));
        System.out.println(kit2.toString());
        kit2.annuler();

        // FIN TEST 3
    }
}

