public class Field {
    char[][] field;
    Field() {
        field = new char[10][19];
        for (char i = 0; i < 10; i++) {
            for (char j = 0; j < 19; j++) {
                if (j % 2 != 0) {
                    field[i][j] = '|';
                }
                else {
                    field[i][j] = ' ';
                }
            }
        }
        int position = 1;
        for (int i = 2; i < 18; i++) {
            if (i % 2 == 0) {
                field[0][i] = (char) (position + '0');
                field[i/2][0] = (char) (position + '0');
                position += 1;
            }
        }
        field[4][8] = 'x';
        field[5][10] = 'x';
        field[4][10] = 'o';
        field[5][8] = 'o';
    }

    private int switchIt(int it) {
        it = it * 2;
        return it;
    }

    public int[] getScore() {
        int x = 0, o = 0;
        for (int i = 1; i < 9; i++) {
            for (int j = 0; j < 18; j++) {
                if (field[i][j] == 'x') x += 1;
                if (field[i][j] == 'o') o += 1;
            }
        }
        return new int[]{x, o};
    }

    public boolean canGo(int x, int y) {
        return field[x][switchIt(y)] == '?';
    }

    public int moveVariants(char sign) {
        System.out.println("You can go to:");
        int res = 0;
        for (int i = 1; i < 9; i++) {
            for (int j = 0; j < 18; j++) {
                if (field[i][j] == '?') {
                    res += 1;
                    System.out.println("(" + i + "," + j/2 + ")");
                }
            }
        }
        clearPossMoves();
        return res;
    }

    public void theMoveWasMade(int x, int y, char sign){
        int yy;
        yy = switchIt(y);
        field[x][yy] = sign;
        oppSignChanger(x, y, sign);
    }

    private int[] goUntil(int x, int y, int wx, int wy, int bx, int by, char sign) {
        char oppS = 'x';
        if (oppS == sign) oppS = 'o';
        while (field[x][switchIt(y)] == oppS && x != bx && y != by) {
            x += wx;
            y += wy;
            if (field[x][switchIt(y)] == ' ') break;
        }
        return new int[]{x, y};
    }

    private void goChange(int x, int y, int wx, int wy, int bx, int by) {
        while (x != bx || y != by) {
            if (field[x][switchIt(y)] == 'o') field[x][switchIt(y)] = 'x';
            else if (field[x][switchIt(y)] == 'x'){
                field[x][switchIt(y)] = 'o';
            }
            x += wx;
            y += wy;
        }
    }

