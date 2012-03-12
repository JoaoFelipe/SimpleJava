/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simplejava;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author Joao
 */
public class SimpleJava {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }

    public static void apply(String inputFile, String outputFile) throws SyntaxException {
        String text = "";
        Code code = new Code(text);
        try {
            File in = new File(inputFile);
            File out = new File(outputFile);
            BufferedWriter writer = null;
            try {
                Scanner scanner = new Scanner(in);
                writer = new BufferedWriter(new FileWriter(out));
                
                code = Code.readFile(scanner);
                code.extractBlocks();
                System.out.println(code.text+"\n---------------\n");
                int current = 0;
                current = code.movePackage(current);
                System.out.println(code.text+"\n---------------\n");
                code.removeInners();
                current = code.moveImports(current);
                System.out.println(code.text+"\n---------------\n");
                code.removeInners();
                current = code.moveClasses(current, className(inputFile));
                System.out.println(code.text+"\n---------------\n");
                code.removeInners();
                
                Classe mainClass = code.mainClass;
                
//                
                System.out.println(code.text+"\n---------------\n");
                for (Method method : code.methods) {
                    int pos = 0;
                    code.add(mainClass.getStartBlockPosition(), "\n\n", true);
                    pos += 2;
                    System.out.println(code.text+"\n---------------\n");
                    code.move(method, mainClass.getStartBlockPosition() + pos);
                    assert method.length() == method.text.length();
                    pos += method.text.length();
                    System.out.println(code.text+"\n---------------\n");
                    if (!method.isStatic()) {
                        if (mainClass.getStartBlockPosition() + 2 + method.staticPosition() <= method.getStart()) {
                            code.add(mainClass.getStartBlockPosition() + 2 + method.staticPosition(), "static ", true);
                            method.setStart(method.getStart()-7);
                        } else {
                            code.add(mainClass.getStartBlockPosition() + 2 + method.staticPosition(), "static ", true);
                        }
//                        method.setStart(method.getStart()-7);
                        pos += 7;
                        System.out.println(code.text+"\n---------------\n");
                    }
//                    code.text.substring(method.getStart(), method.getEnd())
                    pos += code.addIdent(method);
                    System.out.println(code.text+"\n---------------\n");
                    code.text.substring(current+pos);
                    current += pos;
                }
                

                Method main = mainClass.getMain();
                if (main != null){
                    throw new SyntaxException("Main already exists", mainClass.getStart()+mainClass.definitionLength+main.getStart());
                }
                
                int pos = 0;
                int start = mainClass.getStartBlockPosition();
                code.add(start, "\n\n", true);
                System.out.println(code.text+"\n---------------\n");
                pos += 2;
                code.add(start+pos, "public static void main(String[] args) {\n", true); 
                System.out.println(code.text+"\n---------------\n");
                pos += 41;
                Pattern pattern = Pattern.compile(RegexUtil.BLANK+"*$", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.UNICODE_CASE | Pattern.CANON_EQ);
                Matcher matcher = pattern.matcher(code.text);
                int i = code.text.length();
                if (matcher.find()) {
                    i = matcher.start();
                }
                System.out.println("code.text.length() = " + code.text.length());
                System.out.println("i = " + i);
                code.text.substring(current+pos,i);
                Block block = new Block(current+pos, i);
                code.move(block, start+pos);
                System.out.println(code.text+"\n---------------\n");
                pos += block.length();
                pos += code.addIdent(block);
                System.out.println(code.text+"\n---------------\n");
                code.add(start+pos, "\n}", true);
                pos += 2;
                block = new Block(start+2, start+pos);
//                code.text.substring(block.getStart(), block.getEnd());
                code.addIdent(block);
                
//                main.setStart(mainClass.getStartBlockPosition()+2);
//                main.setEnd(mainClass.getStartBlockPosition()+2+main.getStaticText().length());
//                current += main.getStaticText().length();
//                current += code.addIdent(main);
                System.out.println(code.text+"\n>--------------\n");
//                
////                assert mainClass.getText().indexOf("public static void main(String[] args) {") != -1;
//                main.setStart(mainClass.getText().indexOf("public static void main(String[] args) {")+mainClass.getStart());
//                main.setEnd(main.getStart() + main.text.length());
//                current += mainClass.length();
//                
////                Block main = new Block()
////                code.add(main.getStart(), "<=======>");
////                System.out.println(code.text+"\n---------------\n");
////              
//                code.move(new Block(current, code.text.length()), main.getStart()+main.bracket+1);
//                mainText = code.text.substring(current);
                
                writer.write(code.remakeComments());
//                System.out.println("\n\n----------------\n");
//                System.out.println(code.text);
//                System.out.println("\n----------------\n\n");

                
//                if (main == null) {
//                    main = new Classe(name);
//                    main.addMain(mainText);
//                    for (Method method : code.methods) {
//                        main.addStaticMethod(method);
//                    }
//                    writer.write(main.getText());
//                }
//                
//                main.addMain(mainText);
//                for (Method method : code.methods) {
//                    main.addStaticMethod(method);
//                }

            } catch (IOException ex) {
                Logger.getLogger(SimpleJava.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    writer.close();
                } catch (IOException ex) {
                    Logger.getLogger(SimpleJava.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SyntaxException s) {
            s.setCode(code);
            s.setFile(inputFile.substring(inputFile.lastIndexOf(File.separator) + 1));
            throw s;
        }
    }

    public static String className(String fileName) {
        return fileName.substring(fileName.lastIndexOf(File.separator) + 1, fileName.lastIndexOf('.'));
    }
}
