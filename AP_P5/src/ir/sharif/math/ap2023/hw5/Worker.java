package ir.sharif.math.ap2023.hw5;

public class Worker extends Thread implements Runnable{
    private int id =-1;
    private long position = 0;
    private final long lenght;
    private long off;
    private long len;
    private boolean finished = false;
    private MultiThreadCopier multiThreadCopier;
    private DownloadedFile downloadedFile ;
    private SourceProvider sourceProvider;
    private SourceReader sourceReader;
    public Worker(Runnable runnable){
        super(runnable);
        lenght = 0;
    }
    public Worker(int id,long off , long len ,MultiThreadCopier multiThreadCopier,DownloadedFile downloadedFile,SourceProvider sourceProvider){
        this.id = id;
        this.multiThreadCopier = multiThreadCopier;
        this.downloadedFile = downloadedFile;
        this.sourceProvider = sourceProvider;
        this.off = off;
        this.len = len;
        position=off;
        lenght = len;
    }

    @Override
    public void run() {
        try {
            SourceReader sourceReader = sourceProvider.connect(off);


                while (!Thread.interrupted() && !finished) {

                        downloadedFile.importData((int) position, sourceReader.read());
                        position++;
                        len--;
                        if (len == 0) finished = true;


                }
//                multiThreadCopier.setEndTime(System.currentTimeMillis());
//            System.out.println(-multiThreadCopier.getStartTime()+multiThreadCopier.getEndTime());

        } catch (Exception e) {
        }
    }


    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }


    public long getOff() {
        return off;
    }

    public void setOff(long off) {
        this.off = off;
    }

    public long getLen() {
        return len;
    }

    public void setLen(long len) {
        this.len = len;
    }

    public MultiThreadCopier getMultiThreadCopier() {
        return multiThreadCopier;
    }

    public void setMultiThreadCopier(MultiThreadCopier multiThreadCopier) {
        this.multiThreadCopier = multiThreadCopier;
    }

    public long getLenght() {
        return lenght;
    }


    public int getWorkerId(){
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
