package simplejava.conversion;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import simplejava.SimpleJava;
import simplejava.SyntaxException;
import utils.FileUtils;
import static org.junit.Assert.*;

/**
 *
 * @author Joao
 */
public class TestImportsPackages {
    
    public static String l = File.separator;
       
    @Test
    public void importsShouldBeMovedToBOF() throws SyntaxException {
        String test = "importsShouldBeMovedToBOF";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");
        
        String digest = FileUtils.calculateMD5("tests"+l+test+l+"out"+l+"Program.java");
        assertTrue(FileUtils.isFileValid("tests"+l+test+l+"in"+l+"Program.java", digest));
    }
    
    @Test
    public void packageShouldBeMovedToBOF() throws SyntaxException {
        String test = "packageShouldBeMovedToBOF";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");
        
        String digest = FileUtils.calculateMD5("tests"+l+test+l+"out"+l+"Program.java");
        assertTrue(FileUtils.isFileValid("tests"+l+test+l+"in"+l+"Program.java", digest));
    }
    
    @Test
    public void staticImportShouldBeMovedToBOF() throws SyntaxException {
        String test = "staticImportShouldBeMovedToBOF";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");
        String digest = FileUtils.calculateMD5("tests"+l+test+l+"out"+l+"Program.java");
        assertTrue(FileUtils.isFileValid("tests"+l+test+l+"in"+l+"Program.java", digest));;
    }
    
    @Test
    public void noMoreThanOnePackageShouldBeFound_Exception() {
        String test = "noMoreThanOnePackageShouldBeFound";
        Throwable throwable = null;
        try {
            SimpleJava.apply(
                    "tests"+l+test+l+"in"+l+"Program.sjava", 
                    "tests"+l+test+l+"in"+l+"Program.java");
        } catch (Throwable tr) {
            throwable = tr;
        }
        assertEquals("You must define just one package (Program.sjava - Line 5)", throwable.getMessage());
    }
    
}
