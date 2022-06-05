import java.util.*;

import static java.lang.System.out;

public class Text implements Processor {

    private final int limit;
    private List<String> words= new LinkedList<>();
    private Map<String, Integer> commonWords = new LinkedHashMap<>();
    private final AnalysePrinter printer = new AnalysePrinter(this);


    public Text(int limit) {
        this.limit = limit;
    }

    public void analyse(final String text) {
        String filteredText = ignoreSnippetsCode(text);
        words = getWords(filteredText);
        commonWords = findCommonWords();
    }

    @Override
    public void printAnalyse() {
        printer.printAnalyse();
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
            printReportError(map, index);
            throw e;
        }
        return element;
    }

    private Object[] ConvertToArray(Map<String, Integer> map) {
        return map.keySet().toArray();
    }

    private void printReportError(Map<String, Integer> map, Integer index) {
        String report = String.format("Array Index Out Of Bounds Exception. Array size: %d But Entered: %d", map.size(), index);
        out.println(report);
    }


    private int getCommonWordsSize() {
        return commonWords.size();
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

    public Map<String, Integer> getTopTenFirstItems(){
        return getTopTenFirstItems(commonWords);
    }
    private Map<String, Integer> getTopTenFirstItems(Map<String, Integer> sortedMap) {
        Map<String, Integer> map = new LinkedHashMap<>();
        int counter = 0;
        for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
            if (isOutOfLimit(counter))
                map.put(entry.getKey(), entry.getValue());
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
