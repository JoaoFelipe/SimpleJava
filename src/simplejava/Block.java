/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simplejava;

import java.util.List;

/**
 *
 * @author Joao
 */
public class Block {
    private int start;
    private int end;
    
    public Block(int start, int end) {
        this.start = start;
        this.end = end;
    }
    
    public boolean containPosition(int position) {
        return (position >= getStart() - 1 && position <= getEnd());
    }
    
    public static String removeBlocksFromText(List<Block> blocks, String text) {
        String mainText = "";
        for (int i = 0; i < text.length(); i++) {
            boolean add = true;
            for (Block abstractBlock : blocks) {
                if (abstractBlock.containPosition(i)) {
                    add = false;
                }
            }
            if (add) {
                mainText += text.charAt(i);
            }
        }
        return mainText;
    }
    
    public boolean isInnerIn(Block block){
        return this != block && 
               this.getStart() >= block.getStart() && 
               this.getEnd() <= block.getEnd();
    }
    
     
    public boolean isInnerInList(List<? extends Block> blocks){
        for (Block block : blocks) {
            if (this.isInnerIn(block)) {
                return true;
            }
        }
        return false;
    }
    
    public int length() {
        return getEnd()-getStart();
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
    
    public void setStartEnd(int start, int end) {
        this.setStart(start);
        this.setEnd(end);
    }
    
    public void setStartLength(int start, int length) {
        this.setStart(start);
        this.setEnd(start+length);
    }
}
