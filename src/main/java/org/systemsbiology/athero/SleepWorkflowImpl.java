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

	workflowContext = (new DecisionContextProviderImpl()).getDecisionContext().getWorkflowContext();
	System.out.println("sleep_wfimpl tasklist: "+workflowContext.getTaskList());
	System.out.println("sleep_wfimpl execution id: "+workflowContext.getWorkflowExecution().getWorkflowId());
	
    }
	

    @Override
	public void sleep() throws IOException {    	

	System.out.println("calling sleep1");
	Promise<String> ac_tl=sleep_ac.call_sleep1("sleep for 3 seconds", 3);

	System.out.println("calling sleep1");
	sleep_ac.call_sleep2("this is the second message", 3, ac_tl);
    }

    @Asynchronous
	public Promise<Void> printPromise(Promise<String> p) {
	System.out.println("promise: "+p.get());
	return new Promise<Void>;
    }
}
