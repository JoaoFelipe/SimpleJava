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

    String text = null;
    String file = null;
    int position;
    
    SyntaxException(String string, String text, int position) {
        super(string);
        this.text = text;
        this.position = position;
    }
    
    SyntaxException(String string, int position) {
        super(string);
        this.position = position;
    }
    
    
    private int getLine() {
        if (text != null) {
            int line = 1;
            for (int i = 0; i < position; i++) {
                if (text.charAt(i)=='\n') {
                    line++;
                }
            }
            return line;
        }
        return -1;
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
    
    public void setTextIfNull(String text) {
        if (this.text == null) {
            this.text = text;
        }
    }
}
