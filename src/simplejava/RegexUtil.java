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
    
    public static final String IMPORT_MODIFIERS = "(static\\s+)?";
    public static final String CLASS_MODIFIERS = "((abstract|final|private|protected|public|static|strictfp)\\s+)*";
    public static final String METHOD_MODIFIERS = "((abstract|final|native|private|protected|public|static|synchronized|strictfp)\\s+)*";
    public static final String FIELD_MODIFIERS = "((final|private|protected|public|static|transient|volatile)\\s+)*";
    public static final String CONSTRUCTOR_MODIFIERS = "((private|protected|public)\\s+)*";
    public static final String INTERFACE_MODIFIERS = "((abstract|final|private|protected|public|static|strictfp)\\s+)*";
    
    public static final String BLANK = "\\s";
    public static final String ANYTHING = ".*?"; //[ _A-Z0-9.-\[\],]* //without next char, can bug
    public static final String PACKAGE = "[_A-Z][\\w.]*";
    public static final String IMPORT = "[_A-Z][\\w.*]*";
    public static final String TYPE = "[_A-Z][\\w.]*(<[^\\{\\}\\(\\);]+?>)?"; //without next char, > can bug
    public static final String NAME = "[_A-Z]\\w*";
    public static final String ARRAY = "(\\s*\\[\\s*\\])*";
    public static final String INLINE_COMMENT = "//.*?\\n";
    public static final String MULTILINE_COMMENT = "/\\*.*?\\*/";
    public static final String JAVADOC = "/\\*\\*.*?\\*/";
    
    public static final String MAIN_PARAMS = "String\\s*((\\.\\.\\.\\s*args)|(\\[\\s*\\]\\s*args)|(args\\s*\\[\\s*\\]))";

    public static String _comments() {
        return
            group(
                group(INLINE_COMMENT) + 
                "|" +
                group(MULTILINE_COMMENT) + 
                "|" +
                group(JAVADOC)
            );
    }
    
    public static String _annotation() {
        return
            group(                                  // 1 : all annotations
                group(                              // 2
                    group(                          // 3
                        "@" +
                        BLANK + "*" +
                        PACKAGE                     // Name Rule seems to be the same
                    ) +
                    group(                          // 4
                        BLANK + "*" +
                        "\\(" + ANYTHING + "\\)"
                    ) + "?" +
                    BLANK + "+"
                ) + "*"
            );
    }
    
    public static String _package() {
        return
            group(separator()) +                    // 1
            group(                                  // 2 : signature 
                "package" +
                BLANK + "+" +
                group(PACKAGE) +                    // 3 : name
                BLANK + "*" +
                ";" + 
                group(BLANK + "?")                  // 4
            );
    }
    
    public static String _import() {
        return
            group(separator()) +                    // 1
            group(                                  // 2 : signature 
                "import" +
                BLANK + "+" +
                group(IMPORT_MODIFIERS) +           // 3
                group(IMPORT) +                     // 4 : name
                BLANK + "*" +
                ";" + 
                group(BLANK + "?")                  // 5
            );
    }
    
    public static String _class() {
        return
            group(separator()) +                    // 1
            _annotation() +                         // 2(3,4,5)
            group(                                  // 6 : signature
                group(CLASS_MODIFIERS) +            // 7(8,9) : modifiers
                group(                              // 10
                    "class" +
                    "|" +
                    "@?"+BLANK+"*interface" +
                    "|" +
                    "enum"
                ) +
                notSpecial(BLANK + "+") +           // 11
                BLANK + "+" +
                group(TYPE) +                       // 12(13) : name
                group(                              // 14 : implements, extends
                    BLANK + "+" +
                    group(                          // 15
                        group (                     // 16 : extends
                            "extends" +
                            BLANK + "+" +
                            group(TYPE)             // 17(18)
                        ) + "|" +
                        group (                     // 19 : implements
                            "implements" +
                            BLANK + "+" +
                            group(                  // 20
                                group(TYPE) +       // 21(22)
                                BLANK + "*" +
                                group(              // 23
                                    "," + 
                                    BLANK + "*"
                                ) + "?"
                            ) + "+"
                        )
                    ) 
                ) + "*" +
                BLANK + "+" +
                "\\{"
            );
    }
    
    public static String _method() {
        return
            group(separator()) +                    // 1 
            _annotation() +                         // 2(3,4,5)
            group(                                  // 6 : signature
                group(METHOD_MODIFIERS) +           // 7(8,9) : modifiers
                notSpecial(BLANK+"+") +             // 10 
                group(TYPE+ARRAY) +                 // 11(12,13) : type
                BLANK + "+" +                 
                notSpecial(BLANK + "*" + "\\(") +   // 14
                group(NAME) +                       // 15 : name
                BLANK + "*" +
                "\\(" +
                group(ANYTHING) +                   // 16 : params
                "\\)" +
                BLANK + "*" +
                group(                              // 17 : throws exceptions
                    "throws" +
                    BLANK + "+" +
                    group(                          // 18 : exceptions
                        group(NAME) +               // 19
                        BLANK + "*" +
                        group(                      // 20
                            "," + 
                            BLANK + "*"
                        ) + "?"
                    ) + "+"
                ) + "?" +
                "\\{"
            );
    
   }

    
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
            
            result.append("]?");
            SEPARATOR_OPTIONS = result.toString();       
        }
        return SEPARATOR_OPTIONS;
    }
    
    public static String group(String text) {
        return "("+text+")";
    }
    
    
}
