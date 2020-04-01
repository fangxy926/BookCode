package chapter_9_others;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Problem_28_MadianQuick {

	// 生成小根堆的比较器
	public static class MinHeapComparator implements Comparator<Integer> {
		@Override
		public int compare(Integer o1, Integer o2) {
			return o1 - o2;
		}
	}

	// 生成大根堆的比较器
	public static class MaxHeapComparator implements Comparator<Integer> {
		@Override
		public int compare(Integer o1, Integer o2) {
			return o2 - o1;
		}
	}

	public static class MedianHolder {
		// PriorityQueue结构就是堆
		private PriorityQueue<Integer> maxHeap;
		private PriorityQueue<Integer> minHeap;

		public MedianHolder() {
			maxHeap = new PriorityQueue<Integer>(new MaxHeapComparator());
			minHeap = new PriorityQueue<Integer>(new MinHeapComparator());
		}

		public void addNumber(Integer num) {
			if (maxHeap.isEmpty() || num <= maxHeap.peek()) {
				maxHeap.add(num);
			} else {
				minHeap.add(num);
			}
			modifyTwoHeaps();
		}

		public Integer getMedian() {
			if (maxHeap.isEmpty()) {
				return null;
			}
			if (maxHeap.size() == minHeap.size()) {
				return (maxHeap.peek() + minHeap.peek()) / 2;
			} else {
				return maxHeap.size() > minHeap.size() ? maxHeap.peek()
						: minHeap.peek();
			}
		}

		private void modifyTwoHeaps() {
			if (maxHeap.size() == minHeap.size() + 2) {
				minHeap.add(maxHeap.poll());
			}
			if (minHeap.size() == maxHeap.size() + 2) {
				maxHeap.add(minHeap.poll());
			}
		}

	}

	// for test
	public static int[] getRandomArray(int maxLen, int maxValue) {
		int[] res = new int[(int) (Math.random() * maxLen) + 1];
		for (int i = 0; i != res.length; i++) {
			res[i] = (int) (Math.random() * maxValue);
		}
		return res;
	}

	// for test, this method is ineffective but absolutely right
	public static int getMedianOfArray(int[] arr) {
		int[] newArr = Arrays.copyOf(arr, arr.length);
		Arrays.sort(newArr);
		int mid = (newArr.length - 1) / 2;
		if ((newArr.length & 1) == 0) {
			return (newArr[mid] + newArr[mid + 1]) / 2;
		} else {
			return newArr[mid];
		}
	}

	public static void printArray(int[] arr) {
		for (int i = 0; i != arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		boolean err = false;
		int testTimes = 2000000;
		for (int i = 0; i != testTimes; i++) {
			int len = 30;
			int maxValue = 1000;
			int[] arr = getRandomArray(len, maxValue);
			MedianHolder medianHold = new MedianHolder();
			for (int j = 0; j != arr.length; j++) {
				medianHold.addNumber(arr[j]);
			}
			if (medianHold.getMedian() != getMedianOfArray(arr)) {
				err = true;
				printArray(arr);
				break;
			}
		}
		System.out.println(err ? "Oops..what a fuck!"
				: "today is a beautiful day^_^");

	}

}
