import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Index {

    ArrayList<String> doc_names;
    HashMap<String, ArrayList<Integer>> matrix;

    //constructor
    public Index(String folder){
        matrix = new HashMap<>();
        doc_names = new ArrayList();

        File dir = new File(folder);
        File[] files = dir.listFiles();
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("Invalid folder path: " + folder);
            return;
        }

        int doc=-1;
        for (File file : files) {
            doc++;
            doc_names.add(file.getName());

            if(file.isFile()) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    addWords(doc,line);
                }
                } catch (IOException e) {
                    System.out.println("Error reading file: " + file.getName());
                    e.printStackTrace();
                }
            }
        }
            // Save to file
            this.saveToFile();
    }

    //checking and adding words
    public void addWords(int doc, String line){
        if(line.equals("")) return;
        String[] temp = line.split("[^a-zA-Z0-9_]+");
        for(int i=0;i<temp.length;i++){
            if(temp[i].matches("[a-zA-Z0-9_]+")) {
                addWord(temp[i].toLowerCase(),doc);
            }
        }
    }

    //adding words to hashmap
    public void addWord(String word,int doc){
        if(!matrix.containsKey(word)){
            ArrayList<Integer> arr = new ArrayList<>();
            arr.add(doc);
            matrix.put(word,arr);
        }
        else{
            if(!matrix.get(word).contains(doc))
                matrix.get(word).add(doc);
        }
    }

    //boolean search
    public ArrayList<Integer> search(String input) throws Exception {
        input = input.toLowerCase();
        input = input.replaceAll("\\s+","");

        char and = '&', or ='∨', not ='!';
        if(!input.matches("(("+not+")?[\\w]+((("+and+")|("+or+"))("+not+")?[\\w]+)*)"))
            throw new Exception("Incorrect format.");

        String [] words = input.split("(("+and+")|("+or+"))");
        String[] operators = input.split("[^&∨]");
        operators = check(operators);

        byte [] nots = new byte [words.length];
        for(int i=0;i < nots.length; i++){
            if(words[i].charAt(0)==not){
                words[i] = words[i].replaceAll("!","");
                nots[i]=1;
            }
        }

        ArrayList<Integer> res = copy(matrix.get(words[0]));
        if(res==null)res = new ArrayList();
        if(nots[0]==1) res = swap(res);

        for(int i =1;i<words.length;i++){
            if(res==null)res = new ArrayList();

            ArrayList<Integer> temp = copy(matrix.get(words[i]));
            if(temp==null)temp = new ArrayList();

            ArrayList<Integer> ans = new ArrayList();
            if(nots[i] ==1) temp = swap(temp);

            if(operators[i-1].equals("&")){

                for(int j : res){
                    if(temp.contains(j)) ans.add(j);
                }
                res=ans;
            }
            else if (operators[i-1].equals("∨")){

                for(int j : res){
                    ans.add(j);
                }

                for(int j : temp){
                    if(!ans.contains(j)) ans.add(j);
                }
                res=ans;
            }
        }
        return res;
    }

    //check operators
    private String [] check(String [] arr){
        ArrayList<String> temp=new ArrayList();
        int j=0;
        for(int i=0;i<arr.length;i++){
            if(arr[i].equals("&") || arr[i].equals("∨")){
                temp.add(j,arr[i]);
                j++;
            }
        }
        String [] res = new String [j];
        res = temp.toArray(res);
        return res;
    }

    //not array
    private ArrayList<Integer> swap(ArrayList<Integer> arr){
        ArrayList<Integer> result=new ArrayList();
        for(int i=0;i<doc_names.size();i++){
            if(!arr.contains(i)){
                result.add(i);
            }
        }
        return result;
    }

    private ArrayList<Integer> copy(ArrayList<Integer> arr){
        ArrayList<Integer> result=new ArrayList();
        for(int i=0;i<doc_names.size();i++){
            if(arr.contains(i)){
                result.add(i);
            }
        }
        return result;
    }

    /**PRINT OUT IN CONSOLE*/
    public String indexStats() {
        StringBuilder result = new StringBuilder("\nIndex:\n");

        ArrayList<String> res_list = new ArrayList<>(matrix.keySet());
        Collections.sort(res_list);

        for (String s : res_list) {
            result.append(s).append(" ");
            for (int i = 0; i < matrix.get(s).size(); i++) {
                int num = matrix.get(s).get(i) + 1;
                result.append(num).append(" ");
            }
            result.append("\n");
        }
        return result.toString();
    }

    /**SAVE INTO INDEX.TXT FILE*/
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/results/index.txt"))) {
            ArrayList<String> res_list = new ArrayList<>(matrix.keySet());
            Collections.sort(res_list);

            for (String s : res_list) {
                writer.write(s + " ");
                for (int i = 0; i < matrix.get(s).size(); i++) {
                    int num = matrix.get(s).get(i) + 1;
                    writer.write(num + " ");
                }
                writer.newLine();
            }

            // Close the writer so the file appears imeadiately
            writer.close();

            System.out.println("Index saved to src/results/index.txt");
        } catch (IOException e) {
            System.out.println("Error saving index to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**OPEN AN INDEX.TXT FILE*/
    public void openIndexTXT(String filePath) throws IOException {
        File file = new File(filePath);

        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            System.out.println("\nPulling up the file...");

            if (file.exists()) {
                desktop.open(file);
            } else {
                System.out.println("File not found: " + filePath + "; Please restart the program.");
            }
        } else {
            System.out.println("Desktop is not supported.");
        }
    }


}
