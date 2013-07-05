package org.systemsbiology.athero;

import java.io.*;
import java.io.IOException;

import org.systemsbiology.athero.Bowtie2Launcher;
import org.systemsbiology.athero.Host;

public class RnaseqPipelineActivitiesImpl implements RnaseqPipelineActivities{
	
    public RnaseqPipelineActivitiesImpl() {
    }

    @Override
	public void ping(String msg) {
	System.out.println("ping! " + msg);
    }
                
    @Override    
	public String call_bowtie2(final String data_basename, 
				 final String bt2_index) throws IOException {
	//	System.out.println("call_bowtie2 called");
	System.out.println("rp_ai: call_bowtie2 called");
	Bowtie2Launcher bt2_launcher=new Bowtie2Launcher(data_basename, bt2_index);
	int rc=bt2_launcher.run();

	// Read the output of the command:
	// So this is blowing up because the output stream isn't yet established, and ScriptLauncher
	// is throwing an error.
	// Should run a test to see if it's just because the underlying script hasn't finished yet.
	// ScriptLauncher uses Runtime.exec(), what are semantics???
	String output=bt2_launcher.outputString();
	System.out.println(output);

	System.out.println("call_bowtie2: rc="+rc);
	return new Host().getLocalName();
    }
	
    @Override
	public void call_rnaseq_count(String inputFileName, 
				      String ucsc2ll) throws IOException {
	System.out.println("rp_ai: call_rnaseq_count called");
	//	String cmd[]={"
    }
}
