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
public class Classe extends Block {

    public static Pattern pattern = Pattern.compile(RegexUtil._class(), Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE | Pattern.UNICODE_CASE | Pattern.CANON_EQ);
    private String text;
//    public int bracketStart;
    public int definitionLength;
    
    private Code code;

    public static List<Classe> extractClasses(Code code) {
        List<Scope> blocks = Scope.findBlocks(code.text);
        List<Classe> allClasses = new ArrayList<Classe>();
        Matcher matcher = pattern.matcher(code.text);
        
        while (matcher.find()) {
            int start = code.text.indexOf("{", matcher.start(2));
            int end = Scope.blockThatStartsIn(start, blocks).getEnd();
            Classe classe = new Classe(code, matcher.start(2), start, end);
            //Filtrar InnerClasses e LocalClasses
            if (Scope.topBlock(classe, blocks).getLevel().intValue() == -1) {
                allClasses.add(classe);
            }
        }
        return allClasses;
    }

    public Classe(Code code, int start, int bracketStart, int end) {
        super(start, end);
        this.code = code;
//        this.text = code.text.substring(getStart(), getEnd());
        this.definitionLength = bracketStart - start;
    }

    public String getName() {
        Matcher matcher = pattern.matcher(getText());
        if (matcher.find()) {
            return matcher.group(12);
        } 
        return null;
    }

    public boolean isPublic() {
        Matcher matcher = pattern.matcher(getText());
        return (matcher.find() && matcher.group(7).contains("public"));
    }
    
    public boolean isInterface() {
        Matcher matcher = pattern.matcher(getText());
        return (matcher.find() && matcher.group(10).contains("interface"));
    }
    
    public boolean isEnum() {
        Matcher matcher = pattern.matcher(getText());
        return (matcher.find() && matcher.group(10).contains("enum"));
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
        return Method.extractMethods(getTextBetweenBrackets());
    }
    
    public String getTextBetweenBrackets() {
        return getText().substring(definitionLength+1, getText().length()-1);
    }
    
    public int getStartBlockPosition() {
        int result = definitionLength+1;
        if (isEnum()) {
            result += getEnumDefinition().length() + 1;
        }
        return this.getStart()+result;
    }
    
    public String getEnumDefinition() {
        List<Scope> blocks = Scope.findBlocks(getText());
        String inner = getTextBetweenBrackets();
        int index = -1;
        do {
            index = inner.indexOf(";", index+1);
            if (index != -1 && Scope.topBlock(new CharacterBlock(index), blocks).getLevel().intValue() == 0) {
                return inner.substring(0, index);
            }
        } while (index != -1);
        return "";    
    }
    
    public String getText() {
        return code.text.substring(getStart(), getEnd());
    }


  
    
}
