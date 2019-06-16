import java.util.HashSet;
import java.util.Set;

public class Anagram {

    public static void main(String[] args) {
        new Anagram("abcd").getAnagram();
    }

    private char[] chars;
    private Set<String> result = new HashSet<>();

    public Anagram(String word) {
        this.chars = word.toCharArray();
    }

    public void getAnagram() {
        if (chars.length == 1) {
            System.out.println(chars[0]);
            return;
        }

        getAnagram(chars.length);

        for (String anagram : result) {
            System.out.println(anagram);
        }
    }

    private void getAnagram(int length) {
        if (length == 1) {
            return;
        }

        for (int i = 0; i < length; i++) {
            getAnagram(length - 1);
            result.add(String.valueOf(chars));
            rotate(length);
        }
    }

    private void rotate(int length) {
        int pos = chars.length - length;
        char temp = chars[pos];

        for (int i = pos + 1; i < chars.length; i++) {
            chars[i - 1] = chars[i];
        }

        chars[chars.length - 1] = temp;
    }
}
