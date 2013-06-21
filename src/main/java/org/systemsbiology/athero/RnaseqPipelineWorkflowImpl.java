package org.systemsbiology.athero;

import java.io.File;
import java.io.IOException;

import com.amazonaws.services.simpleworkflow.flow.ActivitySchedulingOptions;
import com.amazonaws.services.simpleworkflow.flow.DecisionContextProviderImpl;
import com.amazonaws.services.simpleworkflow.flow.WorkflowContext;
import com.amazonaws.services.simpleworkflow.flow.annotations.Asynchronous;
import com.amazonaws.services.simpleworkflow.flow.core.Promise;
import com.amazonaws.services.simpleworkflow.flow.core.Settable;
import com.amazonaws.services.simpleworkflow.flow.core.TryFinally;

public class RnaseqPipelineWorkflowImpl implements RnaseqPipelineWorkflow {
    private final RnaseqPipelineActivitiesClient rp_ac;
    private final WorkflowContext workflowContext;
	
    public RnaseqPipelineWorkflowImpl(){
	// Create activity clients
	rp_ac = new RnaseqPipelineActivitiesClientImpl(); 

	// This is now obsolete, but not deleting it yet.  
	// Was used to determine a filename.
	// todo: look up DecisionContext and WorkflowContext
	workflowContext = (new DecisionContextProviderImpl()).getDecisionContext().getWorkflowContext();
    }
	

    /*
    @Override
	public void rnaseqPipeline(final String data_basename) throws IOException {    	
    	// Settable to store the worker specific task list returned by the activity
    	final Settable<String> taskList = new Settable<String>();

    	// construct input filename?
    	
    	new TryFinally() {
            @Override
		protected void doTry() throws Throwable {
		// Call bowtie2
		System.out.println("rpwi: about to call bowtie2");
		rp_ac.call_bowtie2(data_basename, "hg_id", "bt2_index_dir");
		System.out.println("rpwi: about to call rnaseq_count");

		// Call rnaseq_count.py
		rp_ac.call_rnaseq_count("inputFilename", "ucsc2ll");
		System.out.println("rpwi: done");
            }

            @Override
		protected void doFinally() throws Throwable {
            }
        };
    }
    */

    @Override
	public void rnaseqPipeline(final String data_basename) throws IOException {
	new TryFinally() {
	    @Override
		protected void doTry() throws Throwable {
		rp_ac.ping("this is message 1");
		rp_ac.ping("this is message 2");
	    }

	    @Override
		protected void doFinally() throws Throwable {
	    }
	};
    }
}
