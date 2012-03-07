package simplejava.conversion;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import simplejava.*;
import java.io.File;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.FileUtils;
import static org.junit.Assert.*;

/**
 *
 * @author Joao
 */
public class TestOuterText {
    
    public static String l = File.separator;
    
    @Test
    public void outerTextShouldBeMovedToMain() throws SyntaxException {
        String test = "outerTextShouldBeMovedToMain";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");
        
        String digest = FileUtils.calculateMD5("tests"+l+test+l+"out"+l+"Program.java");
        assertTrue(FileUtils.isFileValid("tests"+l+test+l+"in"+l+"Program.java", digest));
    }
    
    @Test
    public void outerTextShouldBeMovedToNewMethodMainOnExistentClass() throws SyntaxException {
        String test = "outerTextShouldBeMovedToNewMethodMainOnExistentClass";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");
        
        String digest = FileUtils.calculateMD5("tests"+l+test+l+"out"+l+"Program.java");
        assertTrue(FileUtils.isFileValid("tests"+l+test+l+"in"+l+"Program.java", digest));
    }
    
    @Test
    public void outerTextShouldBeMovedToNewMethodMainOfFilenameClass() throws SyntaxException {
        String test = "outerTextShouldBeMovedToNewMethodMainOfFilenameClass";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");
        
        String digest = FileUtils.calculateMD5("tests"+l+test+l+"out"+l+"Program.java");
        assertTrue(FileUtils.isFileValid("tests"+l+test+l+"in"+l+"Program.java", digest));
    }
    
    @Test
    public void outerTextShouldBeMovedToMainOfNewClassWhenExistsOneWithWrongFilename() throws SyntaxException {
        String test = "outerTextShouldBeMovedToMainOfNewClassWhenExistsOneWithWrongFilename";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");
        
        String digest = FileUtils.calculateMD5("tests"+l+test+l+"out"+l+"Program.java");
        assertTrue(FileUtils.isFileValid("tests"+l+test+l+"in"+l+"Program.java", digest));
    }
    
    @Test
    public void allPublicClassesHaveToHaveNameEqualToFileName_Exception() {
        String test = "allPublicClassesHaveToHaveNameEqualToFileName_Exception";
        Throwable throwable = null;
        try {
            SimpleJava.apply(
                    "tests"+l+test+l+"in"+l+"Program.sjava", 
                    "tests"+l+test+l+"in"+l+"Program.java");
        } catch (Throwable tr) {
            throwable = tr;
        }
        assertEquals("Public Class should be declared in a File with it name (Program.sjava - Line 3)", throwable.getMessage());
    }
    
}
