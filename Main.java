import java.util.*;
import java.io.*;

// -------------------------Main class-------------------------

public class Main {

    static long MOD = (long) 1e9 + 7;

    // -------------------------Main function-------------------------
    public static void main(String args[]) throws IOException {
        FastReader sc = new FastReader();
        // PrintWriter pw = new PrintWriter(System.out);
        StringBuilder sb=new StringBuilder();
        int t = sc.nextInt();
        for (int i = 0; i < t; i++) {
            
        }
        System.out.print(sb);
        // pw.flush();
        // pw.close();
    }

    // -------------------------Other functions-------------------------
    // Time Complexity : log(n)
    static long fast_power_mod(long n, long mod) {
        long pow2 = 2;
        long res = 1;
        while (n > 0) {
            if (n % 2 == 1)
                res = (res % mod * pow2 % mod) % mod;
            pow2 = (pow2 % mod * pow2 % mod) % mod;
            n >>= 1;
        }
        return res;
    }

    // Time Complexity : O(n)
    // Best mod value for hashing = 1e9 + 9
    static void prefixHashAndModInverse(String s, long[] prefixhash, long[] modInversePowP) 
    {
        long p = 31;
        long currhash = 0;
        long powP = 1;
        for(int j=0;j<s.length();j++) {
            char c = s.charAt(j);
            currhash = (currhash + (c - '0' + 1) * powP) % MOD;
            prefixhash[j] = currhash;
            powP = (powP * p) % MOD;
        }
        for(int j=0;j<s.length();j++)
        modInversePowP[j]=inverseMOD(power(p,j,MOD), MOD)%MOD;
    }

    // Use this for multiple nCr queries
    // Time Complexity : n*r
    static long nCrModpDP(long n, long r, long p) {
        long c[] = new long[(int) r + 1];
        c[0] = 1;
        for (long j = 1; j <= n; j++) {
            for (long k = Math.min(j, r); k > 0; k--)
                c[(int) k] = (c[(int) k] % p + c[(int) k - 1] % p) % p;
        }
        return c[(int) r];
    }

    // Use this for multiple nCr queries
    // Time Complexity : log(n) * n * r
    static long nCrModpLucas(long n, long r, long p) {
        if (r == 0)
            return 1;
        long ni = n % p;
        long ri = r % p;
        return (nCrModpLucas(n / p, r / p, p) % p * nCrModpDP(ni, ri, p) % p) % p;
    }

    // Use this for single nCr calculation when n <= 1e6
    // Time Complexity : n
    public static long nCrModpFermat(long n, long r, long p) {
        if (n < r)
            return 0;
        if (r == 0)
            return 1;
        long[] fac = new long[(int) (n + 1)];
        fac[0] = 1;
        for (long i = 1; i <= n; i++)
            fac[(int)i] = (fac[(int)i - 1] * i) % p;
        return (fac[(int) n] * inverseMOD(fac[(int)r], p) % p * inverseMOD(fac[(int) (n - r)], p) % p) % p;
    }

    // Time Complexity : log(mod)
    static long inverseMOD(long x, long mod)
    {
        return power(x,mod-2,mod)%mod;
    }

    // Time Complexity : log(exponent)
    static long power(long base, long exponent, long mod) {
        long ans = 1;
        base = base % mod; 
        while (exponent > 0)
        {
            if (exponent%2!=0)
                ans = (ans * base) % mod;
            exponent/=2;
            base = (base * base) % mod;
        }
        return ans;
    }

    // Time Complexity : log(max(a,b))
    static long bitwiseAND(long a, long b) {
        long shiftcount = 0;
        while (a != b && a > 0) {
            shiftcount++;
            a = a >> 1;
            b = b >> 1;
        }
        return (long) (a << shiftcount);
    }

    // Time Complexity : n*m
    static void dfs(int j, ArrayList<ArrayList<Integer>> al, boolean[] visited) {
        visited[j] = true;
        for (int x = 0; x < al.get(j).size(); x++) {
            if (!visited[al.get(j).get(x)])
                dfs(al.get(j).get(x), al, visited);
        }
    }

