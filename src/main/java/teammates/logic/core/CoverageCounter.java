package teammates.logic.core;

/**
 * A simple class that can be used to calculate coverage
 */
public class CoverageCounter {
    static private int size = 0;
    static boolean[] coverage = new boolean[200]; // Max number of branches is 200.

    /**
     * Init the coverage counter
     * @param newSize The total number of branches
     */
    public static void init(int newSize) {
        size = newSize;
        for (int i = 0; i < 200; i++) {
            coverage[i] = false;
        }
    }

    /**
     * Set the specified branch as covered. Branches are 1-indexed.
     * @param i the branch that should be set to covered
     */
    public static void covered(int i) {
        coverage[i-1] = true;
    }

    /**
     * Print the coverage result
     */
    public static void printCoverage() {
        System.out.println("################################################");

        int actualCoverage = 0;

        for (boolean c : coverage
                ) {
            if (c)
                actualCoverage++;
        }

        double coverage = (double) actualCoverage / (double) size;

        System.out.println("Coverage: " + actualCoverage + " of " + size + ", " + coverage * 100 + "%");

        System.out.println("################################################");
    }
}
