import java.util.Map;

import static java.lang.System.out;

public class AnalysePrinter {

    public void print(String text) {
        out.println(text);
    }

    public void printAnalyse(Map<String, Integer> map,Integer wordsSize) {
      printHeader();
      printInOrder(map);
      printFooter(wordsSize);
    }

    public void printInOrder(Map<String, Integer> map){
        int number = 1;
        for (String key : map.keySet()) {
            out.println(number + ". " + key);
            number++;
        }
    }

    public void printHeader() {
        out.println("Those are the top 10 words used:");
    }

    public void printFooter(Integer wordsSize) {
        out.println("The text has in total "+ wordsSize +" words");
    }
}
