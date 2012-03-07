package simplejava.conversion;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;
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
public class TestBlocks {
    
    public static String l = File.separator;

    @Test
    public void innerClassesShoudntBeMoved() throws Exception {
        String test = "innerClassesShoudntBeMoved";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");
        
        assertTrue(FileUtils.isEqual(
            "tests"+l+test+l+"in"+l+"Program.java",
            "tests"+l+test+l+"out"+l+"Program.java")
        );
//        assertTrue(FileUtils.isFileValid("tests"+l+test+l+"in"+l+"Program.java", digest));
    }
    
    @Test
    public void outerMethodsShouldBeMovedToClassAsStaticMethods() throws Exception {
        String test = "outerMethodsShouldBeMovedToClassAsStaticMethods";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");

        assertTrue(FileUtils.isEqual(
            "tests"+l+test+l+"in"+l+"Program.java",
            "tests"+l+test+l+"out"+l+"Program.java")
        );
    }
    
    @Test
    public void staticMethodsShouldBeMovedToClass() throws Exception {
        String test = "staticMethodsShouldBeMovedToClass";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");
        
        assertTrue(FileUtils.isEqual(
            "tests"+l+test+l+"in"+l+"Program.java",
            "tests"+l+test+l+"out"+l+"Program.java")
        );
    }
    
    @Test
    public void innerMethodsShoudntBeMoved() throws Exception {
        String test = "innerMethodsShoudntBeMoved";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");

        assertTrue(FileUtils.isEqual(
            "tests"+l+test+l+"in"+l+"Program.java",
            "tests"+l+test+l+"out"+l+"Program.java")
        );
    }
    
    @Test
    public void localClassesShoudntBeMoved() throws Exception {
        String test = "localClassesShoudntBeMoved";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");
               
        assertTrue(FileUtils.isEqual(
            "tests"+l+test+l+"in"+l+"Program.java",
            "tests"+l+test+l+"out"+l+"Program.java")
        );
    }
    
    @Test
    public void nonStaticMainShoudntBeRecognizedAsMain() throws Exception {
        String test = "nonStaticMainShoudntBeRecognizedAsMain";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");

        assertTrue(FileUtils.isEqual(
            "tests"+l+test+l+"in"+l+"Program.java",
            "tests"+l+test+l+"out"+l+"Program.java")
        );
    }
    
    @Test
    public void staticMainWithWrongParamsShoudntBeRecognizedAsMain() throws Exception {
        String test = "staticMainWithWrongParamsShoudntBeRecognizedAsMain";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");

        assertTrue(FileUtils.isEqual(
            "tests"+l+test+l+"in"+l+"Program.java",
            "tests"+l+test+l+"out"+l+"Program.java")
        );
    }
    
    @Test
    public void oneClassShouldHaveJustOneMain_Exception() {
        String test = "oneClassShouldHaveJustOneMain";
        Throwable throwable = null;
        try {
            SimpleJava.apply(
                    "tests"+l+test+l+"in"+l+"Program.sjava", 
                    "tests"+l+test+l+"in"+l+"Program.java");
        } catch (Throwable tr) {
            throwable = tr;
        }
        assertEquals("Main already exists (Program.sjava - Line 12)", throwable.getMessage());
    }
    
    @Test
    public void oneClassShouldHaveJustOneMain2_Exception() {
        String test = "oneClassShouldHaveJustOneMain2";
        Throwable throwable = null;
        try {
            SimpleJava.apply(
                    "tests"+l+test+l+"in"+l+"Program.sjava", 
                    "tests"+l+test+l+"in"+l+"Program.java");
        } catch (Throwable tr) {
            throwable = tr;
        }
        assertEquals("Main already exists (Program.sjava - Line 12)", throwable.getMessage());
    }
    
    @Test
    public void oneClassShouldHaveJustOneMain3_Exception() {
        String test = "oneClassShouldHaveJustOneMain3";
        Throwable throwable = null;
        try {
            SimpleJava.apply(
                    "tests"+l+test+l+"in"+l+"Program.sjava", 
                    "tests"+l+test+l+"in"+l+"Program.java");
        } catch (Throwable tr) {
            throwable = tr;
        }
        assertEquals("Main already exists (Program.sjava - Line 12)", throwable.getMessage());
    }
    

    
    //TODO:
    //interface
    //enum
    //annotation
    //static var
    //testar exemplos: http://en.wikipedia.org/wiki/Java_syntax
    
    
}
