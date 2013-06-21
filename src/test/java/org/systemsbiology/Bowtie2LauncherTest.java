package org.systemsbiology;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.systemsbiology.athero.Bowtie2Launcher;
import java.lang.reflect.*;
import org.apache.commons.lang3.StringUtils;


public class Bowtie2LauncherTest extends TestCase {

    public Bowtie2LauncherTest( String testName ) {
        super( testName );
    }

    public static Test suite() {
        return new TestSuite( Bowtie2LauncherTest.class );
    }


    public void testConstructor() {
	try {
	    Bowtie2Launcher b2l=new Bowtie2Launcher("buffy");
	    assertTrue(true);
	} catch (Exception e) {
	    fail("Bowtie2Launcher failed");
	}

	try {
	    Bowtie2Launcher b2l=new Bowtie2Launcher("snipe");
	    fail("Bowtie2Launcher failed");
	} catch (Exception e) {
	    String expected="no info found for 'snipe' found in props file";
	    assertEquals(e.getMessage(), expected);
	}


	// Would like to test the resulting command, 
	
    }

    public void test_build_cmdUsingBuffy() {
	Bowtie2Launcher b2l=new Bowtie2Launcher("buffy");
	Class params[]=new Class[2];
	params[0]=String.class;
	params[1]=String.class;
	try {
	    Method _build_cmd=Bowtie2Launcher.class.getDeclaredMethod("_build_cmd", params);
	    assertEquals(_build_cmd.getClass(), Method.class);
	    _build_cmd.setAccessible(true);
	    String[] cmdl=(String[])_build_cmd.invoke(b2l, 
						      new String("1047-COPD.10K"),
						      new String("hg19"));
	    String cmd=StringUtils.join(cmdl, " ");
	    String expected="/local/bin/bowtie2 /local/src/bowtie2-2.0.5/indexes/hg19 -p 16 -1 1047-COPD.10K_1.fastq -2 1047-COPD.10K_2.fastq -S 1047-COPD.10K.bt2.sam";
	    assertEquals(cmd, expected);
	} catch (Exception e) {
	    System.out.println("caught '"+e.getMessage()+"'");
	}
    }
}
