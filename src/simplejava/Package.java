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
public class Package extends Block {
    
    public static Pattern pattern = Pattern.compile(RegexUtil._package(), Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE | Pattern.UNICODE_CASE | Pattern.CANON_EQ);
    
    private Code code;
    
    public static List<Package> extractPackages(Code code) throws SyntaxException {
            List<Package> result = new ArrayList<Package>();
            Matcher matcher = pattern.matcher(code.text);
            boolean found = false;
            while (matcher.find()) { 
                if (found) {
                    throw new SyntaxException("You must define just one package", code, matcher.start(2));
                }
                result.add(new Package(code, matcher.start(2), matcher.end(2)));
                found = true;
            }
            return result;
    }

    public Package(Code code, int start, int end) {
        super(start, end);
        this.code = code;
    }

    public String getText() {
        return code.text.substring(this.getStart(), this.getEnd());
    }
    
}
