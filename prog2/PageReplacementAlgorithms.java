package pagereplacementalgorithms;
import java.io.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Name:        Marino, Sean
 * Project:     PA-2 (Page Replacement Algorithms
 * File:        filename
 * Instructor:  Feng Chen
 * Class:       CS4103-sp17
 * LogonID:     CS410330
 */

public class PageReplacementAlgorithms 
{

    //The Line of Input
    //Creates the string for "R" or "W" and creates an integer for the data
    static class lineofinput
    {
        private String rw;
        private final String pageNum;
        
        public lineofinput(String rw, String pageNum)
        {
            this.rw = rw;
            this.pageNum = pageNum;
        }
        
        public String readOrWrite()
        {
            return rw;
        }
        
        public String getPageNum()
        {
            return pageNum;
        }
    }
    
    
    //Least Recently Used (LRU) Page Replacement Algorithm Implementation 
    static class LRUAlgorithm{
        private int pageReference;
        private int numPageMisses;
        private int loadTime;
        private int pageWrite;
        private int allocatedFrames;
        private String fileName;
        private ArrayList<lineofinput> pageFrames, recentVal;
    
    public LRUAlgorithm(int cache, String fileName) throws IOException
    {
        this.pageReference = 0;
        this.fileName = fileName;
        this.numPageMisses = 0;
        this.loadTime = 0;
        this.pageWrite = 0;
        this.allocatedFrames = cache;
        this.recentVal = new ArrayList<>();
        this.pageFrames = new ArrayList<>();
        
        FileInputStream inputStream;
        try
        {
         
            inputStream = new FileInputStream(fileName);
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
                String strLine;
                while ((strLine = bufferedReader.readLine()) != null)
                {
                    String[] string = strLine.split(" ");
                    lineofinput line = new lineofinput(string[0], string[1]);
                    pageReference++;
                    boolean found = false;
                    for (lineofinput val : pageFrames)
                    {
                        if (val.getPageNum() == null ? (line.getPageNum()) == null : val.getPageNum().equals(line.getPageNum()))
                        {
                            found = true;
                            break;
                        }
                    }
                    
                    //If the page is not found in memory, it takes 5 time
                    //units to load the page in
                    if (!found)
                    {
                        numPageMisses++;
                        loadTime+=5;

                        //If the victim page has been written to memory ("W"), it takes 10
                        //time unites to write the victim page out
                        if (pageFrames.size() == cache)
                        {
                            int i = 0;
                            lineofinput LRU = recentVal.get(i);
                            while (!pageFrames.contains(LRU))
                                LRU = recentVal.get(i++);
                            if (LRU.readOrWrite().equals("W"))
                            {
                                pageWrite+=10;
                            }
                            pageFrames.set(pageFrames.indexOf(LRU), line);
                        }

                        else if (pageFrames.size() != cache)
                        {
                            pageFrames.add(line);
                        }
                    }
                    
                    for (lineofinput value : recentVal)
                    {
                        if (value.getPageNum() == null ? line.getPageNum() == null : value.getPageNum().equals(line.getPageNum()))
                        {
                            found = true;
                            break;
                        }
                    }
                    if (!found)
                    {
                        recentVal.remove(line);
                    }
                    recentVal.add(line);
                }
                //Print Statements
                System.out.println("The total number of page references: " + pageReference);
                System.out.println("The total number of page misses: " + numPageMisses);
                System.out.println("The total number of time unites for page misses: " + loadTime);
                System.out.println("The total number of time units for writing the modified page: " + pageWrite);
                bufferedReader.close();
            }
        }
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(PageReplacementAlgorithms.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    }
    
    static class object
    {
        lineofinput value;
        object next;
        
        public boolean hasNext()
        {
            return next != null;
        }
    }
    
    //CLOCK Page Replacement Algorithm Implementation
    static class CLOCK
    {
        private int referenceCount;
        private int missCount;
        private int loadTime;
        private int writeTime;
        private int allocatedFrames;
        private String fileName;
        private ArrayList<lineofinput> pageFrames;
        private object arm;
        private object clock;
        
        public CLOCK(int cache, String fileName) throws IOException
        {
            this.referenceCount = 0;
            this.missCount = 0;
            this.loadTime = 0;
            this.writeTime = 0;
            this.allocatedFrames = cache;
            this.fileName = fileName;
            this.arm = this.clock = new object();
            this.pageFrames = new ArrayList<>();
            FileInputStream inputStream;
            int i = 0;
            String strLine;
            for (i = 0; i < cache -1; i++)
            {
                clock.next = new object();
                clock = clock.next;
            }
            clock.next = arm;
            inputStream = new FileInputStream(fileName);
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
                while ((strLine = bufferedReader.readLine()) != null)
                {
                    String[] string = strLine.split(" ");
                    lineofinput line = new lineofinput(string[0], string[1]);
                    referenceCount++;
                    boolean found = false;
                    for (lineofinput val : pageFrames)
                    {
                        if (val.getPageNum() == null ? (line.getPageNum()) == null : val.getPageNum().equals(line.getPageNum()))
                        {
                            found = true;
                            break;
                        }
                    }
                    
                    //If the page is not found in memory, it takes 5 time
                    //units to load the page in
                    if (!found)
                    {
                        missCount++;
                        arm.value = line;
                        arm = arm.next;
                        loadTime += 5;
                        if (pageFrames.size() == cache)
                        {
                            i = pageFrames.indexOf(arm.value);
                            if (arm.value.readOrWrite().equals("W"))
                                writeTime+=10;
                            pageFrames.set(i, line);
                        }

                        else if (pageFrames.size() != cache)
                        {
                            pageFrames.add(line);
                        }
                    }
                }
                
                //Print Statements
                System.out.println("The total number of page references: " + referenceCount);
                System.out.println("The total number of page misses: " + missCount);
                System.out.println("The total number of time unites for page misses: " + loadTime);
                System.out.println("The total number of time units for writing the modified page: " + writeTime);
            }
        }
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException 
    {
        
        String algorithm = args[0];
        int numPageFrames = Integer.parseInt(args[1]);
        String filename = args[2];
        LRUAlgorithm LRU;
        CLOCK clock;
        System.out.println("Algorithm Type : " + algorithm);
        if ("LRU".equals(algorithm))
        {
            LRU = new LRUAlgorithm(numPageFrames, filename);
        }
        if ("CLOCK".equals(algorithm))
        {
            clock = new CLOCK(numPageFrames, filename);
        }
    }
}
