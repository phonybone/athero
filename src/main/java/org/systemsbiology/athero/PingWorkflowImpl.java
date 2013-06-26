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

public class PingWorkflowImpl implements PingWorkflow {
    private final PingActivitiesClient ping_ac;
    private final WorkflowContext workflowContext;
	
    public PingWorkflowImpl(){
	// Create activity clients
	ping_ac = new PingActivitiesClientImpl(); 

	// This is now obsolete, but not deleting it yet.  
	// Was used to determine a filename.
	// todo: look up DecisionContext and WorkflowContext
	workflowContext = (new DecisionContextProviderImpl()).getDecisionContext().getWorkflowContext();
	System.out.println("ping_wfimpl created on tasklist "+workflowContext.getTaskList());
    }
	


    @Override
	public void ping(final String msg) throws IOException {
	new TryFinally() {
	    @Override
		protected void doTry() throws Throwable {
		System.out.println("wf started("+msg+")");
		ping_ac.ping("this is message 1");
		ping_ac.ping("this is message 2");
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
}
