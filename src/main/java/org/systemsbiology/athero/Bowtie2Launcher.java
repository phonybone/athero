package org.systemsbiology.athero;

import org.systemsbiology.ScriptLauncher;
import org.systemsbiology.athero.Host;

import java.util.Properties;
import java.io.*;
import java.util.ArrayList;

public class Bowtie2Launcher extends ScriptLauncher {
    protected String data_basename;
    protected String ref_index;
    protected Host host;

    public Bowtie2Launcher(final String data_basename, final String ref_index) {
	super();
	this.data_basename=data_basename;
	this.ref_index=ref_index;
	host=new Host();	// localhost
    }

    public Bowtie2Launcher(final String data_basename, final String ref_index, 
			   final String hostname) {
	// Not really sure we want this method...
	// (Used in testing, where we generate but don't actually run the command)
	super();
	this.data_basename=data_basename;
	this.ref_index=ref_index;
	host=new Host(hostname);
    }


    protected String[] _build_cmd() {
	ArrayList<String> cmdl=new ArrayList<String>();
	cmdl.add(host.getProperty("bowtie2.exe"));
	cmdl.add(new File(host.getProperty("bowtie2.index_dir"), this.ref_index).getPath());
	cmdl.add("-p");
	cmdl.add(host.getProperty("n_procs"));
	cmdl.add("-1");
	cmdl.add(this.data_basename+"_1.fastq");
	cmdl.add("-2");
	cmdl.add(this.data_basename+"_2.fastq");
	cmdl.add("-S");
	cmdl.add(this.data_basename+".bt2.sam");
	return cmdl.toArray(new String[cmdl.size()]);
    }
}