import java.io.RandomAccessFile;

/**
 * Created by khristian on 3/26/17.
 */
public class MeminfoReaderThread implements Runnable{
    private double MemTotal;
    private double MemAvail;

    MeminfoReaderThread(){
        MemAvail=0;
        MemTotal=0;
    }

    public double getMemTotal() {
        try {
            RandomAccessFile in = new RandomAccessFile("/proc/meminfo", "r");
            String line;
            double MemTotal = Long.valueOf(0);
            double MemAvail = Long.valueOf(0);
            while (true) {
                line = in.readLine();
                if (line != null) {
                    line = line.trim().replaceAll(" +", " ");
                    if (line.contains("MemTotal:")) {
                        String data[] = line.split(" ");
                        MemTotal = Long.parseLong(data[1]);
                        return getMemTotal();
                    }
                }
            }
        }
        catch (Exception ex) {
            System.out.println("Ops, something went wrong: " + ex);
        }
        return 0;
    }
    public double getMemAvail() {
        try {
            RandomAccessFile in = new RandomAccessFile("/proc/meminfo", "r");
            String line;
            double MemAvail = Long.valueOf(0);
            while ((line = in.readLine()) != null) {
                line = line.trim().replaceAll(" +", " ");
                if (line.contains("MemAvailable:")) {
                    String data[] = line.split(" ");
                    MemAvail = Long.parseLong(data[1]);
                    return getMemAvail();
                }
            }
            in.seek(0);
        } catch (Exception ex) {
            System.out.println("Ops, something went wrong: " + ex);
        }
        return 0;
    }

    public void run() {
       while(true){
           this.MemAvail=getMemAvail();
           this.MemTotal=getMemTotal();
       }
    }
}
