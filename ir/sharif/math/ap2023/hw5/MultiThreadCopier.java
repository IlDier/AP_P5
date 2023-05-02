package ir.sharif.math.ap2023.hw5;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class MultiThreadCopier {
    public static final long SAFE_MARGIN = 6;
    private RandomAccessFile downloadedFile;
    private Thread[] workers;
    private SourceReader sourceReader;


    public MultiThreadCopier(SourceProvider sourceProvider, String dest, int workerCount) {
        workers = new Thread[workerCount];
        try {
            downloadedFile = new RandomAccessFile(dest, "rw");
            downloadedFile.setLength(0);
            long size = sourceProvider.size();
            downloadedFile.setLength(size);
            long lim = size / workerCount;
            for (int i = 0 ;i<workerCount;i++){
                int finalI = i;
                sourceReader = sourceProvider.connect(i*lim);
                workers[i] = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        int pos = (int) (finalI *lim);
                        long length = lim;
                    while (!Thread.interrupted()){
                        try {
                            downloadedFile.seek(pos);
                            downloadedFile.write(sourceReader.read());
                            pos++;
                            length--;
                            if(length == 0 ) workers[finalI].interrupt();
                        }catch (IOException o){}
                    }
                    }
                });
            }
        } catch (IOException e) {
        }
    }

    public void start() {

        startAllThreads();
    }
    private void startAllThreads(){
        for (Thread worker : workers) worker.start();
//        workManager.start();
    }
    public void closeAllThreads(){
        for (Thread worker : workers) worker.interrupt();
    }

    public RandomAccessFile getDownloadedFile() {
        return downloadedFile;
    }

    public void setDownloadedFile(RandomAccessFile downloadedFile) {
        this.downloadedFile = downloadedFile;
    }



    public SourceReader getSourceReader() {
        return sourceReader;
    }

    public void setSourceReader(SourceReader sourceReader) {
        this.sourceReader = sourceReader;
    }



}
