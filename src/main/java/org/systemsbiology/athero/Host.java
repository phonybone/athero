package org.systemsbiology.athero;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.lang.RuntimeException;

public class Host {
    private String full_name;
    private static Properties props;

    public Host() {
	this.full_name=this.getLocalName();
	this._verify_host_is_known();
    }

    public Host(final String hostname) {
	this.full_name=hostname;
	this._verify_host_is_known();
    }

    private void _verify_host_is_known() throws RuntimeException {
	// Check that there are keys in props that start with the short name of the host:
	String short_name=this.getShortName();
	for (String key : props.stringPropertyNames()) {
	    String host_sh=key.split("\\.")[0];
	    if (host_sh.equals(short_name)) return;
	}
	throw new RuntimeException("no info found for '"+short_name+"' found in props file");
    }

    static {
	try {
	    props=new Properties();
	    InputStream is=Bowtie2Launcher.class.getResourceAsStream("/athero.properties");
	    props.load(is);
	} catch (IOException ioe) {
	    System.err.println("cannot init Bowtie2Launcher: "+ioe.getMessage());
	    System.exit(-1);
	}
    }


    public String getName() { return this.full_name; }
    public String getShortName() { 
	return this.getName().split("\\.")[0];
    }
    public int n_procs() { return new Integer(this.getProperty("n_procs")); }
    public String getProperty(final String key) { 
	return props.getProperty(this.getShortName() + "." + key); 
    }


    static Host getLocalhost() {
	return new Host();
    }

    static String getLocalName() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            return addr.getHostName();
        }
        catch (UnknownHostException e) {
            throw new Error(e);
        }
    }


}