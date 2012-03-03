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
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public static void apply(String inputFile, String outputFile) {
        File in = new File(inputFile);
        File out = new File(outputFile);
        BufferedWriter writer = null;
        try {
            Scanner scanner = new Scanner(in);
            writer = new BufferedWriter(new FileWriter(out));
            writer.write("public class "+className(inputFile)+" {\n\n");
            writer.write("    public static void main(String[] args) {\n");
            while (scanner.hasNextLine()) {
                writer.write("        "+scanner.nextLine()+"\n");
            }
            writer.write("    }\n");
            writer.write("}");
            
        } catch (IOException ex) {
            Logger.getLogger(SimpleJava.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(SimpleJava.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static String className(String fileName) {
        return fileName.substring(fileName.lastIndexOf(File.separator)+1, fileName.lastIndexOf('.'));
    }
}
