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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        char[][] sudoku = new char[5][5];
        char[] tempSudoku = new char[20];
        
        tempSudoku = remplirTempSudoku();
        sudoku = remplirSudoku(tempSudoku);
        
        while(true){
            char commande = Pep8.chari(); 
            gestionCommande(commande); 
        }
    }
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
        //System.out.print("Nous somme ici!");
        if(c != '1' && c != '2' && c != '3' && c != '4' && c != '.' && c != '\n'){
            System.out.println("Caractere Inconnu!");
            System.exit(0);
        }
    }
    
    public static void gestionSautDeLigne(char c, int i){
        switch (i) {
            case 4: case 9: case 14: case 19:
                if(c != '\n'){
                    System.out.println("Erreur!! Array Illegal!");
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
                        // ignore le saut de ligne qui suit 
                        Pep8.chari();
                        break; 
            case 'q':   commandeQ(); 
                        break; 
            default: System.out.println("Commande Invalide!");
                        break; 
        }
    }
}
