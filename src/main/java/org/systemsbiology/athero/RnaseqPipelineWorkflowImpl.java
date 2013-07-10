package org.systemsbiology.athero;

import java.io.File;
import java.io.IOException;

import com.amazonaws.services.simpleworkflow.flow.ActivitySchedulingOptions;
import com.amazonaws.services.simpleworkflow.flow.DecisionContextProviderImpl;
import com.amazonaws.services.simpleworkflow.flow.WorkflowContext;
import com.amazonaws.services.simpleworkflow.flow.annotations.Asynchronous;
import com.amazonaws.services.simpleworkflow.flow.core.Promise;
import com.amazonaws.services.simpleworkflow.flow.core.Settable;
import com.amazonaws.services.simpleworkflow.flow.core.TryCatchFinally;

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
	System.out.println("rp_wfimpl created on tasklist "+workflowContext.getTaskList());
    }
	

    @Override
	public void rnaseqPipeline(final String data_basename,
				   final String bt2_index,
				   final String dir) throws IOException {    	
    	// Settable to store the worker specific task list returned by the activity
    	final Settable<String> taskList = new Settable<String>();

    	new TryCatchFinally() {
            @Override
		protected void doTry() throws Throwable {
		// Call bowtie2
		System.out.println("rpwi: about to call call_bowtie2");
		Promise<String> ac_tl=rp_ac.call_bowtie2(data_basename, bt2_index, dir);
		taskList.chain(ac_tl);

		// Call rnaseq_count.py
		if (taskList.isReady()) {
		    System.out.println("rpwi: tasklist="+taskList.get());
		    ActivitySchedulingOptions options = new ActivitySchedulingOptions();
		    options.withTaskList(taskList.get());
		    
		    // including tasklist as an arg to force asynch calls...?
		    Promise<Void> done=rp_ac.call_rnaseq_count(data_basename, dir, options, taskList);
		
		} else {
		    System.out.println("rpwi: tasklist not yet ready");
		}
		
		System.out.println("doTry() complete");
            }

	    @Override
		protected void doCatch(Throwable t) {
		System.out.println(t.getMessage());
		t.printStackTrace();
	    }

            @Override
		protected void doFinally() throws Throwable {
		System.out.println("doFinally cleaning up (no-op)");
            }
        };
    }

    /*
    @Override
	public void rnaseqPipeline(final String data_basename) throws IOException {
	new TryFinally() {
	    @Override
		protected void doTry() throws Throwable {
		System.out.println("wf started("+data_basename+")");
		rp_ac.ping("this is message 1");
		rp_ac.ping("this is message 2");
		System.out.println("wf done");
	    }

	    @Override
		protected void doCatch(Throwable e) throws Throwable {
		System.out.println("something bad happened: "+e.getMessage());
	    }

	    @Override
		protected void doFinally() throws Throwable {
	    }
	};
    }
    */
}
