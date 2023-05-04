package ir.sharif.math.ap2023.hw5;

public class WorkManager extends Thread implements Runnable{
    private Worker[] workers;
    private MultiThreadCopier multiThreadCopier;

    public WorkManager(Worker[] workers , MultiThreadCopier multiThreadCopier){
        this.multiThreadCopier = multiThreadCopier;
        this.workers = workers;
    }
    @Override
    public  void run() {
        while (!Thread.interrupted()){
            Worker idle = findIdleWorker(multiThreadCopier.getWorkers());
            Worker busy = findBusyWorker(multiThreadCopier.getWorkers());
            if(idle != null && busy != null){
                long remainLength = busy.getLen();
                System.out.println(remainLength+":");
                System.out.println("Busy Position: "+busy.getPosition());
                if(MultiThreadCopier.SAFE_MARGIN < remainLength) {
                    System.out.println("Remain lenght is :"+remainLength);
                    workers[idle.getWorkerId()-1].interrupt();
                    workers[busy.getWorkerId()-1].interrupt();
                    workers[idle.getWorkerId()-1] = new Worker(idle.getWorkerId(),busy.getPosition(), (remainLength+1) / 2, multiThreadCopier,multiThreadCopier.getDownloadedFile(), multiThreadCopier.getSourceProvider());
                    workers[busy.getWorkerId()-1] = new Worker(busy.getWorkerId(),busy.getPosition()+(remainLength+1)/2,remainLength/2,multiThreadCopier,multiThreadCopier.getDownloadedFile(), multiThreadCopier.getSourceProvider());
                }
            }
            boolean visit = false;
            for(Worker worker : workers) {
                if (!worker.isFinished()) {
                    visit = true;
                    break;
                }
            }
            if(!visit){
                multiThreadCopier.closeAllThreads();
            }
            try {
                sleep(100);
            } catch (InterruptedException e) {

            }
        }

    }
    private Worker findIdleWorker(Worker[] workers){
        for(Worker worker:workers){
            if(worker.isFinished()) return worker;
        }
        return null;
    }
    private Worker findBusyWorker(Worker[] workers){
        long len = -1;
        Worker res = null;
            for (Worker worker : workers) {
                if (!worker.isFinished() ){
                    if(worker.getLen() > len){
                        len = worker.getLen();
                        res = worker;
                    }
                }
            }
        return res;
    }
}