    // Time Complexity : log(n) for composite numbers, n for prime numbers
    static long getPrimeFactors(long n) {
        int x = 2;
        long count = 0;
        // ArrayList<Integer> al=new ArrayList<>();
        while (n > 1) {
            if (n % x == 0) {
                // if(!al.contains(x))
                // al.add(x);
                count++;
                n /= x;
            } else
                x++;
        }
        return count;
    }

    // Time Complexity : log(n)
    static ArrayList<Integer> primeFactorization(int x, int[] spf) {
        HashMap<Integer, Integer> map = new HashMap<>();
        ArrayList<Integer> al = new ArrayList<>();
        while (x != 1) {
            if (!al.contains(spf[x]))
                al.add(spf[x]);
            map.put(spf[x], map.getOrDefault(spf[x], 0) + 1);
            x /= spf[x];
        }
        return al;
        // return map;
    }

    // Time Complexity : n*10
    static long[][] getBitMap(long[] a) {
        int n = a.length;
        long[][] bit_map = new long[n][60];
        for (int j = 0; j < n; j++)
            Arrays.fill(bit_map[j], 0);
        long b[] = new long[n];
        for (int j = 0; j < n; j++)
            b[j] = a[j];
        for (int j = 0; j < n; j++) {
            int counter = 0;
            while (b[j] != 0) {
                bit_map[j][counter] = b[j] % 2;
                b[j] /= 2;
                counter++;
            }
        }
        return bit_map;
    }

    // Time Complexity : n*log(log(n))
    static TreeSet<Integer> sieveOfEratosthenes(int n) {
        boolean prime[] = new boolean[n + 1];
        for (int j = 0; j <= n; j++)
            prime[j] = true;
        for (long p = 2; 1l * p * p <= n; p++) {
            if (prime[(int) p]) {
                for (long j = 1l * p * p; j <= n; j += p)
                    prime[(int) j] = false;
            }
        }
        TreeSet<Integer> al = new TreeSet<>();
        for (long j = 2; j <= n; j++) {
            if (prime[(int) j])
                al.add((int) j);
        }
        return al;
    }

    // Time Complexity : n
    static boolean sortedIncreasing(int[] a) {
        int f = 0;
        for (int j = 1; j < a.length; j++) {
            if (a[j] < a[j - 1])
                f = 1;
        }
        return f == 0 ? true : false;
    }

    // Time Complexity : n
    static boolean sortedDecreasing(int[] a) {
        int f = 0;
        for (int j = 1; j < a.length; j++) {
            if (a[j] > a[j - 1])
                f = 1;
        }
        return f == 0 ? true : false;
    }

    // Time Complexity : sqrt(n)
    static ArrayList<Long> getFactors(long n) {
        ArrayList<Long> al = new ArrayList<>();
        // int count = 0;
        for (long i = 1; i * i <= n; i++) {
            if (n % i == 0) {
                al.add((long)i);
                // count++;
                if (n / i != i) {
                    al.add((long)(n / i));
                    // count++;
                }
            }
        }
        Collections.sort(al);
        return al;
        // return count;
    }

    // Time Complexity : n*log(n)
    static void sort(int[] a) {
        ArrayList<Integer> l = new ArrayList<>();
        for (int i : a)
            l.add(i);
        Collections.sort(l);
        for (int i = 0; i < a.length; i++)
            a[i] = l.get(i);
    }

    static void sort(char[] a) {
        ArrayList<Character> l = new ArrayList<>();
        for (char i : a)
            l.add(i);
        Collections.sort(l);
        for (int i = 0; i < a.length; i++)
            a[i] = l.get(i);
    }

    // Time Complexity : n*log(n)
    static void sort(long[] a) {
        ArrayList<Long> l = new ArrayList<>();
        for (long i : a)
            l.add(i);
        Collections.sort(l);
        for (int i = 0; i < a.length; i++)
            a[i] = l.get(i);
    }

    // Time Complexity : log(min(a,b))
    static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    // Time Complexity : log(min(a,b))
    static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    // Time Complexity : log(min(a,b))
    static long lcm(long a, long b) {
        return ((a / gcd(a, b)) * b);
    }

    // Time Complexity : log(min(a,b))
    static long lcm(int a, int b) {
        return ((a / gcd(a, b)) * b);
    }

