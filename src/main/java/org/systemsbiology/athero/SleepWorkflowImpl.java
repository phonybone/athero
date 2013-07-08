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

public class SleepWorkflowImpl implements SleepWorkflow {
    private final SleepActivitiesClient sleep_ac;
    private final WorkflowContext workflowContext;
	
    public SleepWorkflowImpl(){
	// Create activity clients
	sleep_ac = new SleepActivitiesClientImpl(); 

	// This is now obsolete, but not deleting it yet.  
	// Was used to determine a filename.
	// todo: look up DecisionContext and WorkflowContext
	workflowContext = (new DecisionContextProviderImpl()).getDecisionContext().getWorkflowContext();
	System.out.println("sleep_wfimpl created on tasklist "+workflowContext.getTaskList());
    }
	

    @Override
	public void sleep() throws IOException {    	
			  
    	// Settable to store the worker specific task list returned by the activity
    	final Settable<String> taskList = new Settable<String>();

    	new TryCatchFinally() {
            @Override
		protected void doTry() throws Throwable {
		// Call sleep1
		System.out.println("rpwi: about to call sleep1");
		Promise<String> ac_tl=sleep_ac.call_sleep1("sleep for 3 seconds", 3);
		taskList.chain(ac_tl);

		// Call sleep2
		if (taskList.isReady()) {
		    System.out.println("rpwi: about to call sleep2 on tasklist="+taskList.get());
		    ActivitySchedulingOptions options = new ActivitySchedulingOptions();
		    options.withTaskList(taskList.get());
		    
		    Promise<Void> done=sleep_ac.call_sleep2("sleep for 5 seconds", 5, options);
		    System.out.println("sleep2: done");
		} else {
		    System.out.println("taskList not yet ready");
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
}
