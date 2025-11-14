package edu.iastate.cs2280.hw2;

/**
 * @author Mohammad Mu Tasim Chowdhury
 */
public class InsertionSorter extends AbstractSorter{
    public InsertionSorter(Student[] students) {
        super(students);
        this.algorithm = "InsertionSort";
    }

    @Override
    public void sort() {
        /**
         * checks if the students array is empty
         */
        if (students == null || students.length == 0) {
            return;
        }
        for (int i = 1; i < students.length; i++) {
            Student key = students[i];
            int j = i - 1;
            while (j >= 0 && studentComparator.compare(students[j], key) > 0) {
                students[j + 1] = students[j];
                j--;
            }
            students[j + 1] = key;
        }
    }
}
