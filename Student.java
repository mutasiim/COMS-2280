/**
 * @author Mohammad Mu Tasim Chowdhury
 */
package edu.iastate.cs2280.hw2;

/**
 * Represents a student with a GPA and the total number of credits taken.
 * This class implements the Comparable interface to define a
 * natural ordering for students. The natural order is primarily by GPA
 * (descending), and secondarily by credits taken (descending) as a tie-breaker.
 */
public class Student implements Comparable<Student> {
  /**
   * The student's Grade Point Average.
   */
  private final double gpa;
  /**
   * The total number of credits the student has taken.
   */
  private final int creditsTaken;

  /**
   * Constructs a new Student with a specified GPA and number of credits.
   *
   * @param gpa          The GPA of the student (must be between 0.0 and 4.0).
   * @param creditsTaken The number of credits taken (cannot be negative).
   * @throws IllegalArgumentException if GPA or creditsTaken are out of valid range.
   */
  public Student(double gpa, int creditsTaken) {
    this.gpa = gpa;
    this.creditsTaken = creditsTaken;

    if (gpa < 0.0 || gpa > 4.0) {
      throw new IllegalArgumentException("GPA must be between 0.0 and 4.0");
    }
    if (creditsTaken < 0 ) {
      throw new IllegalArgumentException("Credits taken must be greater than 0");
    }
  }

  /**
   * Copy constructor. Creates a new Student object by copying the data from another.
   *
   * @param other The Student object to copy.
   */
  public Student(Student other) {
    this.gpa = other.gpa;
    this.creditsTaken = other.creditsTaken;
  }

  /**
   * Gets the student's GPA.
   *
   * @return The GPA.
   */
  public double getGpa() {
    return gpa;
  }

  /**
   * Gets the number of credits the student has taken.
   *
   * @return The total credits taken.
   */
  public int getCreditsTaken() {
    return creditsTaken;
  }

  /**
   * Provides a string representation of the Student object.
   *
   * @return A formatted string showing the student's GPA and credits.
   */
  @Override
  public String toString() {
    return String.format("(GPA: %.2f, Credits: %d)", gpa, creditsTaken);
  }

  /**
   * Compares this student with another for natural ordering. The comparison is
   * based first on GPA in descending order, and then by credits taken in
   * descending order as a tie-breaker.
   *
   * @param other The other student to be compared.
   * @return A negative integer, zero, or a positive integer if this student is
   * greater than, equal to, or less than the specified student, respectively,
   * based on the defined natural order.
   */
  @Override
  public int compareTo(Student other) {
    int gpaCompare = Double.compare(other.gpa, this.gpa);
    if (gpaCompare == 0) {
      return Integer.compare(other.creditsTaken, this.creditsTaken);
    }
    return gpaCompare;
  }

  /**
   * Compares this Student object to another object for equality.
   * The comparison is based on GPA and the number of credits taken.
   *
   * @param other The object to be compared with this Student.
   * @return true if the given object is a Student and has the same GPA
   *         and number of credits taken as this Student, false otherwise.
   */
  @Override
  public boolean equals(Object other) {
    if (other == null || other.getClass() != this.getClass()) return false;
    if (other == this) return true;
    Student otherStudent = (Student) other;
    return Double.compare(otherStudent.gpa, this.gpa) == 0 && this.creditsTaken == otherStudent.creditsTaken;
  }
}
