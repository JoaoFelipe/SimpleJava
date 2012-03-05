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
        String test = "Test1";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");
        
        String digest = FileUtils.calculateMD5("tests"+l+test+l+"out"+l+"Program.java");
        assertTrue(FileUtils.isFileValid("tests"+l+test+l+"in"+l+"Program.java", digest));
    }
    
    @Test
    public void ifClassAlreadyExistsContentShouldBeAddedToNewMethodMain() throws SyntaxException {
        String test = "Test2";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");
        
        String digest = FileUtils.calculateMD5("tests"+l+test+l+"out"+l+"Program.java");
        assertTrue(FileUtils.isFileValid("tests"+l+test+l+"in"+l+"Program.java", digest));
    }
    
    @Test
    public void itShouldChooseTheFilenameClass() throws SyntaxException {
        String test = "Test3";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");
        
        String digest = FileUtils.calculateMD5("tests"+l+test+l+"out"+l+"Program.java");
        assertTrue(FileUtils.isFileValid("tests"+l+test+l+"in"+l+"Program.java", digest));
    }
    
    @Test
    public void itShouldCreateTheCorrectClass() throws SyntaxException {
        String test = "Test4";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");
        
        String digest = FileUtils.calculateMD5("tests"+l+test+l+"out"+l+"Program.java");
        assertTrue(FileUtils.isFileValid("tests"+l+test+l+"in"+l+"Program.java", digest));
    }
    
    @Test
    public void innerClassesShoudntBeChanged() throws SyntaxException {
        String test = "Test5";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");
        
        String digest = FileUtils.calculateMD5("tests"+l+test+l+"out"+l+"Program.java");
        assertTrue(FileUtils.isFileValid("tests"+l+test+l+"in"+l+"Program.java", digest));
    }
    
    
    @Test
    public void importsShouldBeMovedToBegin() throws SyntaxException {
        String test = "Test6";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");
        
        String digest = FileUtils.calculateMD5("tests"+l+test+l+"out"+l+"Program.java");
        assertTrue(FileUtils.isFileValid("tests"+l+test+l+"in"+l+"Program.java", digest));
    }
    
    @Test
    public void packageShouldBeMovedToBegin() throws SyntaxException {
        String test = "Test7";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava", 
                "tests"+l+test+l+"in"+l+"Program.java");
        
        String digest = FileUtils.calculateMD5("tests"+l+test+l+"out"+l+"Program.java");
        assertTrue(FileUtils.isFileValid("tests"+l+test+l+"in"+l+"Program.java", digest));
    }
    
    @Test
    public void justOnePackageIsAllowed() {
        String test = "Syntax8";
        try {
            SimpleJava.apply(
                    "tests"+l+test+l+"in"+l+"Program.sjava", 
                    "tests"+l+test+l+"in"+l+"Program.java");
        } catch (Throwable tr) {
            assertEquals("You must define just one package (Program.sjava - Line 5)", tr.getMessage());
        }
    }
    
    @Test
    public void publicClassShouldHaveAnIndividualFile() {
        String test = "Syntax9";
        try {
            SimpleJava.apply(
                    "tests"+l+test+l+"in"+l+"Program.sjava", 
                    "tests"+l+test+l+"in"+l+"Program.java");
        } catch (Throwable tr) {
            assertEquals("Public Class should be declared in a File with it name (Program.sjava - Line 3)", tr.getMessage());
        }
        System.out.println(RegexUtil.method());
    }
    

    
    //TODO:
    //method: modifiers+special(blank+"+")+"("+type+")"+blank+"+"+"("+name+")"+blank+"*\\(("+anything+")\\)"+blank+"*(throws (("+name+")"+blank+"*(,"+blank+"*)?)+)?\\{"
    //  modifiers: ((abstract|final|private|public|protected|strictfp|static|synchronized|native|transient|volatile) )*
    //  special-prox-: (?!(else|if)prox)
    //  type: [_A-Z][_A-Z0-9.-]*(<.*?>)?(\[\])*
    //  blank: \s
    //  name: [_A-Z][_A-Z0-9.-]*
    //  anything: [ _A-Z0-9.-\[\],]*
    //local class
    //interface
    //enum
    //annotation
    //static var
    //testar exemplos: http://en.wikipedia.org/wiki/Java_syntax
    
    
}
