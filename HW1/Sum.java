public class Sum {
    public static void main(String[] args) {
        int sum = 0;
        for (int i = 0; i < args.length; i++) {
            for (int j = 0; j < args[i].length(); j++) {
                if (Character.isWhitespace(args[i].charAt(j)) == false) {
                    int l = j;
                    int r = j;
                    while ((j < args[i].length()) && (Character.isWhitespace(args[i].charAt(j)) == false)) {
                        r++;
                        j++;
                    }
                    sum += Integer.parseInt(args[i].substring(l, r));
                }
            }
        }
        System.out.printf("%d", sum);
    }
}