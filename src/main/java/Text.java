import java.util.*;

import static java.lang.System.in;
import static java.lang.System.out;

public class Text implements Processor {

    private final int limit;
    private List<String> words;
    private Map<String, Integer> commonWords;
    private final AnalysePrinter printer;


    public Text(int limit) {
        this.limit = limit;
        this.commonWords = new LinkedHashMap<>();
        this.words = new LinkedList<>();
        this.printer = new AnalysePrinter();
    }

    public void analyse(final String text) {
        String filteredText = ignoreSnippetsCode(text);
        words = getWords(filteredText);
        commonWords = findCommonWords();
    }

    @Override
    public void printAnalyse() {
        printer.printAnalyse(getTopTenFirstItems(commonWords), getWordsSize());
    }


    public List<String> getWords(String text) {
        String[] words = findWords(text);
        return convertToList(words);
    }

    public int getWordsSize() {
        return words.size();
    }


    public Map<String, Integer> getCommonWords() {
        TreeMap<String, Integer> sortedCommonWords = sortedMapByValue(commonWords);

        sortedCommonWords.putAll(commonWords);

        return getTopTenFirstItems(sortedCommonWords);
    }

    public String getCommonWordsElement(Integer index) {
        return getElement(getCommonWords(), index);
    }

    public String getElement(Map<String, Integer> map, Integer index) {
        String element;
        try {
            element = ConvertToArray(map)[index].toString();
        } catch (Exception e) {
            logError(map, index);
            throw e;
        }
        return element;
    }

    private Object[] ConvertToArray(Map<String, Integer> map) {
        return map.keySet().toArray();
    }

    private void logError(Map<String, Integer> map, Integer index) {
        out.println("Array Index Out Of Bounds Exception. Array size: "
                + map.size() + " But Entered: " + index);
    }


    private int getCommonWordsSize() {
        return getCommonWords().size();
    }

    private Map<String, Integer> findCommonWords() {
        Map<String, Integer> commonWords = new HashMap<>();
        for (String word : words) {
            int n = getNumberOfWordRepeat(commonWords, word);
            commonWords.put(word, n);
        }
        return commonWords;
    }

    private TreeMap<String, Integer> sortedMapByValue(Map<String, Integer> map) {
        ValueComparator bvc = new ValueComparator(map);
        return new TreeMap<>(bvc);
    }

    private int getNumberOfWordRepeat(Map<String, Integer> commonWords, String word) {
        Integer value = commonWords.get(word);
        return value != null ? value + 1 : 1;
    }

    private Map<String, Integer> getTopTenFirstItems(Map<String, Integer> sortedMap) {
        Map<String, Integer> map = new LinkedHashMap<>();
        int counter = 0;
        for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
            if (isOutOfLimit(counter)) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                map.put(key, value);
            }
            counter++;
        }
        return map;
    }

    private List<String> convertToList(String[] array) {
        return Arrays.asList(array);
    }

    private boolean isOutOfLimit(int counter) {
        return counter < this.limit;
    }

    private String ignoreSnippetsCode(String text) {
        return text.replaceAll("` ` `(?<language>\\w)(.*?)` ` `$", "");
    }

    private String[] findWords(String text) {
        return text.toLowerCase().replaceAll("[,.]", "").split(" ");
    }

}
