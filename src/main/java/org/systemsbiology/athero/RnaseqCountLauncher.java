package org.systemsbiology.athero;

import org.systemsbiology.ScriptLauncher;
import org.systemsbiology.athero.Host;

import java.util.Properties;
import java.io.*;
import java.util.ArrayList;

public class RnaseqCountLauncher extends ScriptLauncher {
    public String data_basename;
    protected Host host;

    public RnaseqCountLauncher(final String data_basename,
			       final String hostname, 
			       final File root_dir) {
	// Not really sure we want this method...
	// (Used in testing, where we generate but don't actually run the command)
	super("rnaseq_count.py", root_dir);
	this.data_basename=data_basename;
	host=new Host(hostname);
    }

    public RnaseqCountLauncher(final String data_basename,
			       final File root_dir) {
	this(data_basename, new Host().getLocalName(), root_dir);
    }
    

    protected ArrayList<String> _build_cmd() {
	ArrayList<String> cmdl=new ArrayList<String>();
	Host host=new Host();
	cmdl.add("python");
	final String script=host.getProperty("rnaseq_count");
	cmdl.add(script);
	cmdl.add("--in_fn");
	cmdl.add(this.data_basename+".bt2.sam");
	cmdl.add("--ucsc2ll");
	cmdl.add(host.getProperty("rnaseq_count.ucsc2ll"));

	return cmdl;
    }
}