public class SillyBot implements Lemon{
    public char sign;
    SillyBot(char s) {
        sign = s;
    }

    private double calcSS(int x, int y) {
        if ((x == 1 && y == 1) || (x == 1 && y == 8) || (x == 8 && y == 8) || (x == 8 && y == 1)) return 0.8;
        else if (x > 1 && x < 8 && y > 1 && y < 8) return 0;
        return 0.4;
    }
    @Override
    public void makesMove(Field f) {
        f.possMove(sign);
        double[] max = {0, 0, 0};
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                if (f.getByInd(i, j) == '?') {
                    char opp = 'o';
                    if (opp == sign) opp = 'x';
                    double temp = f.sumCells(i, j) + calcSS(i, j);
                    if (temp > max[0]) max = new double[]{temp, i, j};
                }
            }
        }
        System.out.println("SillyBilly moves on ("+(int)max[1] + ";" + (int)max[2] + ")");
        f.clearPossMoves();
        f.theMoveWasMade((int)max[1], (int)max[2], sign);
    }
}
