package class100;

// 不停删除之后剩下的字符串
// 给定一个字符串s1，如果其中含有s2字符串，就删除最左出现的那个
// 删除之后s1剩下的字符重新拼接在一起，再删除最左出现的那个
// 如此周而复始，返回最终剩下的字符串
// 测试链接 : https://www.luogu.com.cn/problem/P4824
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Code05_DeleteAgainAndAgain {

	public static char[] s1, s2;

	public static int MAXN = 1000001;

	public static int[] next = new int[MAXN];

	public static int[] where = new int[MAXN];

	public static int[] match = new int[MAXN];

	public static int size;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		s1 = br.readLine().toCharArray();
		s2 = br.readLine().toCharArray();
		compute();
		for (int i = 0; i < size; i++) {
			out.print(s1[where[i]]);
		}
		out.println();
		out.flush();
		out.close();
		br.close();
	}

	public static void compute() {
		size = 0;
		int n = s1.length, m = s2.length, x = 0, y = 0;
		nextArray(m);
		while (x < n) {
			if (s1[x] == s2[y]) {
				where[size] = x;
				match[size] = y;
				size++;
				x++;
				y++;
			} else if (y == 0) {
				where[size] = x;
				match[size] = y;
				size++;
				x++;
			} else {
				y = next[y];
			}
			if (y == m) {
				size -= m;
				if (size > 0) {
					y = match[size - 1] + 1;
				} else {
					y = 0;
				}
			}
		}
	}

	public static void nextArray(int m) {
		next[0] = -1;
		next[1] = 0;
		int i = 2, cn = 0;
		while (i < m) {
			if (s2[i - 1] == s2[cn]) {
				next[i++] = ++cn;
			} else if (cn > 0) {
				cn = next[cn];
			} else {
				next[i++] = 0;
			}
		}
	}

}
