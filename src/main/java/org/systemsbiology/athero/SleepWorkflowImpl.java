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
import com.amazonaws.services.simpleworkflow.flow.core.TryFinally;

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
	protected Promise<Void> call_sleep2(final String msg, int n_secs, Promise<String> taskList) {
	System.out.println("sleep2: launching");
	ActivitySchedulingOptions options = new ActivitySchedulingOptions();
	options.withTaskList(taskList.get());
	    
	//		Promise<Void> done=sleep_ac.call_sleep2("sleep for 5 seconds", 5, options, taskList);
	Promise<Void> done=sleep_ac.call_sleep2(Promise.asPromise(msg), Promise.asPromise(n_secs), options, taskList);
	System.out.println("sleep2: launched");
	return done;
    }
}
