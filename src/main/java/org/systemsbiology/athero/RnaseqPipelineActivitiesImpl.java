package org.systemsbiology.athero;

import java.io.File;
import java.io.IOException;


/**
 * This implementation of FileProcessingActivities converts the file
 */
public class RnaseqPipelineActivitiesImpl implements RnaseqPipelineActivities{
	
    /*This constructor will create a implementation of RnaseqPipelineActrivities
     * @param 
     */
    public RnaseqPipelineActivitiesImpl() {
    }
                
    /**
     * This is the Activity implementation that does the convert of a file to Grayscale
     * @param inputFileName
     *          Name of file to convert
     * @param targetFileName
     *          Filename after convert
     */	
    @Override    
	public void call_bowtie2(String data_basename, 
				 String bt2_index,
				 String bt2_index_dir) throws IOException {
	System.out.println("call_bowtie2 called");
    }
	
    /**
     * This is the Activity implementation that does the convert of a file to Sepia
     * @param inputFileName
     *          Name of file to convert
     * @param targetFileName
     *          Filename after convert
     */	
    @Override
	public void call_rnaseq_count(String inputFileName, 
				      String ucsc2ll) throws IOException {
	System.out.println("call_rnaseq_count called");
    }
}
