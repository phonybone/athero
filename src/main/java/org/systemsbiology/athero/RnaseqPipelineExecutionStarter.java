package org.systemsbiology.athero;

import java.util.UUID;

import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import org.systemsbiology.common.ConfigHelper;

/**
 * This is used for launching a Workflow instance of RnaseqPipeline
 */
public class RnaseqPipelineExecutionStarter {
    
    private static AmazonSimpleWorkflow swfService;
    private static String domain;
    
    public static void main(String[] args) throws Exception {
	String usage="prog <data_basename> <ref_index> <dir>";
	if (args.length != 3) {
	    System.out.println(usage);
	    System.exit(1);
	}
	String data_basename=args[0];
	String ref_index=args[1];
	String dir=args[2];

    	// Load configuration
    	ConfigHelper configHelper = ConfigHelper.createConfig();
        
        // Create the client for Simple Workflow Service
        swfService = configHelper.createSWFClient();
        domain = configHelper.getDomain();
        
        // Start Workflow instance
        String executionId = configHelper.getValueFromConfig(ImageProcessingConfigKeys.WORKFLOW_EXECUTION_ID_KEY) + UUID.randomUUID();

	/* Removed a bunch of constant look-ups (having to do with bucket and source file names)
	   from the config */

	// Create workflow via call to factory:
        RnaseqPipelineWorkflowClientExternalFactory clientFactory = new RnaseqPipelineWorkflowClientExternalFactoryImpl(swfService, domain);
        RnaseqPipelineWorkflowClientExternal workflow = clientFactory.getClient(executionId);
	System.out.println("about to call wf.rnaseqPipeline()");
        workflow.rnaseqPipeline(data_basename, ref_index, dir);
        System.exit(0);
    }    
}
