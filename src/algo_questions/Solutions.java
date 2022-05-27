package algo_questions;

import java.util.Arrays;

import static java.lang.Math.min;

public class Solutions {

    /**
     * Method computing the maximal amount of tasks out
     * of n tasks that can be completed with m time slots.
     * A task can only be completed in a time slot if the
     * length of the time slot is grater than the no.
     * of hours needed to complete the task.
     * @param tasks - array of integers of length n.
     *             tasks[i] is the time in hours required to complete task i.
     * @param timeSlots - array of integers of length m.
     *                  timeSlots[i] is the length in hours of the slot i.
     * @return maximal amount of tasks that can be completed
     */
    public static int alotStudyTime(int[] tasks, int[] timeSlots){
        int length = min(tasks.length, timeSlots.length);
        int counter = 0;
        if (length == 0) {
            return counter;
        }
        Arrays.sort(tasks);
        Arrays.sort(timeSlots);
        for (int indexTasks = 0; indexTasks < length; indexTasks++) {
            for (int indexTimeSlot = indexTasks; indexTimeSlot < length; indexTimeSlot++) {
                if(tasks[indexTasks] <= timeSlots[indexTimeSlot]){
                    counter += 1;
                    if (indexTimeSlot == length - 1) {
                        // used the last time slot
                        return counter;
                    }
                    break;
                }
            }
        }
        return  counter;
    }


    /**
     * Method computing the min amount of leaps a frog needs to
     * jump across n waterlily leaves, from leaf 1 to leaf n.
     * The leaves vary in size and how stable they are,
     * so some leaves allow larger leaps than others.
     * leapNum[i] is an integer telling you how many leaves ahead
     * you can jump from leaf i. If leapNum[3]=4,
     * the frog can jump from leaf 3,
     * and land on any of the leaves 4, 5, 6 or 7.
     * @param leapNum - array of ints.
     *               leapNum[i] is how many leaves ahead you can jump from leaf i.
     * @return minimal no. of leaps to last leaf.
     */
    public static int minLeap(int[] leapNum)
    {
        if (leapNum.length <= 1 || leapNum[0] == 0)
            return 0;

        int counterJumps = 1;
        int  MaximalReachableIndex  = leapNum[0];
        int NumOfsteps = leapNum[0];

        for (int currIndex = 1; currIndex < leapNum.length; currIndex++) {
            if (currIndex == leapNum.length - 1) // if we wre in the end
                return counterJumps;

            MaximalReachableIndex = Math.max(MaximalReachableIndex, currIndex + leapNum[currIndex]);
            NumOfsteps--;
            if (NumOfsteps == 0) {
                counterJumps++;
                if (currIndex >= MaximalReachableIndex) {
                    return 0;
                }
                NumOfsteps = MaximalReachableIndex - currIndex;
            }
        }

        return 0;
    }




    /**
     *Method computing the solution to the following problem:
     * A boy is filling the water trough for his
     * father's cows in their village. The trough holds n liters of water.
     *  With every trip to the village well,
     * he can return using either the 2 bucket yoke, or simply with a single bucket.
     * A bucket holds 1 liter.
     * In how many different ways can he fill the water trough?
     * n can be assumed to be greater or equal to 0, less than or equal to 48.
     * @param n
     * @return valid output of algorithm.
     */
    public static int bucketWalk(int n) {
        int tableDynamicSolutionLength = n + 1;
        if (n == 0 || n == 1){
            return 1;
        }
        int[] tableDynamicSolution = new int[tableDynamicSolutionLength];
        tableDynamicSolution[0] = 1;
        tableDynamicSolution[1] = 1;
        for (int index = 2; index < tableDynamicSolutionLength ; index++) {
            tableDynamicSolution[index] = tableDynamicSolution[index - 1] + tableDynamicSolution[index - 2];
        }
        // the solution is on the last index:
        return tableDynamicSolution[n];
    }


    /**
     * Method computing the solution to the following problem:
     * Given an integer n, return the number of
     * structurally unique BST's (binary search trees)
     * which has exactly n nodes of unique values from 1 to n.
     * You can assume n is at least 1 and at most 19.
     * (Definition:
     * two trees S and T are structurally
     * distinct if one can not be obtained from the other by renaming of the nodes.)
     * (credit: LeetCode)
     * @param n - the nodes of unique values are from 1 to n.
     * @return the number of structurally unique BST's
     */
    public static int numTrees(int n) {
       //array of n+1 int:
        int tableDynamicSolutionLength = n + 1;
        int[] tableDynamicSolution = new int[tableDynamicSolutionLength];
        tableDynamicSolution[0] = 1;
        tableDynamicSolution[1] = 1;
        //filing the table:
        for (int i=2; i<=n; i++) {
            tableDynamicSolution[i] = 0;
            for (int j=1; j<=i; j++) {
                int valueOfTheProblemWithIndex =  tableDynamicSolution[j-1] * tableDynamicSolution[i-j];
                tableDynamicSolution[i] += valueOfTheProblemWithIndex;
            }
        }
        // the solution is on the last index:
        return tableDynamicSolution[n];
    }

}
