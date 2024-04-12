import java.util.ArrayList;
import java.util.Random;

/*******************************************
 * Completez le programme à partir d'ici.
 *******************************************/
class Postulant {
    private String nom;
    private int nbElecteur;


    public String getNom() { return nom; }
    public int getVotes() { return nbElecteur; }
    public void elect() { this.nbElecteur++; }
    public void init() { this.nbElecteur = 0; }

    public Postulant(String nom, int nbElecteur) {
        this.nom = nom;
        this.nbElecteur = nbElecteur;
    }

    public Postulant(String nom) {
        this(nom, 0);
    }

    public Postulant(Postulant p) {
        this(p.getNom(), p.getVotes());
    }
}

abstract class Vote {
    private String nom;
    private int date;
    private int dateLimite;

    public int getDate() { return date; }
    public int getDateLimite() { return dateLimite; }
    public String getPostulant() { return nom; }

    abstract public boolean estInvalide();

    public Vote(String nom, int date, int dateLimite) {
        this.nom = nom;
        this.date = date;
        this.dateLimite = dateLimite;
    }

    public String toString() {
        String str;
        if (this.estInvalide()) {
            str = String.format(" pour %s -> invalide", this.getPostulant());
        } else {
            str = String.format(" pour %s -> valide", this.getPostulant());
        }
        return str;
    }
}

interface CheckBulletin {
    boolean checkDate();
}


class BulletinPapier extends Vote {
    private boolean signature;

    public BulletinPapier(String nom, int date, int dateLimite, boolean signature) {
        super(nom, date, dateLimite);
        this.signature = signature;
    }

    @Override
    public boolean estInvalide() {
        return !this.signature;
    }

    @Override
    public String toString() {
        String str;
        if (this.estInvalide()) {
            str = String.format("vote par bulletin papier pour %s -> invalide", this.getPostulant());
        } else {
            str = String.format("vote par bulletin papier pour %s -> valide", this.getPostulant());
        }
        return str;
    }
}

class BulletinCourrier extends BulletinPapier implements CheckBulletin {
    public BulletinCourrier(String nom, int date, int dateLimite, boolean signature) {
        super(nom, date, dateLimite, signature);
    }

    @Override
    public boolean estInvalide() {
        boolean res = super.estInvalide() || !this.checkDate();
        return res;
    }

    @Override
    public boolean checkDate() {
        return this.getDate() <= this.getDateLimite();
    }

    @Override
    public String toString() {
        String str;
        if (this.estInvalide()) {
            str = String.format("envoi par courrier d'un vote par bulletin papier pour %s -> invalide", this.getPostulant());
        } else {
            str = String.format("envoi par courrier d'un vote par bulletin papier pour %s -> valide", this.getPostulant());
        }
        return str;
    }
}

class BulletinElectronique extends Vote implements CheckBulletin {
    public BulletinElectronique(String nom, int date, int dateLimite) {
        super(nom, date, dateLimite);
    }

    @Override
    public boolean estInvalide() {
        return !this.checkDate();
    }

    @Override
    public boolean checkDate() {
        return this.getDate() <= (this.getDateLimite() - 2);
    }

    @Override
    public String toString() {
        String str;
        if (this.estInvalide()) {
            str = String.format("vote electronique pour %s -> invalide", this.getPostulant());
        } else {
            str = String.format("vote electronique pour %s -> valide", this.getPostulant());
        }
        return str;
    }
}


class Scrutin {
    private ArrayList<Postulant> postulants;
    private ArrayList<Vote> votes;
    private int nbElecteurMax;
    private int date;

    private void resetElection() {
        for (Postulant p : this.postulants) {
            p.init();
        }
    }

    private void incrementerVote(String nom) {
        for (Postulant p : this.postulants) {
            if(p.getNom() == nom) {
                p.elect();
            }
        }
    }

    public Scrutin(ArrayList<Postulant> postulants, int nbElecteurMax, int date, boolean reset) {
        this.nbElecteurMax = nbElecteurMax;
        this.date = date;
        this.votes = new ArrayList<>();
        this.postulants = new ArrayList<>();
        if (postulants != null) {
            for (Postulant p : postulants) {
                this.postulants.add(new Postulant(p));
            }
        }

        if (reset) {
            this.resetElection();
        }
    }

    public Scrutin(ArrayList<Postulant> postulants, int nbElecteurMax, int date) {
        this(postulants, nbElecteurMax, date, true);
    }

