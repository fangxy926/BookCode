package chapter_8_arrayandmatrix;

public class Problem_31_WaterProblem {

	public static int getWater1(int[] arr) {
		if (arr == null || arr.length < 3) {
			return 0;
		}
		int res = 0;
		// 0位置和n-1位置上方一定没有水，所以不尝试了
		for (int i = 1; i < arr.length - 1; i++) {
			int leftMax = 0;
			int rightMax = 0;
			// 遍历求i位置的左侧最大值
			for (int l = 0; l < i; l++) {
				leftMax = Math.max(arr[l], leftMax);
			}
			// 遍历求i位置的右侧最大值
			for (int r = i + 1; r < arr.length; r++) {
				rightMax = Math.max(arr[r], rightMax);
			}
			// i位置上方的水量累加到结果中
			res += Math.max(Math.min(leftMax, rightMax) - arr[i], 0);
		}
		return res;
	}

	public static int getWater2(int[] arr) {
		if (arr == null || arr.length < 3) {
			return 0;
		}
		int[] leftMaxs = new int[arr.length];
		leftMaxs[0] = arr[0];
		for (int i = 1; i < arr.length; i++) {
			leftMaxs[i] = Math.max(leftMaxs[i - 1], arr[i]);
		}
		int[] rightMaxs = new int[arr.length];
		rightMaxs[arr.length - 1] = arr[arr.length - 1];
		for (int i = arr.length - 2; i >= 0; i--) {
			rightMaxs[i] = Math.max(rightMaxs[i + 1], arr[i]);
		}
		int res = 0;
		for (int i = 1; i < arr.length - 1; i++) {
			res += Math.max(Math.min(leftMaxs[i - 1], rightMaxs[i + 1])
					- arr[i], 0);
		}
		return res;
	}

	public static int getWater3(int[] arr) {
		if (arr == null || arr.length < 3) {
			return 0;
		}
		int res = 0;
		int leftMax = arr[0];
		int rightMax = arr[arr.length - 1];
		int L = 1;
		int R = arr.length - 2;
		while (L <= R) {
			if (leftMax <= rightMax) {
				res += Math.max(0, leftMax - arr[L]);
				leftMax = Math.max(leftMax, arr[L++]);
			} else {
				res += Math.max(0, rightMax - arr[R]);
				rightMax = Math.max(rightMax, arr[R--]);
			}
		}
		return res;
	}

	public static int[] generateRandomArray() {
		int[] arr = new int[(int) (Math.random() * 98) + 2];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) (Math.random() * 200) + 2;
		}
		return arr;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 1000000; i++) {
			int[] arr = generateRandomArray();
			int r1 = getWater1(arr);
			int r2 = getWater2(arr);
			int r3 = getWater3(arr);
			if (!(r1 == r2 && r1 == r3)) {
				System.out.println("What a fucking day! fuck that! man!");
			}
		}

	}

}
