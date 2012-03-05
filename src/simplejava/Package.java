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
public class Package extends AbstractBlock {
    
    public static Pattern pattern = Pattern.compile("package ([A-Z0-9.-]+);(\n?)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE | Pattern.UNICODE_CASE | Pattern.CANON_EQ);
    
    public String text;
    
    public static List<Package> extractPackages(String text) throws SyntaxException {
            List<Package> result = new ArrayList<Package>();
            Matcher matcher = pattern.matcher(text);
            boolean found = false;
            while (matcher.find()) { 
                if (found) {
                    throw new SyntaxException("You must define just one package", text, matcher.start());
                }
                result.add(new Package(matcher.group(), matcher.start(), matcher.end()));
                found = true;
            }
            return result;

    }

    public Package(String clsText, int start, int end) {
        this.text = clsText;
        this.start = start;
        this.end = end;
    }
    
}