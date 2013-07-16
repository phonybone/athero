package org.systemsbiology.athero;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import com.amazonaws.services.simpleworkflow.flow.ActivitySchedulingOptions;
import com.amazonaws.services.simpleworkflow.flow.DecisionContextProviderImpl;
import com.amazonaws.services.simpleworkflow.flow.WorkflowContext;
import com.amazonaws.services.simpleworkflow.flow.annotations.Asynchronous;
import com.amazonaws.services.simpleworkflow.flow.core.Promise;
import com.amazonaws.services.simpleworkflow.flow.core.Settable;
import com.amazonaws.services.simpleworkflow.flow.core.TryCatchFinally;
import com.amazonaws.services.simpleworkflow.flow.core.TryFinally;
import org.apache.log4j.Logger;

import org.apache.log4j.Logger;

public class SleepWorkflowImpl implements SleepWorkflow {
    private final SleepActivitiesClient sleep_ac;
    private final WorkflowContext workflowContext;
    private static final Logger log=Logger.getLogger(SleepWorkflowImpl.class.getName());

    public SleepWorkflowImpl(){
	// Create activity clients
	sleep_ac = new SleepActivitiesClientImpl(); 

	// This is now obsolete, but not deleting it yet.  
	// Was used to determine a filename.
	// todo: look up DecisionContext and WorkflowContext
	workflowContext = (new DecisionContextProviderImpl()).getDecisionContext().getWorkflowContext();
	log.debug("swfi: tasklist = "+workflowContext.getTaskList());
	log.debug("swfi execution id = "+workflowContext.getWorkflowExecution().getWorkflowId());

	
    }
	
    @Override
	public void sleep() throws IOException {    	
	log.debug("swfi: sleep entered");
	Promise<String> tasklist=sleep_ac.call_sleep1("sleep for 3 seconds", 3);
	call_sleep2(tasklist);
    }

    @Asynchronous
	private void call_sleep2(Promise<String> tasklist) {
	sleep_ac.call_sleep2("2) sleep for 2 seconds", 2, tasklist);
	//	return Promise.Void();
    }
}
