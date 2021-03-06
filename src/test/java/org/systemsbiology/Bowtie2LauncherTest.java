package org.systemsbiology;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.systemsbiology.athero.Bowtie2Launcher;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.io.File;
import org.apache.commons.lang3.StringUtils;


public class Bowtie2LauncherTest extends TestCase {

    public Bowtie2LauncherTest( String testName ) {
        super( testName );
    }

    public static Test suite() {
        return new TestSuite( Bowtie2LauncherTest.class );
    }


    public void testConstructor() {
	File dir=new File(System.getProperty("user.dir"));
	try {
	    Bowtie2Launcher b2l=new Bowtie2Launcher("1047-COPD.10K", "hg19", dir);
	    assertTrue(true);
	} catch (Exception e) {
	    fail("Bowtie2Launcher failed");
	}

	try {
	    Bowtie2Launcher b2l=new Bowtie2Launcher("doesn't", "matter", "snipe", dir);
	    fail("Bowtie2Launcher failed");
	} catch (Exception e) {
	    String expected="no info found for 'snipe' found in props file";
	    assertEquals(e.getMessage(), expected);
	}


	// Would like to test the resulting command, 
	
    }

    public void test_build_cmdUsingBuffy() {
	String data_basename="1047-COPD.10K";
	String ref_index="hg19";
	File dir=new File("/local/vcassen/Nof1/data/rawdata");
	Bowtie2Launcher b2l=new Bowtie2Launcher(data_basename, ref_index, "buffy", dir);
	Class params[]=new Class[2];
	params[0]=String.class;
	params[1]=String.class;
	try {
	    Method _build_cmd=Bowtie2Launcher.class.getDeclaredMethod("_build_cmd");
	    assertEquals(_build_cmd.getClass(), Method.class);
	    _build_cmd.setAccessible(true);
	    ArrayList<String> cmdl=(ArrayList<String>)_build_cmd.invoke(b2l);
	    //	    String cmd=StringUtils.join(cmdl, " ");
	    String expected="/local/bin/bowtie2 /local/src/bowtie2-2.0.5/indexes/hg19 -p 16 -1 1047-COPD.10K_1.fastq -2 1047-COPD.10K_2.fastq -S 1047-COPD.10K.bt2.sam";
	    String[] parts=b2l.asString().split(" ");
	    assertTrue(parts[0].contains("bowtie2"));
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
}
