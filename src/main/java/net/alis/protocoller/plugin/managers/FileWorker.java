package net.alis.protocoller.plugin.managers;

import lombok.SneakyThrows;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class FileWorker {

    private final File file;
    private FileReader reader;
    private FileWriter writer;
    private Scanner scanner;
    private int linesCount = 0;

    @SneakyThrows
    public FileWorker(File file) {
        this.file = file;
    }

    @SneakyThrows
    public FileWorker(String directoryPath, String name) {
        File directory = new File(directoryPath);
        if(!directory.exists()) directory.mkdir();
        this.file = new File(directory, name);
        if(!this.file.exists()) this.file.createNewFile();
    }

    public boolean isFileWritingNow() {
        return this.writer != null;
    }

    public boolean isFileReadingNow() {
        return this.scanner != null;
    }

    @SneakyThrows
    public FileWorker startWriting() {
        this.writer = new FileWriter(this.file);
        return this;
    }

    @SneakyThrows
    public FileWorker stopWriting() {
        if(isFileWritingNow()) {
            this.writer.close();
            this.writer = null;
        }
        return this;
    }

    @SneakyThrows
    public FileWorker startReading() {
        this.reader = new FileReader(this.file);
        this.scanner = new Scanner(this.reader);
        while (this.scanner.hasNextLine()) this.linesCount++;
        return this;
    }

    @SneakyThrows
    public FileWorker stopReading() {
        if(isFileReadingNow()) {
            this.scanner.close();
            this.reader.close();
            this.reader = null;
            this.scanner = null;
        }
        return this;
    }

    public File getFile() {
        return file;
    }

    @SneakyThrows
    public FileWorker write(String o) {
        if(isFileWritingNow()) this.writer.write(o);
        return this;
    }

    public FileWorker writeNewLine(String o) {
        write("\n" + o);
        return this;
    }

    public FileWorker writeNewLine() {
        write("\n");
        return this;
    }

    @SneakyThrows
    public @Nullable String readLine(int line) {
        if(isFileReadingNow()) {
            if(line <= this.linesCount) {
                int currentLine = 1;
                while (this.scanner.hasNextLine()) {
                    if(currentLine == line) return this.scanner.nextLine();
                    currentLine++;
                }
            }
            return null;
        }
        return null;
    }

    public File stopAll() {
        this.stopWriting();
        this.stopReading();
        return this.file;
    }

}
