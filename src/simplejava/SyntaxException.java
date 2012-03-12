/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simplejava;

/**
 *
 * @author Joao
 */
public class SyntaxException extends Exception {

    Code code = null;
    String file = null;
    int position;
    
    SyntaxException(String string, Code code, int position) {
        super(string);
        this.code = code;
        this.position = position;
    }
    
    SyntaxException(String string, int position) {
        super(string);
        this.position = position;
    }
    
    
    private int getLine() {
        int line = 1;
        int original = code.history.getOriginal(position);
//        code.text.charAt(30)
        code.textWithComment.charAt(original);
        for (int i = 0; i < original; i++) {
            if (code.textWithComment.charAt(i)=='\n') {
                line++;
            }
        }
        return line;
//        if (code != null) {
//            for (Move line : code.lines) {
//                if (position >= line.getStart() && position <= line.getEnd())
//                    return line.number;
//            }
//        }
    }
    
    @Override
    public String getMessage() {
        if (file == null) {
            return super.getMessage() + " (Line "+getLine()+")";
        }
        return super.getMessage() + " ("+file+" - Line "+getLine()+")";
    }
    
    public void setFile(String fileName) {
        this.file = fileName;
    }
    
    public void setCode(Code code) {
        this.code = code;
    }
}
