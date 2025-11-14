/**
 * @author Mohammad Mu Tasim Chowdhury
 */
package edu.iastate.cs2280.hw2;

import java.util.Comparator;

/**
 * AbstractSorter is an abstract class that provides a foundation for implementing
 * various sorting algorithms on an array of Student objects. It defines common
 * properties and methods, such as handling a comparator for sorting criteria
 * and utility methods for swapping elements.
 */
public abstract class AbstractSorter {
  /**
   * Array of students to be sorted.
   */
  protected Student[] students;
  /**
   * The name of the sorting algorithm implementation.
   * Should be set by concrete sorter classes.
   * The values can be "SelectionSort", "InsertionSort", "MergeSort", or "QuickSort".
   */
  protected String algorithm = null;
  /**
   * The comparator used to define the sorting order for students.
   */
  protected Comparator<Student> studentComparator = null;

  /**
   * This constructor accepts an array of students as input. It creates a deep copy
   * of the array to prevent modification of the original data.
   *
   * @param students input array of students
   * @throws IllegalArgumentException if students is null or its length is 0.
   */
  protected AbstractSorter(Student[] students) throws IllegalArgumentException {
    if (students == null || students.length == 0) {
      throw new IllegalArgumentException();
    }
    /**
     * Deep copy
     */
    this.students = new Student[students.length];
    for (int i = 0; i < students.length; i++) {
      this.students[i] = new Student(students[i]);
    }
  }

  /**
   * Generates a comparator for sorting students.
   * If order is 0, students are compared primarily by GPA (descending) with tie-breaker by credits (descending).
   * If order is 1, students are compared primarily by credits taken (ascending) with tie-breaker by GPA (descending).
   *
   * @param order 0 to sort by GPA, 1 to sort by credits taken.
   * @throws IllegalArgumentException if order is not 0 or 1.
   */
  public void setComparator(int order) throws IllegalArgumentException {
    if (order != 0 && order != 1) {
      throw new IllegalArgumentException();
    }

    if (order == 0) {
      /**
       * Comparing students by GPA
       * Comparing with credits if GPA matches
       * Sort by GPA in descending order (highest GPA first)
       * If it's the same GPA, then sort the credits taken in descending order again (highest credits taken first)
       */
      studentComparator = (a,b) -> {
        int gpa = Double.compare(b.getGpa(), a.getGpa());
        if (gpa == 0) {
          return Integer.compare(b.getCreditsTaken(), a.getCreditsTaken());
        }
        return gpa;
      };
    }
    else if (order == 1) {
      /**
       * Compare students by credits taken
       * Compare with GPA is same amount of credits taken
       * Sort by credits taken in ascending order (lowest credits first)
       * If it's the same amount of credits taken, then sort by GPA in descending order (highest GPA first)
       */
      studentComparator = (a,b) -> {
        int credits = Integer.compare(a.getCreditsTaken(), b.getCreditsTaken());
        if (credits == 0) {
          return Double.compare(b.getGpa(), a.getGpa());
        }
        return credits;
      };
    }
  }

  /**
   * Abstract method for sorting the students array.
   * Each concrete sorter class must provide an implementation of this method.
   */
  public abstract void sort();

  /**
   * Returns the student at the median index of the sorted array.
   *
   * @return The median student.
   */
  public Student getMedian() {
    /**
     * checks if the students array is empty
     */
    if (students.length == 0) {
      return null;
    }
    /**
     * searches for the index for student in the median position
     */
    int medianIndex = (students.length) / 2;
    return students[medianIndex];
  }

  /**
   * Swaps the students at two specified indices in the students array.
   *
   * @param i The index of the first student to swap.
   * @param j The index of the second student to swap.
   */
  protected void swap(int i, int j) {
    Student temp = students[i];
    students[i] = students[j];
    students[j] = temp;
  }
}
