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

    public static Pattern pattern = Pattern.compile(RegexUtil._class(), Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE | Pattern.UNICODE_CASE | Pattern.CANON_EQ);
    public String text;
    public int bracketStart;
    
    private List<Method> methods = null;

    public static List<Classe> extractClasses(String text) {
        List<Block> blocks = Block.findBlocks(text);
        List<Classe> allClasses = new ArrayList<Classe>();
        Matcher matcher = pattern.matcher(text);
        
        while (matcher.find()) {
            int start = text.indexOf("{", matcher.start(2));
            int end = Block.blockThatStartsIn(start, blocks).end;
            String methodText = text.substring(matcher.start(2), end+1);
            
            Classe classe = new Classe(methodText, matcher.start(2), start, end);
            //Filtrar InnerClasses e LocalClasses
            if (Block.topBlock(classe, blocks).getLevel().intValue() == -1) {
                allClasses.add(classe);
            }
        }
        return allClasses;
    }

    public Classe(String clsText, int start, int bracketStart, int bracketEnd) {
        this.text = clsText;
        this.start = start;
        this.bracketStart = bracketStart;
        this.end = bracketEnd;
    }

    public Classe(String name) {
        this.text = "public class " + name + " {\n\n}";
        this.start = 1;
        this.bracketStart = 15 + name.length();
        this.end = text.length() - 1;
    }

    public String getName() {
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(7);
        } 
        return null;
    }

    public boolean isPublic() {
        Matcher matcher = pattern.matcher(text);
        return (matcher.find() && matcher.group(3).contains("public"));
    }

    public void addMain(String mainText) throws SyntaxException {
        Method main = getMain();
        if (main != null){
            throw new SyntaxException("Main already exists", this.bracketStart+main.start);
        }
        String before = text.substring(0, bracketStart - start + 1);
        String after = text.substring(bracketStart - start + 1);
        text = before + "\n\n"
                + "    public static void main(String[] args) {\n"
                + "        " + mainText.replaceAll("\n", "\n        ") + '\n'
                + "    }" + ((after.charAt(0) == '\n') ? "" : "\n")
                + after;
    }

    public void addStaticMethod(Method method) {
        String before = text.substring(0, bracketStart - start + 1);
        String after = text.substring(bracketStart - start + 1);
        text = before + "\n\n    " + method.getStaticText().replaceAll("\n", "\n    ") + after;
    }

    public Method getMain() {
        for(Method method: getMethods()){
            if (method.isMain()) {
                return method;
            }
        }
        return null;
    }
    
    
    public List<Method> getMethods() {
        if (methods == null) {
            methods = Method.extractMethods(text.substring(bracketStart+1-start, end-1-start));
        }
        return methods;
    }
}
