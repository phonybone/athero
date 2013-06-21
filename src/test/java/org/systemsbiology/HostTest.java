package org.systemsbiology;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.systemsbiology.athero.Host;
import java.lang.RuntimeException;

public class HostTest extends TestCase {

    public HostTest( String testName ) {
        super( testName );
    }

    public static Test suite() {
        return new TestSuite( HostTest.class );
    }


    public void testConstructor() {
	String buffy="buffy.systemsbiology.org";
	Host host=new Host(buffy);
	assertEquals(host.getName(), buffy);
	assertEquals(host.getShortName(), "buffy");
	assertEquals(host.n_procs(), 16);
    }

    public void testUnknownHost() {
	try {
	    Host host=new Host("unknown.systemsbiology.org");
	    fail();
	} catch (RuntimeException e) {
	    String expected="no info found for 'unknown' found in props file";
	    assertEquals(e.getMessage(), expected);
	}
	    
    }
}
