package org.systemsbiology.athero;

import java.io.*;
import java.io.IOException;

public class SleepActivitiesImpl implements SleepActivities {
	
    public SleepActivitiesImpl() {}

    @Override public String call_sleep1(final String msg, final int n_secs) {
	System.out.println("sai: sleep 1! " + msg);
	call_sleep(msg, n_secs);
	String name=new Host().getLocalName();
	System.out.println("sai: sleep1 returning "+name);
	return name;
    }

    @Override public void call_sleep2(final String msg, final int n_secs) {
	System.out.println("sai: sleep 2! " + msg);
	call_sleep(msg, n_secs);
    }

    private void call_sleep(final String msg, final int n_secs) {
	File dir=new File(System.getProperty("user.dir"));
	SleepLauncher launcher=new SleepLauncher(msg, n_secs, dir);
	launcher.run();
    }
}
