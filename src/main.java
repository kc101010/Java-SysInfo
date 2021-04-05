
import java.io.*;

//https://www.baeldung.com/java-lang-unsupportedclassversion
//https://stackoverflow.com/questions/47177/how-do-i-monitor-the-computers-cpu-memory-and-disk-usage-in-java

public class main {
    public static void main(String[] args) throws InterruptedException, IOException {

            SystemMonitor pc = new SystemMonitor();
            UI ui = new UI(pc.getOS(), pc.getGPU(), pc.getUsername(), pc.getSysMemory(), pc.getArch(), pc.getThreads());


            while(true){
                ui.writeCPU_usage(pc.calculateCPU_usage());
                Thread.sleep(600);
            }

    }

}

