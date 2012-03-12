/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package history;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Joao
 */
public class History {
    
    List<Operation> moves;

    public History() {
        moves = new ArrayList<Operation>();
    }
    
    public int getOriginal(int position) {
        for (int i = moves.size() -1 ; i >= 0; i--) {
            Operation move = moves.get(i);
            position = move.getOriginalPosition(position);
        }
        return position;
    }

    public int getNew(int position) {
        for (int i = 0 ; i < moves.size(); i++) {
            Operation move = moves.get(i);
            position = move.getNewPosition(position);
        }
        return position;
    }
    
    public void add(int position, int length) {
        moves.add(new Add(position, length));
    }

    public void remove(int position, int length) {
        moves.add(new Remove(position, length));
    }

    public void move(int pos, int newPos, int length) {
        moves.add(new Move(pos, newPos, length));
    }
    
    
}
