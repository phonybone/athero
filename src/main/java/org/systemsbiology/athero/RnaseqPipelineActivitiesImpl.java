package org.systemsbiology.athero;

import java.io.*;
import java.io.IOException;

import org.systemsbiology.athero.Bowtie2Launcher;

public class RnaseqPipelineActivitiesImpl implements RnaseqPipelineActivities{
	
    public RnaseqPipelineActivitiesImpl() {
    }

    @Override
	public void ping(String msg) {
	System.out.println("ping! " + msg);
    }
                
    @Override    
	public void call_bowtie2(final String data_basename, 
				 final String bt2_index) throws IOException {
	//	System.out.println("call_bowtie2 called");
	System.out.println("rp_ai: call_bowtie2 called");
	Bowtie2Launcher bt2_launcher=new Bowtie2Launcher(data_basename, bt2_index);
	int rc=bt2_launcher.run();

	// Read the output of the command:
	Reader r=new InputStreamReader(bt2_launcher.output());
	StringBuilder s = new StringBuilder();
	char[] buf=new char[2048];
	while (true) {
	    int n=r.read(buf);
	    if (n<0) break;
	    s.append(buf);
	}
	System.out.println(s.toString());

	System.out.println("call_bowtie2: rc="+rc);
    }
	
    @Override
	public void call_rnaseq_count(String inputFileName, 
				      String ucsc2ll) throws IOException {
	System.out.println("rp_ai: call_rnaseq_count called");
	//	String cmd[]={"
    }
}
