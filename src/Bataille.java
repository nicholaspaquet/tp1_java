import java.util.Random;
import java.util.Scanner;

/**
 * @author Nicholas Paquet
 * @since 8 fevrier 2023
 */

public class Bataille {
    public static int[][] grilleOrdi = new int[10][10];
    public static int[][] grilleJeu = new int[10][10];

    public static int[][] grilleDeJeuTest = {
            { 5, 0, 0, 4, 0, 0, 2, 2, 2, 2 }, // 1
            { 0, 0, 0, 4, 0, 0, 0, 0, 0, 0 }, // 2
            { 0, 0, 0, 4, 0, 0, 0, 0, 0, 0 }, // 3
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 4
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 5
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 6
            { 0, 3, 3, 3, 0, 0, 0, 0, 0, 0 }, // 7
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 8
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, // 9
            { 0, 0, 0, 0, 0, 1, 1, 1, 1, 1 } // 10
    };

    /**
     * @param args
     */
    public static void main(String[] args) {
        // System.out.println("test: "+ (char)(1+'0'));
        engagement();
        scanner.close();
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
     *               Cette fonction affiche la grille qu'on lui passe en paramètre
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
        System.out.println();
    }

    public static void initGrilleJeu() {
        String[] nomBateau = { "porte-avions", "croiseur", "contre-torpilleur", "sous-marin", "torpilleur" };
        int[] t = { 5, 4, 3, 3, 1 };

        for (int i = 0; i < 5; ++i) {
            System.out.printf("Placement du %s\n", nomBateau[i]);

            // Lecture de trois données demandé au joueur
            int c = lireLettreBateau();
            int l = lireNombreBateau();
            int d = lireDirectionBateau();

            while (!posOk(grilleJeu, l, c, d, t[i])) {
                System.out.printf(
                        "Impossible de placer un bateau ici. Veuillez choisir une autre position pour placer le %s\n",
                        nomBateau[i]);

                c = lireLettreBateau();
                l = lireNombreBateau();
                d = lireDirectionBateau();
            }

            if (d == 1) {
                for (int j = c; j < c + t[i]; ++j) {
                    grilleJeu[l][j] = i + 1;
                }
            } else if (d == 2) {
                for (int j = l; j < l + t[i]; ++j) {
                    grilleJeu[j][c] = i + 1;
                }
            }
            afficherGrille(grilleJeu);
            System.out.printf("Le %s a ete place.\n", nomBateau[i]);
        }
    }

    static Scanner scanner = new Scanner(System.in);

    public static int lireLettreBateau() {

        char lettre;

        System.out.printf("Donnez la lettre pour le bateau: ");
        lettre = Character.toUpperCase(scanner.nextLine().charAt(0));

        while (lettre < 'A' || lettre > 'J') {
            System.out.println("Lettre invalide, veuillez entre une lettre de 'A' a 'J'.");
            System.out.printf("Donnez la lettre pour le bateau: ");
            lettre = Character.toUpperCase(scanner.nextLine().charAt(0));
        }
        return (Integer.valueOf(lettre) - 65);

    }

    // Plante si on entre une lettre
    public static int lireNombreBateau() {
        int nombre;

        System.out.printf("Donnez le nombre pour le bateau: ");
        nombre = Integer.valueOf(scanner.nextLine());

        while (nombre < 1 || nombre > 10) {
            System.out.println("Nombre invalide, veuillez entrez un nombre de 1 a 10.");
            System.out.printf("Donnez le nombre pour le bateau: ");
            nombre = Integer.valueOf(scanner.nextLine());
        }
        return nombre - 1;
    }

    public static int lireDirectionBateau() {
        int direction;

        System.out.print("Voulez-vous qu'il soit horizontal (1) ou vertical (2): ");
        direction = Integer.valueOf(scanner.nextLine());

        while (direction < 1 || direction > 2) {
            // System.out.println("Direction invalide, veuillez entrer 1 ou 2.");
            System.out.print("Voulez-vous qu'il soit horizontal (1) ou vertical (2): ");
            direction = Integer.valueOf(scanner.nextLine());
        }
        return direction;
    }

    /**
     * @param grille une grille de jeu
     * @param bateau un numéro de bateau de 1 à 5
     * @return vrai si le bateau a coulé, faux si le bateau est toujours sur la
     *         grille
     * 
     *         Cette fonction retourne si le bateau donné en argument est toujours
     *         sur la grille de jeu.
     */

