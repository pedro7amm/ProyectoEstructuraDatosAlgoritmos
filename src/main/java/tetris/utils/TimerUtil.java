/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tetris.utils;

import java.util.Timer;
import java.util.TimerTask;

//Clase TimerUtil: Encargada de Gestionar eventos temporales del juego

public class TimerUtil {
    private Timer timer;
    private long interval;
    private TimerTask task;

    public TimerUtil(long interval) {
        this.interval = interval;
        this.timer = new Timer();
    }

    // Iniciar el temporizador 
    public void startTimer(Runnable task) {
        this.task = new TimerTask() {
            @Override
            public void run() {
                task.run();
            }
        };
        // Controla los intervalos en segundos 
        timer.scheduleAtFixedRate(this.task, 0, interval); 
    }

    // Detener el temporizador 
    public void stopTimer() {
        if (task != null) {
            task.cancel();
        }
    }
}

    

