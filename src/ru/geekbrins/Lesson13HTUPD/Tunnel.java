package ru.geekbrins.Lesson13HTUPD;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {

    private final Semaphore smp;
    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
        this.smp = new Semaphore(MainClass.CARS_COUNT/2); //лимитируем количество потоков в тоннеле
    }
    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
// в этой секции поток захватывает семафор
                smp.acquire();
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(c.getName() + " закончил этап: " + description);
// отпускаем семафор
                smp.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}