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
    private String text;
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
        Matcher matcher = pattern.matcher(getText());
        if (matcher.find()) {
            return matcher.group(8);
        } 
        return null;
    }

    public boolean isPublic() {
        Matcher matcher = pattern.matcher(getText());
        return (matcher.find() && matcher.group(3).contains("public"));
    }
    
    public boolean isInterface() {
        Matcher matcher = pattern.matcher(getText());
        return (matcher.find() && matcher.group(6).contains("interface"));
    }
    
    public boolean isEnum() {
        Matcher matcher = pattern.matcher(getText());
        return (matcher.find() && matcher.group(6).contains("enum"));
    }

    public void addMain(String mainText) throws SyntaxException {
        Method main = getMain();
        if (main != null){
            throw new SyntaxException("Main already exists", this.bracketStart+main.start);
        }
        addStaticMethod(Method.createMain(mainText));
    }

    public void addStaticMethod(Method method) throws SyntaxException {
        if (isEnum() && getEnumDefinition().isEmpty()) {
            throw new SyntaxException("Enum enumeration is not defined", this.start);
        }
        String before = getText().substring(0, getStartBlockPosition());
        String after = getText().substring(getStartBlockPosition());
        setText(before + "\n\n    " + 
                method.getStaticText().replaceAll("\n", "\n    ") + 
                ((after.charAt(0) == '\n') ? "" : "\n") + 
                after);
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
            methods = Method.extractMethods(getTextBetweenBrackets());
        }
        return methods;
    }
    
    public String getTextBetweenBrackets() {
        return text.substring(bracketStart+1-start, end-1-start);
    }
    
    public int getStartBlockPosition() {
        int result = bracketStart - start + 1;
        if (isEnum()) {
            result += getEnumDefinition().length() + 1;
        }
        return result;
    }
    
    public String getEnumDefinition() {
        List<Block> blocks = Block.findBlocks(text);
        String inner = getTextBetweenBrackets();
        int index = -1;
        do {
            index = inner.indexOf(";", index+1);
            if (index != -1 && Block.topBlock(new CharacterBlock(index), blocks).getLevel().intValue() == 0) {
                return inner.substring(0, index);
            }
        } while (index != -1);
        return "";    
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
