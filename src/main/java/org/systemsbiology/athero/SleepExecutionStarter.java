package org.systemsbiology.athero;

import java.util.UUID;

import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import org.systemsbiology.common.ConfigHelper;
import org.apache.log4j.Logger;

/**
 * This is used for launching a Workflow instance of the Sleep wf
 */
public class SleepExecutionStarter {
    
    private static AmazonSimpleWorkflow swfService;
    private static String domain;
    static String classname=SleepExecutionStarter.class.getName();
    static Logger log=Logger.getLogger(SleepExecutionStarter.class.getName());

    public static void main(String[] args) throws Exception {
    	
    	// Load configuration
    	ConfigHelper configHelper = ConfigHelper.createConfig();
        
        // Create the client for Simple Workflow Service
        swfService = configHelper.createSWFClient();
        domain = configHelper.getDomain();
        
        // Start Workflow instance
        String executionId = configHelper.getValueFromConfig(ImageProcessingConfigKeys.WORKFLOW_EXECUTION_ID_KEY) + UUID.randomUUID();
	System.out.println("executionId: "+executionId);

	// Create workflow via call to factory:
        SleepWorkflowClientExternalFactory clientFactory = new SleepWorkflowClientExternalFactoryImpl(swfService, domain);
        SleepWorkflowClientExternal workflow = clientFactory.getClient(executionId);
        workflow.sleep();
        System.exit(0);
    }    
}
