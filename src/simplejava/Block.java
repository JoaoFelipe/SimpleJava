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
public class Block extends AbstractBlock {
    
    public static List<Block> findBlocks(String text) {
        List<Block> blocks = new ArrayList<Block>();
        
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
                blocks.add(new Block(count, stack.pop(), i));
            } 
            
        }
        blocks.add(new Block(-1, 0, text.length()-1));
        
        
        return blocks;
    }
    
    public static List<Block> orderByLevel(List<Block> blocks) {
        List<Block> result = new ArrayList<Block>();
        result.addAll(blocks);
        
        Collections.sort(result, new Comparator<Block>(){
            @Override
            public int compare(Block o1, Block o2) {
                return o2.getLevel().compareTo(o1.getLevel());
            }
        });
        
        return result;
    }
    
    public static Block topBlock(AbstractBlock absb, List<Block> blocks) {
        blocks = Block.orderByLevel(blocks);
        for (int i = 0; i < blocks.size(); i++) {
            Block block = blocks.get(i);
            if (absb.isInnerIn(block)) {
                return block;
            }
        }
        return blocks.get(blocks.size()-1);
    }
    
    public static Block blockThatStartsIn(int position, List<Block> blocks) {
        for (int i = 0; i < blocks.size(); i++) {
            Block block = blocks.get(i);
            if (block.start == position) {
                return block;
            }
        }
        return null;
    }
    
    private int level;

    public Block(int count, int start, int end) {
        this.start = start;
        this.end = end;
        this.level = count;
    }
    
    public Integer getLevel() {
        return level;
    }
}
