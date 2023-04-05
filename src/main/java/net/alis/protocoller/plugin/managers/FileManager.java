package net.alis.protocoller.plugin.managers;

import lombok.SneakyThrows;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class FileManager {

    private final File file;
    private FileReader reader;
    private FileWriter writer;
    private Scanner scanner;
    private int linesCount = 0;

    @SneakyThrows
    public FileManager(File file) {
        this.file = file;
    }

    @SneakyThrows
    public FileManager(String path, String name) {
        this.file = new File(path, name);
        if(!this.file.exists()) this.file.createNewFile();
    }

    public boolean isFileWritingNow() {
        return this.writer != null;
    }

    public boolean isFileReadingNow() {
        return this.scanner != null;
    }

    @SneakyThrows
    public FileManager startWriting() {
        this.writer = new FileWriter(this.file);
        return this;
    }

    @SneakyThrows
    public FileManager stopWriting() {
        if(isFileWritingNow()) {
            this.writer.close();
            this.writer = null;
        }
        return this;
    }

    @SneakyThrows
    public FileManager startReading() {
        this.reader = new FileReader(this.file);
        this.scanner = new Scanner(this.reader);
        while (this.scanner.hasNextLine()) this.linesCount++;
        return this;
    }

    @SneakyThrows
    public FileManager stopReading() {
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
    public FileManager write(String o) {
        if(isFileWritingNow()) this.writer.write(o);
        return this;
    }

    @SneakyThrows
    public FileManager writeNewLine(String o) {
        write("\n" + o);
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
