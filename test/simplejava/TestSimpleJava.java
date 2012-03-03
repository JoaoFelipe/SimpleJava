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
    public void allContentShouldBeMovedToMain() {
        
        SimpleJava.apply(
                "tests"+l+"Test1"+l+"in"+l+"Program.sjava", 
                "tests"+l+"Test1"+l+"in"+l+"Program.java");
        
        String digest = FileUtils.calculateMD5("tests"+l+"Test1"+l+"out"+l+"Program.java");
        assertTrue(FileUtils.isFileValid("tests"+l+"Test1"+l+"in"+l+"Program.java", digest));
        
    }
    
    @Test
    public void ifClassAlreadyExistsContentShouldBeAddedToNewMethodMain() {
        
        SimpleJava.apply(
                "tests"+l+"Test2"+l+"in"+l+"Program.sjava", 
                "tests"+l+"Test2"+l+"in"+l+"Program.java");
        
        String digest = FileUtils.calculateMD5("tests"+l+"Test2"+l+"out"+l+"Program.java");
        assertTrue(FileUtils.isFileValid("tests"+l+"Test2"+l+"in"+l+"Program.java", digest));
        
    }
}
