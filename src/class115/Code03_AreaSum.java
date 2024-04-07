package class115;

// 矩形面积并
// 测试链接 : https://www.luogu.com.cn/problem/P5490
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下的code，提交时请把类名改成"Main"，可以直接通过

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Arrays;

public class Code03_AreaSum {

	public static int MAXN = 300001;

	public static int[][] rec = new int[MAXN][4];

	public static int[][] line = new int[MAXN][4];

	public static int[] y = new int[MAXN];

	public static int[] length = new int[MAXN << 2];

	public static int[] times = new int[MAXN << 2];

	public static int[] cover = new int[MAXN << 2];

	public static int prepare(int n) {
		Arrays.sort(y, 1, n + 1);
		int m = 1;
		for (int i = 2; i <= n; i++) {
			if (y[m] != y[i]) {
				y[++m] = y[i];
			}
		}
		y[m + 1] = y[m];
		return m;
	}

	public static int rank(int n, int num) {
		int ans = 0;
		int l = 1, r = n, mid;
		while (l <= r) {
			mid = (l + r) / 2;
			if (y[mid] >= num) {
				ans = mid;
				r = mid - 1;
			} else {
				l = mid + 1;
			}
		}
		return ans;
	}

	private static void build(int l, int r, int i) {
		if (l < r) {
			int mid = (l + r) / 2;
			build(l, mid, i << 1);
			build(mid + 1, r, i << 1 | 1);
		}
		length[i] = y[r + 1] - y[l];
		times[i] = 0;
		cover[i] = 0;
	}

	public static void up(int i) {
		if (times[i] > 0) {
			cover[i] = length[i];
		} else {
			cover[i] = cover[i << 1] + cover[i << 1 | 1];
		}
	}

	private static void add(int jobl, int jobr, int jobv, int l, int r, int i) {
		if (jobl <= l && r <= jobr) {
			times[i] += jobv;
		} else {
			int mid = (l + r) / 2;
			if (jobl <= mid) {
				add(jobl, jobr, jobv, l, mid, i << 1);
			}
			if (jobr > mid) {
				add(jobl, jobr, jobv, mid + 1, r, i << 1 | 1);
			}
		}
		up(i);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		for (int i = 1; i <= n; i++) {
			in.nextToken(); rec[i][0] = (int) in.nval;
			in.nextToken(); rec[i][1] = (int) in.nval;
			in.nextToken(); rec[i][2] = (int) in.nval;
			in.nextToken(); rec[i][3] = (int) in.nval;
		}
		out.println(compute(n));
		out.flush();
		out.close();
		br.close();
	}

	public static long compute(int n) {
		for (int i = 1, j = 1 + n, x1, y1, x2, y2; i <= n; i++, j++) {
			x1 = rec[i][0]; y1 = rec[i][1]; x2 = rec[i][2]; y2 = rec[i][3];
			y[i] = y1; y[j] = y2;
			line[i][0] = x1; line[i][1] = y1; line[i][2] = y2; line[i][3] = 1;
			line[j][0] = x2; line[j][1] = y1; line[j][2] = y2; line[j][3] = -1;
		}
		n <<= 1;
		int m = prepare(n);
		build(1, m, 1);
		Arrays.sort(line, 1, n + 1, (a, b) -> a[0] - b[0]);
		long ans = 0;
		for (int i = 1, pre = 0; i <= n; i++) {
			ans += (long) cover[1] * (line[i][0] - pre);
			pre = line[i][0];
			add(rank(m, line[i][1]), rank(m, line[i][2]) - 1, line[i][3], 1, m, 1);
		}
		return ans;
	}

}