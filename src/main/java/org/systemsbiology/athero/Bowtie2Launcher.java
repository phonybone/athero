package org.systemsbiology.athero;

import org.systemsbiology.ScriptLauncher;
import org.systemsbiology.athero.Host;

import java.util.Properties;
import java.io.*;
import java.util.ArrayList;

public class Bowtie2Launcher  {
    private Host host;

    public Bowtie2Launcher() {
	host=new Host();
    }

    public Bowtie2Launcher(final String hostname) {
	host=new Host(hostname);
    }

    public int run(final String data_basename, final String ref_index) {
	String[] cmd=this._build_cmd(data_basename, ref_index);
	ScriptLauncher sl=new ScriptLauncher(cmd);
	return sl.run();
    }

    private String[] _build_cmd(final String data_basename, final String ref_index) {
	ArrayList<String> cmdl=new ArrayList<String>();
	cmdl.add(host.getProperty("bowtie2.exe"));
	cmdl.add(new File(host.getProperty("bowtie2.index_dir"), ref_index).getPath());
	cmdl.add("-p");
	cmdl.add(host.getProperty("n_procs"));
	cmdl.add("-1");
	cmdl.add(data_basename+"_1.fastq");
	cmdl.add("-2");
	cmdl.add(data_basename+"_2.fastq");
	cmdl.add("-S");
	cmdl.add(data_basename+".bt2.sam");
	return cmdl.toArray(new String[cmdl.size()]);
    }
}