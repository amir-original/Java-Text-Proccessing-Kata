import java.util.*;

import static java.lang.System.out;

public class TextProcessing{

    private String text;
    private final int limit;

    public TextProcessing(String text,int limit){
        this.text = text;
        this.limit = limit;
    }

    public void analyse(){
        ignoreSnippetsCode();
        Map<String,Integer> commonWords = getCommonWords();
        String header = "Those are the top 10 words used:";
        out.println(header);
        int number = 1;
        for (String commonWord : commonWords.keySet()) {
            out.println(number++ +". "+commonWord);
        }
        out.println("The text has in total "+ getWords().size() +" words");
    }

    public List<String> getWords() {
        ignoreSnippetsCode();
        String[] words  = text.toLowerCase().replaceAll("[,.]","").split(" ");
        List<String> list = Arrays.asList(words);
        return new ArrayList<>(list);
    }

    public Map<String,Integer> getCommonWords() {
        ignoreSnippetsCode();
        List<String> words = getWords();

        Map<String,Integer> commonWords = new HashMap<>();
        ValueComparator bvc = new ValueComparator(commonWords);

        TreeMap<String,Integer> sorted_commonWords = new TreeMap<>(bvc);
        for (String word : words) {
            int i = commonWords.get(word) != null ? commonWords.get(word) + 1:1;
            commonWords.put(word,i);
        }

        sorted_commonWords.putAll(commonWords);

        return getTenFirstItems(sorted_commonWords);
    }

    private Map<String, Integer> getTenFirstItems(TreeMap<String, Integer> commonWords) {
        int limit = 10;
        Map<String,Integer> map = new LinkedHashMap<>();
        for (Map.Entry<String,Integer> entry : commonWords.entrySet()) {
            if (limit > 0){
                map.put(entry.getKey(),entry.getValue());
            }else{
                break;
            }
               limit--;
        }
        return map;
    }

    public void ignoreSnippetsCode(){
        text = text.replaceAll("` ` `(?<language>\\w)(.*?)` ` `$","");
    }

}
