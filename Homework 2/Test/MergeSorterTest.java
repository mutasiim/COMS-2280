package edu.iastate.cs2280.hw2;
/**
 * @author Mohammad Mu Tasim Chowdhury
 */
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MergeSorterTest {

        @Test
        public void sortOrder0() {
            Student[] arr = {
                    new Student(3.1, 95),
                    new Student(4.0, 80),
                    new Student(3.2, 120),
                    new Student(2.4, 20)
            };

            /**
             * sort by GPA in descending order
             * if GPA same, sort by credits in descending order
             */
            MergeSorter sorter = new MergeSorter(arr);
            sorter.setComparator(0); // order 0
            sorter.sort();

            /**
             * Expected result after sorting
             */
            double[] expectedGpa = {4.0, 3.2, 3.1, 2.4};
            int[] expectedCredits = {80,  120, 95, 20 };

            for (int i = 0; i < sorter.students.length; i++) {
                assertEquals(expectedGpa[i], sorter.students[i].getGpa(), "GPA mismatch at index " + i);
                assertEquals(expectedCredits[i], sorter.students[i].getCreditsTaken(), "Credits mismatch at index " + i);
            }
        }

        @Test
        public void sortOrder1() {
            Student[] arr = {
                    new Student(3.8, 60),
                    new Student(3.4, 66),
                    new Student(3.6, 59),
                    new Student(3.9, 66)
            };

            /**
             * sort by credits in ascending order
             * sort by GPA in descending order when amount of credits same
             */
            MergeSorter sorter = new MergeSorter(arr);
            sorter.setComparator(1); // order 1
            sorter.sort();

            /**
             * assert
             */
            int[] expectedCredits = {59, 60, 66, 66};
            double[] expectedGpa = {3.6, 3.8, 3.9, 3.4};

            for (int i = 0; i < sorter.students.length; i++) {
                assertEquals(expectedCredits[i], sorter.students[i].getCreditsTaken(),
                        "Credits mismatch at index " + i);
                assertEquals(expectedGpa[i], sorter.students[i].getGpa(),
                        "GPA mismatch at index " + i);
            }
        }

        @Test
        public void emptyArray() {
            Student[] emptyArray = new Student[0]; // make empty array
            try {
                /**
                 * manually triggers a test failure
                 */
                new MergeSorter(emptyArray);
                fail("Expected IllegalArgumentException for empty array, but no exception was thrown.");
            } catch (IllegalArgumentException e) {
                /**
                 * do nothing, as the test passes
                 */
            }
        }
    }

