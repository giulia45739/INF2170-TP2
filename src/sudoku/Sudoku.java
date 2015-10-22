/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;
/**
 *
 * @author Utilisateur
 */
public class Sudoku {
    static char[][] sudoku = new char[5][5];
    
    public static void main(String[] args) {
        
        char[] tempSudoku = new char[20];
        
        tempSudoku = remplirTempSudoku();
        sudoku = remplirSudoku(tempSudoku);
        System.out.println("-----------------------");
        while(true){
            char commande = Pep8.chari();
            gestionCommande(commande); 
        }
    }
    
    // Methodes d'initialisation
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
    
    // Methodes de P
    public static void gestionCaseJouee(int x){
        if(x > 16 || x < 1){
            System.out.println("Mauvais coup");
            System.exit(0);
        }
    }
    
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
    
    // Methodes de ? 
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
    
    public static boolean doubleDansLigne(int ligne, char[][] arrayDouble){
        // for each colunm 
        for(int col = 0; col < 4; col++){
            
            // stores the value to be compares 
            int valeur = sudoku[ligne][col]; 
            
            // takes each column except itself and checks if it equals. If equals returns true 
            for(int autreCol = col +1; autreCol < 4; autreCol++){
                if(sudoku[ligne][autreCol] != '.' && valeur == sudoku[ligne][autreCol]){
                    return true; 
                }
            }
        }
        return false; 
    }
    
    public static boolean doubleDansColonne(int col){
        // for each colunm 
        for(int ligne = 0; ligne < 4; ligne++){
            
            // stores the value to be compares 
            int valeur = sudoku[ligne][col]; 
            
            // takes each column except itself and checks if it equals. If equals returns true 
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
            return doubleDansLigne(ligne, sudoku);  
        }
        
        // Pour chaque colonne 
        for(int col = 0; col < 4; col++){
            return doubleDansColonne(col); 
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
    
    // Methode de S
    public static boolean resoudreGrille(){
        int x = premiereCaseVide();
        for(int i = 0; i < 4; i++){
            placerChiffre(x, toChar(i));
            if(resoudreGrille()){
                return true;
            }
        }
        placerChiffre(x, '.');
        return false;
    }
    
    public static int premiereCaseVide(){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 5; j++){
                if(sudoku[i][j] == '.')return (i*4 + j)+1; // Calcul de la case (1-16)
            }
        }
        return 0;
    }
}
