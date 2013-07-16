package org.systemsbiology;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.systemsbiology.athero.TouchLauncher;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.io.File;
import org.apache.commons.lang3.StringUtils;


public class TouchLauncherTest extends TestCase {

    public TouchLauncherTest( String testName ) {
        super( testName );
    }

    public static Test suite() {
        return new TestSuite( TouchLauncherTest.class );
    }


    public void testConstructor() {
	File dir=new File(System.getProperty("user.dir"));
	File file=new File("touch.target");
	TouchLauncher toucher=null;
	try {
	    toucher=new TouchLauncher(file, dir);
	    assertTrue(true);
	} catch (Exception e) {
	    fail("TouchLauncher constructor failed: "+e.getMessage());
	}


	// Would like to test the resulting command, 
	String[] cmd=toucher.asString().split(" ");
	System.out.println("cmd is "+toucher.asString());
	assertTrue(cmd[0].contains("touch"));
	assertTrue(cmd[1].contains(toucher.file.getPath()));
    }

}
