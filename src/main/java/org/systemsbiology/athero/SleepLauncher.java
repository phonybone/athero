package org.systemsbiology.athero;

import org.systemsbiology.ScriptLauncher;
import org.systemsbiology.athero.Host;

import java.util.Properties;
import java.io.*;
import java.util.ArrayList;

public class SleepLauncher extends ScriptLauncher {
    public String msg;
    public int n_secs;
    protected Host host;

    public SleepLauncher(final String msg,
			 final int n_secs,
			 final File root_dir) {
	super("sleeper", root_dir);
	this.msg=msg;
	this.n_secs=n_secs;
	host=new Host();	// localhost
    }
    
    public SleepLauncher(final String msg,
			 final int n_secs,
			 final String hostname, 
			 final File root_dir) {
	// Not really sure we want this method...
	// (Used in testing, where we generate but don't actually run the command)
	super("sleeper", root_dir);
	this.msg=msg;
	this.n_secs=n_secs;
	host=new Host(hostname);
    }


    protected ArrayList<String> _build_cmd() {
	ArrayList<String> cmdl=new ArrayList<String>();
	cmdl.add("perl");
	final String sleep_script=this.getClass().getClassLoader().getResource("sleep.pl").getFile();
	cmdl.add(sleep_script);
	cmdl.add(String.valueOf(this.n_secs));
	cmdl.add(this.msg);
	return cmdl;
    }
}