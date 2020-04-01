package chapter_3_binarytreeproblem;

import java.util.ArrayList;
import java.util.List;

public class Problem_21_MaxHappy {

	public static class Employee {
		public int happy;
		List<Employee> subordinates;

		public Employee(int happy) {
			this.happy = happy;
			subordinates = new ArrayList<>();
		}
	}

	// 每棵树处理完之后的返回值类型
	public static class ReturnData {
		public int yesHeadMax; // 树的头节点来的情况下，整棵树的最大快乐值
		public int noHeadMax; // 树的头节点不来的情况下，整棵树的最大快乐值

		public ReturnData(int yesHeadMax, int noHeadMax) {
			this.yesHeadMax = yesHeadMax;
			this.noHeadMax = noHeadMax;
		}
	}

	// 该函数处理以X为头的树，并且返回X来和不来两种情况下的两个最大快乐值
	// 所以返回值的类型为ReturnData类型
	public static ReturnData process(Employee X) {
		int yesX = X.happy;// X来的情况下，一定要累加上X自己的快乐值
		int noX = 0;// X不来的情况下，不累加上X自己的快乐值
		if (X.subordinates.isEmpty()) { // 如果X没有直接下属，说明是基层员工，直接返回即可
			return new ReturnData(yesX, noX);
		} else { // 如果X有直接下属，就按照题目的分析来
			// 枚举X的每一个直接下级员工next
			for (Employee next : X.subordinates) {
				// 递归调用process，得到以next为头的子树，在next来和不来两个情况下，分别的最大收益
				ReturnData subTreeInfo = process(next);
				yesX += subTreeInfo.noHeadMax; // 见书中yes_X_max的分析
				noX += Math.max(subTreeInfo.yesHeadMax, subTreeInfo.noHeadMax);// 见书中no_X_max的分析
			}
			return new ReturnData(yesX, noX);
		}
	}

	public static int getMaxHappy(Employee boss) {
		ReturnData allTreeInfo = process(boss);
		return Math.max(allTreeInfo.noHeadMax, allTreeInfo.yesHeadMax);
	}

}
