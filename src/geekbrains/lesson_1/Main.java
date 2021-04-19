package geekbrains.lesson_1;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static int SIZE;
    public static int DOTS_TO_WIN;
    public static final char DOT_EMPTY = '*';
    public static final char DOT_X = 'X';
    public static final char DOT_O = 'O';
    public static char[][] map;

    public static Scanner scanner = new Scanner((System.in));
    public static Random random = new Random();

    public static void main(String[] args) {
        SIZE = SizeOfBoard();
        DOTS_TO_WIN = getDotsToWin(SIZE);
        int[] temp, temp2;
        initMap();
        printMap();

        while (true) {
            temp = humanTurn();
            printMap();
            if(checkWin(DOT_X, temp)) {
                System.out.println("Вы победили");
                break;
            }
            if(isMapFull()) {
                System.out.println("Ничья");
                break;
            }
            temp2 = predictTurn(temp);
            temp = aiTurn(temp2);
            printMap();
            if(checkWin(DOT_O, temp)) {
                System.out.println("победил ИИ");
                break;

            }
            if(isMapFull()) {
                System.out.println("Ничья");
                break;
            }
        }
        System.out.println("Игра окончена");

    }

    public static void initMap() {
        map =  new char[SIZE][SIZE];

        for(int i=0; i<SIZE; i++) {
            for(int j=0; j<SIZE; j++) {
                map[i][j]=DOT_EMPTY;
            }
        }
    }
    public static void printMap() {
        for (int i=0; i<=SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for(int i=0; i<SIZE; i++) {
            System.out.print((i+1) + " ");
            for(int j=0; j<SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    public static int SizeOfBoard() {
        int size=0;
        while(true) {
            Scanner temp = new Scanner(System.in);
            System.out.println("Введите размерность игрвоого поля: ");
            if(temp.hasNextInt()) {
                size= temp.nextInt();
                break;
            }
            else {
                System.out.println("Повторите ввод: ");
            }


        }
        System.out.println(size);
        return size;
    }
    public static int getDotsToWin(int size) {
        return size;
    }
   // public static boolean checkWin(char symb) {

       // if (map[0][0] == symb && map[0][1] == symb && map[0][2]==symb) {return true;}
       // if (map[1][0] == symb && map[1][1] == symb && map[1][2]==symb) {return true;}
      //  if (map[2][0] == symb && map[2][1] == symb && map[2][2]==symb) {return true;}
       // if (map[0][0] == symb && map[1][0] == symb && map[2][0]==symb) {return true;}
//        if (map[0][2] == symb && map[1][2] == symb && map[2][2]==symb) {return true;}
     //   if (map[0][0] == symb && map[1][1] == symb && map[2][2]==symb) {return true;}
    //    if (map[0][2] == symb && map[1][1] == symb && map[2][0]==symb) {return true;}
     //   else {return false;}

   // }
    public static boolean checkWin(char symb, int[] coors ) {
        int counter = 0;
        for (int i = 0; i < SIZE; i++) {
            if (map[i][i] == symb) {
                counter++;
            }
        }
        System.out.println(counter);
        if (counter == SIZE)
            return true;

        counter = 0;
        for (int i = 0; i < SIZE; i++) {
            if (map[i][SIZE - i - 1] == symb) {
                counter++;
            }
        }
        System.out.println(counter);
        if (counter == SIZE)
            return true;

        counter = 0;
        int index = coors[0];
        for (int i = 0; i < SIZE; i++) {
            if (map[index][i] == symb) {
                counter++;
            }
        }
        System.out.println(counter);
        if (counter == SIZE)
            return true;


        counter=0;
        index = coors[1];
        for(int i = 0; i<SIZE; i++) {
            if(map[i][index] == symb) {
                counter++;
            }
        }
        System.out.println(counter);
        if(counter == SIZE) return true;
        return false;


    }
    public static boolean isMapFull() {
        for(int i=0; i<SIZE; i++) {
            for(int j=0; j<SIZE; j++) {
                if(map[i][j] == DOT_EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean isCellValid(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            return false;
        }
        if (map[y][x] == DOT_EMPTY) {
            return true;
        }
        return false;
    }
    public static int[] humanTurn() {
        int x, y;
        int[] coors = {0, 0};
        do {
            System.out.println("Введите координаты в формате X Y");
            x =scanner.nextInt() - 1;
            y=scanner.nextInt() - 1;
        } while (!isCellValid(x, y));
        map[y][x] = DOT_X;
        coors[0]=y;
        coors[1]=x;
        return coors;
    }
    public static int[] aiTurn(int[] playerLastTurn) {
        int x, y;
        int[] coors = {0, 0};
        if(playerLastTurn[0]==-1) {
            do {
                x = random.nextInt(SIZE);
                y = random.nextInt(SIZE);
            } while (!isCellValid(x, y));
            System.out.println("Компьютер сделал ход на" + (x + 1) + " " + (y + 1));
            map[y][x] = DOT_O;
            coors[0]=y;
            coors[1]=x;
            return coors;
        } else {
            x=playerLastTurn[1];
            y=playerLastTurn[0];
            coors[0]=x;
            coors[1]=y;
            map[y][x]=DOT_O;
            System.out.println("Компьютер сделал ход на" + (x + 1) + " " + (y + 1));
            return coors;
        }
    }
    public static int[] predictTurn(int[] playerLastTurn) {
        int counter =0;
        int[] temp;
        for(int i=0; i<SIZE; i++) {
            if(map[i][i] !=DOT_X && map[i][i] !=DOT_O) {
                counter++;
            }

        }
        if(counter == SIZE-1) {
            for( int i =0; i< SIZE; i++) {
                if(map[i][i] !=DOT_X && map[i][i] != DOT_O) {
                    temp = new int[]{i, i};
                    return temp;
                }
            }
        }
        counter=0;
        for(int i =0; i<SIZE; i++) {
            if(map[i][SIZE-i-1]== DOT_X && map[i][SIZE-i-1] != DOT_X) {
                counter++;
            }
        }
        if(counter == SIZE) {
            for(int i=0; i<SIZE; i++) {
                if(map[i][SIZE-i-1]!= DOT_X && map[i][SIZE-i-1] != DOT_O) {
                    temp = new int[] {i, SIZE-i-1};
                    return temp;
                }
            }
        }
        counter=0;
        int index = playerLastTurn[0];
        for(int i=0; i<SIZE; i++) {
            if(map[i][index]==DOT_X && map[i][index] != DOT_O) {
                counter++;
            }
        }
        if(counter==SIZE-1) {
            for(int i=0; i<SIZE;  i++) {
                if(map[i][index] !=DOT_X && map[i][index] != DOT_O) {
                    temp = new int[]{i, index};
                    return temp;
                }
            }
        }
        temp=new int[]{-1,1};
        return temp;

    }



}
