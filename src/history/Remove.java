/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package history;

/**
 *
 * @author Joao
 */
public class Remove extends Operation {

    private int original;
    private int count;
    
    public Remove(int position, int count) {
        this.original = position;
        this.count = count;
    }
    
    @Override
    public int getOriginalPosition(int position) { 
        if (position >= original) {
            return position + count;
        } 
        return position;
    }

    @Override
    public int getNewPosition(int position) {
        if (position > original + count) {
            return position - count;
        } else if (position >= original) {
            return original;
        }
        return position;
    }
    
    
}
