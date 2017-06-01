package com.kwetter.batch;

import java.io.Serializable;

/**
 * Created by hein on 5/25/17.
 */
public class ItemNumberCheckpoint implements Serializable{
    private long lineNumber;

    public ItemNumberCheckpoint() {
        lineNumber = 0;
    }

    public long getLineNumber() {
        return lineNumber;
    }

    public void nextLine() {
        lineNumber++;
    }
}
