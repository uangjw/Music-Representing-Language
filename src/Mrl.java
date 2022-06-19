import java.io.*;


public class Mrl {

    public static void main(String[] args) {
        try{
            Scanner s = new Scanner(new FileReader(args[0]));
            Parser p = new Parser(s);
            p.parse();
        }
        catch(Exception e) {
            System.out.println("Exception caught: " + e.toString());
            System.out.println("Stack trace:");
            e.printStackTrace();
        }
    }
}
