// This is Tic-Tac-Toe game

import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class TicTacToe 
{
    static TreeMap<Box, String> board = new TreeMap<Box, String>();
    static int greatestRow = -1, greatestCol = -1;
    public static void main(String[]args)
    {
        int totalMove1 = 0, totalMove2 = 0;

        System.out.println("\t\tWelcome to Tic-Tac-Toe!");
        System.out.println();
        Scanner console = new Scanner(System.in);
        System.out.print("How many same character in a row would you like to win? ");
        final int win = console.nextInt();
        System.out.println();
        System.out.print("At most how many of the same character would you like to see on the board at once? ");
        final int most = console.nextInt();
        System.out.println();

        System.out.print("This game requires two players to start. Are you ready to play (Yes/No)? ");
        String ready = console.next();
        System.out.println();
        while(!ready.toLowerCase().equals("yes") && !ready.toLowerCase().equals("no"))
        {
            System.out.print("Input not recognized. Please re-type your answer. Are you ready to play (Yes/No)? ");
            ready = console.next();
            System.out.println();
        }

        System.out.print("Please enter Player 1's name: ");
        String Player1 = console.next();
        System.out.println();
        System.out.print("Hi " + Player1 + ", which character would you like to use (X/O): ");
        String p1Char = console.next();
        System.out.println();
        String p2Char = "X";
        if(p1Char.equals("X"))
            p2Char = "O";

        System.out.print("Please enter Player 2's name: ");
        String Player2 = console.next();
        System.out.println();
        System.out.println("Hi " + Player2 + ", your character is " + p2Char + ".");
        System.out.println();
        System.out.println("For each move, we will prompt each player 2 times for their desired ROW and COLUMN.");
        System.out.println();

        // continuously getting the two players' moves until a player is detected to win
        do
        {
            totalMove1 = playerGeneral(Player1, p1Char, console, totalMove1, win, most);
            if(totalMove1 == -1)
                break;
            totalMove2 = playerGeneral(Player2, p2Char, console, totalMove2, win, most);
        }while(totalMove2!=-1);
    }

    // the general method to get players' move and checking if they win
    public static int playerGeneral(String name, String character, Scanner console, int totalMove, int win, int most)
    {
        int playerRow=0, playerColumn=0;
        boolean contain = true;

        // getting the player's move
        while(contain)
        {
            System.out.print(name + ", please enter your move for desired row: ");
            playerRow = console.nextInt();
            System.out.println();
            System.out.print(name + ", please enter your move for desired column: ");
            playerColumn = console.nextInt();
            System.out.println();

            if(totalMove==0 && playerRow>=0 && playerColumn >=0)
                contain = false;
        
            if(playerRow<0 || playerColumn<0)
            {
                System.out.print("Sorry " + name + ", your move is invalid. Select another move.");
                contain = true;
                continue;
            }

            for(Box move : board.keySet())
            {
                if((move.getRow() == playerRow && move.getColumn() == playerColumn))
                {
                    System.out.print("Sorry " + name + ", your move is invalid. Select another move. ");
                    contain = true;
                    break;
                }
                contain = false;
            }
        }

        if(!contain)
        {
            totalMove++;
            board.put(new Box(playerRow, playerColumn), character + String.valueOf(totalMove));
            if(playerRow > greatestRow)
                greatestRow = playerRow;
            if(playerColumn > greatestCol)
                greatestCol = playerColumn;
                
            printBoard(greatestRow, greatestCol);
            System.out.println();
        }

        if(totalMove>=win) //check winning once the move is greater than the desired winning in a row
        {
            for(Box move : board.keySet())
            {
                if(String.valueOf(board.get(move).charAt(0)).equals(character))
                {
                    boolean isWin = checkWin(move.getRow(), move.getColumn(), character, win, name, 0, 1);
                    if(!isWin)
                        isWin = checkWin(move.getRow(), move.getColumn(), character, win, name, 1, 0);
                    if(!isWin)
                        checkWin(move.getRow(), move.getColumn(), character, win, name, 1, 1);
                    if(!isWin)
                        checkWin(move.getRow(), move.getColumn(), character, win, name, 1, -1);
                    if(isWin)
                        return -1;
                }
            }
        }

        //if the player gets to the maximum move but still didn't win so we need to remove the first moves
        if((totalMove!=1) && (totalMove%most==0))
        {
            for(Box move : board.keySet())
            {
                if(board.get(move).equals(character+String.valueOf(totalMove+1-most)))
                {
                    board.remove(move);
                    break;
                }
            }
        }
        return totalMove;
    }

    // this method checks if the user wins for 4 cases: row, column, diagnose, and anti-diagnose
    // returns true if the player wins, false otherwise
    public static boolean checkWin(int currentRow, int currentCol, String character, int win, String name, int rowAdd, int colAdd)
    {
        int countWin = 1;
        for(Box check : board.keySet()){
            if((check.getRow()==currentRow+rowAdd) && (check.getColumn()==currentCol+colAdd) && (String.valueOf(board.get(check).charAt(0)).equals(character))){
                countWin++;
                currentRow = check.getRow();
                currentCol = check.getColumn();
                if(countWin==win){
                    System.out.println("Congratulations " + name +", you won the game!");
                    return true;
                }
            }
        }
        return false;
    }

    // This method prints out the board each time a player place a move
    public static void printBoard(int greatestRow, int greatestCol)
    {
        Set<Box> key = board.keySet();
        Iterator<Box> iterator = key.iterator();
        Box move = iterator.next();
        for(int i = 0; i<=greatestRow; i++)
        {
            System.out.print("|");
            for(int j = 0; j<=greatestCol; j++)
            {
                if(move.getRow()==i && move.getColumn()==j)
                {
                    System.out.print(board.get(move) + "|");
                    if(iterator.hasNext())
                        move = iterator.next();
                }
                else 
                    System.out.print("__|");
            }
            System.out.println();
        }
    }
}
