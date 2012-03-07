package simplejava;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
public class TestSimpleJava {
    
    public static String l = File.separator;
    
    public TestSimpleJava() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    
    
    @Test
    public void allContentShouldBeMovedToMain() throws SyntaxException {
        String test = "allContentShouldBeMovedToMain";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");
        
        String digest = FileUtils.calculateMD5("tests"+l+test+l+"out"+l+"Program.java");
        assertTrue(FileUtils.isFileValid("tests"+l+test+l+"in"+l+"Program.java", digest));
    }
    
    @Test
    public void ifClassAlreadyExistsContentShouldBeAddedToNewMethodMain() throws SyntaxException {
        String test = "ifClassAlreadyExistsContentShouldBeAddedToNewMethodMain";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");
        
        String digest = FileUtils.calculateMD5("tests"+l+test+l+"out"+l+"Program.java");
        assertTrue(FileUtils.isFileValid("tests"+l+test+l+"in"+l+"Program.java", digest));
    }
    
    @Test
    public void itShouldChooseTheFilenameClass() throws SyntaxException {
        String test = "itShouldChooseTheFilenameClass";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");
        
        String digest = FileUtils.calculateMD5("tests"+l+test+l+"out"+l+"Program.java");
        assertTrue(FileUtils.isFileValid("tests"+l+test+l+"in"+l+"Program.java", digest));
    }
    
    @Test
    public void itShouldCreateTheCorrectClass() throws SyntaxException {
        String test = "itShouldCreateTheCorrectClass";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");
        
        String digest = FileUtils.calculateMD5("tests"+l+test+l+"out"+l+"Program.java");
        assertTrue(FileUtils.isFileValid("tests"+l+test+l+"in"+l+"Program.java", digest));
    }
    
    @Test
    public void innerClassesShoudntBeChanged() throws SyntaxException {
        String test = "innerClassesShoudntBeChanged";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");
        
        String digest = FileUtils.calculateMD5("tests"+l+test+l+"out"+l+"Program.java");
        assertTrue(FileUtils.isFileValid("tests"+l+test+l+"in"+l+"Program.java", digest));
    }
    
    
    @Test
    public void importsShouldBeMovedToBegin() throws SyntaxException {
        String test = "importsShouldBeMovedToBegin";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");
        
        String digest = FileUtils.calculateMD5("tests"+l+test+l+"out"+l+"Program.java");
        assertTrue(FileUtils.isFileValid("tests"+l+test+l+"in"+l+"Program.java", digest));
    }
    
    @Test
    public void packageShouldBeMovedToBegin() throws SyntaxException {
        String test = "packageShouldBeMovedToBegin";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");
        
        String digest = FileUtils.calculateMD5("tests"+l+test+l+"out"+l+"Program.java");
        assertTrue(FileUtils.isFileValid("tests"+l+test+l+"in"+l+"Program.java", digest));
    }
    
    @Test
    public void justOnePackageIsAllowed() {
        String test = "justOnePackageIsAllowed";
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
    
    @Test
    public void publicClassShouldHaveAnIndividualFile() {
        String test = "publicClassShouldHaveAnIndividualFile";
        Throwable throwable = null;
        try {
            SimpleJava.apply(
                    "tests"+l+test+l+"in"+l+"Program.sjava", 
                    "tests"+l+test+l+"in"+l+"Program.java");
        } catch (Throwable tr) {
            throwable = tr;
        }
        assertEquals("Public Class should be declared in a File with it name (Program.sjava - Line 3)", throwable.getMessage());
//        System.out.println(RegexUtil.method());
    }
    
    @Test
    public void methodsShouldBeMovedToClass() throws SyntaxException {
        String test = "methodsShouldBeMovedToClass";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");
        
        String digest = FileUtils.calculateMD5("tests"+l+test+l+"out"+l+"Program.java");
        assertTrue(FileUtils.isFileValid("tests"+l+test+l+"in"+l+"Program.java", digest));;
    }
    
    @Test
    public void staticMethodsShouldBeMovedToClass() throws SyntaxException {
        String test = "staticMethodsShouldBeMovedToClass";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");
        
        String digest = FileUtils.calculateMD5("tests"+l+test+l+"out"+l+"Program.java");
        assertTrue(FileUtils.isFileValid("tests"+l+test+l+"in"+l+"Program.java", digest));;
    }
    
    @Test
    public void methodInClassShouldntBeMoved() throws SyntaxException {
        String test = "methodInClassShouldntBeMoved";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");
        
        String digest = FileUtils.calculateMD5("tests"+l+test+l+"out"+l+"Program.java");
        assertTrue(FileUtils.isFileValid("tests"+l+test+l+"in"+l+"Program.java", digest));;
    }
    
    @Test
    public void mainAlreadyExistsInClass() {
        String test = "mainAlreadyExistsInClass";
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
    public void mainAlreadyExistsInClass2() {
        String test = "mainAlreadyExistsInClass2";
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
    public void mainAlreadyExistsInClass3() {
        String test = "mainAlreadyExistsInClass3";
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
    public void nonStaticMainShoudntBeFound() throws SyntaxException {
        String test = "nonStaticMainShoudntBeFound";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");
        String digest = FileUtils.calculateMD5("tests"+l+test+l+"out"+l+"Program.java");
        assertTrue(FileUtils.isFileValid("tests"+l+test+l+"in"+l+"Program.java", digest));;
    }
    
    @Test
    public void mainWithWrongParamsShoudntBeFound() throws SyntaxException {
        String test = "mainWithWrongParamsShoudntBeFound";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");
        String digest = FileUtils.calculateMD5("tests"+l+test+l+"out"+l+"Program.java");
        assertTrue(FileUtils.isFileValid("tests"+l+test+l+"in"+l+"Program.java", digest));;
    }
    
    @Test
    public void localClassShoudntBeMoved() throws SyntaxException {
        System.out.println(RegexUtil._class());
        String test = "localClassShoudntBeMoved";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");
        String digest = FileUtils.calculateMD5("tests"+l+test+l+"out"+l+"Program.java");
        assertTrue(FileUtils.isFileValid("tests"+l+test+l+"in"+l+"Program.java", digest));;
    }
    
    //TODO:
    //interface
    //enum
    //annotation
    //static var
    //testar exemplos: http://en.wikipedia.org/wiki/Java_syntax
    
    
}
