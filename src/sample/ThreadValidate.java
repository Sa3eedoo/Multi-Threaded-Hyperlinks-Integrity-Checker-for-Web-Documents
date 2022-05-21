package sample;

public class ThreadValidate extends Thread {
    private String link;
    private int currentDepth;
    private int totalDepth;
    private int nThreads;

    public ThreadValidate(String link, int currentDepth, int totalDepth, int nThreads) {
        this.link = link;
        this.currentDepth = currentDepth;
        this.totalDepth = totalDepth;
        this.nThreads = nThreads;
    }

    @Override
    public void run() {
        try {
            LinkValidation.linksValidation(link, currentDepth, totalDepth, nThreads);
        } catch (Exception exception){}
    }
}
