import java.io.*;
import java.util.Scanner;

public class IOHandler {
    private static File file;
    private static Scanner fileInput;

    // Open a file and keep it open for reading until next close / next change
    public void OpenFile(String filename) {
        try {
            file = new File(filename);
            fileInput = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }
    }

    // Code from MadLibs project we did in class
    public boolean fileHasNextLine() {
        if (fileInput == null) {
            return false;
        } else {
            return fileInput.hasNextLine();
        }
    }

    public String getNextLine() {
        if (fileHasNextLine()) {
            return fileInput.nextLine();
        } else {
            return "";
        }
    }

    public void writeFileLine(String filePath, String line) {
        // https://www.digitalocean.com/community/tutorials/java-filewriter-example
        // Most ideas from this article
        try {
            FileWriter fileWriter = new FileWriter(filePath, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(line);
            printWriter.close();
        // Handle IOExceptions
        // https://docs.oracle.com/javase/8/docs/api/java/io/IOException.html
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Became important for editing seed / flashcards studied
    // in the deck file
    public void editFileLine(String filePath, int lineNumber, String newLine) {
        try {
            File inputFile = new File(filePath);

            // Creates a temp file to write the new content to
            File tempFile = new File("tempFile.txt");

            // Readers for the input and temp files
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            PrintWriter writer = new PrintWriter(new FileWriter(tempFile));

            String currentLine;
            int currentLineNumber = 0;

            while ((currentLine = reader.readLine()) != null) {
                currentLineNumber++;
                if (currentLineNumber == lineNumber) {
                    writer.println(newLine);
                } else {
                    writer.println(currentLine);
                }
            }

            writer.close();
            reader.close();

            // Delete the OG file
            inputFile.delete();
            // Rename the temp file to the original file name -> Replaces the OG file
            tempFile.renameTo(inputFile);

        // IOException handling
        // https://docs.oracle.com/javase/8/docs/api/java/io/IOException.html
        } catch (IOException e) {
            // Prints the stack trace for debugging
            e.printStackTrace();
        }
    }

    // Get array of files in a directory
    // https://stackoverflow.com/questions/5694385/getting-the-filenames-of-all-files-in-a-folder
    // Useful for getting all the decks in the decks folder when loading app
    public String[] getFilesInDirectory(String directoryPath) {
        File directory = new File(directoryPath);
        String[] files = directory.list();
        return files;
    }

    public void closeFile() {
        if (fileInput != null) {
            fileInput.close();
        }
        file = null;
        fileInput = null;
    }
}
