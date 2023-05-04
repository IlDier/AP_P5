package ir.sharif.math.ap2023.hw5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public  class DownloadedFile extends RandomAccessFile {
    public DownloadedFile(String name, String mode) throws FileNotFoundException {
        super(name, mode);
    }

    public DownloadedFile(File file, String mode) throws FileNotFoundException {
        super(file, mode);
    }
    public synchronized void importData(int position,byte data){
       try {
           seek(position);
           write(data);
    }
       catch (IOException o){
       }
    }
}
