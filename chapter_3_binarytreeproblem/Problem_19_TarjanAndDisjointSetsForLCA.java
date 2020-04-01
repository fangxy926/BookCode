package chapter_3_binarytreeproblem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Problem_19_TarjanAndDisjointSetsForLCA {

	public static class Element<V> {
		public V value;

		public Element(V value) {
			this.value = value;
		}

	}

	public static class UnionFindSet<V> {
		public HashMap<V, Element<V>> elementMap;
		public HashMap<Element<V>, Element<V>> fatherMap;
		public HashMap<Element<V>, Integer> rankMap;

		public UnionFindSet(List<V> list) {
			elementMap = new HashMap<>();
			fatherMap = new HashMap<>();
			rankMap = new HashMap<>();
			for (V value : list) {
				Element<V> element = new Element<V>(value);
				elementMap.put(value, element);
				fatherMap.put(element, element);
				rankMap.put(element, 1);
			}
		}

		private Element<V> findHead(Element<V> element) {
			Stack<Element<V>> path = new Stack<>();
			while (element != fatherMap.get(element)) {
				path.push(element);
				element = fatherMap.get(element);
			}
			while (!path.isEmpty()) {
				fatherMap.put(path.pop(), element);
			}
			return element;
		}

		public V findHead(V value) {
			return elementMap.containsKey(value) ? findHead(elementMap
					.get(value)).value : null;
		}

		public boolean isSameSet(V a, V b) {
			if (elementMap.containsKey(a) && elementMap.containsKey(b)) {
				return findHead(elementMap.get(a)) == findHead(elementMap
						.get(b));
			}
			return false;
		}

		public void union(V a, V b) {
			if (elementMap.containsKey(a) && elementMap.containsKey(b)) {
				Element<V> aF = findHead(elementMap.get(a));
				Element<V> bF = findHead(elementMap.get(b));
				if (aF != bF) {
					int aFrank = rankMap.get(aF);
					int bFrank = rankMap.get(bF);
					if (aFrank >= bFrank) {
						fatherMap.put(bF, aF);
						rankMap.put(aF, aFrank + bFrank);
						rankMap.remove(bF);
					} else {
						fatherMap.put(aF, bF);
						rankMap.put(bF, aFrank + bFrank);
						rankMap.remove(aF);
					}
				}
			}
		}

	}

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	public static class Query {
		public Node o1;
		public Node o2;

		public Query(Node o1, Node o2) {
			this.o1 = o1;
			this.o2 = o2;
		}
	}

	public static Node[] tarJanQuery(Node head, Query[] quries) {
		HashMap<Node, LinkedList<Node>> queryMap = new HashMap<>();
		HashMap<Node, LinkedList<Integer>> indexMap = new HashMap<>();
		HashMap<Node, Node> ancestorMap = new HashMap<>();
		UnionFindSet<Node> sets = new UnionFindSet<>(getAllNodes(head));
		Node[] ans = new Node[quries.length];
		setQueriesAndSetEasyAnswers(quries, ans, queryMap, indexMap);
		setAnswers(head, ans, queryMap, indexMap, ancestorMap, sets);
		return ans;
	}

	public static List<Node> getAllNodes(Node head) {
		List<Node> res = new ArrayList<>();
		process(head, res);
		return res;
	}

	public static void process(Node head, List<Node> res) {
		if (head == null) {
			return;
		}
		res.add(head);
		process(head.left, res);
		process(head.right, res);
	}

	public static void setQueriesAndSetEasyAnswers(Query[] ques, Node[] ans,
			HashMap<Node, LinkedList<Node>> queryMap,
			HashMap<Node, LinkedList<Integer>> indexMap) {
		Node o1 = null;
		Node o2 = null;
		for (int i = 0; i != ans.length; i++) {
			o1 = ques[i].o1;
			o2 = ques[i].o2;
			if (o1 == o2 || o1 == null || o2 == null) {
				ans[i] = o1 != null ? o1 : o2;
			} else {
				if (!queryMap.containsKey(o1)) {
					queryMap.put(o1, new LinkedList<Node>());
					indexMap.put(o1, new LinkedList<Integer>());
				}
				if (!queryMap.containsKey(o2)) {
					queryMap.put(o2, new LinkedList<Node>());
					indexMap.put(o2, new LinkedList<Integer>());
				}
				queryMap.get(o1).add(o2);
				indexMap.get(o1).add(i);
				queryMap.get(o2).add(o1);
				indexMap.get(o2).add(i);
			}
		}
	}

	public static void setAnswers(Node head, Node[] ans,
			HashMap<Node, LinkedList<Node>> queryMap,
			HashMap<Node, LinkedList<Integer>> indexMap,
			HashMap<Node, Node> ancestorMap, UnionFindSet<Node> sets) {
		if (head == null) {
			return;
		}
		setAnswers(head.left, ans, queryMap, indexMap, ancestorMap, sets);
		sets.union(head.left, head);
		ancestorMap.put(sets.findHead(head), head);
		setAnswers(head.right, ans, queryMap, indexMap, ancestorMap, sets);
		sets.union(head.right, head);
		ancestorMap.put(sets.findHead(head), head);
		LinkedList<Node> nList = queryMap.get(head);
		LinkedList<Integer> iList = indexMap.get(head);
		Node node = null;
		Node nodeFather = null;
		int index = 0;
		while (nList != null && !nList.isEmpty()) {
			node = nList.poll();
			index = iList.poll();
			nodeFather = sets.findHead(node);
			if (ancestorMap.containsKey(nodeFather)) {
				ans[index] = ancestorMap.get(nodeFather);
			}
		}
	}

	// for test -- print tree
	public static void printTree(Node head) {
		System.out.println("Binary Tree:");
		printInOrder(head, 0, "H", 17);
		System.out.println();
	}

	public static void printInOrder(Node head, int height, String to, int len) {
		if (head == null) {
			return;
		}
		printInOrder(head.right, height + 1, "v", len);
		String val = to + head.value + to;
		int lenM = val.length();
		int lenL = (len - lenM) / 2;
		int lenR = len - lenM - lenL;
		val = getSpace(lenL) + val + getSpace(lenR);
		System.out.println(getSpace(height * len) + val);
		printInOrder(head.left, height + 1, "^", len);
	}

	public static String getSpace(int num) {
		String space = " ";
		StringBuffer buf = new StringBuffer("");
		for (int i = 0; i < num; i++) {
			buf.append(space);
		}
		return buf.toString();
	}

	public static void main(String[] args) {
		Node head = new Node(1);
		head.left = new Node(2);
		head.right = new Node(3);
		head.left.left = new Node(4);
		head.left.right = new Node(5);
		head.right.left = new Node(6);
		head.right.right = new Node(7);
		head.right.right.left = new Node(8);
		printTree(head);
		System.out.println("===============");

		Query[] qs = new Query[7];
		qs[0] = new Query(head.left.right, head.right.left);
		qs[1] = new Query(head.left.left, head.left);
		qs[2] = new Query(head.right.left, head.right.right.left);
		qs[3] = new Query(head.left.left, head.right.right);
		qs[4] = new Query(head.right.right, head.right.right.left);
		qs[5] = new Query(head, head);
		qs[6] = new Query(head.left, head.right.right.left);

		Node[] ans = tarJanQuery(head, qs);

		for (int i = 0; i != ans.length; i++) {
			System.out.println("o1 : " + qs[i].o1.value);
			System.out.println("o2 : " + qs[i].o2.value);
			System.out.println("ancestor : " + ans[i].value);
			System.out.println("===============");
		}

	}
}
