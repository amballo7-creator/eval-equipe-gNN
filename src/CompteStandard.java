public class CompteStandard extends Compte {

    public CompteStandard(String numero, String titulaire) {
        super(numero, titulaire);
    }

    @Override
    public long frais(long montant) {
        return montant * Parametres.TAUX_FRAIS / 1000;
    }

    @Override
    public String type() {
        return "STD";
    }
}
