package com.example.tribesquiz;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void questionsAreCorrect() throws Exception {
        Game game = new Game();
        assertEquals(true , game.checkAnswer(0));

    }
    @Test
    public void checkStrings() throws Exception{
        Game g = new Game();
        assertEquals("האלוהים שהמוסלמים עובדים זהה ליהודים ולנוצרים",g.getNextQuestion());
        assertEquals("המוסלמים מתפללים לירח",g.getNextQuestion());
        assertEquals("ביהדות יש 4 ימי ראש השנה",g.getNextQuestion());
        assertEquals("חילוני הוא אדם אשר לא מאמין באלוהים",g.getNextQuestion());
    }

    @Test
    public void checkCurrentQuestionNum() throws Exception{
        Game g = new Game();
        assertEquals(0,g.getCorrectAnswers());
    }

    @Test
    public void checkTotalquestions() throws Exception{
        Game g = new Game();
        assertEquals(5,g.getTotalquestions());
    }

    @Test
    public void checkCorrectAnswers() throws Exception{
        Game g = new Game();
        g.getNextQuestion();
        g.getNextQuestion();
        assertEquals(0,g.getCorrectAnswers());
    }
/*
    @Test
    public void checkshowAnswerResults() throws Exception{
        QuizActivity q = new QuizActivity();
        q.showAnswerResults(true);
    }
*/
}