/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simplejava;

/**
 *
 * @author Joao
 */
public class CommentBlock extends Block {
    
    String text;
    
    public CommentBlock(int start, String text) {
        super(start, start + text.length());
        this.text = text;
    }
    
}
