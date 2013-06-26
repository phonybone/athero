package org.systemsbiology.athero;

import java.io.*;
import java.io.IOException;

import org.systemsbiology.ScriptLauncher; // fixme: this will be replaced with Bowtie2Launcher
import org.systemsbiology.athero.Bowtie2Launcher;

public class PingActivitiesImpl implements PingActivities{
	
    public PingActivitiesImpl() {
    }

    @Override
	public void ping(String msg) {
	System.out.println("ping! " + msg);
    }
}
