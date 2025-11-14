package edu.iastate.cs2280.hw2;

/**
 * @author Mohammad Mu Tasim Chowdhury
 */
public class SelectionSorter extends AbstractSorter {
    public SelectionSorter(Student[] students) {
        super(students);
        this.algorithm = "SelectionSort";
    }

    @Override
    public void sort() {
        if (students == null || students.length == 0) {
            return;
        }
        for (int i = 0; i < students.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < students.length; j++) {
                if (studentComparator.compare(students[j], students[minIndex]) < 0) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                swap(i, minIndex);
            }
        }
    }
}

