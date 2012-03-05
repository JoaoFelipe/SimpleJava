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

    String text;
    String file = null;
    int position;
    
    SyntaxException(String string, String text, int position) {
        super(string);
        this.text = text;
        this.position = position;
    }
    
    private int getLine() {
        int line = 1;
        for (int i = 0; i < position; i++) {
            if (text.charAt(i)=='\n') {
                line++;
            }
        }
        return line;
    }
    
    @Override
    public String getMessage() {
        if (file == null) {
            return super.getMessage() + " (Line "+getLine()+")";
        }
        return super.getMessage() + " ("+file+" - Line "+getLine()+")";
    }
    
    public void setFile(String fileName) {
        file = fileName;
    }
    
}
