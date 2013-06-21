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
    public interface RnaseqPipelineActivities {
	
	@Activity(name = "ping", version = "1.0")
	@ExponentialRetry(
			  initialRetryIntervalSeconds=10,
			  backoffCoefficient=1,
			  maximumAttempts=5)
	    public void ping(String msg);

	@Activity(name = "bowtie2", version = "1.0")
	@ExponentialRetry(
			  initialRetryIntervalSeconds=10,
			  backoffCoefficient=1,
			  maximumAttempts=5)
	    public void call_bowtie2(String data_basename, 
				     String bt2_index,
				     String bt2_index_dir) throws IOException;	
	
	@Activity(name = "rnaseq_count.py", version = "1.0")
	@ExponentialRetry(
			  initialRetryIntervalSeconds=10,
			  backoffCoefficient=1,
			  maximumAttempts=5)
	    public void call_rnaseq_count(String inputFileName, String ucsc2ll) throws IOException;
    }
