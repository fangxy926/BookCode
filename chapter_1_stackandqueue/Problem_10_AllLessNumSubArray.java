package chapter_1_stackandqueue;

import java.util.LinkedList;

public class Problem_10_AllLessNumSubArray {

	public static int getNum(int[] arr, int num) {
		if (arr == null || arr.length == 0 || num < 0) {
			return 0;
		}
		LinkedList<Integer> qmin = new LinkedList<Integer>();
		LinkedList<Integer> qmax = new LinkedList<Integer>();
		int i = 0;
		int j = 0;
		int res = 0;
		while (i < arr.length) {
			while (j < arr.length) {
				if (qmin.isEmpty() || qmin.peekLast() != j) {
					while (!qmin.isEmpty() && arr[qmin.peekLast()] >= arr[j]) {
						qmin.pollLast();
					}
					qmin.addLast(j);
					while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[j]) {
						qmax.pollLast();
					}
					qmax.addLast(j);
				}
				if (arr[qmax.getFirst()] - arr[qmin.getFirst()] > num) {
					break;
				}
				j++;
			}
			res += j - i;
			if (qmin.peekFirst() == i) {
				qmin.pollFirst();
			}
			if (qmax.peekFirst() == i) {
				qmax.pollFirst();
			}
			i++;
		}
		return res;
	}

	// for test
	public static int[] getRandomArray(int len) {
		if (len < 0) {
			return null;
		}
		int[] arr = new int[len];
		for (int i = 0; i < len; i++) {
			arr[i] = (int) (Math.random() * 10);
		}
		return arr;
	}

	// for test
	public static int getNumRightWay(int[] arr, int num) {
		int res = 0;
		for (int start = 0; start < arr.length; start++) {
			for (int end = start; end < arr.length; end++) {
				if (isValid(arr, start, end, num)) {
					res++;
				}
			}
		}
		return res;
	}

	public static boolean isValid(int[] arr, int start, int end, int num) {
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for (int i = start; i <= end; i++) {
			min = Math.min(min, arr[i]);
			max = Math.max(max, arr[i]);
		}
		return (max - min) <= num;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr != null) {
			for (int i = 0; i < arr.length; i++) {
				System.out.print(arr[i] + " ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		int size = 5;
		int testTime = 1000000;
		for (int i = 0; i < testTime; i++) {
			int[] arr = getRandomArray(size);
			int num = (int) (Math.random() * size);
			if (getNum(arr, num) != getNumRightWay(arr, num)) {
				printArray(arr);
				System.out.println(num);
				System.out.println(getNum(arr, num));
				System.out.println(getNumRightWay(arr, num));
				break;
			}
		}
	}

}
