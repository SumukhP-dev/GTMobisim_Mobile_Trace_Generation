// Copyright (c) 2012, Georgia Tech Research Corporation
// Authors:
//   Peter Pesti (pesti@gatech.edu)
//
package edu.gatech.lbs.sim.scheduling.activity;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import edu.gatech.lbs.sim.Simulation;
import edu.gatech.lbs.sim.tracegenerator.ITraceGenerator;

public class TraceGenerationActivity implements ISimActivity {
  protected String traceFilename;
  protected ITraceGenerator traceGenerator;
  protected boolean isOverwriteAllowed;

  public TraceGenerationActivity(String traceFilename, ITraceGenerator traceGenerator, boolean isOverwriteAllowed) {
    this.traceFilename = traceFilename;
    this.traceGenerator = traceGenerator;
    this.isOverwriteAllowed = isOverwriteAllowed;
  }

  public void scheduleOn(Simulation sim) {
    // if (!isOverwriteAllowed) {
    //   return;
    // }
    
    long startTime = System.nanoTime();
    System.out.println(" Generating trace '" + traceFilename + "'... ");
    try {
      traceGenerator.generateTrace(traceFilename);
      // allow garbage collection of generation setup:
      traceGenerator = null;
    } catch (IOException e) {
      System.out.println(" failed.");
      System.exit(-1);
    }

    long endTime = System.nanoTime();
    long elapsedTime = endTime - startTime;
    System.out.println(" done with "
    + TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS) + " milliseconds elapsed");

    Runtime runtime = Runtime.getRuntime();
    long memoryUsed = runtime.totalMemory() - runtime.freeMemory();
    System.out.println("Memory used: " + (memoryUsed / (1024 * 1024)) + " in Megabytes");
  }

  public void cleanup() {
    // do nothing
  }
}
