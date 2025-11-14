package edu.iastate.cs2280.hw2;

/**
 * @author Mohammad Mu Tasim Chowdhury
 */
public class MergeSorter extends AbstractSorter {
    public MergeSorter(Student[] students) {
        super(students);
        this.algorithm = "MergeSort";
    }

    @Override
    public void sort() {
        if (students == null || students.length <= 1) {
            return;
        }
        mergeSort(0, students.length - 1);
    }

    /**
     * Recursive merge sort
     *
     * @param first
     * @param last
     */
    private void mergeSort(int first, int last) {
        if (first >= last) {
            return;
        }
        int mid = (first + last) / 2;
        mergeSort(first, mid);
        mergeSort(mid + 1, last);
        merge(first, mid, last);
    }

    /**
     * Merge the two sorted arrays
     *
     * @param first
     * @param mid
     * @param last
     */
    private void merge(int first, int mid, int last) {
        int leftSize = mid - first + 1;
        int rightSize = last - mid;

        Student[] leftArr = new Student[leftSize];
        Student[] rightArr = new Student[rightSize];
        /**
         * copying the subarrays
         */
        for (int i = 0; i < leftSize; i++) {
            leftArr[i] = students[first + i];
        }
        for (int j = 0; j < rightSize; j++) {
            rightArr[j] = students[mid + 1 + j];
        }

        int i = 0, j = 0, k = first;
        while (i < leftSize && j < rightSize) {
            if (studentComparator.compare(leftArr[i], rightArr[j]) <= 0) {
                students[k++] = leftArr[i++];
            } else {
                students[k++] = rightArr[j++];
            }
        }
        while (i < leftSize) {
            students[k] = leftArr[i];
            i++;
            k++;
        }
        while (j < rightSize) {
            students[k] = rightArr[j];
            j++;
            k++;
        }
    }
}

