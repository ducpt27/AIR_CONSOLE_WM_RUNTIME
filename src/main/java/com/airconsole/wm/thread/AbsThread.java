package com.airconsole.wm.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbsThread extends Thread{
    private static final List<AbsThread> threads = Collections.synchronizedList(new ArrayList<AbsThread>());
    protected Log logger = LogFactory.getLog(this.getClass());
    protected final Object syncLock = new Object();
    protected boolean isRepeat;
    protected long delay = 1;
    protected boolean isHideLog = false;

    public AbsThread(){
        isRepeat = false;
    }

    public AbsThread(boolean isRepeat){
        this.isRepeat = isRepeat;
    }

    public AbsThread(boolean isRepeat, long delay) {
        this.isRepeat = isRepeat;
        this.delay = delay;
    }

    public AbsThread(boolean isRepeat, long delay, String name) {
        this.isRepeat = isRepeat;
        this.delay = delay;
        this.setName(name);
    }

    public void start() {
        threads.add(this);
        if (!isHideLog) {
            logger.info(String.format("[THREAD - %s] is starting ... ", this.getName()));
        }
        super.start();
        if (!isHideLog) {
            logger.info(String.format("[THREAD - %s] started successfully !", this.getName()));
        }
    }

    public void run() {
        do {
            try {
                execute();
            } catch (Exception ex) {
                logger.warn(String.format("[THREAD - %s] throw exception when execute cause by %s", this.getName(), ex));
                ex.printStackTrace();
            } finally {
                try {
                    synchronized (syncLock) {
                        syncLock.wait(delay);
                    }
                } catch (Exception ex) {
                    logger.warn(String.format("[THREAD - %s] is interrupted !", this.getName()));
                    ex.printStackTrace();
                }
            }
        }
        while (isRepeat);

    }

    protected abstract void execute() throws Exception;

    public void kill(){
        while (this.isAlive()){
            if (isRepeat) {
                isRepeat = false;
            }
            synchronized (syncLock){
                syncLock.notifyAll();
            }
        }
        logger.warn(String.format("[THREAD - %s] is interrupted",this.getName()));
    }

    public static void stopAllThread(){
        for(AbsThread thread : threads){
            thread.kill();
        }
        System.err.println("[ABS_THREAD] - STOPPED ALL THREAD");
    }

    public void disableLog(){
        this.isHideLog = true;
    }

    public void enableLog(){
        this.isHideLog = false;
    }
}
