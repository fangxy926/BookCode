package chapter_4_recursionanddp;

import java.util.Arrays;
import java.util.Comparator;

public class Problem_08_EnvelopesProblem {

	public static class Envelope {
		public int len;
		public int wid;

		public Envelope(int weight, int hight) {
			len = weight;
			wid = hight;
		}
	}

	public static class EnvelopeComparator implements Comparator<Envelope> {
		@Override
		public int compare(Envelope o1, Envelope o2) {
			return o1.len != o2.len ? o1.len - o2.len : o2.wid - o1.wid;
		}
	}

	public static Envelope[] getSortedEnvelopes(int[][] matrix) {
		Envelope[] res = new Envelope[matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			res[i] = new Envelope(matrix[i][0], matrix[i][1]);
		}
		Arrays.sort(res, new EnvelopeComparator());
		return res;
	}

	public static int maxEnvelopes(int[][] matrix) {
		Envelope[] envelopes = getSortedEnvelopes(matrix);
		int[] ends = new int[matrix.length];
		ends[0] = envelopes[0].wid;
		int right = 0;
		int l = 0;
		int r = 0;
		int m = 0;
		for (int i = 1; i < envelopes.length; i++) {
			l = 0;
			r = right;
			while (l <= r) {
				m = (l + r) / 2;
				if (envelopes[i].wid > ends[m]) {
					l = m + 1;
				} else {
					r = m - 1;
				}
			}
			right = Math.max(right, l);
			ends[l] = envelopes[i].wid;
		}
		return right + 1;
	}

	public static void main(String[] args) {
		int[][] test = { { 3, 4 }, { 2, 3 }, { 4, 5 }, { 1, 3 }, { 2, 2 },
				{ 3, 6 }, { 1, 2 }, { 3, 2 }, { 2, 4 } };
		System.out.println(maxEnvelopes(test));
	}
}
