/**
 * @author Mohammad Mu Tasim Chowdhury
 */
package edu.iastate.cs2280.hw2;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * This class serves as the main driver for the sorting algorithm comparison application.
 * It provides a prompt interface for users to generate or load student data,
 * run four different sorting algorithms (Selection, Insertion, Merge, Quick),
 * and compare their performance based on execution time. The results can be
 * displayed in the console and optionally exported to a CSV file.
 */
public class CompareSorters {

  /**
   * The main entry point of the application. It presents a menu to the user to
   * choose between generating random student data, reading data from a file, or exiting.
   * For each set of data, it runs the sorting algorithms and prints their performance statistics.
   * Should handle InputMismatchExceptions and FileNotFoundExceptions gracefully.
   *
   * @param args Command-line arguments (not used).
   */
  public static void main(String[] args) {
    System.out.println("Sorting Algorithms Performance Analysis using Student Data\n");
    System.out.println("keys:  1 (random student data)  2 (file input)  3 (exit)");
    Scanner scan = new Scanner(System.in);

    int trial = 1;

    try {
      while (true) {
        /**
         * taking the user's action as input
         * store the input in key
         */
        System.out.print("Trial " + trial + ": ");
        String key = scan.nextLine().trim();
        if (key.isEmpty()) continue;
        /**
         * deciding what to do based on the user's choice of input
         * exit if the input is 3
         */
        if (key.equals("3")) {
          break;
        }

        Student[] studentsData = null;

        if (key.equals("1")) {
          System.out.print("Enter number of random students: ");
          String numOfStudentsStr = scan.nextLine().trim();
          int numofStudents;
          try {
            numofStudents = Integer.parseInt(numOfStudentsStr);
          }
          catch (NumberFormatException ex) {
            System.out.println("Invalid number. Please enter an integer.");
            /**
             * incrementing the trial even if its an exception
             */
            trial++;
            continue;
          }
          /**
           * checks if the number of students is at least 1
           */
          if (numofStudents < 1) {
            System.out.println("Number of students must be at least 1.");
            trial++;
            continue;
          }
          /**
           * generates random data for the students and stores it in the array studentsData
           */
          studentsData = generateRandomStudents(numofStudents, new Random());
        }
        else if (key.equals("2")) {
          System.out.print("File name: ");
          String fileName = scan.nextLine().trim();
          /**
           * reads the data from the file provided
           * FileNotFoundException when the file is missing
           * InputMismatchException when the input for file format is wrong
           * Printing the exception messages
           */
          try {
            studentsData = readStudentsFromFile(fileName);
          } catch (FileNotFoundException e) {
            System.out.println("Error: File not found: " + fileName);
            trial++;
            continue;
          } catch (InputMismatchException e) {
            System.out.println("Error: Input file format is incorrect. " + e.getMessage());
            trial++;
            continue;
          }
        }
        /**
         * checks if the user's input is from valid options
         */
        else {
          System.out.println("Invalid choice. Please enter 1, 2, or 3.");
          trial++;
          continue;
        }

        /**
         *ensures no null students before sorting
         */
        for (int i = 0; i < studentsData.length; i++) {
          if (studentsData[i] == null) {
            System.out.println("Input contains a null student at index " + i + ".");
            studentsData = null;
            break;
          }
        }
        if (studentsData == null) {
          trial++;
          continue;
        }

        /**
         * Building the scanners for algorithms
         */
        StudentScanner[] scanners = new StudentScanner[] {
                new StudentScanner(studentsData, Algorithm.SelectionSort),
                new StudentScanner(studentsData, Algorithm.InsertionSort),
                new StudentScanner(studentsData, Algorithm.MergeSort),
                new StudentScanner(studentsData, Algorithm.QuickSort)
        };

        /**
         * Printing the table following the structure provided in the pdf
         */
        System.out.println();
        System.out.println("algorithm       size  time (ns)");
        System.out.println("------------------------------------");

        /**
         * run each algorithm in order
         */
        Algorithm[] order = {
                Algorithm.SelectionSort, Algorithm.InsertionSort,
                Algorithm.MergeSort, Algorithm.QuickSort
        };
        for (int i = 0; i < scanners.length; i++) {
          try {
            scanners[i].scan();
            System.out.println(scanners[i].stats());
          } catch (Exception e) {
            System.out.println(order[i] + " failed: " + e.getMessage());
          }
        }

        System.out.println("------------------------------------");

        /**
         * Printing the median student's profile
         */
        for (int i = 0; i < scanners.length; i++) {
          StudentScanner scanner = scanners[i];
          scanner.scan();
          Student medianStudent = scanner.getMedianStudent();
          if (medianStudent != null) {
            System.out.printf("Median Student Profile: (GPA: %.2f, Credits: %d)%n",
                    medianStudent.getGpa(), medianStudent.getCreditsTaken());
            break;
          }
        }
        System.out.println();

        /**
         * CVS export
         */
        handleExportOption(scan, scanners);

        // advance to next trial at the very end of the loop
        trial++;
      }
    } catch (Exception e) {
      System.out.println("Unexpected error: " + e.getMessage());
    }
    System.out.println("Exiting program.");
    scan.close();
  }

