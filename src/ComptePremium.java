public class ComptePremium extends CompteStandard {

    public ComptePremium(String numero, String titulaire) {
        super(numero, titulaire);
    }

    @Override
    public long frais(long montant) {
        if (montant <= Parametres.SEUIL_PREMIUM) {
            return 0;
        }
        return super.frais(montant) / 2;
    }

    @Override
    public long plafond() {
        return 2 * super.plafond();
    }

    @Override
    public String type() {
        return "PRM";
    }
}
