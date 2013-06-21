package org.systemsbiology.athero;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.flow.WorkflowWorker;
import org.systemsbiology.common.ConfigHelper;


/**
 * This is the process which hosts all SWF Deciders and Activities specified in this package
 */

public class WorkflowHost {  
    private static AmazonSimpleWorkflow swfService;
    private static String domain;
    private static WorkflowWorker executor;
    private static WorkflowHost host;

    // Factory method for Workflow worker
    public synchronized static WorkflowHost getWorkflowWorker() {
        if (host == null) {
            host = new WorkflowHost();
        }
        return host;
    }

    public static void main(String[] args) throws Exception {
    	ConfigHelper configHelper = loadConfiguration();
    	
        // Start Activity Executor Service
        getWorkflowWorker().startDecisionExecutor(configHelper);

        // Add a Shutdown hook to close ActivityExecutorService
        addShutDownHook();
        
        System.out.println("Please type 'exit' to terminate service.");
        try {
        	String CurLine = "";
        	InputStreamReader converter = new InputStreamReader(System.in);
        	BufferedReader in = new BufferedReader(converter);
        	while (!(CurLine!=null && CurLine.equals("exit"))){
    		  CurLine = in.readLine();
    		}
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        System.exit(0);
    }
        
    /* Is this the decider? */
    private void startDecisionExecutor(ConfigHelper configHelper) throws Exception {
        System.out.println("Starting Executor Host Service...");

        String taskList = configHelper.getValueFromConfig(ImageProcessingConfigKeys.WORKFLOW_WORKER_TASKLIST);
        executor = new WorkflowWorker(swfService, domain, taskList); // WorkflowWorker defined in aws-java-sdk.jar
        executor.addWorkflowImplementationType(RnaseqPipelineWorkflowImpl.class);
        
        // Start Executor Service
        executor.start();

        System.out.println("Executor Host Service Started...");
    }

    private void stopHost() throws InterruptedException {
        System.out.println("Stopping Decision Executor Service...");
        executor.shutdownNow();
        swfService.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        System.out.println("Decision Executor Service Stopped...");
    }
    
    static ConfigHelper loadConfiguration() throws IllegalArgumentException, IOException{
        ConfigHelper configHelper = ConfigHelper.createConfig();

        // Create the client for Simple Workflow Service
        swfService = configHelper.createSWFClient();
        domain = configHelper.getDomain();
        configHelper.createS3Client();
        
        return configHelper;
    }
    
    static void addShutDownHook(){
  	  Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

            public void run() {
                try {
                    getWorkflowWorker().stopHost();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }));    	
  }
}
