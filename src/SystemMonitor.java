import java.io.*;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class SystemMonitor {

    private String OperatingSys;
    private String GPU;
    private String Username;
    private long SysMemory;
    private int threads;
    private String architecure;

    private OperatingSystemMXBean os = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

    public SystemMonitor() throws IOException {


        setGPU();
        setOS();
        setSysMemory();
        setUsername();
        setThreads();
        setArch();

    }

    private void setArch(){architecure = os.getArch();}
    public String getArch(){return architecure;}

    private void setThreads(){threads = os.getAvailableProcessors();}
    public int getThreads(){return threads;}

    private void setSysMemory(){SysMemory = (os.getTotalPhysicalMemorySize() / (1024 * 1024 * 1024) );}
    public long getSysMemory(){return SysMemory;}

    private void setOS(){
        OperatingSys = os.getName() + " " + os.getVersion();
    }
    public String getOS(){return  OperatingSys;}

    private void setUsername(){Username = System.getProperty("user.name");}
    public String getUsername(){return Username;}

    private void setGPU() throws IOException {
        if(System.getProperty("os.name").contains("Windows") ){
            GPU = getGPUWin();

        } else if(System.getProperty("os.name").equals("Linux") ){

            GPU = getGPULin();
        }
    }
    public String getGPU(){return GPU;}

    //gpu getters - no need to be public as they're only used by the constructor
    private String getGPUWin() throws IOException {

        String GPU = "";

        try {
            File checkWin = new File("gpu.bat");
            checkWin.setExecutable(true);
            checkWin.createNewFile();
            FileWriter fw = new FileWriter("gpu.bat");
            fw.write("dxdiag /t gpu.txt");
            fw.close();



        }catch (Exception e){
            FileWriter crashlog = new FileWriter("error.txt", true);
            Date t = new Date();
            t.getTime();
            crashlog.append("\n" + t + " :: " + e.toString());
            crashlog.close();
            return "PLEASE RESTART APP";
        }

        try {

            Process pr = Runtime.getRuntime().exec("cmd /C gpu.bat");




        }catch(Exception e){
            FileWriter crashlog = new FileWriter("error.txt", true);
            Date t = new Date();
            t.getTime();
            crashlog.append("\n" + t + " :: " + e.toString());
            crashlog.close();
            return "PLEASE RESTART APP";
        }


        try{
            File fgpu = new File("gpu.txt");
            Scanner rgpu = new Scanner(fgpu);
            StringBuilder sgpu = new StringBuilder();

            while(rgpu.hasNextLine()){
                if(rgpu.nextLine().equals("Display Devices")){
                    rgpu.nextLine();

                    sgpu.append(rgpu.nextLine());
                    sgpu.delete(0,22);
                    GPU = sgpu.toString();


                }

            }


            rgpu.close();
        }catch(Exception e){
            FileWriter crashlog = new FileWriter("error.txt", true);
            Date t = new Date();
            t.getTime();
            crashlog.append("\n" + t + " :: " + e.toString());
            crashlog.close();
            return "PLEASE RESTART APP";
        }

        return GPU;
    }
    private String getGPULin() throws IOException {
        // -- Method for Linux works by executing a script and then reading from a file - it works at least
        String GPU = "";


        try {
            //Create a new script file just in case, make sure it is allowed to run then write the bash script to the file
            File check = new File("./gpu.bash");
            check.setExecutable(true);  //ALWAYS SET IF YOU WANT A PROGRAM TO RUN OFF OF ANOTHER PROGRAM
            check.createNewFile();
            FileWriter fo = new FileWriter("./gpu.bash");
            fo.write("lshw -numeric -C display > gpu.txt");
            fo.close();

            //Declare runtime and set new process - will run bash script that stores gpu info
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("./gpu.bash");
            Thread.sleep(500);  //Sleep before moving on - prevents errors when trying to read output file
            process.destroyForcibly(); //Make sure the process gets killed so program functions

        }catch(Exception e){
            //e.printStackTrace();
            FileWriter crashlog = new FileWriter("error.txt", true);
            Date t = new Date();
            t.getTime();
            crashlog.append("\n" + t + " :: " + e.toString());
            crashlog.close();
            return "PLEASE RESTART APP";
        }


        FileInputStream fs = new FileInputStream("gpu.txt");
        Scanner sc = new Scanner(fs);

        /*
        File test = new File("storage.txt");
        test.createNewFile();
        FileWriter tfw = new FileWriter("storage.txt", true);

        */

        //run through file
        while(sc.hasNextLine())
        {
            //declare string builder
            StringBuilder sb = new StringBuilder();

            //find where our info should be
            if(sc.nextLine().contains("description")){

                //read and append product and vendor lines
                String line = sc.nextLine();
                sb.append(line);
                sb.delete(0, 16);

                //convert to substring and assign
                GPU = sb.toString();
                //tfw.append(GPU + "\n");
                System.out.println(GPU);
                return GPU; //Return here and display very first GPU in system

            }

        }

        //tfw.close();
        fs.close();
        sc.close();
        return GPU;
    }

    public int calculateCPU_usage(){
        double cpu_usage = (os.getSystemCpuLoad() * 1000) / 10.0;
        return (int)cpu_usage;
    }








}
