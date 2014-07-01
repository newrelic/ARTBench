package com.example.artbench;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.newrelic.agent.android.NewRelic;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;


public class MyActivity extends Activity {
    private static final NumberFormat formatter = new DecimalFormat("#.000000000");
    private static final String vmVersion = System.getProperty("java.vm.version");
    private static final int cores = Runtime.getRuntime().availableProcessors();
    private static final int iterations = 10;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        NewRelic.withApplicationToken("your NR application token goes here")
                .start(this.getApplication());

        context = this;

        final Button benchButton = (Button)findViewById(R.id.bench_button);

        // Just a simple button to kick off the benchmark.
        benchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<Long> samples = new ArrayList<Long>();

                /* According to http://developer.android.com/guide/practices/verifying-apps-art.html#GC_Migration
                   If the vm version is equal to or higher than 2.0.0, its ART.  We'll start a custom
                   trace and set its name appropriately to track our performance test.
                */
                if (vmVersion.equals("2.0.0")) {
                    NewRelic.startInteraction(context, "SpectralNormART");
                } else {
                    NewRelic.startInteraction(context, "SpectralNormDalvik");
                }

                Log.d("benchmark", "Starting on " + cores + " core(s)!");

                // We'll run the test a few times to show off the agent's thread tracking abilities.
                for (int i = 0; i < iterations; i++) {
                    final long startTime = System.currentTimeMillis();
                    final double result = SpectralNorm.go();
                    final long stopTime = System.currentTimeMillis();
                    final long finalTime = stopTime - startTime;

                    samples.add(finalTime);

                    Log.d("benchmark", "Iteration " + i + " finished with " + formatter.format(result) + " after " + finalTime + "ms");
                }

                long average = 0;
                for (final long sample : samples) {
                    average += sample;
                }
                average = average / iterations;

                Log.d("benchmark", "All done, average was " + average + " ms!");
            }
        });
    }
}
