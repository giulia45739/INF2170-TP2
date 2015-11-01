
package sudoku;

/*
Le programme suivant prend en entree un sudoku 4x4 et propose plusieurs
    options a l'utilisateur :
Option w -> Affiche la grille dans son etat actuel
Option q -> Quitte le programme
Option p n1 n2 -> Joue n2 dans la case n1 (voir plus bas les # de cases)
Option ? -> Verifie si le sudoku est gagnant, perdant ou s'il reste des cases libres
Option s -> Resout (si c'est possible) le sudoku dans son etat actuel

Le programme est separe efficacement en plusieurs methodes qui ont chacune
une tache bien precise.

Limite du programme :

La methode placeChiffre(int caseJouee, char chiffre) n'est pas optimale

*/
public class Sudoku {
    // La variable sudoku est static afin que toutes les methodes puissent y
    // acceder
    static char[][] sudoku = new char[5][5];
    
    public static void main(String[] args) {
        
        char[] tempSudoku;
        tempSudoku = remplirTempSudoku();
        sudoku = remplirSudoku(tempSudoku);
        
        while(true){
            char commande = Pep8.chari();
            gestionCommande(commande); 
        }
    }
    
    // Methodes d'initialisation
    
    // La methode remplirTempSudoku prend les 20 caracteres qui constituent le
    //  sudoku et les places dans un tableau 1 dimension
    // Chaque caractere est verifie par les methodes gestionCaractereIllegaux
    //  et gestionSautDeLigne
    public static char[] remplirTempSudoku(){
        char[] tempSudoku = new char[20];
        char tempChar;
        for(int i = 0; i < 20; i++){
            tempChar = Pep8.chari();
            gestionCaractereIllegaux(tempChar);
            gestionSautDeLigne(tempChar, i);
            tempSudoku[i] = tempChar;
        }
        return tempSudoku;
    }
    
    public static void gestionCaractereIllegaux(char c){
        if(c != '1' && c != '2' && c != '3' && c != '4' && c != '.' && c != '\n'){
            System.out.println("Caractere Inconnu!");
            System.exit(0);
        }
    }
    
    // La methode gestionSautDeLigne s'assure qu'apres 4 chiffres il y a bien un
    // saut de ligne
    public static void gestionSautDeLigne(char c, int i){
        switch (i) {
            case 4: case 9: case 14: case 19:
                if(c != '\n'){
                    System.out.println("Erreur! Array Illegal!");
                    System.exit(0);
                }
            break;
        }
    }
    
