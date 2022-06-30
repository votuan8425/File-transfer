package data;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;

/**
 *
 * @author PC
 */
public class DataWriter {

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public RandomAccessFile getAccFile() {
        return accFile;
    }

    public void setAccFile(RandomAccessFile accFile) {
        this.accFile = accFile;
    }

    public DataWriter(File file, long fileSize) throws IOException {
        accFile = new RandomAccessFile(file, "rw");
        this.file = file;
        this.fileSize = fileSize;
    }

    private File file;
    private long fileSize;
    private RandomAccessFile accFile;

    public synchronized long writeFile(byte[] data) throws IOException {
        accFile.seek(accFile.length());
        accFile.write(data);
        return accFile.length();
    }

    public void close() throws IOException {
        accFile.close();
    }
public String getMaxFileSize() {
       return convertFile(fileSize);
    }
    public String getCurretnFileSize() throws IOException{
       return convertFile(accFile.length());
    }
    public double getPercentage() throws IOException {
        double percentage;
        long filePointer = accFile.getFilePointer();
        percentage = filePointer * 100 / fileSize;
        return percentage;
    }
    private String convertFile(double bytes) {
//        double bytes = fileSize;
        String[] fileSizeUnits = {"bytes", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"};
        String sizeToReturn;
        DecimalFormat df = new DecimalFormat("0.#");
        int index;
        for (index = 0; index < fileSizeUnits.length; index++) {
            if (bytes < 1024) {
                break;
            }
            bytes = bytes / 1024;
        }
        sizeToReturn = df.format(bytes) + " " + fileSizeUnits[index];
        return sizeToReturn;

    }
}
