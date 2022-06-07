import java.util.*;

import static java.lang.System.out;

public class Text implements Processor {

    private final int limit;
    private List<String> words = new LinkedList<>();
    private final Map<String, Integer> commonWords = new LinkedHashMap<>();
    private final AnalysePrinter printer = new AnalysePrinter(this);

    public Text(int limit) {
        this.limit = limit;
    }

    public void analyse(final String text) {
        String filteredText = ignoreSnippetsCode(text);
        List<String> textWords = getWords(filteredText);
        findCommonWords(textWords);
    }

    private String ignoreSnippetsCode(String text) {
        return text.replaceAll("` ` `(?<language>\\w)(.*?)` ` `$", "");
    }

    public List<String> getWords(String text) {
        words = getListOf(findWords(text));
        return words;
    }

    private String[] findWords(String text) {
        return text.toLowerCase().replaceAll("[,.]", "").split(" ");
    }

    private List<String> getListOf(String[] array) {
        return Arrays.asList(array);
    }

    private void findCommonWords(List<String> words) {
        for (String word : words) {
            int n = getNumberOfWordRepeat(commonWords, word);
            commonWords.put(word, n);
        }
    }

    private int getNumberOfWordRepeat(Map<String, Integer> commonWords, String word) {
        Integer wordCount = commonWords.get(word);
        return isNotNull(wordCount) ? wordCount + 1 : 1;
    }

    private boolean isNotNull(Integer value) {
        return !isNull(value);
    }
    private boolean isNull(Integer value) {
        return value == null;
    }

    public String getCommonWordsElement(Integer index) {
        return getElement(getSortedCommonWords(), index);
    }

    public Map<String, Integer> getSortedCommonWords() {
        Map<String, Integer> sortedCommonWords = getSortedCommonWordsByValue();
        sortedCommonWords.putAll(commonWords);

        return getTopTenFirstItems(sortedCommonWords);
    }

    private Map<String, Integer> getSortedCommonWordsByValue() {
        ValueComparator bvc = new ValueComparator(commonWords);
        return new TreeMap<>(bvc);
    }

    private Map<String, Integer> getTopTenFirstItems(Map<String, Integer> sortedMap) {
        Map<String, Integer> topTenFirstItems = new LinkedHashMap<>();
        int counter = 0;
        for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
            if (isOutOfLimit(counter))
                topTenFirstItems.put(entry.getKey(), entry.getValue());
            counter++;
        }
        return topTenFirstItems;
    }

    private boolean isOutOfLimit(int counter) {
        return counter < limit;
    }

    public String getElement(Map<String, Integer> map, Integer index) {
        String element;
        try {
            element = getArrayOf(map)[index].toString();
        } catch (Exception e) {
            printReportError(map, index);
            throw e;
        }
        return element;
    }

    private Object[] getArrayOf(Map<String, Integer> map) {
        return map.keySet().toArray();
    }

    private void printReportError(Map<String, Integer> map, Integer index) {
        out.println(reportError(map, index));
    }

    private String reportError(Map<String, Integer> map, Integer index) {
        return String.format("Array Index Out Of Bounds Exception." + " Array size: %d But En: %d", map.size(), index);
    }

    public Map<String, Integer> getTopTenFirstItems(){
        return getTopTenFirstItems(commonWords);
    }

    public int getWordsSize() {
        return words.size();
    }

    @Override
    public void printAnalyse() {
        printer.printAnalyse();
    }

}
