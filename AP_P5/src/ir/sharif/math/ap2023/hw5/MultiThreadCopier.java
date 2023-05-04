package ir.sharif.math.ap2023.hw5;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

public class MultiThreadCopier {
    public static final long SAFE_MARGIN = 6;
    private DownloadedFile downloadedFile;
    private Worker[] workers;
    private SourceReader sourceReader;
    private SourceProvider sourceProvider;
    private WorkManager workManager;
    private long startTime ;
    private long endTime;


    public MultiThreadCopier(SourceProvider sourceProvider, String dest, int workerCount) {
        this.sourceProvider = sourceProvider;
//        startTime = System.currentTimeMillis();
        workers = new Worker[workerCount];
        try {
            downloadedFile = new DownloadedFile(dest, "rw");
            downloadedFile.setLength(0);
            long size = sourceProvider.size();
            downloadedFile.setLength(size);
            long lim = size / workerCount;
            for (int i = 0; i < workerCount - 1; i++) workers[i] = new Worker(i+1,i * lim, lim, this, downloadedFile, sourceProvider);
            workers[workerCount - 1] = new Worker(workerCount,(workerCount - 1) * lim, size - (workerCount - 1) * lim, this,downloadedFile,sourceProvider);
            workManager = new WorkManager(workers,this);
        } catch (IOException e) {
        }
    }

    public void start() {

        startAllThreads();
    }
    private void startAllThreads(){
        for (Worker worker : workers) worker.start();
        workManager.start();
    }
    public void closeAllThreads(){
        for (Worker worker : workers) worker.interrupt();
        workManager.interrupt();
    }

    public DownloadedFile getDownloadedFile() {
        return downloadedFile;
    }

    public void setDownloadedFile(DownloadedFile downloadedFile) {
        this.downloadedFile = downloadedFile;
    }

    public Worker[] getWorkers() {
        return workers;
    }

    public void setWorkers(Worker[] workers) {
        this.workers = workers;
    }

    public SourceReader getSourceReader() {
        return sourceReader;
    }

    public void setSourceReader(SourceReader sourceReader) {
        this.sourceReader = sourceReader;
    }

    public SourceProvider getSourceProvider() {
        return sourceProvider;
    }

    public void setSourceProvider(SourceProvider sourceProvider) {
        this.sourceProvider = sourceProvider;
    }

    public WorkManager getWorkManager() {
        return workManager;
    }

    public void setWorkManager(WorkManager workManager) {
        this.workManager = workManager;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
