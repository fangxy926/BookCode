package chapter_8_arrayandmatrix;

import java.util.PriorityQueue;

public class Problem_28_SplitGold {

	public static int getMinSplitCost(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		// 优先级队列就是堆结构，而且默认是小根堆结构
		PriorityQueue<Integer> minHeap = new PriorityQueue<>();
		for (int i = 0; i < arr.length; i++) {
			minHeap.add(arr[i]);
		}
		int ans = 0;
		while (minHeap.size() != 1) {
			int sum = minHeap.poll() + minHeap.poll();
			ans += sum;
			minHeap.add(sum);
		}
		return ans;
	}

	public static void main(String[] args) {
		int[] arr = { 3, 9, 5, 2, 4, 4 };
		System.out.println(getMinSplitCost(arr));
	}
}
