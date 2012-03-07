package conversion;

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
public class OuterTextTest {
    
    public static String l = File.separator;
    
    @Test
    public void outerTextShouldBeMovedToMain() throws Exception {
        String test = "outerTextShouldBeMovedToMain";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");
        
        assertTrue(FileUtils.isEqual(
            "tests"+l+test+l+"in"+l+"Program.java",
            "tests"+l+test+l+"out"+l+"Program.java")
        );
    }
    
    @Test
    public void outerTextShouldBeMovedToNewMethodMainOnExistentClass() throws Exception {
        String test = "outerTextShouldBeMovedToNewMethodMainOnExistentClass";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");
        
        assertTrue(FileUtils.isEqual(
            "tests"+l+test+l+"in"+l+"Program.java",
            "tests"+l+test+l+"out"+l+"Program.java")
        );
    }
    
    @Test
    public void outerTextShouldBeMovedToNewMethodMainOfFilenameClass() throws Exception {
        String test = "outerTextShouldBeMovedToNewMethodMainOfFilenameClass";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");
        
        assertTrue(FileUtils.isEqual(
            "tests"+l+test+l+"in"+l+"Program.java",
            "tests"+l+test+l+"out"+l+"Program.java")
        );
    }
    
    @Test
    public void outerTextShouldBeMovedToMainOfNewClassWhenExistsOneWithWrongFilename() throws Exception {
        String test = "outerTextShouldBeMovedToMainOfNewClassWhenExistsOneWithWrongFilename";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");

        assertTrue(FileUtils.isEqual(
            "tests"+l+test+l+"in"+l+"Program.java",
            "tests"+l+test+l+"out"+l+"Program.java")
        );
    }
    
    @Test
    public void allPublicClassesHaveToHaveNameEqualToFileName_Exception() {
        String test = "allPublicClassesHaveToHaveNameEqualToFileName";
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
    
    @Test
    public void mainCantBeCreatedOnInterface_Exception() {
        String test = "mainCantBeCreatedOnInterface";
        Throwable throwable = null;
        try {
            SimpleJava.apply(
                    "tests"+l+test+l+"in"+l+"Program.sjava", 
                    "tests"+l+test+l+"in"+l+"Program.java");
        } catch (Throwable tr) {
            throwable = tr;
        }
        assertEquals("Main cannot be created on Interface (Program.sjava - Line 2)", throwable.getMessage());
    }
    
}
