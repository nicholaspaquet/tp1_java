// Questions à poser lundi
// Est-ce que chaque tir doit ecrire un 6 s'il ne touche rien

import java.util.Random;
import java.util.Scanner;

/**
 * @author Nicholas Paquet
 * @since 8 fevrier 2023
 */

public class Bataille {

    /** Grille de jeu de l'ordinateur */
    public static int[][] grilleOrdi = new int[10][10];

    /** Grille de jeu du joueur */
    public static int[][] grilleJeu = new int[10][10];

    /** Grille de jeu de test */
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
     * Point de départ du programme
     * 
     * @param args
     */
    public static void main(String[] args) {
        // engagement();
        // engagementTest();
        // initGrilleJeu();
        // afficherGrille(grilleJeu);
        lireLettreBateau();

        // initGrilleOrdi();
        // afficherGrille(grilleOrdi);

        // Si le scanner n'est pas fermé, il y a un "leak" des ressources.
        scanner.close();
    }

    /**
     * Valide si les cases voulues sur la grille sont disponibles pour y placer un
     * bateau.
     * 
     * @param grille une grille de jeu
     * @param l      un numero de ligne
     * @param c      un numero de colonne (compris entre 0 et 9)
     * @param d      un entier d codant une direction (1 pour horizontal et 2 pour
     *               vertical)
     * @param t      un entier t donnant le nombre de cases d'un bateau
     */

    public static boolean posOk(int[][] grille, int l, int c, int d, int t) {
        boolean possibleDePlacerBateau = false;

        // Il y a un test selon la direction (1) ou (2)
        switch (d) {
            case 1:
                // La colonne(c) + le nombre de cases(t) que le bateau occupe doit être en
                // dessous de 10 pour rentrer dans la grille
                if (c + t < 10) {
                    for (int i = c; i < c + t; ++i) {
                        // On boucle et si on ne rencontre pas de zéro, on peut placer le bateau
                        if (grille[l][i] != 0) {
                            possibleDePlacerBateau = false;
                            break;
                        } else {
                            possibleDePlacerBateau = true;
                        }
                    }
                }
                break;
            case 2:
                // Même principle mais il faut vérifier que la ligne(l) + le nombre de cases(t)
                // est sous 10
                if (l + t < 10) {
                    for (int i = l; i < l + t; ++i) {
                        if (grille[i][c] != 0) {
                            possibleDePlacerBateau = false;
                            break;
                        } else {
                            possibleDePlacerBateau = true;
                        }
                    }
                }
                break;
        }
        return possibleDePlacerBateau;
    }

    /** Création d'un objet Random afin de pouvoir générer des nombres aléatoires */
    public static Random rand = new Random();

    /**
     * Génère des nombres aléatoires à l'aide de la classe importé java.util.Random.
     * <p>
     * Prise telle quelle dans la documentation du tp1.
     * 
     * @param a limite inferieur pour la generation d'un nombre aleatoire
     * @param b limite superieur pour la generation d'un nombre aleatoire
     * @return retourne un entier aleatoire entre a inclu et b exclu
     */

    public static int randRange(int a, int b) {
        return rand.nextInt(b - a) + a;
    }

    /**
     * Place un bateau sur la grille de jeux selon les paramètres passés
     * 
     * @param grille       une grille de jeu
     * @param l            un numero de ligne
     * @param c            un numero de colonne (compris entre 0 et 9)
     * @param d            un entier d codant une direction (1 pour horizontal et 2
     *                     pour vertical)
     * @param t            un entier t donnant le nombre de cases d'un bateau
     * @param indiceBateau Numéro représentant un bateau sur la grille
     */

    public static void placerBateau(int grille[][], int l, int c, int d, int t, int indiceBateau) {
        switch (d) {
            case 1:
                for (int j = c; j < c + t; ++j) {
                    grille[l][j] = indiceBateau + 1;
                }
                break;
            case 2:
                for (int j = l; j < l + t; ++j) {
                    grille[j][c] = indiceBateau + 1;
                }
                break;
        }
    }

    /**
     * Initialise la grille de l'ordinateur de façon aléatoire.
     */

