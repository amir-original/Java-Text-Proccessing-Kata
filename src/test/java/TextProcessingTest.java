import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static java.lang.System.runFinalization;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class TextProcessingTest {
    private final int limit = 10;
    private Text textProcessor;

    @BeforeEach
    void init() {
        textProcessor = new Text(limit);
    }

    @Test
    void should_be_return_words_size_in_the_text() {
        String text = "Hello, this is an example for you to practice. You should grab " +
                "this text and make it as your test case.";
        textProcessor.analyse(text);
        assertThat(textProcessor.getWordsSize()).isEqualTo(21);
        textProcessor.printAnalyse();
    }

    @Test
    void should_be_return_most_common_words_used_in_the_text() {
        String text = "Hello, this is an example for you to practice. You should grab " +
                "this text and make it as your test case.";

        textProcessor.analyse(text);
        out.println(textProcessor.getCommonWords());
        assertThat(textProcessor.getCommonWords().size()).isEqualTo(10);
        assertThat(textProcessor.getCommonWordsElement(0)).isEqualTo("you");
        assertThat(textProcessor.getCommonWordsElement(1)).isEqualTo("this");
        textProcessor.printAnalyse();
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
                "   out.println('should should should')" +
                "}" +
                "` ` `";

        textProcessor.analyse(text);
        assertThat(textProcessor.getWordsSize()).isEqualTo(21);
        assertThat(textProcessor.getCommonWordsElement(0)).isEqualTo("you");
        assertThat(textProcessor.getCommonWordsElement(1)).isEqualTo("this");
        textProcessor.analyse(text2);
        assertThat(textProcessor.getWordsSize()).isEqualTo(21);
        assertThat(textProcessor.getCommonWordsElement(0)).isEqualTo("you");
        assertThat(textProcessor.getCommonWordsElement(1)).isEqualTo("this");
    }

    @Test
    void should_be_ignore_snippets_code_and_analyse() {
        String text = "Hello, this is an example for you to practice. You should grab " +
                "this text and make it as your test case." +
                "` ` `Java" +
                "if (true) {" +
                "  out.println('should should should')" +
                "}" +
                "` ` `";
        textProcessor.analyse(text);
        assertThat(textProcessor.getWordsSize()).isEqualTo(21);
        assertThat(textProcessor.getCommonWordsElement(0)).isEqualTo("you");
        assertThat(textProcessor.getCommonWordsElement(1)).isEqualTo("this");

    }
}
