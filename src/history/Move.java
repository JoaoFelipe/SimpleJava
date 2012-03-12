/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package history;

/**
 *
 * @author Joao
 */
public class Move extends Operation {

    private int original;
    private int newPosition;
    private int length;

    public Move(int position, int newPosition, int length) {
        this.original = position;
        this.newPosition = newPosition;
        this.length = length;
    }
    
    @Override
    public int getOriginalPosition(int position) { 
        if (position >= newPosition && position < newPosition + length) {
            return position + (original-newPosition);
        }
        if (position >= original && position < newPosition) {
            return position + length;
        }
        if (position >= newPosition && position < original) {
            return position - length;
        }
        if (position >= original && position < original + length) {
            return position - (original-newPosition);
        }
        return position;
    }

    @Override
    public int getNewPosition(int position) {
        if (position >= original && position < original + length) {
            return position - (original-newPosition);
        }
        if (position >= original && position < newPosition) {
            return position - length;
        }
        if (position >= newPosition && position < original) {
            return position + length;
        }
        if (position >= newPosition && position < newPosition + length) {
            return position + (original-newPosition);
        }
        return position;
    }
    
    
}
