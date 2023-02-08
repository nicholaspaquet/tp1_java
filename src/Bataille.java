import java.util.Random;
import java.util.Scanner;

/**
 * @author Nicholas Paquet
 * @since 8 fevrier 2023
 */

public class Bataille {
    public static int[][] grilleOrdi = new int[10][10];
    public static int[][] grilleJeu = new int[10][10];

    /**
     * @param args
     */
    public static void main(String[] args) {
        // initGrilleOrdi();
        // afficherGrille(grilleOrdi);
        initGrilleJeu();
    }

    /**
     * @param grille une grille
     * @param l      un numero de ligne
     * @param c      un numero de colonne (compris entre 0 et 9)
     * @param d      un entier d codant une direction (1 pour horizontal et 2 pour
     *               vertical)
     * @param t      un entier t donnant le nombre de cases d'un bateau
     */

    public static boolean posOk(int[][] grille, int l, int c, int d, int t) {
        boolean possibleDePlacerBateau = false;

        if (d == 1 && c + t < 10) {
            for (int i = c; i < c + t; ++i) {
                if (grille[l][i] != 0) {
                    possibleDePlacerBateau = false;
                    break;
                } else {
                    possibleDePlacerBateau = true;
                }
            }
        } else if (d == 2 && l + t < 10) {
            for (int i = l; i < l + t; ++i) {
                if (grille[i][c] != 0) {
                    possibleDePlacerBateau = false;
                    break;
                } else {
                    possibleDePlacerBateau = true;
                }
            }
        }
        return possibleDePlacerBateau;
    }

    public static Random rand = new Random();

    /**
     * 
     * @param a limite inferieur pour la generation d'un nombre aleatoire
     * @param b limite superieur pour la generation d'un nombre aleatoire
     * @return retourne un entier aleatoire entre a inclu et b exclu
     */

    public static int randRange(int a, int b) {
        return rand.nextInt(b - a) + a;
    }

    public static void initGrilleOrdi() {
        int l = randRange(0, 10);
        int c = randRange(0, 10);
        int d = randRange(1, 3);
        int[] t = { 5, 4, 3, 3, 1 };

        for (int bateau = 0; bateau < 5; ++bateau) {
            while (!posOk(grilleOrdi, l, c, d, t[bateau])) {
                l = randRange(0, 10);
                c = randRange(0, 10);
                d = randRange(1, 3);
            }
            if (d == 1) {
                for (int i = c; i < c + t[bateau]; ++i) {
                    grilleOrdi[l][i] = bateau + 1;
                }
            } else if (d == 2) {
                for (int i = l; i < l + t[bateau]; ++i) {
                    grilleOrdi[i][c] = bateau + 1;
                }
            }
        }
    }

    /**
     * 
     * @param grille grille de jeu de bataille navale
     */

    public static void afficherGrille(int[][] grille) {
        char[] premiereLigne = { ' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J' };

        for (char lettre : premiereLigne) {
            System.out.printf("%2c ", lettre);
        }
        System.out.println();

        for (int i = 0; i < 10; ++i) {
            System.out.printf("%2d ", i + 1);
            for (int j = 0; j < 10; ++j) {
                System.out.printf("%2d ", grille[i][j]);
            }
            System.out.println();
        }
    }

    public static void initGrilleJeu() {
        Scanner scanner = new Scanner(System.in);
        String[] bateau = { "porte-avions", "croiseur", "contre-torpilleur", "sous-marin", "torpilleur" };

        char lettre = '0';
        int nombre = 0;
        int direction = 0;
        int i = 0;

        do {
            System.out.printf("Donnez la lettre pour le %s: ", bateau[i]);
            lettre = Character.toUpperCase(scanner.nextLine().charAt(0));
        } while (lettre < 'A' || lettre > 'J');

        do {
            System.out.printf("Donnez le nombre pour le %s: ", bateau[i]);
            nombre = Integer.valueOf(scanner.nextLine());
        } while (nombre < 1 || nombre > 9);
        
        do {
            System.out.print("Voulez-vous qu'il soit horizontal (1) ou vertical (2): ");
            direction = Integer.valueOf(scanner.nextLine());
        } while (direction < 1 || direction > 2);
    }
}

// porte-avion, representé par le nombre 1, 5 cases
// croiseur, representé par le nombre 2, 4 cases
// contre-torpilleur, representé par le nombre 3, 3 cases
// sous-marin, representé par le nombre 4, 3 cases
// torpilleur, representé par le nombre 5, 1 cases

// int[][] grilleDeJeuTest = {
// { 5, 6, 0, 4, 0, 0, 2, 2, 2, 2 }, // 1
// { 0, 0, 0, 4, 0, 0, 0, 0, 0, 0 }, // 2
// { 0, 0, 0, 4, 0, 0, 0, 0, 0, 0 }, // 3
// { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 4
// { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 5
// { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 6
// { 0, 3, 3, 3, 0, 0, 0, 0, 0, 0 }, // 7
// { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 8
// { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 9
// { 0, 0, 0, 0, 0, 1, 1, 1, 1, 1 } // 10
// };
