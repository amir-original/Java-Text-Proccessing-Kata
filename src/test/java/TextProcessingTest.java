import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TextProcessingTest {
    private final int limit = 10;

    @Test
    void should_return_words_size_in_the_text() {
        String text = "Hello, this is an example for you to practice. You should grab " +
                "this text and make it as your test case.";
        assertThat(textProcessing(text).getWords().size()).isEqualTo(21);
    }

    @Test
    void should_return_most_common_words_used_in_the_text() {
        String text = "Hello, this is an example for you to practice. You should grab " +
                "this text and make it as your test case.";

        System.out.println(textProcessing(text).getCommonWords());
        assertThat(textProcessing(text).getCommonWords().size()).isEqualTo(10);
        assertThat(textProcessing(text).getCommonWords().keySet().toArray()[0]).isEqualTo("you");
        assertThat(textProcessing(text).getCommonWords().keySet().toArray()[1]).isEqualTo("this");
    }

    @Test
    void should_be_processing_and_print_analyse_output() {
        String text = "Hello, this is an example for you to practice. You should grab " +
                "this text and make it as your test case.";
        textProcessing(text).analyse();
    }

    @Test
    void should_be_ignore_a_given_piece_of_text_to_analyse() {
        String text = "Hello, this is an example for you to practice. You should grab " +
                "this text and make it as your test case." +
                "` ` `javascript" +
                "if (true) {" +
                "  console.log('should should should')" +
                "}" +
                "` ` `";
        String text2 = "Hello, this is an example for you to practice. You should grab " +
                "this text and make it as your test case." +
                "` ` `Java" +
                "if (true) {" +
                "  console.log('should should should')" +
                "}" +
                "` ` `";

        assertThat(textProcessing(text).getWords().size()).isEqualTo(21);
        assertThat(textProcessing(text2).getWords().size()).isEqualTo(21);
    }

    @Test
    void should_be_ignore_snippets_code_and_analyse() {
        String text = "Hello, this is an example for you to practice. You should grab " +
                "this text and make it as your test case." +
                "` ` `Java" +
                "if (true) {" +
                "  console.log('should should should')" +
                "}" +
                "` ` `";
        textProcessing(text).analyse();
    }


    private TextProcessing textProcessing(String text){
        return new TextProcessing(text,limit);
    }
}
