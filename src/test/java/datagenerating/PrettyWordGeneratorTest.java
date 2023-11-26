package datagenerating;

import com.jfund.testutils.datagenerating.PrettyWordGenerator;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class PrettyWordGeneratorTest {
    @Test
    public void shouldGeneration1000UniqueWordsWithLength6(){
        int wordLength = 6;
        int maxWordCount = 1000;
        Set<String> uniqueWords = generateWords(wordLength, maxWordCount);

        assertFalse(uniqueWords.isEmpty());
    }

    @Test
    public void testParallelLaunch() throws ExecutionException, InterruptedException {
       CompletableFuture<Void> task = CompletableFuture.runAsync(() -> {
           try {
               generateWords(6, 30);
               TimeUnit.MILLISECONDS.sleep(20);
           } catch (InterruptedException e) {
               throw new RuntimeException(e);
           }
       });

       CompletableFuture.allOf(task, task, task, task, task, task, task).get();
    }

    private Set<String> generateWords(int wordLength, int maxWordCount){
        PrettyWordGenerator wordGenerator = new PrettyWordGenerator(wordLength);
        String word = wordGenerator.getCurrentWord();
        Set<String> uniqueWords = new HashSet<>(Set.of(word));
        for (int i = 0; i < maxWordCount - 1 && wordGenerator.hasNext(); i++) {
            word = wordGenerator.next();
            uniqueWords.add(word);
        }

        return uniqueWords;
    }
}
