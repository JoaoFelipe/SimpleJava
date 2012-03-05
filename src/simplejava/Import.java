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
public class Import extends AbstractBlock {
    
    public static Pattern pattern = Pattern.compile("import ([A-Z0-9.-]+);(\n?)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE | Pattern.UNICODE_CASE | Pattern.CANON_EQ);
    
    public String text;
    
    public static List<Import> extractImports(String text) {
            List<Import> result = new ArrayList<Import>();
            Matcher matcher = pattern.matcher(text);
            while (matcher.find()) {               
                result.add(new Import(matcher.group(), matcher.start(), matcher.end()));
            }
            return result;

    }

    public Import(String clsText, int start, int end) {
        this.text = clsText;
        this.start = start;
        this.end = end;
    }
    
}
