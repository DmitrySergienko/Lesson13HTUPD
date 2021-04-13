package ru.geekbrins.Lesson13HTUPD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Race {
    private final ArrayList<Stage> stages;
    private final Lock lock;
    private final CountDownLatch allFinish;
    private boolean haveWinner;
    private final Set<Car> arrivedFinish;

    public ArrayList<Stage> getStages() { return stages; }

    public Race(CountDownLatch allFinish, Stage... stages) {
        this.stages = new ArrayList<>(Arrays.asList(stages));
        this.lock = new ReentrantLock();
        this.allFinish = allFinish;
        this.arrivedFinish = new HashSet<>();
    }


    public void finishRace(Car car){
        try {
            lock.lock();
            if(arrivedFinish.contains(car)){
                return;
            }
            arrivedFinish.add(car);
            System.out.println("Привехал на финиш" + car.getName());
            if(!haveWinner){
                haveWinner = true;
                System.out.println("Winner: " + car.getName() + " !!!");
                System.out.println("___________________________");
            }
            allFinish.countDown();
        }finally {
            lock.unlock();

            //allFinish.countDown();

        }
    }
}



