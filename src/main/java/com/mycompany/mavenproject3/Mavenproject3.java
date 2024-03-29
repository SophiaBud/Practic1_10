package com.mycompany.mavenproject3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Mavenproject3 {

    public static void main(String[] args) {
        System.out.println("Practical task 1.10, Student Budrik Sophia, RIBO-04-22, Variant 4");
        System.out.println("");
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please, enter your path:");
        String directoryPath = scanner.nextLine();
        System.out.println("Please, enter needed file extension:");
        String fileExtension = scanner.nextLine();
        System.out.println("");

        try (Stream<Path> walk = Files.walk(Paths.get(directoryPath))) { //проход по всем файлам в каталоге
            List<String> result = walk.filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(fileExtension))  //отбираем файлы нужного нам расширения
                    .map(x -> x.toString() + " " + getFileSize(x))  //подтягиваем размер файла
                    .sorted(Comparator.comparing(Mavenproject3::getDirectoryPath))  //сортируем по названию каталогов
                    .collect(Collectors.toList());  //собираем все в массив

            if (result.isEmpty()) {
                System.out.println("No files of this extention");
            } else {
                System.out.println("Finding " + fileExtension + " in " + directoryPath + " :");
                System.out.println("");
                result.forEach(System.out::println);
            }
        } catch (NoSuchFileException ex) {
            System.out.println("The directory: " + directoryPath + " was not found");
        } catch (IOException ex) {
            System.out.println("Error when reading the directory: " + directoryPath);
        }
    }
    private static String getDirectoryPath(String fullPath) { //метод для получения полного пути,чтобы была возможность потом остротировать файлы из всех папок в каталоге
        Path path = Paths.get(fullPath);
        return path.getParent().toString();
    }

    private static String getFileSize(Path path) { //метод для получения размера файла, чтобы потом его добавить к выходному значению
        try {
            return String.valueOf(Files.size(path));
        } catch (IOException ex) {
            return "Error when getting the file size";
        }
    }
}