    public static void initGrilleOrdi() {
        // Génération de trois nombres aléatoires
        int l = randRange(0, 10); // numéro de ligne
        int c = randRange(0, 10); // numéro de colonne
        int d = randRange(1, 3); // direction du bateau
        int[] t = { 5, 4, 3, 3, 1 }; // le nombre de cases que chaque bateau occupe

        // On boucle 5 fois,car il y a 5 bateaux à placer.
        for (int indiceBateau = 0; indiceBateau < 5; ++indiceBateau) {

            // On boucle tant que les cases voulues ne sont pas disponibles.
            while (!posOk(grilleOrdi, l, c, d, t[indiceBateau])) {
                l = randRange(0, 10);
                c = randRange(0, 10);
                d = randRange(1, 3);
            }
            // Finalement, on place le bateau sur la grille
            placerBateau(grilleOrdi, l, c, d, t[indiceBateau], indiceBateau);
        }
    }

    /**
     * Affiche la grille de jeu qu'on lui passe en paramètre.
     * 
     * @param grille grille de jeu de bataille navale
     */

    public static void afficherGrille(int[][] grille) {
        char[] entetesColonnes = { ' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J' };

        // Boucle for each, pour chaque lettreColonne dans entetesColonnes, on imprime
        // la lettre
        for (char lettreColonne : entetesColonnes) {
            System.out.printf("%2c ", lettreColonne);
        }
        System.out.println();

        for (int l = 0; l < 10; ++l) {
            // On imprime d'abord le numéro de la ligne
            System.out.printf("%2d ", l + 1);

            // Puis, on imprime chaque case de [l][0] jusqu'a [l][9]
            for (int c = 0; c < 10; ++c) {
                System.out.printf("%2d ", grille[l][c]);
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Initialise la grille du joueur en fonction des données qu'il saisit
     * manuellement.
     */

    public static void initGrilleJeu() {
        String[] nomBateau = { "porte-avions", "croiseur", "contre-torpilleur", "sous-marin", "torpilleur" };
        int[] t = { 5, 4, 3, 3, 1 }; // Nombre de cases que les bateaux occupent

        for (int i = 0; i < 5; ++i) {
            System.out.printf("Placement du %s\n", nomBateau[i]);

            // Lecture de trois données demandé au joueur
            int c = lireLettreBateau();
            int l = lireNombreBateau();
            int d = lireDirectionBateau();

            // On boucle tant que la position n'est pas valide.
            while (!posOk(grilleJeu, l, c, d, t[i])) {
                // Message d'erreur pour le joueur
                System.out.printf(
                        "Impossible de placer un bateau ici. Veuillez choisir une autre position pour placer le %s\n",
                        nomBateau[i]);
                // On recommence la lecture
                c = lireLettreBateau();
                l = lireNombreBateau();
                d = lireDirectionBateau();
            }
            // On place le bateau sur la grille de jeu
            placerBateau(grilleJeu, l, c, d, t[i], i);

            // On affiche la grille mise à jour
            System.out.println();
            afficherGrille(grilleJeu);
            System.out.printf("Le %s a ete place.\n\n", nomBateau[i]);
        }
    }

    /**
     * Instanciation d'un objet Scanner qui permet de lire au clavier
     * https://www.w3schools.com/java/java_user_input.asp
     */

    static Scanner scanner = new Scanner(System.in);

    /**
     * Lis une lettre au clavier entrée par le joueur de 'A' à 'J'.
     * 
     * @return retourne un nombre représentant la colonne de la grille de 0 à 9
     */

    public static int lireLettreBateau() {
        String lecture;
        char lettre = '0';

        System.out.printf("Donnez la lettre pour le bateau: ");
        // Lecture d'une lettre,
        lecture = scanner.nextLine();

        // Je test ici afin de savoir si quelque chose a été lu sinon ça plante
        // Je valide également que seulement un caractère a été entré
        if (!lecture.isEmpty() && lecture.length() == 1) {
            // La premiere lettre lue dans la lecture sera convertie en caractère puisque le
            // scanner n'a pas de façon intégré de lire directement un char.
            // Je converti également la lettre en majuscule pour que ce soit valide peu
            // importe la case.
            lettre = Character.toUpperCase(lecture.charAt(0));
        }

        // Validation que la lettre est entre 'A' et 'J'
        while (lettre < 'A' || lettre > 'J') {
            System.out.println("Lettre invalide, veuillez entre une lettre de 'A' a 'J'.");

            System.out.printf("Donnez la lettre pour le bateau: ");
            lecture = scanner.nextLine();

            if (!lecture.isEmpty() && lecture.length() == 1) {
                lettre = Character.toUpperCase(lecture.charAt(0));
            }
        }
        // Conversation de la valeur de la lettre en nombre de 0 à 9
        return (Integer.valueOf(lettre) - 65);
    }

    /**
     * Cette méthode permet de déterminer si le String lu est bel et bien un nombre.
     * <p>
     * Basé sur ce que j'ai vu au lien suivant:
     * https://www.baeldung.com/java-check-string-number
     * <p>
     * Essentiellement, c'est l'utilisation du try catch que j'ai appris à
     * utiliser. Puisque mon scanner plantait en y entrant une lettre
     * lorsque je tentais de lire un nombre. Je recevais un
     * NumberFormatException. Avec le try catch, je peux tester pour cette
     * exception et ainsi m'assurer de toujours avoir un nombre valide.
     * 
     * @param nombre accepte un nombre sous forme de String
     * @return Si le String est un nombre, on retourne vrai, sinon on retourne faux
     * 
     */

    public static boolean estUnNombre(String nombre) {
        try {
            Integer.valueOf(nombre);
        } catch (NumberFormatException exception) {
            return false;
        }
        return true;
    }

    /**
     * Lis un nombre au clavier de 1 à 10
     * 
     * @return retourne un nombre de 0 à 9
     */
    public static int lireNombreBateau() {
        String lecture;
        int nombre = 0;

        System.out.printf("Donnez le nombre pour le bateau: ");
        lecture = scanner.nextLine();

        // Si un nombre est lu, on converti le string en int
        if (estUnNombre(lecture)) {
            nombre = Integer.valueOf(lecture);
        }

        // On valide si le nombre lu se trouve entre 1 et 10
        while (nombre < 1 || nombre > 10) {
            System.out.println("Nombre invalide, veuillez entrez un nombre de 1 à 10.");
            
            System.out.printf("Donnez le nombre pour le bateau: ");
            lecture = scanner.nextLine();

            if (estUnNombre(lecture)) {
                nombre = Integer.valueOf(lecture);
            }
        }
        // Retourne le nombre -1 puisqu'on travaille avec des tableaux
        // qui commencent à 0
        return nombre - 1;
    }

    /**
     * @return
     */
    public static int lireDirectionBateau() {
        String lecture;
        int direction = 0;

        System.out.print("Voulez-vous qu'il soit horizontal (1) ou vertical (2): ");
        lecture = scanner.nextLine();

        if (estUnNombre(lecture)) {
            direction = Integer.valueOf(lecture);
        }

        while (direction < 1 || direction > 2) {
            System.out.println("Direction invalide, veuillez entrer 1 ou 2.");
            System.out.print("Voulez-vous qu'il soit horizontal (1) ou vertical (2): ");
            lecture = scanner.nextLine();

            if (estUnNombre(lecture)) {
                direction = Integer.valueOf(lecture);
            }
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
        System.out.println("\n");
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

    /**
     * Déroulement du jeu, le joueur joue contre l'ordinateur
     */
    public static void engagement() {
        System.out.println("Bienvenue au jeu de bataille navale!\n");
        System.out.println("Commencez par placer vos bateaux sur la grille de jeu.\n");

        // Initialisation des deux grilles de jeu
        initGrilleOrdi();
        initGrilleJeu();

        System.out.print("Pesez sur entrée pour commencer la partie");
        scanner.nextLine();

        // Boucle tant que les grilles contiennent des bateaux
        while (!vainqueur(grilleJeu) && !vainqueur(grilleOrdi)) {
            // L'ordinateur joue en premier
            int[] tirOrdi = tirOrdinateur();

            System.out.println("********************************\n");
            System.out.println("Tour de l'ordinateur");
            System.out.printf("Coordonnées du tir: [%c][%d]", (char) (tirOrdi[1] + 'A'), tirOrdi[0] + 1);
            System.out.println();

            mouvement(grilleJeu, tirOrdi[0], tirOrdi[1]);

            System.out.println("*****   Grille du joueur   *****\n");
            afficherGrille(grilleJeu);
            System.out.println("********************************\n");

            // Le joueur peut jouer tant qu'il lui reste un bateau
            if (!vainqueur(grilleJeu)) {
                int[] tirJoueur = new int[2];

                System.out.println("Tour du joueur");

                tirJoueur[1] = lireLettreBateau();
                tirJoueur[0] = lireNombreBateau();

                System.out.printf("Coordonnées du tir: [%c][%d]", (char) (tirJoueur[1] + 'A'), tirJoueur[0] + 1);
                System.out.println();

                mouvement(grilleOrdi, tirJoueur[0], tirJoueur[1]);

                System.out.print("Peser sur entrée pour continuer");
                scanner.nextLine();
            }
        }

        // Affichage du vainqueur à la fin de la partie
        if (vainqueur(grilleJeu)) {
            System.out.println("L'ordinateur a gagné! Vous avez perdu!");
        } else {
            System.out.println("Vous avez gagné! L'ordinateur a perdu!");
        }
    }

    /** Permet de tester le jeu en évitant de faire certains input répétitifs */
    public static void engagementTest() {
        System.out.println("Bienvenue au module de test de bataille navale!\n");
        System.out.println("Voici la grille de test du joueur: ");
        afficherGrille(grilleDeJeuTest);

        System.out.println("Veuillez choisir le mode de test:");
        System.out.println("joueur avec grille test (1)");
        System.out.println("Ordi vs ordi (2)");
        int choixTest = Integer.valueOf(scanner.nextLine());

        initGrilleOrdi();

        if (choixTest == 1) {
            // Test joueur avec grille test
            // Boucle tant que les grilles contiennent des bateaux
            while (!vainqueur(grilleDeJeuTest) && !vainqueur(grilleOrdi)) {
                // L'ordinateur joue en premier
                int[] tirOrdi = tirOrdinateur();

                System.out.println("********************************\n");
                System.out.println("Tour de l'ordinateur");
                System.out.printf("Coordonnées du tir: [%c][%d]", (char) (tirOrdi[1] + 'A'), tirOrdi[0] + 1);
                System.out.println();

                mouvement(grilleDeJeuTest, tirOrdi[0], tirOrdi[1]);

                System.out.println("*****   Grille du joueur   *****\n");
                afficherGrille(grilleDeJeuTest);
                System.out.println("********************************");

                // Le joueur peut jouer tant qu'il lui reste un bateau
                if (!vainqueur(grilleDeJeuTest)) {
                    int[] tirJoueur = new int[2];

                    System.out.println("Tour du joueur");

                    tirJoueur[0] = lireLettreBateau();
                    tirJoueur[1] = lireNombreBateau();

                    System.out.printf("Coordonnées du tir: [%c][%d]", (char) (tirJoueur[0] + 'A'), tirJoueur[1] + 1);
                    System.out.println();

                    mouvement(grilleOrdi, tirJoueur[1], tirJoueur[0]);
                    System.out.println("***  Grille de l'ordinateur  ***\n");
                    afficherGrille(grilleOrdi);

                    System.out.print("Peser sur entrée pour continuer");
                    scanner.nextLine();
                }
            }
        } else if (choixTest == 2) {
            // Test ordi vs ordi
            while (!vainqueur(grilleDeJeuTest) && !vainqueur(grilleOrdi)) {
                // L'ordinateur joue en premier
                int[] tirOrdi = tirOrdinateur();

                System.out.println("********************************\n");
                System.out.println("Tour de l'ordinateur");
                System.out.printf("Coordonnées du tir: [%c][%d]", (char) (tirOrdi[1] + 'A'), tirOrdi[0] + 1);

                System.out.println();

                mouvement(grilleDeJeuTest, tirOrdi[0], tirOrdi[1]);

                System.out.println("*****   Grille du joueur   *****\n");
                afficherGrille(grilleDeJeuTest);
                System.out.println("********************************");

                // scanner.nextLine();

                // Le joueur peut jouer tant qu'il lui reste un bateau
                if (!vainqueur(grilleDeJeuTest)) {
                    int[] tirJoueur = tirOrdinateur();

                    System.out.println("Tour du joueur");
                    System.out.printf("Coordonnées du tir: [%c][%d]", (char) (tirJoueur[1] + 'A'), tirJoueur[0] + 1);
                    System.out.println();

                    mouvement(grilleOrdi, tirJoueur[0], tirJoueur[1]);

                    System.out.println("***  Grille de l'ordinateur  ***\n");
                    afficherGrille(grilleOrdi);
                }
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
