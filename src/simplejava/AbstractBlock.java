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
public abstract class AbstractBlock {
    public int start;
    public int end;
    
    public boolean containPosition(int position) {
        return (position >= start - 1 && position <= end);
    }
    
    public static String removeBlocksFromText(List<AbstractBlock> blocks, String text) {
        String mainText = "";
        for (int i = 0; i < text.length(); i++) {
            boolean add = true;
            for (AbstractBlock abstractBlock : blocks) {
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
    
    public boolean isInnerIn(AbstractBlock block){
        return this.start > block.start && this.end < block.end;
    }
    
     
    public boolean isInnerInList(List<? extends AbstractBlock> blocks){
        for (AbstractBlock block : blocks) {
            if (this.isInnerIn(block)) {
                return true;
            }
        }
        return false;
    }
}