    public void possMove(char sign) {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                //System.out.println(" "+ i + j);
                if (field[i][switchIt(j)] == ' ') {
                    char opp = 'o';
                    if (opp == sign) opp = 'x';
                    if (field[i + 1][switchIt(j)] == opp) {
                        int[]fin = goUntil(i + 1, j, 1, 0,8, 0, sign);
                        if (field[fin[0]][switchIt(fin[1])] == sign) field[i][switchIt(j)] = '?';
                    }
                    if (field[i - 1][switchIt(j)] == opp) {
                        int[]fin = goUntil(i - 1, j, -1, 0,1, 0, sign);
                        if (field[fin[0]][switchIt(fin[1])] == sign) field[i][switchIt(j)] = '?';
                    }
                    if (field[i][switchIt(j + 1)] == opp) {
                        int[]fin = goUntil(i, j + 1, 0, 1,0, 8, sign);
                        if (field[fin[0]][switchIt(fin[1])] == sign) field[i][switchIt(j)] = '?';
                    }
                    if (field[i][switchIt(j - 1)] == opp) {
                        int[]fin = goUntil(i, j - 1, 0, -1,0, 1, sign);
                        if (field[fin[0]][switchIt(fin[1])] == sign) field[i][switchIt(j)] = '?';
                    }
                    if (field[i + 1][switchIt(j + 1)] == opp) {
                        int[]fin = goUntil(i + 1, j + 1, 1, 1,8, 8, sign);
                        if (field[fin[0]][switchIt(fin[1])] == sign) field[i][switchIt(j)] = '?';
                    }
                    if (field[i - 1][switchIt(j + 1)] == opp) {
                        int[]fin = goUntil(i - 1, j + 1, -1, 1,1, 8, sign);
                        if (field[fin[0]][switchIt(fin[1])] == sign) field[i][switchIt(j)] = '?';
                    }
                    if (field[i + 1][switchIt(j - 1)] == opp) {
                        int[]fin = goUntil(i + 1, j - 1, 1, -1,8, 1, sign);
                        if (field[fin[0]][switchIt(fin[1])] == sign) field[i][switchIt(j)] = '?';
                    }
                    if (field[i - 1][switchIt(j - 1)] == opp) {
                        int[]fin = goUntil(i - 1, j - 1, -1, -1,1, 1, sign);
                        if (field[fin[0]][switchIt(fin[1])] == sign) field[i][switchIt(j)] = '?';
                    }
                }
            }
        }
    }

    public void clearPossMoves() {
        for (int i = 1; i < 9; i++) {
            for (int j = 0; j < 18; j++) {
                if (field[i][j] == '?') field[i][j] = ' ';
            }
        }
    }

    public void oppSignChanger(int x, int y, char sign) {
        char opp = 'o';
        if (opp == sign) opp = 'x';
        if (field[x + 1][switchIt(y)] == opp && x < 7) {
            int[]fin = goUntil(x + 1, y, 1, 0,8, 0, sign);
            if (field[fin[0]][switchIt(fin[1])] == sign) {
                goChange(x + 1, y, 1, 0, fin[0], fin[1]);
            }
        }
        if (field[x - 1][switchIt(y)] == opp && x > 2) {
            int[]fin = goUntil(x - 1, y, -1, 0,1, 0, sign);
            if (field[fin[0]][switchIt(fin[1])] == sign) {
                goChange(x - 1, y, -1, 0, fin[0], fin[1]);
            }
        }
        if (field[x][switchIt(y + 1)] == opp && y < 7) {
            int[]fin = goUntil(x, y + 1, 0, 1,0, 8, sign);
            if (field[fin[0]][switchIt(fin[1])] == sign) {
                goChange(x, y + 1, 0, 1, fin[0], fin[1]);
            }
        }
        if (field[x][switchIt(y - 1)] == opp && y > 2) {
            int[]fin = goUntil(x, y - 1, 0, -1,0, 1, sign);
            if (field[fin[0]][switchIt(fin[1])] == sign) {
                goChange(x, y - 1, 0, -1, fin[0], fin[1]);
            }
        }
        if (field[x + 1][switchIt(y + 1)] == opp && y < 7 && x < 7) {
            int[]fin = goUntil(x + 1, y + 1, 1, 1,8, 8, sign);
            if (field[fin[0]][switchIt(fin[1])] == sign) {
                goChange(x + 1, y + 1, 1, 1, fin[0], fin[1]);
            }
        }
        if (field[x - 1][switchIt(y + 1)] == opp && y < 7 && x > 2) {
            int[]fin = goUntil(x - 1, y + 1, -1, 1,1, 8, sign);
            if (field[fin[0]][switchIt(fin[1])] == sign) {
                goChange(x - 1, y + 1, -1, 1, fin[0], fin[1]);
            }
        }
        if (field[x + 1][switchIt(y - 1)] == opp && y > 2 && x < 7) {
            int[]fin = goUntil(x + 1, y - 1, 1, -1,8, 1, sign);
            if (field[fin[0]][switchIt(fin[1])] == sign) {
                goChange(x + 1, y - 1, 1, -1, fin[0], fin[1]);
            }
        }
        if (field[x - 1][switchIt(y - 1)] == opp && y > 2 && x > 2) {
            int[]fin = goUntil(x - 1, y - 1, -1, -1,1, 1, sign);
            if (field[fin[0]][switchIt(fin[1])] == sign) {
                goChange(x - 1, y - 1, -1, -1, fin[0], fin[1]);
            }
        }
    }

    public char getByInd(int x, int y) {
        return field[x][switchIt(y)];
    }

    public int sumCells(int x, int y) {
        int res = 0;
        char opp = 'o';
        if (field[x + 1][switchIt(y)] == opp) {
            int[]fin = goUntil(x + 1, y, 1, 0,8, 0, 'x');
            if (field[fin[0]][switchIt(fin[1])] == 'x') {
                int cx = x + 1, cy = y;
                for (int i = 1; i < Math.abs(fin[0] - x); i++) {
                    if (cx == 8) res += 2;
                    else res += 1;
                    cx += 1;
                }
            }
        }
        if (field[x - 1][switchIt(y)] == opp) {
            int[]fin = goUntil(x - 1, y, -1, 0,1, 0, 'x');
            if (field[fin[0]][switchIt(fin[1])] == 'x') {
                int cx = x - 1, cy = y;
                for (int i = 1; i < Math.abs(fin[0] - x); i++) {
                    if (cx == 1) res += 2;
                    else res += 1;
                    cx -= 1;
                }
            }
        }
        if (field[x][switchIt(y + 1)] == opp) {
            int[]fin = goUntil(x, y + 1, 0, 1,0, 8, 'x');
            if (field[fin[0]][switchIt(fin[1])] == 'x') {
                int cx = x, cy = y + 1;
                for (int i = 1; i < Math.abs(fin[1] - y); i++) {
                    if (cy == 8) res += 2;
                    else res += 1;
                    cy += 1;
                }
            }
        }
        if (field[x][switchIt(y - 1)] == opp) {
            int[]fin = goUntil(x, y - 1, 0, -1,0, 1, 'x');
            if (field[fin[0]][switchIt(fin[1])] == 'x') {
                int cx = x, cy = y - 1;
                for (int i = 1; i < Math.abs(fin[1] - y); i++) {
                    if (cy == 1) res += 2;
                    else res += 1;
                    cy -= 1;
                }
            }
        }
        if (field[x + 1][switchIt(y + 1)] == opp) {
            int[]fin = goUntil(x + 1, y + 1, 1, 1,8, 8, 'x');
            if (field[fin[0]][switchIt(fin[1])] == 'x') {
                int cx = x + 1, cy = y + 1;
                for (int i = 1; i < Math.abs(fin[1] - y); i++) {
                    if (cy == 8 ||  cx == 8) res += 2;
                    else res += 1;
                    cy += 1;
                    cx += 1;
                }
            }
        }
        if (field[x - 1][switchIt(y + 1)] == opp) {
            int[]fin = goUntil(x - 1, y + 1, -1, 1,1, 8, 'x');
            if (field[fin[0]][switchIt(fin[1])] == 'x') {
                int cx = x - 1, cy = y + 1;
                for (int i = 1; i < Math.abs(fin[1] - y); i++) {
                    if (cy == 8 ||  cx == 1) res += 2;
                    else res += 1;
                    cy += 1;
                    cx -= 1;
                }
            }
        }
        if (field[x + 1][switchIt(y - 1)] == opp) {
            int[]fin = goUntil(x + 1, y - 1, 1, -1,8, 1, 'x');
            if (field[fin[0]][switchIt(fin[1])] == 'x') {
                int cx = x + 1, cy = y - 1;
                for (int i = 1; i < Math.abs(fin[1] - y); i++) {
                    if (cy == 1 ||  cx == 8) res += 2;
                    else res += 1;
                    cy -= 1;
                    cx += 1;
                }
            }
        }
        if (field[x - 1][switchIt(y - 1)] == opp) {
            int[]fin = goUntil(x - 1, y - 1, -1, -1,1, 1, 'x');
            if (field[fin[0]][switchIt(fin[1])] == 'x') {
                int cx = x - 1, cy = y - 1;
                for (int i = 1; i < Math.abs(fin[1] - y); i++) {
                    if (cy == 1 ||  cx == 1) res += 2;
                    else res += 1;
                    cy -= 1;
                    cx -= 1;
                }
            }
        }

        return res;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 18; j++) {
                res.append(field[i][j]);
            }
            res.append('\n');
        }
        return res.toString();
    }
}
