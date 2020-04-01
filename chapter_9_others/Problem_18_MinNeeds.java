package chapter_9_others;

public class Problem_18_MinNeeds {

	public static int minNeeds(int[] arr, int range) {
		int needs = 0;
		long touch = 0;
		for (int i = 0; i != arr.length; i++) {
			while (arr[i] > touch + 1) {
				touch += touch + 1;
				needs++;
				if (touch >= range) {
					return needs;
				}
			}
			touch += arr[i];
			if (touch >= range) {
				return needs;
			}
		}
		while (range >= touch + 1) {
			touch += touch + 1;
			needs++;
		}
		return needs;
	}

	public static void main(String[] args) {
		int[] test = { 1, 2, 31, 33 };
		int n = 2147483647;
		System.out.println(minNeeds(test, n));
	}

}
