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
        try {
            File in = new File(inputFile);
            File out = new File(outputFile);
            BufferedWriter writer = null;
            try {
                Scanner scanner = new Scanner(in);
                writer = new BufferedWriter(new FileWriter(out));
                
                while (scanner.hasNextLine()) {
                    text += scanner.nextLine() + '\n';
                }
                text = text.substring(0, text.length() - 1);
                
                List<Classe> classes = Classe.extractClasses(text);
                List<Import> imports = Import.extractImports(text);
                List<Package> packages = Package.extractPackages(text);
                List<Method> methods = Method.extractMethods(text);

                List<AbstractBlock> blocks = new ArrayList<AbstractBlock>();
                blocks.addAll(classes);
                blocks.addAll(imports);
                blocks.addAll(packages);
                blocks.addAll(methods);

                String mainText = AbstractBlock.removeBlocksFromText(blocks, text);


                if (!packages.isEmpty()) {
                    writer.write(packages.get(0).text);
                    writer.write("\n");
                }

                for (Import imp : imports) {
                    writer.write(imp.text);
                }
                if (!imports.isEmpty()) {
                    writer.write("\n\n");
                }


                String name = className(inputFile);
                boolean found = false;
                for (int i = 0; i < classes.size(); i++) {
                    Classe classe = classes.get(i);
                    
                    if (classe.getName().equals(name)) {
                        classe.addMain(mainText);
                        for (Method method : methods) {
                            classe.addStaticMethod(method);
                        }
                        found = true;
                    } else if (classe.isPublic()) {
                        throw new SyntaxException("Public Class should be declared in a File with it name", text, classe.start);
                    }
                    writer.write(classe.text);
                    if (i != classes.size() - 1 || !found) {
                        writer.write("\n\n");
                    }
                }
                if (!found) {
                    Classe main = new Classe(name);
                    main.addMain(mainText);
                    for (Method method : methods) {
                        main.addStaticMethod(method);
                    }
                    writer.write(main.text);
                }

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
            s.setTextIfNull(text);
            s.setFile(inputFile.substring(inputFile.lastIndexOf(File.separator) + 1));
            throw s;
        }
    }

    public static String className(String fileName) {
        return fileName.substring(fileName.lastIndexOf(File.separator) + 1, fileName.lastIndexOf('.'));
    }
}
