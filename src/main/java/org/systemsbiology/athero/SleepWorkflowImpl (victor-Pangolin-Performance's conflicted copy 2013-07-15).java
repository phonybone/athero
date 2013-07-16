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

public class SleepWorkflowImpl implements SleepWorkflow {
    private final SleepActivitiesClient sleep_ac;
    private final WorkflowContext workflowContext;

    private final Logger log=Logger.getLogger(SleepWorkflowImpl.class.getName());
	
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
	Promise<String> ac_tl=sleep_ac.call_sleep1("sleep for 3 seconds", 3);
	log.debug("swfi: call_sleep1 returned, done");
    }

    /*
    @Override
	public void sleep() throws IOException {    	
	log.debug("swfi: sleep entered");
    	// Settable to store the worker specific task list returned by the activity
	//    	final Settable<String> taskList = new Settable<String>();
	new TryCatchFinally() {
	    @Override
		protected void doTry() {
		
		log.debug("swfi: about to call sleep1");
		Promise<String> ac_tl=sleep_ac.call_sleep1("sleep for 3 seconds", 3);
		log.debug("swfi:  returned from call_sleep1");

		//		taskList.chain(ac_tl);
		
		//		Promise<Void> done=call_sleep2("sleep for 5 seconds", 5, taskList);
		//		log.debug("swfi: doTry() complete");
	    }
	    @Override
		protected void doCatch(Throwable t) {
		log.warn("SWFI: CAUGHT "+t.getMessage());
		StringWriter sw=new StringWriter();
		log.warn("SWFI: "+sw.toString());
	    }
	    @Override
		protected void doFinally() {
		log.debug("swfi: cleaning up (no-op)");
	    }
	};
	log.debug("swfi: done");
    }
    */

    @Asynchronous
	protected Promise<Void> call_sleep2(final String msg, int n_secs, Promise<String> taskList) {
	log.debug("sleep2: launching");
	//ActivitySchedulingOptions options = new ActivitySchedulingOptions();
	//options.withTaskList(taskList.get());
	    
	Promise<Void> done=sleep_ac.call_sleep2("sleep for 5 seconds", 5, taskList);
	//	Promise<Void> done=sleep_ac.call_sleep2(Promise.asPromise(msg), Promise.asPromise(n_secs), options, taskList);
	log.debug("sleep2: launched");
	return done;
    }
}
