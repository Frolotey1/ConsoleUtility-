import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.List;
import java.util.logging.*;

public class ConsoleUtilityItself {
    private static final String
            ESC = "\u001B",
            GREEN = ESC + "[32m",
            RED = ESC + "[31m",
            RESET = ESC + "[0m";
    public static void main(String[] args) throws IOException {
        Logger loggerForProgramm = Logger.getLogger(ConsoleUtilityItself.class.getName());
        FileHandler programmError = new FileHandler("LogFileWithErrors.log",true);
        programmError.setFormatter(new SimpleFormatter());
        loggerForProgramm.addHandler(programmError);
        File file;
        Scanner operation = new Scanner(System.in);
        if(args.length == 0) {
            loggerForProgramm.log(Level.WARNING, RED + "You must have not less than 2 arguments in this console utility" + RESET);
        }
        for (String arg : args) {
            switch (arg) {
                case "--help", "--h" -> allCommands();
                case "--add", "--a" -> {
                    System.out.println("Write the name for file: ");
                    String nameFile = operation.nextLine();
                    System.out.println("Write the text for file: ");
                    String text = operation.nextLine();
                    file = new File(nameFile);
                    try (BufferedWriter write = new BufferedWriter(new FileWriter(file))) {
                        write.write(text);
                    } catch (IOException e) {
                        loggerForProgramm.log(Level.WARNING, RED + "This is error for writing a text in file" + RESET);
                    }
                }
                case "--read", "--r" -> {
                    System.out.println("Write the dateiName fur checking his existence: ");
                    String nameFile = operation.nextLine();
                    if (Files.exists(Path.of(nameFile))) {
                        try (BufferedReader bekommen = new BufferedReader(new FileReader(nameFile))) {
                            System.out.println(bekommen.readLine());
                        } catch (IOException e) {
                            loggerForProgramm.log(Level.WARNING, RED + "This is error for writing a text in file" + RESET);
                        }
                    } else {
                        System.err.println(RED + "This file doesn't exist" + RESET);
                    }
                }
                case "--delete", "--d" -> {
                    System.out.println("Write the name of the file for checking existence: ");
                    String nameFile = operation.nextLine();
                    file = new File(nameFile);
                    if (Files.exists(file.toPath())) {
                        System.out.println(GREEN + "This file: " + file.toPath() + " was deleted successfully" + RESET);
                        Files.delete(file.toPath());
                    } else {
                        System.err.println(RED + "This file doesn't exist" + RESET);
                    }
                }
                case "--copy", "--c" -> {
                    System.out.println("Write the name of the file for checking existence: ");
                    String nameFile = operation.nextLine();
                    System.out.println("Write the name of the new file, which will added the information from the past file in: ");
                    String newFile = operation.nextLine();
                    file = new File(nameFile);
                    if (Files.exists(file.toPath())) {
                        System.out.println(GREEN + "Copy from one file to other file is successfully: " + RESET);
                        Files.copy(Path.of(newFile), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    } else {
                        System.err.println(RED + "This file doesn't exist" + RESET);
                    }
                }
                case "--move", "--m" -> {
                    System.out.println("Write the name of the file for checking existence: ");
                    String nameFile = operation.nextLine();
                    System.out.println("Write the new disk: ");
                    char newDisk = operation.nextLine().charAt(0);
                    file = new File(nameFile);
                    if (Files.exists(file.toPath())) {
                        System.out.println(GREEN + "This file was moved to other disk successfully" + RESET);
                        Files.move(file.toPath(), Path.of(newDisk + ":\\",file.getName()), StandardCopyOption.REPLACE_EXISTING);
                    } else {
                        System.err.println(RED + "This file doesn't exist" + RESET);
                    }
                }
                case "--newname", "--n" -> {
                    System.out.println("Write the file, which you want to rename: ");
                    String theFile = operation.nextLine();
                    file = new File(theFile);
                    if(Files.exists(file.toPath())) {
                        System.out.println("Write the new name for the file:");
                        String newName = operation.nextLine();
                        String[] saveTheData = new String[2]; saveTheData[0] = file.getName();
                        try(BufferedReader readTheDataFromFile = new BufferedReader(new FileReader(file))) {
                            saveTheData[1] = readTheDataFromFile.readLine();
                            saveTheData[0] = newName;
                        }
                        Files.delete(file.toPath());
                        file = new File(saveTheData[0]);
                        try(BufferedWriter writeTheDataFromThePastFile = new BufferedWriter(new FileWriter(file))) {
                            writeTheDataFromThePastFile.write(saveTheData[1]);
                        } catch (IOException e) {
                            System.err.println(RED + "This is the error of the input/output operations" + RESET);
                        }
                    }
                }
                case "--taskmgr", "--t" -> {
                    if(Desktop.isDesktopSupported()) {
                        file = new File("C:\\Windows\\System32\\Taskmgr.exe");
                        Desktop desktop = Desktop.getDesktop();
                        desktop.open(file);
                    } else {
                        System.err.println(RED + "System doesn't support including the taskmgr" + RESET);
                    }
                }
                case null, default -> System.err.println(RED + "This operation doesn't exist" + RESET);
            }
        }
    }
    public static void allCommands() {
        new LinkedList<>(
                List.of("--help       / --h = familiarization with commands of the programm",
                        "--add        / --a = add file and write information in computer system",
                        "--read       / --r = read the information from the file",
                        "--delete     / --d = delete the file from computer",
                        "--copy       / --c = copy the data from one file to other file",
                        "--move       / --m = move the file from one disk to other disk",
                        "--newname    / --n = rename the file",
                        "--taskmgr    / --t = include the Taskmgr.exe from the system files in Windows"
                )).forEach(System.out::println);
    }
}
