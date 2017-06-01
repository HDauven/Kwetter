package com.kwetter.batch;

import javax.batch.api.chunk.ItemReader;
import javax.batch.runtime.context.JobContext;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

/**
 * Created by hein on 5/25/17.
 */
@Dependent
@Named
public class TweetReader implements ItemReader {

    private ItemNumberCheckpoint checkpoint;
    private String fileName;
    private BufferedReader reader;
    @Inject
    private JobContext jobContext;

    @Override
    public void open(Serializable point) throws Exception {
        if (point == null) {
            checkpoint = new ItemNumberCheckpoint();
        } else {
            checkpoint = (ItemNumberCheckpoint) point;
        }
        fileName = jobContext.getProperties().getProperty("input");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        reader = new BufferedReader(new InputStreamReader(inputStream));

        for (int i = 0; i < checkpoint.getLineNumber(); i++) {
            String result = reader.readLine();
            System.out.println("Restart: " + result);
        }
    }

    @Override
    public void close() throws Exception {
        reader.close();
    }

    @Override
    public Object readItem() throws Exception {
        String tweetJson = reader.readLine();
        System.out.println("Read item: " + tweetJson);
        if (tweetJson != null) {
            checkpoint.nextLine();
            return tweetJson;
        } else {
            return null;
        }
    }

    @Override
    public Serializable checkpointInfo() throws Exception {
        return checkpoint;
    }
}
