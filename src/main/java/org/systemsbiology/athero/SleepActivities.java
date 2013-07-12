package org.systemsbiology.athero;

import java.io.IOException;

import com.amazonaws.services.simpleworkflow.flow.annotations.Activities;
import com.amazonaws.services.simpleworkflow.flow.annotations.Activity;
import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions;
import com.amazonaws.services.simpleworkflow.flow.annotations.ExponentialRetry;
import com.amazonaws.services.simpleworkflow.flow.common.FlowConstants;


/**
 * Contract for file processing activities
 */
@Activities
@ActivityRegistrationOptions(
			     defaultTaskHeartbeatTimeoutSeconds = FlowConstants.NONE, 
			     defaultTaskScheduleToCloseTimeoutSeconds = 300, 
			     defaultTaskScheduleToStartTimeoutSeconds = FlowConstants.NONE, 
			     defaultTaskStartToCloseTimeoutSeconds = 300)
    public interface SleepActivities {

	@Activity(name = "sleep1", version = "1.0")
	@ExponentialRetry(
			  initialRetryIntervalSeconds=10,
			  backoffCoefficient=1,
			  maximumAttempts=5)
	    public String call_sleep1(final String msg,
				      final int n_secs) throws IOException;
	
	@Activity(name = "sleep2", version = "1.0")
	@ExponentialRetry(
			  initialRetryIntervalSeconds=10,
			  backoffCoefficient=1,
			  maximumAttempts=5)
	    public void call_sleep2(String msg, 
				    int n_secs) throws IOException;
    }
