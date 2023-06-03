import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class Temp {

  private static final Map<int[], Integer> map = new HashMap<>();

  private static int findHappiness(int sum, int index, List<int[]> pairs, int[] prefixSumArray) {
    if (index == pairs.size()) {
      return sum;
    }

    int[] key = new int[] {sum, index};
    if (map.get(key) != null) {
      return map.get(key);
    }

    int csum = findRangeSum(pairs.get(index)[0], pairs.get(index)[1], prefixSumArray);
    map.put(key, Math.max(findHappiness(sum,index + 1, pairs, prefixSumArray),
        findHappiness(csum + sum, index + 1, pairs, prefixSumArray)));

    return map.get(key);
  }

  private static int findRangeSum(int l, int r, int[] psa) {
    l -= 1;
    r -= 1;

    return psa[r] - (l > 0 ? psa[l - 1] : 0);
  }

  private static int[] createPrefixSumArray(List<Integer> list) {
    int[] psa = new int[list.size()];
    if (list.size() >= 1) {
      psa[0] = list.get(0);
    }

    for (int i = 1;i < list.size();i++) {
      psa[i] = psa[i - 1] + list.get(i);
    }

    return psa;
  }

  public static void main(String[] args) {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    try {
      String[] input = reader.readLine().split(" ");

      int n = Integer.parseInt(input[0]);
      int m = Integer.parseInt(input[1]);

      List<Integer> flowers = Arrays.stream(reader.readLine().split(" ")).map(Integer::parseInt)
          .collect(Collectors.toList());
      int[] psa = createPrefixSumArray(flowers);
      int sum = 0;

      for (int i = 0;i < m;i++) {
        input = reader.readLine().split(" ");

        int l = Integer.parseInt(input[0]);
        int r = Integer.parseInt(input[1]);

        int csum = findRangeSum(l, r, psa);
        sum += (Math.max(csum, 0));
      }

      System.out.println(sum);

    } catch (Exception ignored) {}
  }
}
