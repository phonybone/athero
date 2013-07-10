package org.systemsbiology.athero;

import java.io.*;
import java.io.IOException;

import org.systemsbiology.athero.Bowtie2Launcher;

public class RnaseqPipelineActivitiesImpl implements RnaseqPipelineActivities{
	
    public RnaseqPipelineActivitiesImpl() {
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
	public void call_rnaseq_count(final String data_basename,
				      final String dir) throws IOException {
	System.out.println("rp_ai: call_rnaseq_count("+data_basename+ ", dir)");
	RnaseqCountLauncher launcher=new RnaseqCountLauncher(data_basename, new File(dir));
	int rc=launcher.run();
    }
}