  /**
   * Handles the user prompt for exporting sorting performance results to a CSV file.
   * This method catches and handles a {@link FileNotFoundException} if the specified
   * output file cannot be created or written to, printing an error message to the console.
   *
   * @param scan     The Scanner object to read user input.
   * @param scanners An array of StudentScanner objects containing the performance stats.
   */
  private static void handleExportOption(Scanner scan, StudentScanner[] scanners) {
    System.out.print("Export results to CSV? (y/n): ");
    String input = scan.nextLine().trim();
    /**
     * do nothing and move on to the next trial if user doesn't say 'y'
     */
    if (!input.equals("y")) {
      return;
    }
    /**
     * do the following if user says yes to export
     */
    System.out.print("Enter filename for export (e.g., results.csv): ");
    String filename = scan.nextLine().trim();
    if (!filename.endsWith(".csv")) {
      filename = filename + ".csv";
    }

    try (java.io.PrintWriter pw = new java.io.PrintWriter(new java.io.File(filename))) {
      pw.println("Stats,Median");
      /**
       * Go through each scanner one by one
       */
      for (int i = 0; i < scanners.length; i++) {

        /**
         *  Get the current StudentScanner from the list
         */
        StudentScanner currentScanner = scanners[i];

        /**
         * Get the median student from this scanner
         */
        Student medianStudent = currentScanner.getMedianStudent();

        /**
         * This will store the text describing the median student's GPA and credits
         */
        String medianSudentInfo;

        /**
         * Check if the median student exists
         */
        if (medianStudent == null) {
          /**
           * If there is no median student, keep it blank
           */
          medianSudentInfo = "";
        } else {
          /**
           * If there is a median student, get their GPA and credits
           */
          double gpa = medianStudent.getGpa();
          int credits = medianStudent.getCreditsTaken();

           medianSudentInfo = "(GPA: " + String.format("%.2f", gpa) + ", Credits: " + credits + ")";
        }

        /**
         * Get the stats for this scanner (like total students, average GPA, etc.)
         */
        String statsText = currentScanner.stats();

        /**
         * Print both the stats and median info to the file
         */
        pw.printf("\"%s\",\"%s\"%n", statsText, medianSudentInfo);
      }

      System.out.println("Data exported successfully to " + filename);
      /**
       * handles the exception
       */
    } catch (java.io.FileNotFoundException e) {
      System.out.println("Error: Unable to write to file " + filename);
    }
  }


  /**
   * Generates an array of Student objects with random GPA and credit values.
   *
   * @param numStudents The number of random students to generate.
   * @param rand        The Random object to use for generating values.
   * @return An array of randomly generated Student objects.
   * @throws IllegalArgumentException if numStudents is less than 1.
   */
  public static Student[] generateRandomStudents(int numStudents, Random rand) {
    if (numStudents < 1) {
      throw new IllegalArgumentException("numStudents must be at least 1.");
    }
    if (rand == null) {
      rand = new Random();
    }

    Student[] arr = new Student[numStudents];
    for (int i = 0; i < numStudents; i++) {
      /**
       * GPA bounded within 0.0 and 4.0
       */
      double gpa = Math.round(rand.nextDouble() * 400.0) / 100.0;
      if (gpa > 4.0) gpa = 4.0;
      /**
       *credits between 0 and 200
       *setting up a reasonable bound for the amount of credits taken
       * can create large values of random data without a bound
       */
      int credits = rand.nextInt(201);
      arr[i] = new Student(gpa, credits);
    }
    return arr;
  }

  /**
   * Reads student data from a text file. Each line of the file should contain a
   * GPA (double) followed by credits taken (int), separated by whitespace.
   *
   * @param filename The name of the file to read from.
   * @return An array of Student objects created from the file data.
   * @throws FileNotFoundException  if the specified file does not exist.
   * @throws InputMismatchException if the file content is not in the expected format
   *                                (e.g., non-numeric data, empty file).
   */
  private static Student[] readStudentsFromFile(String filename) throws FileNotFoundException, InputMismatchException {
    java.io.File file = new java.io.File(filename);
    if (!file.exists()) {
      throw new FileNotFoundException();
    }

    ArrayList<Student> list = new ArrayList<>();
    try (Scanner fileScanner = new Scanner(file)) {
      while (fileScanner.hasNext()) {
        if (!fileScanner.hasNextDouble()) {
          throw new InputMismatchException("File format error: Expected GPA (double).");
        }
        double gpa = fileScanner.nextDouble();

        if (!fileScanner.hasNextInt()) {
          throw new InputMismatchException("File format error: Expected credits (int).");
        }
        int credits = fileScanner.nextInt();

        list.add(new Student(gpa, credits));
      }
    }

    if (list.isEmpty()) {
      throw new InputMismatchException("File is empty or contains no valid student data.");
    }

    Student[] arr = new Student[list.size()];
    return list.toArray(arr);
  }
}

