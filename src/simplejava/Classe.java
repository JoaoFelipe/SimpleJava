/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simplejava;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Joao
 */
public class Classe extends AbstractBlock {
    
    public static String clsModifiers = "((abstract|final|private|public|protected|strictfp|static) )?";
    public static Pattern pattern = Pattern.compile(clsModifiers + clsModifiers + clsModifiers + clsModifiers + clsModifiers + "class", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE | Pattern.UNICODE_CASE | Pattern.CANON_EQ);
    public static Pattern namePattern = Pattern.compile("class ([A-Z0-9.-]+) ", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE | Pattern.UNICODE_CASE | Pattern.CANON_EQ);
    public static Pattern publicPattern = Pattern.compile("public (.*)class", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE | Pattern.UNICODE_CASE | Pattern.CANON_EQ);
    
    public String text;
    public int bracketStart;
    
    
    public static List<Classe> extractClasses(String text) {
            List<Classe> allClasses = new ArrayList<Classe>();
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {
                String clsText = "";
                int open = 0;
                int start = -1;
                int end = -1;
                for (int j = matcher.start(); j < text.length() && !(start != -1 && end != -1); j++) {
                    char c = text.charAt(j);
                    if (c == '{') {
                        if (open == 0) {
                            start = j;
                        }
                        open++;
                    }
                    if (c == '}') {
                        open--;
                        if (open == 0) {
                            end = j;
                        }
                        
                    }
                    clsText += c;
                }
                allClasses.add(new Classe(clsText, matcher.start(), start, end));

            }
            //Filtrar InnerClasses
            List<Classe> result = new ArrayList<Classe>();
            for (Classe classe : allClasses) {
                if (!classe.isInnerInList(result)) {
                    result.add(classe);
                }
            }
            return result;

    }

    public Classe(String clsText, int start, int bracketStart, int bracketEnd) {
        this.text = clsText;
        this.start = start;
        this.bracketStart = bracketStart;
        this.end = bracketEnd;
    }
    
    public Classe(String name) {
        this.text = "public class "+name+" {\n\n}" ;
        this.start = 1;
        this.bracketStart = 15 + name.length();
        this.end = text.length()-1;
    }
    
    public String getName() {
        Matcher matcher = namePattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
                
    }
    
    public boolean isPublic() {
        Matcher matcher = publicPattern.matcher(text);
        return matcher.find();
    }
    
    
    public void addMain(String mainText) {
        String before = text.substring(0, bracketStart - start + 1);
        String after =  text.substring(bracketStart - start + 1);
        text = before + "\n\n" +
                "    public static void main(String[] args) {\n" +
                "        "+mainText.replaceAll("\n", "\n        ") + '\n' +
                "    }" + ((after.charAt(0) == '\n')? "": "\n") +  
               after;
    }
    
    public boolean isInnerIn(Classe classe){
        return this.start > classe.start && this.end < classe.end;
    }
    
    public boolean isInnerInList(List<Classe> classes){
        for (Classe classe : classes) {
            if (this.isInnerIn(classe)) {
                return true;
            }
        }
        return false;
    }
    
}
