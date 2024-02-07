
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

        System.out.println("\n1 - Print matrix;\n2 - Print index;\n3 - Show matrix in file;\n4 - Show index in file;\n5 - Show time for matrix and time for index;\n6 - Search matrix;\n7 - Search index;\n-1 - Exit\n");
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
                    m.openMatrixTXT("src/results/matrix.txt");
                    break;
                case 4:
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
                    System.out.printf("Term " + input + " is present in doc(s)? : ");
                    Matrix.print(res);
                    break;
                case 7:
                    System.out.println("Enter:");
                    in.nextLine();
                    input = in.nextLine();
                    ArrayList<Integer> res_list = ind.search(input);
                    System.out.printf("Term " + input + " present in doc(s): " + res_list + "\n");
                    break;
                default:
                    System.out.println("No such option for this program.");
            }
            System.out.println("\n1 - Print matrix;\n2 - Print index;\n3 - Show matrix in file;\n4 - Show index in file;\n5 - Show time for matrix and time for index;\n6 - Search matrix;\n7 - Search index;\n-1 - Exit\n");

            i = in.nextInt();
        }


    }
}