    // Transforme un tableau 1 dimension en tableau 2 dimensions
    public static char[][] remplirSudoku(char[] arrayTemp){
        char[][] sudoku = new char[4][5];
        int k = 0;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 5; j++){
                sudoku[i][j] = arrayTemp[k];
                k++;
            }
        }
        return sudoku;
    }
    
    
    // Gestion des commandes entrees dans le programme
    
    public static void gestionCommande(char commande){
        switch (commande) {
            case 'w':   commandeW();
                        break; 
            case 'q':   commandeQ(); 
                        break; 
            case 'p':   commandeP(); 
                        break;
            case '?':   commandePointInter();
                        break; 
            case 's':   commandeS();
                        break;
            case '\n':  break;
            default:    System.out.println("Commande Invalide!");
                        commandeQ(); 
                        break; 
        }
    }
    
    // Methodes de commandes
    
    public static void commandeQ(){
        System.exit(0);
    }
    
    public static void commandeW(){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 5; j++){
                System.out.print(sudoku[i][j]);
            }
        }
    }
    
    public static void commandeP(){
        int caseJouee = Pep8.deci();
        int chiffreJoue = Pep8.deci();
        gestionCaseJouee(caseJouee);
        gestionChiffreJoue(chiffreJoue);
        placerChiffre(caseJouee, toChar(chiffreJoue));
    }
    
    public static boolean commandePointInter(){
        if(verifierSiPerdu()){
            System.out.println("Perdu!");
            return false; 
        } 
        else if(verifierSiJouer()){
            System.out.println("Jouer!");
            return false; 
        }
        else{
            System.out.println("Bravo!");
            return true; 
        }
    }
    
    public static void commandeS(){
        boolean estGagne = resoudreGrille();
        if(estGagne){
            System.out.println("Solution");
        }
        else{
            System.out.println("Pas de solution");
        }
    }
    
    // Methodes utilisees par P
    
    // Verifie si la case joue existe
    public static void gestionCaseJouee(int x){
        if(x > 16 || x < 1){
            System.out.println("Mauvais coup");
            System.exit(0);
        }
    }
    
    // Verifie si le chiffre joue est valide
    public static void gestionChiffreJoue(int x){
        if(x > 4 || x < 1){
            System.out.println("Mauvais coup");
            System.exit(0);
        }
    }
    
    public static char toChar(int x){
        switch (x){
            case 1: return '1';
            case 2: return '2';
            case 3: return '3';
            case 4: return '4';
        }
        return '.';
    }
    
    public static void placerChiffre(int caseJouee, char chiffre ){
        switch (caseJouee){
            case 1: chiffreToArray(0, 0, chiffre);
                    break;
            case 2: chiffreToArray(0, 1, chiffre);
                    break;
            case 3: chiffreToArray(0, 2, chiffre);
                    break;
            case 4: chiffreToArray(0, 3, chiffre);
                    break;
            case 5: chiffreToArray(1, 0, chiffre);
                    break;
            case 6: chiffreToArray(1, 1, chiffre);
                    break;
            case 7: chiffreToArray(1, 2, chiffre);
                    break;
            case 8: chiffreToArray(1, 3, chiffre);
                    break;
            case 9: chiffreToArray(2, 0, chiffre);
                    break;
            case 10: chiffreToArray(2, 1, chiffre);
                    break;
            case 11: chiffreToArray(2, 2, chiffre);
                    break;
            case 12: chiffreToArray(2, 3, chiffre);
                    break;
            case 13: chiffreToArray(3, 0, chiffre);
                    break;
            case 14: chiffreToArray(3, 1, chiffre);
                    break;
            case 15: chiffreToArray(3, 2, chiffre);
                    break;
            case 16: chiffreToArray(3, 3, chiffre);
                    break;
        }
    }
    
    public static void chiffreToArray(int i, int j, char chiffre){
        sudoku[i][j] = chiffre;
    }
    
    // Methodes utilisees PointInter (?)
    
    // Verifie si les valeurs sont uniques dans tous les carres
    public static boolean doubleDansCarre(int ligne, int col){
        int valeur = sudoku[ligne][col];   
        if(sudoku[ligne+1][col+1] != '.' && valeur == sudoku[ligne+1][col+1]){
            return true; 
        }
        
        valeur = sudoku[ligne][col++];
        if(sudoku[ligne+1][col-1] != '.' && valeur == sudoku[ligne+1][col-1]){
            return true; 
        }
        return false; 
    }
    
    // Verifie si les valeurs sont uniques dans toutes les ligne
    public static boolean doubleDansLigne(int ligne){
        for(int col = 0; col < 4; col++){
             
            int valeur = sudoku[ligne][col]; 
             
            for(int autreCol = col +1; autreCol < 4; autreCol++){
                if(sudoku[ligne][autreCol] != '.' && valeur == sudoku[ligne][autreCol]){
                    return true; 
                }
            }
        }
        return false; 
    }
    
    // Verifie si les valeurs sont uniques dans toutes les colonnes
    public static boolean doubleDansColonne(int col){
        for(int ligne = 0; ligne < 4; ligne++){
            
            int valeur = sudoku[ligne][col]; 
             
            for(int autreLigne = ligne +1; autreLigne < 4; autreLigne++){
                if(sudoku[autreLigne][col] != '.' && valeur == sudoku[autreLigne][col]){
                    return true; 
                }
            }
        }
        return false; 
    }
    
    public static boolean verifierSiPerdu(){
       // Pour chaque ligne  
        for(int ligne = 0; ligne < 4; ligne++){
            if(doubleDansLigne(ligne)){
                return true;
            }
        }
        
        // Pour chaque colonne 
        for(int col = 0; col < 4; col++){
            if(doubleDansColonne(col)){
                return true;
            }
        }
        
        // Pour chaque carre
        if(doubleDansCarre(0,0) || doubleDansCarre(0,2) || doubleDansCarre(2,0) || doubleDansCarre(2,2)){
            return true; 
        }
        return false; 
    }
    
    public static boolean verifierSiJouer(){
        for(int ligne =0; ligne <4; ligne++){
            for(int col =0; col <4; col++){
                if(sudoku[ligne][col] == '.'){
                    return true;
                }
            }
        }
        return false; 
    }
    
    // Methodes utilisee par S
    
    // La methode est basee sur le pseudo-code fournis
    public static boolean resoudreGrille(){
        if(verifierSiPerdu()){
            return false; 
        }
        else if(!verifierSiJouer()){
            return true; 
        }
        int x = premiereCaseVide();
        for(int i = 1; i <= 4; i++){
            placerChiffre(x, toChar(i));
            if(resoudreGrille()){
                return true;
            }
        }
        placerChiffre(x, '.');
        return false;
    }
    
    // Retourne la position de la premiere case qui contient un point
    public static int premiereCaseVide(){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 5; j++){
                // Calcul de la case (1-16)
                if(sudoku[i][j] == '.')return (i*4 + j)+1;
            }
        }
        return 0;
    }
}