    // Time Complexity : log(n)
    static long floorSqrt(long x) {
        if (x == 0 || x == 1)
            return x;
        long l = 1;
        long r = (long) Math.sqrt(x) + 1;
        long ans = 0;
        while (l <= r) {
            long mid = l + (r - l) / 2;
            long curr = mid * mid;
            if (curr == x)
                return mid;
            else if (curr > 0 && curr <= x) {
                ans = mid;
                l = mid + 1;
            } else
                r = mid - 1;
        }
        return ans;
    }

    // Time Complexity : log(n*logn)
    static long getRemainderSum(long[] a, long totalsum, int x) {
        long k = 0;
        outer: for (int mul = x;; mul += x) {
            int l = 0;
            int r = a.length - 1;
            int index = -1;
            while (l <= r) {
                int mid = l + (r - l) / 2;
                if (a[mid] >= mul) {
                    index = mid;
                    r = mid - 1;
                } else
                    l = mid + 1;
            }
            if (index == -1)
                break outer;
            else
                k += a.length - index;
        }
        return (totalsum - k * x);
    }

    // -------------------------BIT-------------------------

    // private static class BIT {
    //     long[] bit;
    //     long[] arr;
    //     int len;
 
    //     public BIT(int len) {
    //         bit = new long[len + 1];
    //         arr = new long[len];
    //         this.len = len;
    //     }
 
    //     public void add(int ind, long val) {
    //         arr[ind] += val;
    //         ind++;
    //         for (; ind <= len; ind += ind & -ind) {
    //             bit[ind] += val;
    //         }
    //     }
 
    //     public long query(int ind) {
    //         ind++;
    //         long sum = 0;
    //         for (; ind > 0; ind -= ind & -ind) {
    //             sum += bit[ind];
    //         }
    //         return sum;
    //     }
    // }

    // -------------------------Input class-------------------------

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;
 
        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }
 
        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }
 
        int nextInt() {
            return Integer.parseInt(next());
        }
 
        long nextLong() {
            return Long.parseLong(next());
        }
 
        double nextDouble() {
            return Double.parseDouble(next());
        }
 
        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}

// -------------------------Other classes-------------------------

class ArrayComparator implements Comparator<int[]> {
    public int compare(int[] a, int[] b) {
        if(a[0]!=b[0])
        return Integer.compare(a[0],b[0]);
        else
        return Integer.compare(a[1],b[1]);
    }
}

class ArrayComparatorString implements Comparator<String[]> {
    public int compare(String[] a, String[] b) {
        if(a[1]!=b[1])
        return b[1].compareTo(a[1]);
        else if(a[2]!=b[2])
        return a[2].compareTo(b[2]);
        else
        return a[0].compareTo(b[0]);
    }
}

class ArrayComparatorDouble implements Comparator<double[]> {
    public int compare(double[] a, double[] b) {
        if(a[3]!=b[3])
        return Double.compare(b[3], a[3]);
        else
        return Double.compare(b[2],a[2]);
    }
}

// Class and function to calculate 2D range sums
class NumMatrix {

    long[][] mat;

    public NumMatrix(long[][] matrix) {
        if(matrix.length == 0) return;
        mat = new long[matrix.length][matrix[0].length];
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                mat[i][j] = matrix[i][j];
            }
        }
        for(int i = 1; i < matrix.length; i++) {
            mat[i][0] += mat[i - 1][0];
        }
        for(int j = 1; j < matrix[0].length; j++) {
            mat[0][j] += mat[0][j - 1];
        }
        for(int i = 1; i < matrix.length; i++) {
            for(int j = 1; j < matrix[0].length; j++) {
                mat[i][j] += (mat[i - 1][j] + mat[i][j - 1] - mat[i - 1][j - 1]);
            }
        }
    }

    public long rangeSum(int row1, int col1, int row2, int col2) {
        if(mat.length == 0)
        return 0;
        long num = mat[row2][col2];
        long left = 0, upper = 0, leftupper = 0;
        if(col1 > 0 && row1 > 0)
        leftupper = mat[row1 - 1][col1 - 1];
        if(col1 > 0)
        left = mat[row2][col1 - 1];
        if(row1 > 0)
        upper = mat[row1 - 1][col2];
        return num - left - upper + leftupper;
    }
}