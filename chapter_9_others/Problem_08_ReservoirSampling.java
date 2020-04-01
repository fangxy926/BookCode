package chapter_9_others;

public class Problem_08_ReservoirSampling {

	// һ���򵥵��������������һ�����������ǲ���
	public static int rand(int max) {
		return (int) (Math.random() * max) + 1;
	}

	public static int[] getKNumsRand(int k, int max) {
		if (max < 1 || k < 1) {
			return null;
		}
		int[] res = new int[Math.min(k, max)];
		for (int i = 0; i != res.length; i++) {
			res[i] = i + 1; // ǰk����ֱ�ӽ�����
		}
		for (int i = k + 1; i < max + 1; i++) {
			if (rand(i) <= k) { // ����i����������
				res[rand(k) - 1] = i; // i�����������е�һ��
			}
		}
		return res;
	}

	public static void printArray(int[] arr) {
		for (int i = 0; i != arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int[] res = getKNumsRand(10, 10000);
		printArray(res);
	}

}
