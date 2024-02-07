import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        String startFilePath = "C:\\Users\\armad\\OneDrive\\Desktop\\IntelliJ IDEA Community Edition 2021.1.1\\IdeaProjects\\InfoSearch_Pract2_BoolSearch\\src\\res";

        long timeM =  System.nanoTime();
        Matrix m = new Matrix(startFilePath);
        long endTimeM = System.nanoTime();
        long elapsedTimeM = endTimeM - timeM; //final time it took

        long timeI =  System.nanoTime();
        Index ind = new Index(startFilePath);
        long endTimeI = System.nanoTime();
        long elapsedTimeI = endTimeI - timeI;//final time it took

        System.out.println("\n1 - Print matrix;\n2 - Print index;\n3.Show matrix in file;\n4 - Show index in file;\n5 - Show time for matrix and time for index;\n6 - Search matrix;\n7 - Search index;\n-1 - Exit\n");
        int i= in.nextInt();

        while(i!=-1) {
            switch (i) {
                case 1:
                    System.out.println(m.matrixStats());
                    break;
                case 2:
                    System.out.println(ind.indexStats());
                    break;
                case 3:
                    System.out.println("MATRIX FILE OPENING IN PROGRESS");
                    break;
                case 4:
                   // System.out.println("INDEX FILE OPENING IN PROGRESS");
                    ind.openIndexTXT("src/results/index.txt");
                    break;
                case 5:
                    System.out.println("Time to build matrix: " + elapsedTimeM + " ns, or " + elapsedTimeM / 1_000_000.0 + " ms, or " + elapsedTimeM / 1_000_000_000.0 + " s");
                    System.out.println("Time to build index: " + elapsedTimeI + " ns, or " + elapsedTimeI / 1_000_000.0 + " ms, or " + elapsedTimeI / 1_000_000_000.0 + " s");
                    break;
                case 6:
                    System.out.println("Enter:");
                    in.nextLine();
                    String input = in.nextLine();
                    byte[] res = m.search(input);
                    System.out.printf(input+": ");
                    print(res);
                    break;
                case 7:
                    System.out.println("Enter:");
                    in.nextLine();
                    input = in.nextLine();
                    ArrayList<Integer> res_list = ind.search(input);
                    System.out.printf(input+": " + res_list + "\n");
                    break;
                default:
                    System.out.println("Wrong format");
            }
            System.out.println("\n1 - Print matrix;\n2 - Print index;\n3.Show matrix in file;\n4 - Show index in file;\n5 - Show time for matrix and time for index;\n6 - Search matrix;\n7 - Search index;\n-1 - Exit\n");

            i = in.nextInt();
        }


    }
    //print byte array
    public static void print(byte[] res){
        for(int i=0;i<res.length;i++)
            System.out.print(res[i]+" ");
        System.out.println();
    }
}
