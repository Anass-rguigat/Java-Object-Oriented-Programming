import java.util.ArrayList;


class Position {
    private double x, y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Position() {
        this(0, 0);
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public String toString() {
        String str = "(" + getX() + ", " + getY() + ")";
        return str;
    }
}

class Neurone {
    private Position position;
    private double signal, facteur;
    protected ArrayList<Neurone> connexions;

    public Neurone(Position position, double facteur) {
        this.position = position;
        this.facteur = facteur;
        this.signal = 0;
        this.connexions = new ArrayList<>();
    }
    public Position getPosition() { return position; }
    public int getNbConnexions() { return connexions.size(); }
    public Neurone getConnexion(int index) { return connexions.get(index); }
    public double getAttenuation() { return facteur; }
    public double getSignal() { return signal; }

    public void setSignal(double signal) { this.signal = signal; }

    public void connexion(Neurone n) {
        connexions.add(n);
    }

    public void recoitStimulus(double stimulus) {
        this.setSignal(stimulus * this.getAttenuation());
        this.envoiStimulus();
    }
    public void envoiStimulus() {
        for (Neurone n: connexions) {
            n.recoitStimulus(this.getSignal());
        }
    }

    public String toString() {
        String str = "Le neurone en position " + this.getPosition().toString() + " avec attenuation " + this.getAttenuation();
        if (this.getNbConnexions() > 0) {
            str += " en connexion avec" + System.lineSeparator();
            for (Neurone n : connexions) {
                str += "  - un neurone en position " + n.getPosition().toString() + System.lineSeparator();
            }
        } else {
            str += " sans connexion" + System.lineSeparator();
        }
        return str;
    }
}

class NeuroneCumulatif extends Neurone {

    public NeuroneCumulatif(Position position, double facteur) {
        super(position, facteur);
    }

    public void recoitStimulus(double stimulus) {
        this.setSignal(this.getSignal() + (stimulus * this.getAttenuation()) );
        this.envoiStimulus();
    }
}

class Cerveau {
    private ArrayList<Neurone> neurones;

    public Cerveau() {
        this.neurones = new ArrayList<>();
    }

    public int getNbNeurones() { return neurones.size(); }
    public Neurone getNeurone(int index) { return neurones.get(index); }
    public void ajouterNeurone(Position pos, double attenuation) {
        neurones.add(new Neurone(pos, attenuation));
    }
    public void ajouterNeuroneCumulatif(Position pos, double attenuation) {
        neurones.add(new NeuroneCumulatif(pos, attenuation));
    }
    public void stimuler(int index, double stimulus) {
        this.getNeurone(index).recoitStimulus(stimulus);
    }
    public double sonder(int index) { return this.getNeurone(index).getSignal(); }
    public void creerConnexions() {
        if (this.getNbNeurones() >= 2) {
            this.getNeurone(0).connexion(this.getNeurone(1));
        }
        if (this.getNbNeurones() >= 3) {
            this.getNeurone(0).connexion(this.getNeurone(2));
        }

        for (int i = 1; i < this.getNbNeurones()-2; i += 2) {
            this.getNeurone(i).connexion(this.getNeurone(i+1));
            this.getNeurone(i+1).connexion(this.getNeurone(i+2));
        }
    }

    public String toString() {
        String str = System.lineSeparator();
        str += "*----------*" + System.lineSeparator() + System.lineSeparator() + System.lineSeparator();
        str += String.format("Le cerveau contient %d neurone(s)", this.getNbNeurones());
        for (Neurone n : this.neurones) {
            str += System.lineSeparator();
            str += n.toString();
        }
        str += System.lineSeparator();
        str += "*----------*" + System.lineSeparator() + System.lineSeparator();
        return str;
    }

}
/*******************************************
 * Completez le programme a partir d'ici.
 *******************************************/
/*******************************************
 * Ne pas modifier apres cette ligne
 * pour pr'eserver les fonctionnalit'es et
 * le jeu de test fourni.
 * Votre programme sera test'e avec d'autres
 * donn'ees.
 *******************************************/
public class SimulateurNeurone {

    public static void main(String[] args) {
        // TEST DE LA PARTIE 1
        System.out.println("Test de la partie 1:");
        System.out.println("--------------------");

        Position position1 = new Position(0, 1);
        Position position2 = new Position(1, 0);
        Position position3 = new Position(1, 1);

        Neurone neuron1 = new Neurone(position1, 0.5);
        Neurone neuron2 = new Neurone(position2, 1.0);
        Neurone neuron3 = new Neurone(position3, 2.0);

        neuron1.connexion(neuron2);
        neuron2.connexion(neuron3);
        neuron1.recoitStimulus(10);

        System.out.println("Signaux : ");
        System.out.println(neuron1.getSignal());
        System.out.println(neuron2.getSignal());
        System.out.println(neuron3.getSignal());

        System.out.println();
        System.out.println("Premiere connexion du neurone 1");
        System.out.println(neuron1.getConnexion(0));


        // FIN TEST DE LA PARTIE 1

        // TEST DE LA PARTIE 2
        System.out.println("Test de la partie 2:");
        System.out.println("--------------------");

        Position position5 = new Position(0, 0);
        NeuroneCumulatif neuron5 = new NeuroneCumulatif(position5, 0.5);
        neuron5.recoitStimulus(10);
        neuron5.recoitStimulus(10);
        System.out.println("Signal du neurone cumulatif  -> " + neuron5.getSignal());

        // FIN TEST DE LA PARTIE 2

        // TEST DE LA PARTIE 3
        System.out.println();
        System.out.println("Test de la partie 3:");
        System.out.println("--------------------");
        Cerveau cerveau = new Cerveau();

        // parametres de construction du neurone:
        // la  position et le facteur d'attenuation
        cerveau.ajouterNeurone(new Position(0,0), 0.5);
        cerveau.ajouterNeurone(new Position(0,1), 0.2);
        cerveau.ajouterNeurone(new Position(1,0), 1.0);

        // parametres de construction du neurone cumulatif:
        // la  position et le facteur d'attenuation
        cerveau.ajouterNeuroneCumulatif(new Position(1,1), 0.8);
        cerveau.creerConnexions();
        cerveau.stimuler(0, 10);

        System.out.println("Signal du 3eme neurone -> " + cerveau.sonder(3));
        System.out.println(cerveau);
        // FIN TEST DE LA PARTIE 3
    }
}
