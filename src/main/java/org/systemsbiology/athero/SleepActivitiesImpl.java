package org.systemsbiology.athero;

import java.io.*;
import java.io.IOException;

public class SleepActivitiesImpl implements SleepActivities {
	
    public SleepActivitiesImpl() {}

    @Override public String call_sleep1(final String msg, final int n_secs) {
	System.out.println("sleep 1! " + msg);
	call_sleep(msg, n_secs);
	return new Host().getLocalName();
    }

    @Override public void call_sleep2(String msg, int n_secs) {
	System.out.println("sleep 2! " + msg);
	call_sleep(msg, n_secs);
    }

    private void call_sleep(String msg, int n_secs) {
	File dir=new File(System.getProperty("user.dir"));
	SleepLauncher launcher=new SleepLauncher(msg, n_secs, dir);
	launcher.run();
    }
}
