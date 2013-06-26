package org.systemsbiology.athero;

import java.util.UUID;

import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import org.systemsbiology.common.ConfigHelper;

/**
 * This is used for launching a Workflow instance of the Ping wf
 */
public class PingExecutionStarter {
    
    private static AmazonSimpleWorkflow swfService;
    private static String domain;
    
    public static void main(String[] args) throws Exception {
    	
    	// Load configuration
    	ConfigHelper configHelper = ConfigHelper.createConfig();
        
        // Create the client for Simple Workflow Service
        swfService = configHelper.createSWFClient();
        domain = configHelper.getDomain();
        
        // Start Workflow instance
        String executionId = configHelper.getValueFromConfig(ImageProcessingConfigKeys.WORKFLOW_EXECUTION_ID_KEY) + UUID.randomUUID();

	/* Removed a bunch of constant look-ups from the config */

	// Create workflow via call to factory:
        PingWorkflowClientExternalFactory clientFactory = new PingWorkflowClientExternalFactoryImpl(swfService, domain);
        PingWorkflowClientExternal workflow = clientFactory.getClient(executionId);
	System.out.println("about to call wf.ping(1047-COPD.10K)");
        workflow.ping("1047-COPD.10K");
        System.exit(0);
    }    
}
