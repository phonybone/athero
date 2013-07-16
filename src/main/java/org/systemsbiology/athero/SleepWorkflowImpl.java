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
	log.debug("sleep_wfimpl tasklist: "+workflowContext.getTaskList());
	log.debug("sleep_wfimpl execution id: "+workflowContext.getWorkflowExecution().getWorkflowId());
	
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
    /*
    @Override
	public void sleep() throws IOException {    	
	log.debug("swfi: sleep entered");
    	// Settable to store the worker specific task list returned by the activity
    	final Settable<String> taskList = new Settable<String>();

    	new TryCatchFinally() {
            @Override
		protected void doTry() throws Throwable {
		// Call sleep1
		log.debug("swfi: about to call sleep1");
		//String ac_tl=sleep_ac.call_sleep1("sleep for 3 seconds", 3);
		Promise<String> ac_tl=sleep_ac.call_sleep1("sleep for 3 seconds", 3);
		taskList.chain(ac_tl);

		// Call sleep2
		Promise<Void> done=call_sleep2("sleep for 5 seconds", 5, taskList);
		log.debug("swfi: doTry() complete");
            }

	    @Override
		protected void doCatch(Throwable t) {
		log.debug("oh crap: "+t.getMessage());
		//		t.printStackTrace();
	    }

            @Override
		protected void doFinally() throws Throwable {
		log.debug("swfi: doFinally cleaning up (no-op)");
            }
        };
    }
    */

    @Asynchronous
	protected Promise<Void> call_sleep2(final String msg, int n_secs, Promise<String> taskList) {
	log.debug("sleep2: launching");
	ActivitySchedulingOptions options = new ActivitySchedulingOptions();
	options.withTaskList(taskList.get());
	    
	//		Promise<Void> done=sleep_ac.call_sleep2("sleep for 5 seconds", 5, options, taskList);
	Promise<Void> done=sleep_ac.call_sleep2(Promise.asPromise(msg), Promise.asPromise(n_secs), options, taskList);
	log.debug("sleep2: launched");
	return done;
    }
}