    public static boolean couler(int[][] grille, int bateau) {
        for (int ligne = 0; ligne < 10; ++ligne) {
            for (int colonne = 0; colonne < 10; ++colonne) {
                if (grille[ligne][colonne] == bateau) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param grille Une grille de jeu
     * @param l      Numéro de ligne
     * @param c      Numéro de colonne
     */

    public static void mouvement(int[][] grille, int l, int c) {
        int numeroBateau = grille[l][c];

        System.out.print("Résultat du tir: ");
        if (numeroBateau == 0 || numeroBateau == 6) {
            System.out.print("À l'eau");
        } else if (numeroBateau >= 1 && numeroBateau <= 5) {
            grille[l][c] = 6;

            if (couler(grille, numeroBateau)) {
                System.out.print("Coulé");
            } else {
                System.out.print("Touché");
            }
        }
        System.out.println();
    }

    /**
     * @return Retourne un tableau avec les coordonnées aléatoires d'un tir
     */
    public static int[] tirOrdinateur() {
        int[] tir = new int[2];

        for (int i = 0; i < 2; ++i) {
            tir[i] = randRange(0, 10);
        }
        return tir;
    }

    /**
     * @param grille Une grille de jeu
     * @return Retourne faux s'il reste un bateau sur la grille de jeu, sinon
     *         retourne vrai
     */
    public static boolean vainqueur(int[][] grille) {
        for (int ligne = 0; ligne < 10; ++ligne) {
            for (int colonne = 0; colonne < 10; ++colonne) {
                if (grille[ligne][colonne] >= 1 && grille[ligne][colonne] <= 5) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void engagement() {
        System.out.println("Bienvenue au jeu de bataille navale!\n");
        System.out.println("Commencez par placer vos bateaux sur la grille de jeu.\n");

        // Initialisation des deux grilles de jeu
        initGrilleOrdi();
        // initGrilleJeu();

        // Boucle tant que les grilles contiennent des bateaux
        // while (!vainqueur(grilleJeu) && !vainqueur(grilleOrdi)) {
        // // L'ordinateur joue en premier
        // int[] tirOrdi = tirOrdinateur();

        // System.out.println("Tour de l'ordinateur");
        // System.out.printf("[%c][%d]", (char) (tirOrdi[0] + 1 + 64), tirOrdi[1] + 1);
        // System.out.println();

        // mouvement(grilleJeu, tirOrdi[0], tirOrdi[1]);

        // System.out.println("Grille du joueur");
        // afficherGrille(grilleJeu);

        // // Le joueur peut jouer tant qu'il lui reste un bateau
        // if (!vainqueur(grilleJeu)) {
        // int[] tirJoueur = new int[2];

        // tirJoueur[0] = lireLettreBateau();
        // tirJoueur[1] = lireNombreBateau();

        // System.out.println("Tour du joueur");
        // System.out.printf("[%c][%d]", (char) (tirOrdi[0] + 1 + 64), tirJoueur[1] +
        // 1);
        // System.out.println();

        // mouvement(grilleOrdi, tirJoueur[0], tirJoueur[1]);
        // }
        // }

        // Test
        while (!vainqueur(grilleDeJeuTest) && !vainqueur(grilleOrdi)) {
            // L'ordinateur joue en premier
            int[] tirOrdi = tirOrdinateur();

            System.out.println("Tour de l'ordinateur");
            System.out.printf("[%c][%d]", (char) (tirOrdi[1] + 'A'), tirOrdi[0] + 1);

            System.out.println();

            mouvement(grilleDeJeuTest, tirOrdi[0], tirOrdi[1]);

            System.out.println("Grille du joueur");
            afficherGrille(grilleDeJeuTest);

            String bidon = scanner.nextLine();

            // Le joueur peut jouer tant qu'il lui reste un bateau
            if (!vainqueur(grilleDeJeuTest)) {
                int[] tirJoueur = tirOrdinateur();

                System.out.println("Tour du joueur");
                System.out.printf("[%c][%d]", (char) (tirJoueur[1] + 'A'), tirJoueur[0] + 1);
                System.out.println();

                mouvement(grilleOrdi, tirJoueur[0], tirJoueur[1]);

                System.out.println("Grille de l'ordinateur");
                afficherGrille(grilleOrdi);
            }
        }

        // Affichage du vainqueur à la fin de la partie
        if (vainqueur(grilleDeJeuTest)) {
            System.out.println("L'ordinateur a gagné! Vous avez perdu!");
        } else {
            System.out.println("Vous avez gagné! L'ordinateur a perdu!");
        }
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
