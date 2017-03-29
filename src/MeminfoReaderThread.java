import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by khristian on 3/26/17.
 */
public class MeminfoReaderThread implements Runnable{
    private double MemTotal;
    private double MemAvail;
    private double PercentMemUsed;

    MeminfoReaderThread(){
        MemAvail=0;
        MemTotal=0;
        PercentMemUsed =0;
    }

    public double getPercentMemUsed(){
        PercentMemUsed = MemAvail/MemTotal;
        return PercentMemUsed *100;
    }

    public double getMemTotal() {
        try {
            RandomAccessFile in = new RandomAccessFile("/proc/meminfo", "r");
            String line;
            while ((line = in.readLine()) != null) {
                    line = line.trim().replaceAll(" +", " ");
                    if (line.contains("MemTotal:")) {
                        String data[] = line.split(" ");
                        MemTotal = Long.parseLong(data[1]);
                    }
                }
                in.seek(0);
            } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (Exception ex) {
            System.out.println("Ops, something went wrong: " + ex);
        }
        return MemTotal;
    }
    public double getMemAvail() {
        try {
            RandomAccessFile in = new RandomAccessFile("/proc/meminfo", "r");
            String line;
            while ((line = in.readLine()) != null) {
                line = line.trim().replaceAll(" +", " ");
                if (line.contains("MemAvailable:")) {
                    String data[] = line.split(" ");
                    MemAvail = Long.parseLong(data[1]);
                }
            }
            in.seek(0);
        } catch (Exception ex) {
            System.out.println("Ops, something went wrong: " + ex);
        }
        return MemAvail;
    }

    public void run() {
       while(true){
           this.MemAvail=getMemAvail();
           this.MemTotal=getMemTotal();
           this.PercentMemUsed=getPercentMemUsed();
           System.out.println(PercentMemUsed);
           try {
               Thread.sleep(5000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
    }

    public static void main(String[] args) {
        MeminfoReaderThread meminfoReaderThread = new MeminfoReaderThread();
        meminfoReaderThread.run();
    }
}
