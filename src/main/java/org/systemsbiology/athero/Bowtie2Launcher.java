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

    public Bowtie2Launcher(final String data_basename, 
			   final String ref_index,
			   final File root_dir) {
	super("bowtie2", root_dir, new File(data_basename));
	this.data_basename=data_basename;
	this.ref_index=ref_index;
	host=new Host();	// localhost
    }

    public Bowtie2Launcher(final String data_basename, 
			   final String ref_index, 
			   final String hostname, 
			   final File root_dir) {
	// Not really sure we want this method...
	// (Used in testing, where we generate but don't actually run the command)
	super("bowtie2", root_dir);
	this.data_basename=data_basename;
	this.ref_index=ref_index;
	host=new Host(hostname);
    }


    protected ArrayList<String> _build_cmd() {
	ArrayList<String> cmdl=new ArrayList<String>();
	cmdl.add(host.getProperty("bowtie2.exe"));
	cmdl.add(new File(host.getProperty("bowtie2.index_dir"), this.ref_index).getPath());
	cmdl.add("-p");
	cmdl.add(host.getProperty("n_procs"));
	cmdl.add("-1");
	File input1=new File(this.dir, this.data_basename+"_1.fastq");
	cmdl.add(input1.getPath());
	cmdl.add("-2");
	File input2=new File(this.dir, this.data_basename+"_2.fastq");
	cmdl.add(input2.getPath());
	cmdl.add("-S");
	File output=new File(this.dir, this.data_basename+"bt2.sam");
	cmdl.add(output.getPath());
	return cmdl;
    }
}