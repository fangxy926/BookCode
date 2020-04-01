package chapter_8_arrayandmatrix;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Problem_27_GetMaxMoney {

	public static class Program {
		public int cost; // 项目的花费
		public int profit; // 项目的利润
		public Program(int cost, int profit) {
			this.cost = cost;
			this.profit = profit;
		}
	}

	// 定义小根堆如何比较大小
	public static class CostMinComp implements Comparator<Program> {
		@Override
		public int compare(Program o1, Program o2) {
			return o1.cost - o2.cost;
		}
	}

	// 定义大根堆如何比较大小
	public static class ProfitMaxComp implements Comparator<Program> {
		@Override
		public int compare(Program o1, Program o2) {
			return o2.profit - o1.profit;
		}
	}

	public static int getMaxMoney(int W, int K, int[] costs, int[] profits) {
		// 无效参数
		if (W < 1 || K < 0 || costs == null || profits == null || costs.length != profits.length) {
			return W;
		}
		// 项目花费小根堆，花费最少的项目在顶部
		PriorityQueue<Program> costMinHeap = new PriorityQueue<>(new CostMinComp());
		// 项目利润大根堆，利润最大的项目在顶部
		PriorityQueue<Program> profitMaxHeap = new PriorityQueue<>(new ProfitMaxComp());
		// 所有项目都进项目花费小根堆
		for (int i = 0; i < costs.length; i++) {
			costMinHeap.add(new Program(costs[i], profits[i]));
		}
		// 依次做K个项目
		for (int i = 1; i <= K; i++) {
			// 当前资金为W，在项目花费小根堆里所有花费小于等于W的项目，都可以考虑
			while (!costMinHeap.isEmpty() && costMinHeap.peek().cost <= W) {
				// 把可以考虑的项目，都放进项目利润大根堆里
				profitMaxHeap.add(costMinHeap.poll());
			}
			// 如果此时项目利润大根堆为空，说明可以考虑的项目为空
			// 说明当前资金W已经无法解锁任何项目了，直接返回W
			if (profitMaxHeap.isEmpty()) {
				return W;
			}
			// 如果还可以做项目，从项目利润大根堆拿出获得利润最多的项目完成
			W += profitMaxHeap.poll().profit;
		}
		return W;
	}

}
