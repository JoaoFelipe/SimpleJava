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
public class Method extends Block {

    public static Pattern pattern = Pattern.compile(RegexUtil._method(), Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE | Pattern.UNICODE_CASE | Pattern.CANON_EQ);
    public String text;
//    public int bracketStart;
    public int bracket;

    public static List<Method> extractMethods(String text) {
        List<Scope> blocks = Scope.findBlocks(text);
        List<Method> allMethods = new ArrayList<Method>();
        Matcher matcher = pattern.matcher(text);
        
        while (matcher.find()) {
            int start = text.indexOf("{", matcher.start(2));
            int end = Scope.blockThatStartsIn(start, blocks).getEnd();
            String clsText = text.substring(matcher.start(2), end);
            
            Method method = new Method(clsText, matcher.start(2), start, end);
            //Filtrar Metodos de classe
            if (Scope.topBlock(method, blocks).getLevel().intValue() == -1) {
                allMethods.add(method);
            }
        }
        return allMethods;
    }
    
    public static Method createMain(String mainBody) {
        return new Method(mainBody);
    }

    public Method(String methodText, int start, int bracketStart, int end) {
        super(start, end);
        this.text = methodText;
        this.bracket = bracketStart - start;
    }
    
    private Method(String mainBody) {
        super(0, ("public static void main(String[] args) {" + '\n' +
            "    "+mainBody.replaceAll("\n", "\n    ") + '\n' +
            "}").length());
        this.text = 
            "public static void main(String[] args) {" + '\n' +
            "    "+mainBody.replaceAll("\n", "\n    ") + '\n' +
            "}";
        assert this.getEnd() == text.length();
        this.bracket = "public static void main(String[] args) {".length();
    }

    public String getStaticText() {
        if (isStatic()) {
            return text;
        } 
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return text.replace(matcher.group(6), "static "+matcher.group(6));
        } 
        return ("static " + text);
    }
    
    public int staticPosition() {
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.start(6);
        } 
        return -1;
    }
    
    public String getName() {
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(15);
        } 
        return null;
    }
    
    public String getParams() {
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(16);
        } 
        return null;
    }
    
    public boolean isStatic() {
        Matcher matcher = pattern.matcher(text);
        return (matcher.find() && matcher.group(7).contains("static"));
    }
    
    public boolean isMain() {
        return (this.isStatic() && this.getName().equals("main") && 
                this.getParams().matches(RegexUtil.MAIN_PARAMS));
    }
}
