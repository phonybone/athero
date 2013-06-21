package org.systemsbiology.athero;

import java.io.*;
import java.io.IOException;

import org.systemsbiology.ScriptLauncher; // fixme: this will be replaced with Bowtie2Launcher
import org.systemsbiology.athero.Bowtie2Launcher;

public class RnaseqPipelineActivitiesImpl implements RnaseqPipelineActivities{
	
    public RnaseqPipelineActivitiesImpl() {
    }

    @Override
	public void ping(String msg) {
	System.out.println("ping! " + msg);
    }
                
    @Override    
	public void call_bowtie2(String data_basename, 
				 String bt2_index,
				 String bt2_index_dir) throws IOException {
	//	System.out.println("call_bowtie2 called");
	System.out.println("call_bowtie2 called; launching pickle_test.py");
	String cmd[]={"python", "/home/ISB/vcassen/Dropbox/sandbox/python/pickle_test.py"};
	ScriptLauncher sl=new ScriptLauncher(cmd);
	int rc=sl.run();
	
	// Read the output of the command:
	Reader r=new InputStreamReader(sl.output());
	StringBuilder s = new StringBuilder();
	char[] buf=new char[2048];
	while (true) {
	    int n=r.read(buf);
	    if (n<0) break;
	    s.append(buf);
	}
	System.out.println(s.toString());

	System.out.println("pickle_test: rc="+rc);
    }
	
    @Override
	public void call_rnaseq_count(String inputFileName, 
				      String ucsc2ll) throws IOException {
	System.out.println("call_rnaseq_count called");
    }
}
