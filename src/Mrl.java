import java.io.*;
import exceptions.*;
import write_midi.*;

public class Mrl {
    
    // public static void testScannerStr(String file) throws Exception{
    //     ScannerStr s = new ScannerStr(new FileReader(file));
    //     System.out.println("Now compiling '" + file + "' ...");
    //     String kindOfWord;
	// 	while (true) {
	// 		try {
	// 			kindOfWord = s.yylex();
	// 			System.out.println(kindOfWord + " : " + s.yytext());
	// 		} catch (LexicalException e) {
	// 			System.out.print(s.yytext() + "处出现错误：");
	// 			System.out.println(e);
	// 			break;
	// 		}
	// 		if (kindOfWord.equals("EOF")) {
	// 			System.out.println("无词法错误");
	// 			break;
	// 		}
	// 	}
    // }

    public static void testMidiWriter() throws Exception{
        MidiWriter mw = new MidiWriter(120, 3, 0, 1);
        mw.addNote(new Note("C", 3, false), 1, 1);
        mw.addNote(new Note("C", 3, false), 10, 1);
        mw.writeMidiFile("../result/tmp.mid");
    }

    public static void main(String[] args) {
        try{
            //testMidiWriter();
            Scanner s = new Scanner(new FileReader(args[0]));
            Parser p = new Parser(s);
            p.parse();
            //testScannerStr(args[0]);
        }
        catch(Exception e) {
            System.out.println("Exception caught: " + e.toString());
            System.out.println("Stack trace:");
            e.printStackTrace();
        }
    }
}
