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
	public String call_bowtie2(final String data_basename, 
				   final String bt2_index,
				   final String dir) throws IOException {
	System.out.println("rp_ai: call_bowtie2 called");
	Bowtie2Launcher bt2_launcher=new Bowtie2Launcher(data_basename, bt2_index, new File(dir));
	System.out.println("rp_ai: cmd is "+bt2_launcher.asString());
	int rc=bt2_launcher.run();
	System.out.println("call_bowtie2: rc="+rc);
	return new Host().getLocalName();
    }
	
    @Override
	public void call_rnaseq_count(final String inputFileName, 
				      final String ucsc2ll,
				      final String dir) throws IOException {
	System.out.println("rp_ai: call_rnaseq_count called (NYI)");
	System.out.println("rp_ai: inputFileName is "+inputFileName);
	System.out.println("rp_ai: ucsc2ll is "+ucsc2ll);
	System.out.println("rp_ai: dir is "+dir);
	
    }
}
