/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package history;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Joao
 */
public abstract class Operation {
    
    public abstract int getOriginalPosition(int position);

    public abstract int getNewPosition(int position);
    
}