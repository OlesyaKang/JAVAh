import java.util.Scanner;


public class Player implements Lemon{
    public char sign;
    Player(char s) {
        sign = s;
    }

    private int lol = 0;

    public int[] getCoord() {
        int x = 0, y = 0;
        boolean check = false;
        while (!check) {
            System.out.print("Player \"" + sign + "\", enter coords in format: \"x y\"");
            Scanner in = new Scanner(System.in);
            try {
                x = in.nextInt();
                y = in.nextInt();
                if (x > 0 && x < 9 && y > 0 && y < 9) check = true;
                else {
                    lol += 1;
                    if (lol >= 10) {
                        System.out.println("What da duh dude.");
                        lol = 0;
                    }
                    System.out.println("There are no such a cell! Try one more time:");
                }
            } catch (Exception e) {
                System.out.println("Incorrect input, try one more time.");
            }
        }
        return new int[]{x, y};
    }

    @Override
    public void makesMove(Field f) {
        f.clearPossMoves();
        f.possMove(sign);
        System.out.println(f);
        int[] moveCoords = getCoord();
        while (!f.canGo(moveCoords[0], moveCoords[1])) {
            System.out.println("You can not go here, this cell is not marked by \"?\" :( try one more time:");
            moveCoords = getCoord();
        }
        f.theMoveWasMade(moveCoords[0], moveCoords[1], sign);
        f.clearPossMoves();
    }
}