    public void compterVotes() {
        for (Vote v : this.votes) {
            if (!v.estInvalide()) {
                this.incrementerVote(v.getPostulant());
            }
        }
    }

    public void simuler(double taux, int dateVote) {
        int nbVotants = (int)(taux * this.nbElecteurMax);
        for (int i = 0; i < nbVotants; i++) {
            int candNum = Utils.randomInt(this.postulants.size());
            Postulant p = this.postulants.get(candNum);
            Vote v;
            switch (i % 3) {
                case 0:
                    v = new BulletinElectronique(p.getNom(), dateVote, this.date);
                    break;

                case 1:
                    v = new BulletinPapier(p.getNom(), dateVote, this.date, !(i % 2 == 0));
                    break;

                default:
                    v = new BulletinCourrier(p.getNom(), dateVote, this.date, !(i % 2 == 0));
                    break;
            }
            System.out.println(v);
            this.votes.add(v);
        }
    }

    public int calculerVotants() {
        int res = 0;
        for (Postulant p : this.postulants) {
            res += p.getVotes();
        }
        return res;
    }

    public String gagnant() {
        Postulant winner = this.postulants.get(0);
        for (Postulant p : this.postulants) {
            if (p.getVotes() >= winner.getVotes()) {
                winner = p;
            }
        }
        return winner.getNom();
    }

    public void resultats() {
        String str = "";
        if (this.calculerVotants() == 0) {
            str = "Scrutin annulé, pas de votants" + System.lineSeparator();
        } else {
            str += String.format("Taux de participation -> %.1f pour cent", (100 * (double)this.calculerVotants() / (double)this.nbElecteurMax)) + System.lineSeparator();
            str += String.format("Nombre effectif de votants -> %d", this.calculerVotants()) + System.lineSeparator();
            str += String.format("Le chef choisi est -> %s", this.gagnant()) + System.lineSeparator() + System.lineSeparator();
            str += "Répartition des électeurs"  + System.lineSeparator();
            for (Postulant p : this.postulants) {
                str += String.format("%s -> %.1f pour cent des électeurs", p.getNom(), 100 * (double)p.getVotes() / (double)this.calculerVotants()) + System.lineSeparator();
            }
        }
        System.out.println(str);
    }
}
/*******************************************
 * Ne pas modifier les parties fournies
 * pour pr'eserver les fonctionnalit'es et
 * le jeu de test fourni.
 * Votre programme sera test'e avec d'autres
 * donn'ees.
 *******************************************/

class Utils {

    private static final Random RANDOM = new Random();

    // NE PAS UTILISER CETTE METHODE DANS LES PARTIES A COMPLETER
    public static void setSeed(long seed) {
        RANDOM.setSeed(seed);
    }

    // génère un entier entre 0 et max (max non compris)
    public static int randomInt(int max) {
        return RANDOM.nextInt(max);
    }
}

/**
 * Classe pour tester la simulation
 */

class Votation {

    public static void main(String args[]) {
        Utils.setSeed(20000);
        // TEST 1
        System.out.println("Test partie I:");
        System.out.println("--------------");

        ArrayList<Postulant> postulants = new ArrayList<Postulant>();
        postulants.add(new Postulant("Tarek Oxlama", 2));
        postulants.add(new Postulant("Nicolai Tarcozi", 3));
        postulants.add(new Postulant("Vlad Imirboutine", 2));
        postulants.add(new Postulant("Angel Anerckjel", 4));

        // 30 -> nombre maximal de votants
        // 15 jour du scrutin
        Scrutin scrutin = new Scrutin(null, 30, 15, false);

        scrutin.resultats();

        // FIN TEST 1

        // TEST 2
        System.out.println("Test partie II:");
        System.out.println("---------------");

        scrutin = new Scrutin(postulants, 20, 15);
        // tous les bulletins passent le check de la date
        // les parametres de simuler sont dans l'ordre:
        // le pourcentage de votants et le jour du vote
        scrutin.simuler(0.75, 12);
        scrutin.compterVotes();
        scrutin.resultats();

        scrutin = new Scrutin(postulants, 20, 15);
        // seuls les bulletins papier non courrier passent
        scrutin.simuler(0.75, 15);
        scrutin.compterVotes();
        scrutin.resultats();

        scrutin = new Scrutin(postulants, 20, 15);
        // les bulletins electroniques ne passent pas
        scrutin.simuler(0.75, 15);
        scrutin.compterVotes();
        scrutin.resultats();
        //FIN TEST 2

    }
}
