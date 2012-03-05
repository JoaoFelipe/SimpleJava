/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simplejava;

/**
 *
 * @author Joao
 */
public class RegexUtil {
    
    public static final String METHOD_MODIFIERS = "((abstract|final|native|private|protected|public|static|synchronized|strictfp)\\s+)*";
    public static final String FIELD_MODIFIERS = "((final|private|protected|public|static|transient|volatile)\\s+)*";
    public static final String CONSTRUCTOR_MODIFIERS = "((private|protected|public)\\s+)*";
    public static final String CLASS_MODIFIERS = "((abstract|final|private|protected|public|static|strictfp)\\s+)*";
    public static final String INTERFACE_MODIFIERS = "((abstract|final|private|protected|public|static|strictfp)\\s+)*";
    
    public static final String BLANK = "\\s";
    public static final String TYPE = "[_A-Z][\\w.]*(<[^\\{\\}\\(\\);]+?>)?(\\[\\])*"; //without next char, > can bug
    public static final String NAME = "[_A-Z]\\w*";
    public static final String ANYTHING = ".*?"; //[ _A-Z0-9.-\[\],]* //without next char, can bug
    
    public static String method() {
        return
            group(separator()) +
            group(
                METHOD_MODIFIERS +
                notSpecial(BLANK+"+") +
                group(TYPE) +
                BLANK + "+" +
                notSpecial(BLANK + "*" + "\\(") +
                group(NAME) +
                BLANK + "*" +
                "\\(" +
                group(ANYTHING) +
                "\\)" +
                BLANK + "*" +
                group(
                    "throws" +
                    BLANK + "+" +
                    group(
                        group(NAME) +
                        BLANK + "*" +
                        group(
                            "," + 
                            BLANK + "*"
                        ) + "?"
                    ) + "+"
                ) + "?" +
                "\\{"
            );
    
   }
    
        //method: +blank+"*(,"+blank+"*)?"

    
    private static String[] KEYWORDS = {
        //boolean, byte, char, double, float, int, long, short, void
        "abstract", "assert", "break", "case", "catch", "class", "const", 
        "continue", "default", "do", "else", "enum", "extends", "final", "finally",
        "for", "if", "goto", "implements", "import", "instanceof", "interface", "native",
        "new", "package", "private", "protected", "public", "return", "static", "strictfp", "super",
        "switch", "synchronized", "this", "throw", "throws", "transient", "try", "volatile", "while"
    };
    
    private static String[] LITERALS = {
        "true", "false", "null"
    };
    
    private static String[] SEPARATORS = {
        "\\(", "\\)", "\\{", "\\}", "\\[", "\\]", ";", ",", "\\."
    };
    
    private static String[] OPERATORS = {
        "=", ">", "<", "!", "~", "?", ":",
        "==", "<=", ">=", "!=", "&&", "||", "++", "--",
        "+", "-", "*", "/", "&", "|", "^", "%", "<<", ">>", ">>>",
        "+=", "-=", "*=", "/=", "&=", "|=", "^=", "%=", "<<=", "<<=", ">>>="
        
    };
    
    private static String SPECIAL_TEMP = null;
    private static String SEPARATOR_OPTIONS = null;
    
    public static String notSpecial(String next) {
        //special-prox-: (?!(else|if)prox)
        if (SPECIAL_TEMP == null) {
            StringBuilder result = new StringBuilder();
            result.append("(?!(");
            for (int i = 0; i < KEYWORDS.length; i++) {
                String string = KEYWORDS[i];
                result.append(string).append("|");
            }
            for (int i = 0; i < LITERALS.length; i++) {
                String string = LITERALS[i];
                if (i < LITERALS.length -1)
                    result.append(string).append("|");
                else
                    result.append(string).append(")");
            }
            result.append("<next>)");
            SPECIAL_TEMP = result.toString();       
        }
        return SPECIAL_TEMP.replace("<next>", next);
    }
    
    public static String separator() {
        //special-prox-: (?!(else|if)prox)
        if (SEPARATOR_OPTIONS == null) {
            StringBuilder result = new StringBuilder();
            result.append("[\\s");
            for (int i = 0; i < SEPARATORS.length; i++) {
                String string = SEPARATORS[i];
                result.append(string);
            }
            
            result.append("]");
            SEPARATOR_OPTIONS = result.toString();       
        }
        return SEPARATOR_OPTIONS;
    }
    
    public static String group(String text) {
        return "("+text+")";
    }
    
    
}
