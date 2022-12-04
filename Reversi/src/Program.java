import java.util.Objects;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {

        System.out.println("""
                Welcome to Reversi! please, choose the game mode:
                1) Player vs player
                2) Silly bot vs Player
                """);
        int mode = 0;
        boolean check = false;
        while (!check) {
            Scanner in = new Scanner(System.in);
            try {
                System.out.println("I wanna play: ");
                String inp = in.next();
                mode = Integer.parseInt(inp);
                if (mode != 1 && mode != 2) {
                    System.out.println("We do not have such a mode :( try again: ");
                }
                else {
                    check = true;
                }
            } catch (Exception e) {
                System.out.println("Incorrect input, try one more time.");
            }
        }
        boolean wannaPlay = true;
        while (wannaPlay) {
            if (mode == 1) {
                PvP();
            } else {
                SBvP();
            }
            System.out.println("If you want to play the same mode again enter Yes");
            Scanner in = new Scanner(System.in);
            String yN = in.next();
            if (!Objects.equals(yN, "Yes")) wannaPlay = false;
        }
        System.out.println("Goodbye!");
    }

    private static void PvP() {
        var f = new Field();
        Player p1 = new Player('o'), p2 = new Player('x');
        int check = 0;
        while ((f.getScore()[0] > 0 && f.getScore()[1] > 0) && f.getScore()[0] + f.getScore()[1] != 64 && check < 2) {
            check = 0;
            f.possMove('o');
            if (f.moveVariants('o') == 0) {
                System.out.println("Oops! you have nowhere to go! Skip!");
                check += 1;
            } else {
                p1.makesMove(f);
            }

            f.possMove('x');
            if (f.moveVariants('x') == 0) {
                System.out.println("Oops! you have nowhere to go! Skip!");
                check += 1;
            } else {
                p2.makesMove(f);
            }
        }
        int[] score = f.getScore();
        if (score[0] > score[1]) {
            System.out.println("Player \"x\" wins: score: " + score[0] + ":" + score[1]);

            System.out.println(f);
        } else {
            System.out.println("Player \"o\" wins: score: " + score[0] + ":" + score[1]);
            System.out.println(f);
        }
    }

    private static void SBvP() {
        System.out.println("You are playing first!");
        var f = new Field();
        Player p1 = new Player('o');
        SillyBot p2 = new SillyBot('x');
        int check = 0;
        while ((f.getScore()[0] > 0 && f.getScore()[1] > 0) && f.getScore()[0] + f.getScore()[1] != 64 && check < 2) {
            check = 0;
            f.possMove('o');
            if (f.moveVariants('o') == 0) {
                System.out.println("Oops! you have nowhere to go! Skip!");
                check += 1;
            } else {
                p1.makesMove(f);
            }

            f.possMove('x');
            if (f.moveVariants('x') == 0) {
                System.out.println("Oops! SillyBiilly has nowhere to go! You are lucky");
                check += 1;
            } else {
                p2.makesMove(f);
            }
        }
        int[] score = f.getScore();
        if (score[0] > score[1]) {
            System.out.println("SillyBilly wins: score: " + score[0] + ":" + score[1]);
            System.out.println(f);
        } else {
            System.out.println("Player \"o\" wins: score: " + score[0] + ":" + score[1]);
            System.out.println(f);
        }
    }
}