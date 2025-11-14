/**
 * @author Mohammad Mu Tasim Chowdhury
 */
package edu.iastate.cs2280.hw2;

/**
 * This class implements the quicksort algorithm for an array of Student objects.
 * It uses a median-of-three pivot selection strategy to improve performance and
 * avoid worst-case scenarios with already sorted or reverse-sorted data.
 */
public class QuickSorter extends AbstractSorter {
  public QuickSorter(Student[] students) {
    super(students);
    this.algorithm = "QuickSort";
  }

  @Override
  public void sort() {
    if (students == null || students.length <= 1) {
      return;
    }
    quickSortRec(0, students.length - 1);
  }

  private void quickSortRec(int first, int last) {
    if (first >= last) {
      return;
    }
    if (last - first >= 2) {
      medianOfThree(first, last);
      swap(last - 1, last);
    }

    int p = partition(first, last);
    quickSortRec(first, p - 1);
    quickSortRec(p + 1, last);
  }

  private int partition(int first, int last) {
    Student pivot = students[last];
    int i = first - 1;
    for (int j = first; j < last; j++) {
      if (studentComparator.compare(students[j], pivot) <= 0) {
        i++;
        swap(i, j);
      }
    }
    swap(i + 1, last);
    return i + 1;
  }


  /**
   * Selects a pivot using the median-of-three strategy. It considers the first,
   * middle, and last elements of the subarray, sorts them, and uses the median
   * as the pivot. The pivot is swapped to the second-to-last position (last - 1)
   * to simplify the partition step.
   *
   * @param first The starting index of the subarray.
   * @param last  The ending index of the subarray.
   */
  private void medianOfThree(int first, int last) {
    /**
     * Checks if amount of elements in array less than 2
     */
    int numOfElements = last - first + 1;
    if (numOfElements <= 1) {
      return;
    }
    /**
     * sets the median value
     */
    int median = first + (last - first) / 2;
    /**
     * checks and compares all the values with each other
     */
    if (studentComparator.compare(students[median], students[first]) < 0) {
      swap(first, median);
    }
    /**
     * Order (first, last)
     */
    if (studentComparator.compare(students[last], students[first]) < 0) {
      swap(first, last);
    }
    /**
     * Order (mid, last) so that first <= mid <= last under the comparator
     */
    if (studentComparator.compare(students[last], students[median]) < 0) {
      swap(median, last);
    }
    /**
     * Place the median (currently at mid) as the pivot.
     */
    swap(median, last);
  }
}