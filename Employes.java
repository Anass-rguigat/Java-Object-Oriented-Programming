/*******************************************
 * Completez le programme a partir d'ici.
 *******************************************/
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class NumberTooHigh extends Exception {
	public NumberTooHigh(String msg) {
	    super(msg);
	}
}

class Employe {
    private final String nom;
    private double salaire;
    private int taux;
    private double prime = 0;

    public Employe(String nom, double salaire, int taux) {
        if (taux < 10) {
            taux = 10;
        } else if (taux > 100) {
            taux = 100;
        }
        this.nom = nom;
        this.salaire = salaire;
        this.taux = taux;
        System.out.format("Nous avons un nouvel employé : %s, ", nom);
    }
    public Employe(String nom, double salaire) {
        this(nom, salaire, 100);
    }

    public double revenuAnnuel() { return (12 * this.salaire * this.taux / 100) + this.prime; }
    public String toString() {
        String str = String.format("%s :", this.nom);
        str += System.lineSeparator();
        str += String.format("  Taux d'occupation : %d%s. Salaire annuel : %.2f francs", this.taux, '%', this.revenuAnnuel());
        if (this.prime > 0) {
            str += String.format(", Prime : %.2f.", this.prime);
        } else {
            str += "."; 
        }
        str += System.lineSeparator();
        return str;
    }

    private double askBonus(Scanner clavier) throws InputMismatchException, NumberTooHigh {
        double primeMax = this.revenuAnnuel() * 2 / 100;
        System.out.format("Montant de la prime souhaitée par %s ? ", this.nom);
        System.out.println();
        double bonus = clavier.nextDouble();
        if (bonus > primeMax) {
            throw new NumberTooHigh("Prime demandé supérieur à " + primeMax);
        }
        return bonus;
    }

    public void demandePrime() {
        Scanner clavier = new Scanner(System.in);
        double bonus = -1;
        for (int i = 0; i < 5; i++) {
            try { 
                bonus = askBonus(clavier);
                break;
            } catch (InputMismatchException e) {
                System.out.println("Vous devez introduire un nombre!");
                clavier.nextLine();
            } catch (NumberTooHigh e) {
                System.out.println("Trop cher!");
            } finally {
            }
        }
        if (bonus > 0) {
            this.prime = bonus;
        }
        clavier.close();
    }
}

class Manager extends Employe {
    public static final int FACTEUR_GAIN_CLIENT = 500;
    public static final int FACTEUR_GAIN_VOYAGE = 100;

    private int nbJourVoyage;
    private int nbClient;

    public Manager(String nom, double salaire, int nbJourVoyage, int nbClient, int taux) {
        super(nom, salaire, taux);
        this.nbJourVoyage = nbJourVoyage;
        this.nbClient = nbClient;
        System.out.println("c'est un manager.");
    }

    public Manager(String nom, double salaire, int nbJourVoyage, int nbClient) {
        this(nom, salaire, nbJourVoyage, nbClient, 100);
    }

    public double revenuAnnuel() {
        double revenu = super.revenuAnnuel();
        revenu += this.nbClient * FACTEUR_GAIN_CLIENT;
        revenu += this.nbJourVoyage * FACTEUR_GAIN_VOYAGE;
        return revenu;
    }

    public String toString() {
        String str = super.toString();
        str += String.format("  A voyagé %d jours et apporté %d nouveaux clients.", this.nbJourVoyage, this.nbClient);
        str += System.lineSeparator();
        return str;
    }
}

class Testeur extends Employe {
    public static final int FACTEUR_GAIN_ERREURS = 10;

    private int nbErreur;

    public Testeur(String nom, double salaire, int nbErreur, int taux) {
        super(nom, salaire, taux);
        this.nbErreur = nbErreur;
        System.out.println("c'est un testeur.");
    }

    public Testeur(String nom, double salaire, int nbErreur) {
        this(nom, salaire, nbErreur, 100);
    }

    public double revenuAnnuel() {
        double revenu = super.revenuAnnuel();
        revenu += this.nbErreur * FACTEUR_GAIN_ERREURS;
        return revenu;
    }

    public String toString() {
        String str = super.toString();
        str += String.format("  A corrigé %d erreurs.", this.nbErreur);
        str += System.lineSeparator();
        return str;
    }
}

class Programmeur extends Employe {
    public static final int FACTEUR_GAIN_PROJETS = 200;

    private int nbProjet;

    public Programmeur(String nom, double salaire, int nbProjet, int taux) {
        super(nom, salaire, taux);
        this.nbProjet = nbProjet;
        System.out.println("c'est un programmeur.");
    }

    public Programmeur(String nom, double salaire, int nbProjet) {
        this(nom, salaire, nbProjet, 100);
    }

    public double revenuAnnuel() {
        double revenu = super.revenuAnnuel();
        revenu += this.nbProjet * FACTEUR_GAIN_PROJETS;
        return revenu;
    }

    public String toString() {
        String str = super.toString();
        str += String.format("  A mené à bien %d projets", this.nbProjet);
        str += System.lineSeparator();
        return str;
    }
}

/*******************************************
 * Ne rien modifier apres cette ligne.
 *******************************************/
class Employes {
    public static void main(String[] args) {

        List<Employe> staff = new ArrayList<Employe>();

        // TEST PARTIE 1:

        System.out.println("Test partie 1 : ");
        staff.add(new Employe("Employé15", 5060.733098, 77));
        staff.add(new Manager("Serge Legrand", 7456, 30, 4 ));
        staff.add(new Programmeur("Paul Lepetit" , 6456, 3, 75 ));
        staff.add(new Testeur("Pierre Lelong", 5456, 124, 50 ));

        System.out.println("Affichage des employés : ");
        for (Employe modele : staff) {
            System.out.println(modele);
        }
        // FIN TEST PARTIE 1
        // TEST PARTIE 2
        System.out.println("Test partie 2 : ");

        staff.get(0).demandePrime();

        System.out.println("Affichage après demande de prime : ");
        System.out.println(staff.get(0));

        // FIN TEST PARTIE 2
    }
}

