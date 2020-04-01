package chapter_5_stringproblem;

public class Problem_25_MaxEOR {

	public static int maxXorSubarray1(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int[] eor = new int[arr.length];
		eor[0] = arr[0];
		// 生成eor数组，eor[i]代表arr[0..i]的异或和
		for (int i = 1; i < arr.length; i++) {
			eor[i] = eor[i - 1] ^ arr[i];
		}
		int max = Integer.MIN_VALUE;
		for (int j = 0; j < arr.length; j++) { // 以j位置结尾的情况下，每一个子数组最大的异或和
			for (int i = 0; i <= j; i++) { // 依次尝试arr[0..j]、arr[1..j]..arr[i..j]..arr[j..j]
				max = Math.max(max, i == 0 ? eor[j] : eor[j] ^ eor[i - 1]);
			}
		}
		return max;
	}

	// 前缀树的节点类型，每个节点向下只可能有走向0或1的路
	public static class Node {
		public Node[] nexts = new Node[2];
	}

	// 基于本题，定制前缀树的实现
	public static class NumTrie {
		// 头节点
		public Node head = new Node();

		// 把某个数字newNum加入到这棵前缀树里
		// num是一个32位的整数，所以加入的过程一共走32步
		public void add(int newNum) {
			Node cur = head;
			for (int move = 31; move >= 0; move--) {
				int path = ((newNum >> move) & 1);
				cur.nexts[path] = cur.nexts[path] == null ? new Node()
						: cur.nexts[path];
				cur = cur.nexts[path];
			}
		}

		// 给定一个eorj，eorj表示eor[j]，即以j位置结尾的情况下，arr[0..j]的异或和
		// 因为之前把eor[0]、eor[1]...eor[j-1]都加入过这棵前缀树，所以可以选择出一条最优路径来
		// maxXor方法就是把最优路径找到，并且返回eor[j]与最优路径结合之后得到的最大异或和
		public int maxXor(int eorj) {
			Node cur = head;
			int res = 0;
			for (int move = 31; move >= 0; move--) {
				int path = (eorj >> move) & 1;
				int best = move == 31 ? path : (path ^ 1);
				best = cur.nexts[best] != null ? best : (best ^ 1);
				res |= (path ^ best) << move;
				cur = cur.nexts[best];
			}
			return res;
		}

	}

	public static int maxXorSubarray2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int max = Integer.MIN_VALUE;
		int eor = 0;
		NumTrie numTrie = new NumTrie();
		numTrie.add(0);
		for (int j = 0; j < arr.length; j++) {
			eor ^= arr[j];
			max = Math.max(max, numTrie.maxXor(eor));
			numTrie.add(eor);
		}
		return max;
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random())
					- (int) (maxValue * Math.random());
		}
		return arr;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// for test
	public static void main(String[] args) {
		int testTime = 500000;
		int maxSize = 30;
		int maxValue = 50;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr = generateRandomArray(maxSize, maxValue);
			int comp = maxXorSubarray1(arr);
			int res = maxXorSubarray2(arr);
			if (res != comp) {
				succeed = false;
				printArray(arr);
				System.out.println(res);
				System.out.println(comp);
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");
		//
		// // int[] arr = generateRandomArray(6, maxValue);
		// int[] arr = { 3, -28, -29, 2};
		//
		// for (int i = 0; i < arr.length; i++) {
		// System.out.println(arr[i] + " ");
		// }
		// System.out.println("=========");
		// System.out.println(maxXorSubarray(arr));
		// System.out.println((int) (-28 ^ -29));

	}
}
