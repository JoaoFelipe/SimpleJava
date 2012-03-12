/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simplejava;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Joao
 */
public class Scope extends Block {
    
    public static List<Scope> findBlocks(String text) {
        List<Scope> blocks = new ArrayList<Scope>();
        
        Stack<Integer> stack = new Stack<Integer>();
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == '{') {
                count++;
                stack.add(i);
            }
            if (ch == '}') {
                count--;
                blocks.add(new Scope(count, stack.pop(), i+1));
            } 
            
        }
        blocks.add(new Scope(-1, 0, text.length()));
        
        
        return blocks;
    }
    
    public static List<Scope> orderByLevel(List<Scope> blocks) {
        List<Scope> result = new ArrayList<Scope>();
        result.addAll(blocks);
        
        Collections.sort(result, new Comparator<Scope>(){
            @Override
            public int compare(Scope o1, Scope o2) {
                return o2.getLevel().compareTo(o1.getLevel());
            }
        });
        
        return result;
    }
    
    public static Scope topBlock(Block absb, List<Scope> blocks) {
        blocks = Scope.orderByLevel(blocks);
        for (int i = 0; i < blocks.size(); i++) {
            Scope block = blocks.get(i);
            if (absb.isInnerIn(block)) {
                return block;
            }
        }
        return blocks.get(blocks.size()-1);
    }
    
    public static Scope blockThatStartsIn(int position, List<Scope> blocks) {
        for (int i = 0; i < blocks.size(); i++) {
            Scope block = blocks.get(i);
            if (block.getStart() == position) {
                return block;
            }
        }
        return null;
    }
    
    private int level;

    public Scope(int count, int start, int end) {
        super(start, end);
        this.level = count;
    }
    
    public Integer getLevel() {
        return level;
    }
}
