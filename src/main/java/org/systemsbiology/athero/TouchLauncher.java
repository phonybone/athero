package org.systemsbiology.athero;

import org.systemsbiology.ScriptLauncher;
import org.systemsbiology.athero.Host;

import java.util.Properties;
import java.io.*;
import java.util.ArrayList;

public class TouchLauncher extends ScriptLauncher {
    public File file;
    protected Host host;

    public TouchLauncher(final File file,
			 final File root_dir) {
	super("toucher", root_dir);
	this.file=file;
	host=new Host();	// localhost
    }
    
    public TouchLauncher(final File file,
			 final String hostname, 
			 final File root_dir) {
	// Not really sure we want this method...
	// (Used in testing, where we generate but don't actually run the command)
	super("toucher", root_dir);
	this.file=file;
	host=new Host(hostname);
    }


    protected ArrayList<String> _build_cmd() {
	ArrayList<String> cmdl=new ArrayList<String>();
	cmdl.add("touch");
	cmdl.add(this.file.getPath());
	return cmdl;
    }
}