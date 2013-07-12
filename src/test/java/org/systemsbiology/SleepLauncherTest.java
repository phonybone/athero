package org.systemsbiology;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.systemsbiology.athero.SleepLauncher;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.io.File;
import org.apache.commons.lang3.StringUtils;


public class SleepLauncherTest extends TestCase {

    public SleepLauncherTest( String testName ) {
        super( testName );
    }

    public static Test suite() {
        return new TestSuite( SleepLauncherTest.class );
    }


    public void testConstructor() {
	File dir=new File(System.getProperty("user.dir"));
	SleepLauncher sleeper=null;
	try {
	    sleeper=new SleepLauncher("'sleep for 3 seconds'", 3, dir);
	    assertTrue(true);
	} catch (Exception e) {
	    fail("SleepLauncher constructor failed: "+e.getMessage());
	}


	// Would like to test the resulting command, 
	String[] cmd=sleeper.asString().split(" ");
	System.out.println("cmd is "+sleeper.asString());
	assertTrue(cmd[0].contains("perl"));
	assertTrue(cmd[1].contains("sleep.pl"));
	assertTrue(cmd[2].equals("3"));
	// Testing the rest of cmd is tedious because of the split on " "
    }

    /*
    public void test_build_cmdUsingBuffy() {
	String data_basename="1047-COPD.10K";
	String ref_index="hg19";
	File dir=new File("/local/vcassen/Nof1/data/rawdata");
	SleepLauncher sleeper=new SleepLauncher(data_basename, ref_index, "buffy", dir);
	Class params[]=new Class[2];
	params[0]=String.class;
	params[1]=String.class;
	try {
	    Method _build_cmd=SleepLauncher.class.getDeclaredMethod("_build_cmd");
	    assertEquals(_build_cmd.getClass(), Method.class);
	    _build_cmd.setAccessible(true);
	    ArrayList<String> cmdl=(ArrayList<String>)_build_cmd.invoke(sleeper);
	    //	    String cmd=StringUtils.join(cmdl, " ");
	    String expected="/local/bin/sleep /local/src/sleep-2.0.5/indexes/hg19 -p 16 -1 1047-COPD.10K_1.fastq -2 1047-COPD.10K_2.fastq -S 1047-COPD.10K.bt2.sam";
	    String[] parts=sleeper.asString().split(" ");
	    assertTrue(parts[0].contains("sleep"));
	    assertTrue(parts[1].contains("hg19"));
	    assertEquals(parts[2], "-p");
	    assertTrue(Integer.parseInt(parts[3]) >= 1);
	    assertEquals(parts[4], "-1");
	    assertTrue(parts[5].contains(data_basename));
	    assertEquals(parts[6], "-2");
	    assertTrue(parts[7].contains(data_basename));
	    assertEquals(parts[8], "-S");
	    assertTrue(parts[9].contains(data_basename));
	} catch (Exception e) {
	    System.out.println("caught '"+e.getMessage()+"'");
	    e.printStackTrace();
	}
    }
    */
}
