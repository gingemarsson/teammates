package teammates.logic.core;

public class CoverageCounter {
    static private int size = 0;
    static boolean[] coverage = new boolean[200];

    public static void init(int newSize) {
        size = newSize;
        for (int i = 0; i < 200; i++) {
            coverage[i] = false;
        }
    }

    public static void covered(int i) {
        coverage[i] = true;
    }

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
