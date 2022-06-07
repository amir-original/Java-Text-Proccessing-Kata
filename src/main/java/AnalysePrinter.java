import java.util.Map;

import static java.lang.System.out;

public class AnalysePrinter {

    private final Text text;

    public AnalysePrinter(Text text) {
        this.text = text;
    }

    public void printAnalyse() {
        String statement = header() + orderMapNumber(text.getTopTenFirstItems()) + footer(text.getWordsSize());
        print(statement);
    }

    public String header() {
        return "Those are the top 10 words used:";
    }

    public String orderMapNumber(Map<String, Integer> map){
        StringBuilder statement = new StringBuilder();
        int number = 1;
        for (String key : map.keySet()) {
            statement.append(formatNumber(number, key));
            number++;
        }
        return statement.toString();
    }

    private String formatNumber(int number, String key) {
        return String.format("%d. %s\n",number,key);
    }

    public String footer(Integer wordsSize) {
        return String.format("The text has in total %d words",wordsSize);
    }

    public void print(String text) {
        out.println(text);
    }
}
