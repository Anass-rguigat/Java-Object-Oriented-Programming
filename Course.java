/*******************************************
 * Completez le programme a partir d'ici.
 *******************************************/
import java.util.ArrayList;
import java.util.Comparator;

class Vehicule {
    private String nom;
    private double vmax;
    private int poids;
    private int fuel;

    public Vehicule(String nom, double vmax, int poids, int fuel) {
        this.nom = nom;
        this.vmax = vmax;
        this.poids = poids;
        this.fuel = fuel;
    }

    public Vehicule() {
        this("Anonyme", 130, 1000, 0);
    }

    public String getNom() { return this.nom; }
    public double getVitesseMax() { return this.vmax; }
    public int getPoids() { return this.poids; }
    public int getCarburant() { return this.fuel; }

    private double performance() {
        return this.getVitesseMax() / this.getPoids();
    }

    public String toString() {
        String str;
        str = this.getNom() + " -> vitesse max = " + this.getVitesseMax() + " km/h, poids = " + this.getPoids() + " kg";
        return str;
    }

    public boolean meilleur(Vehicule other) {
        return (this.performance() > other.performance());
    }

    public boolean estDeuxRoues() { return false; };
}

class Voiture extends Vehicule {
    private String category;

    public Voiture(String nom, double vmax, int poids, int fuel, String category) {
        super(nom, vmax, poids, fuel);
        this.category = category;
    }

    public Voiture() {
        super();
        this.category = "course";
    }

    public String getCategorie() { return this.category; }

    public String toString() {
        String str = super.toString() + ", Voiture de " + this.getCategorie();
        return str;
    }
    public boolean estDeuxRoues() { return false; }
}

class Moto extends Vehicule {
    private boolean hasSidecar;
    
    public Moto(String nom, double vmax, int poids, int fuel, boolean hasSidecar) {
        super(nom, vmax, poids, fuel);
        this.hasSidecar = hasSidecar;
    }

    public Moto(String nom, double vmax, int poids, int fuel) {
        this(nom, vmax, poids, fuel, false);
    }

    public Moto() {
        super();
        this.hasSidecar = false;
    }

    public String toString() {
        String str = super.toString() + ", Moto";
        if (this.hasSidecar) {
            str += ", avec sidecar";
        }
        return str;
    }
    public boolean estDeuxRoues() { return !hasSidecar; }
}

abstract class Rallye {
    abstract public boolean check();
}

class GrandPrix extends Rallye {
    private ArrayList<Vehicule> vehicules;

    public GrandPrix() {
        this.vehicules = new ArrayList<Vehicule>();
    }

    public void ajouter(Vehicule v) {
        this.vehicules.add(v);
    }

    public boolean check() {
        boolean deuxRoues = this.vehicules.get(0).estDeuxRoues();
        for(Vehicule v : this.vehicules) {
            if (v.estDeuxRoues() != deuxRoues) {
                return false;
            }
        }
        return true;
    }

    public void run(int tours) {
        if (!this.check()) {
            System.out.println("Pas de Grand Prix");
            return;
        }

        ArrayList<Vehicule> finalistes = new ArrayList<Vehicule>();
        for(Vehicule v : this.vehicules) {
            if(v.getCarburant() > tours) {
                finalistes.add(v);
            }
        }
        if (finalistes.isEmpty()) {
            System.out.println("Elimination de tous les vehicules");
        } else {
            Vehicule winner = finalistes.get(0);
            for (Vehicule v : finalistes) {
                if (!winner.meilleur(v)) {
                    winner = v;
                }
            }
            System.out.println("Le gagnant du grand prix est :");
            System.out.println(winner);
        }
    }
}
/*******************************************
 * Ne pas modifier apres cette ligne
 * pour pr'eserver les fonctionnalit'es et
 * le jeu de test fourni.
 * Votre programme sera test'e avec d'autres
 * donn'ees.
 *******************************************/
public class Course {

    public static void main(String[] args) {

        // PARTIE 1
        System.out.println("Test partie 1 : ");
        System.out.println("----------------");
        Vehicule v0 = new Vehicule();
        System.out.println(v0);

        // les arguments du constructeurs sont dans l'ordre:
        // le nom, la vitesse, le poids, le carburant
        Vehicule v1 = new Vehicule("Ferrari", 300.0, 800, 30);
        Vehicule v2 = new Vehicule("Renault Clio", 180.0, 1000, 20);
        System.out.println();
        System.out.println(v1);
        System.out.println();
        System.out.println(v2);

        if (v1.meilleur(v2)) {
            System.out.println("Le premier vehicule est meilleur que le second");
        } else {
            System.out.println("Le second vehicule est meilleur que le premier");
        }
        // FIN PARTIE 1

        // PARTIE2
        System.out.println();
        System.out.println("Test partie 2 : ");
        System.out.println("----------------");

        // les trois premiers arguments sont dans l'ordre:
        // le nom, la vitesse, le poids, le carburant
        // le dernier argument indique la presence d'un sidecar
        Moto m1 = new Moto("Honda", 200.0, 250, 15, true);
        Moto m2 = new Moto("Kawasaki", 280.0, 180, 10);
        System.out.println(m1);
        System.out.println();
        System.out.println(m2);
        System.out.println();

        // les trois premiers arguments sont dans l'ordre:
        // le nom, la vitesse, le poids, le carburant
        // le dernier argument indique la categorie
        Voiture vt1 = new Voiture("Lamborghini", 320, 1200, 40, "course");
        Voiture vt2 = new Voiture("BMW", 190, 2000, 35, "tourisme");
        System.out.println(vt1);
        System.out.println();
        System.out.println(vt2);
        System.out.println();
        // FIN PARTIE 2

        // PARTIE 3
        System.out.println();
        System.out.println("Test partie 3 : ");
        System.out.println("----------------");

        GrandPrix gp1 = new GrandPrix();
        gp1.ajouter(v1);
        gp1.ajouter(v2);
        System.out.println(gp1.check());

        GrandPrix gp2 = new GrandPrix();
        gp2.ajouter(vt1);
        gp2.ajouter(vt2);
        gp2.ajouter(m2);
        System.out.println(gp2.check());

        GrandPrix gp3 = new GrandPrix();
        gp3.ajouter(vt1);
        gp3.ajouter(vt2);
        gp3.ajouter(m1);
        System.out.println(gp3.check());

        GrandPrix gp4 = new GrandPrix();
        gp4.ajouter(m1);
        gp4.ajouter(m2);
        System.out.println(gp4.check());
        // FIN PARTIE 3

        // PARTIE 4
        System.out.println();
        System.out.println("Test partie 4 : ");
        System.out.println("----------------");
        GrandPrix gp5 = new GrandPrix();
        gp5.ajouter(vt1);
        gp5.ajouter(vt2);

        System.out.println("Premiere course :");
        gp5.run(11);
        System.out.println();

        System.out.println("Deuxieme  course :");
        gp3.run(40);
        System.out.println();

        System.out.println("Troisieme  course :");
        gp2.run(11);
        // FIN PARTIE 4
    }
}
