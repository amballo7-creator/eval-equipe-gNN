# IsepCash

Cœur d'un portefeuille mobile fictif en Java : comptes de plusieurs types, plateforme de transferts avec journal, et interpréteur de codes USSD (`*144*1*771234567*5000#`).

Exercice de programmation orientée objet réalisé à l'ISEP Diamniadio.

## Fonctionnalités

- **Comptes** : Standard, Premium, Marchand et Épargne, avec frais et plafonds propres à chaque type
- **Plateforme** : ajout et recherche de comptes, transferts, journal des opérations, tri par solde
- **USSD** : validation et normalisation des numéros, lecture des montants, découpage des codes, exécution des services (transfert, solde)

## Notions travaillées

Classe abstraite, encapsulation, héritage sur deux niveaux, redéfinition, `super`, liaison dynamique, `instanceof`, `equals` / `hashCode`, attribut `static`, tableaux statiques, tri par insertion, copies défensives, analyse de chaînes caractère par caractère, gestion des erreurs sans exception.

## Lancer

```bash
javac -d bin src/*.java
java -cp bin Main
java -cp bin Verification
```

## Structure

| Fichier | Rôle |
|---|---|
| `Compte.java` | classe abstraite : solde, dépôt, retrait |
| `CompteStandard.java` | frais de 1 % |
| `ComptePremium.java` | frais réduits, plafond doublé |
| `CompteMarchand.java` | frais divisés par 2, plafond × 5, enseigne |
| `CompteEpargne.java` | sans frais, 2 retraits maximum |
| `Plateforme.java` | comptes, transferts, journal, tri |
| `Ussd.java` | chaînes et codes USSD |
| `Main.java` | scénario de démonstration |
| `Parametres.java` | constantes |
| `Verification.java` | tests automatiques |
