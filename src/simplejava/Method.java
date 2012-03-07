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
public class Method extends AbstractBlock {

    public static Pattern pattern = Pattern.compile(RegexUtil.method(), Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE | Pattern.UNICODE_CASE | Pattern.CANON_EQ);
    public String text;
    public int bracketStart;

    public static List<Method> extractMethods(String text) {
        List<Block> blocks = Block.findBlocks(text);
        List<Method> allMethods = new ArrayList<Method>();
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String clsText = "";
            int open = 0;
            int start = -1;
            int end = -1;
            for (int j = matcher.start(2); j < text.length() && !(start != -1 && end != -1); j++) {
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
            Method method = new Method(clsText, matcher.start(2), start, end);
            //Filtrar Metodos de classe
            if (Block.topBlock(method, blocks).getLevel().intValue() == -1) {
                allMethods.add(method);
            }
        }

        return allMethods;

    }

    public Method(String methodText, int start, int bracketStart, int bracketEnd) {
        this.text = methodText;
        this.start = start;
        this.bracketStart = bracketStart;
        this.end = bracketEnd;
    }

    public String getStaticText() {
        if (isStatic()) {
            return text;
        } 
        return "static " + text;
    }
    
    public String getName() {
        Matcher matcher = pattern.matcher(' '+text);
        if (matcher.find()) {
            return matcher.group(11);
        } 
        return null;
    }
    
    public String getParams() {
        Matcher matcher = pattern.matcher(' '+text);
        if (matcher.find()) {
            return matcher.group(12);
        } 
        return null;
    }
    
    public boolean isStatic() {
        Matcher matcher = pattern.matcher(' '+text);
        return (matcher.find() && matcher.group(3).contains("static"));
    }
    
    public boolean isMain() {
        return (this.isStatic() && this.getName().equals("main") && 
                this.getParams().matches(RegexUtil.MAIN_PARAMS));
    }
}
