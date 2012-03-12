package conversion;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import simplejava.RegexUtil;
import simplejava.SimpleJava;
import simplejava.SyntaxException;
import utils.FileUtils;
import static org.junit.Assert.*;

/**
 *
 * @author Joao
 */
public class AnnotationsTest {
    
    public static String l = File.separator;

    @Test
    public void classRulesShouldBeAppliedToAnnotationInterfaces() throws Exception {
        String test = "classRulesShouldBeAppliedToAnnotationInterfaces";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava",
                "tests"+l+test+l+"in"+l+"Program.java");

        assertTrue(FileUtils.isEqual(
            "tests"+l+test+l+"in"+l+"Program.java",
            "tests"+l+test+l+"out"+l+"Program.java")
        );
    }
    
    @Test
    public void classAnnotationsShouldStayWithClass() throws Exception {
        String test = "classAnnotationsShouldStayWithClass";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava",
                "tests"+l+test+l+"in"+l+"Program.java");

        assertTrue(FileUtils.isEqual(
            "tests"+l+test+l+"in"+l+"Program.java",
            "tests"+l+test+l+"out"+l+"Program.java")
        );
    }
    
    @Test
    public void methodAnnotationsShouldStayWithMethod() throws Exception {
        String test = "methodAnnotationsShouldStayWithMethod";
        SimpleJava.apply(
                "tests"+l+test+l+"in"+l+"Program.sjava",
                "tests"+l+test+l+"in"+l+"Program.java");

        assertTrue(FileUtils.isEqual(
            "tests"+l+test+l+"in"+l+"Program.java",
            "tests"+l+test+l+"out"+l+"Program.java")
        );
    }
       
}