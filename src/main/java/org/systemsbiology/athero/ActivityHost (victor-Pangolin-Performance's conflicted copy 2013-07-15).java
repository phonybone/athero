package org.systemsbiology.athero;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.flow.ActivityWorker;

import org.systemsbiology.common.ConfigHelper;

import org.apache.log4j.Logger;


/**
 * This is the process which hosts all Activities in this sample
 */
public class ActivityHost {    
    private static AmazonSimpleWorkflow swfService;
    private static String domain;
    private static AmazonS3 s3Client;
    
    private static ActivityWorker executorForCommonTaskList;
    private static ActivityWorker executorForHostSpecificTaskList;
    private static ActivityHost activityHost;

    private static final Logger log=Logger.getLogger(ActivityHost.class.getName());

    // ActivityWorker Factory method
    public synchronized static ActivityHost getActivityHost() {
        if (activityHost == null) {
            activityHost = new ActivityHost();
        }
        return activityHost;
    }

    public static void main(String[] args) throws Exception {
    	// Load configuration
    	ConfigHelper configHelper = loadConfig();

        // Start Activity Executor Services
        getActivityHost().startExecutors(configHelper);
                        
        // Add a Shutdown hook to close ActivityExecutorService
        addShutDownHook();

	log.debug("---> listening on "+getActivityHost().executorForCommonTaskList.getTaskListToPoll()); // fix this

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
    
    private void startExecutors(ConfigHelper configHelper) throws Exception {   
        String commonTaskList = configHelper.getValueFromConfig(ImageProcessingConfigKeys.ACTIVITY_WORKER_COMMON_TASKLIST);
        
    	// Create activity implementations
	/* Deleted other ActivityImplementation, ie SimpleStore*/

    	RnaseqPipelineActivitiesImpl rnaseqPipelineImpl = new RnaseqPipelineActivitiesImpl();
	PingActivitiesImpl pingActivitiesImpl = new PingActivitiesImpl();
	SleepActivitiesImpl sleepActivitiesImpl = new SleepActivitiesImpl();
	executorForCommonTaskList=createExecutor(commonTaskList, 
						 pingActivitiesImpl, 
						 sleepActivitiesImpl,
						 rnaseqPipelineImpl);
	log.debug("listening on common task list "+commonTaskList);
    	
    	// Start executor to poll the host specific task list
	/*
    	executorForHostSpecificTaskList = createExecutor(getHostName(), 
							 sleepActivitiesImpl,
							 pingActivitiesImpl,
							 rnaseqPipelineImpl);
    	executorForHostSpecificTaskList.start();
	log.debug("listening on host task list "+getHostName());
	*/
    }
    
    private ActivityWorker createExecutor(String taskList, Object ...activityImplementations) throws Exception{        
	log.debug("Creating ActivityExecutor for tasklist "+taskList);
        ActivityWorker worker = new ActivityWorker(swfService, domain, taskList);
    	for (Object activityImplementation: activityImplementations) {
    	    worker.addActivitiesImplementation(activityImplementation);
    	}
    	
        return worker;
    }

    private void stopExecutors() throws InterruptedException {
        log.debug("Stopping Executor Services...");
        executorForCommonTaskList.shutdownNow();
        executorForHostSpecificTaskList.shutdownNow();
        swfService.shutdown();
        executorForCommonTaskList.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS); // that's a long time to wait
        log.debug("Executor Services Stopped...");
    }
    
    
    static ConfigHelper loadConfig() throws IllegalArgumentException, IOException{
       	ConfigHelper configHelper = ConfigHelper.createConfig();

        // Create the client for Simple Workflow Service and S3 Service        
        swfService = configHelper.createSWFClient();
        domain = configHelper.getDomain();
        s3Client = configHelper.createS3Client();
        
        return configHelper;
    }
    
    static void addShutDownHook(){
    	  Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

              public void run() {
                  try {
                      getActivityHost().stopExecutors();
                  }
                  catch (InterruptedException e) {
                      e.printStackTrace();
                  }
              }
          }));    	
    }
    
    static String getHostName() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            return addr.getHostName();
        }
        catch (UnknownHostException e) {
            throw new Error(e);
        }
    }

}

/*
  Notes:
  Want to refactor as much as possible:
  Names of Activities classes (args to createExecutor)
  Names of tasklists to listen for
 */