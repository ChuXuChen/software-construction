/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import graph.ConcreteEdgesGraph;
import graph.Graph;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
	
    /*
     * Provide a GraphPoet for tests in GraphPoetTest.
     */
    public GraphPoet graphPoetInstance(String path){
    	try {
            final File corpus = new File(path);
            GraphPoet graphPoet = new GraphPoet(corpus);
            return graphPoet;
    	} catch (IOException e) {
    		throw new RuntimeException(e.getMessage());
    	}
    }
    
    // Testing strategy
    //   divided by the number of lines and the number of words in a lines
	//   1.no words
	//   2.only a line
	//     2.1.only a word
	//     2.2.multiple words
	//   3.multiple lines
    //   divided by weight
    //   1.the vertex whose label is the first word with self -loop
    //   2.not 1, there are two vertices A and B in graph's vertices,
    //     the weight of edge from A to B is greater than 1, A's index is not 1
    //   3.like 2 but A's index is 1
    //   4.there are at least two two-long-edges
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    final GraphPoet graphPoetWithNoWords = graphPoetInstance("test/poet/NoWords.txt");
    final GraphPoet graphPoetWithOneLineWithOneWord = graphPoetInstance("test/poet/OneLineWithOneWord.txt");
    final GraphPoet graphPoetWithOneLineWithMultipleWords = graphPoetInstance("test/poet/OneLineWithMultipleWords.txt");
    final GraphPoet graphPoetWithMultipleLines = graphPoetInstance("test/poet/MultipleLines.txt");
    final GraphPoet graphPoetWithSelfLoop = graphPoetInstance("test/poet/SelfLoop.txt");
    final GraphPoet graphPoetWithWeightGreaterThanOne = graphPoetInstance("test/poet/WeightGreaterThanOne.txt");
    final GraphPoet graphPoetWithAtLeastTwoPaths = graphPoetInstance("test/poet/AtLeastTwoPaths.txt");
    final GraphPoet graphPoetSecondVertexWeightGreaterThanOne = graphPoetInstance("test/poet/SecondVertexWeightGreaterThanOne.txt");

    // TODO tests
    @Test
    public void testNoWords() {
    	String input = "NULL";
    	String output = graphPoetWithNoWords.poem(input);
    	assertEquals("expected that no changes for input", "NULL", output);
    }
    
    @Test
    public void testOneLineWithOneWord() {
    	String input = "HELLO";
    	String output = graphPoetWithNoWords.poem(input);
    	assertEquals("excepted that no changes for input", "HELLO", output);
    }
    
    @Test
    public void testOneLineWithMultipleWords() {
    	String input = "HELLO, MY XINGUODONG!";
    	String output = graphPoetWithOneLineWithMultipleWords.poem(input);
    	assertEquals("expected that add a bridge word for input", "HELLO, MY teacher XINGUODONG!", output);
    }
    
    @Test
    public void testMultipleLines() {
    	String input = "In eye, fear losing I never For XGD";
    	String output = graphPoetWithMultipleLines.poem(input);
    	assertEquals("In my eye, for fear of losing you, I would never cry For XGD", output);
    }
    
    @Test
    public void testSelfLoop() {
    	String input = "Hello World!";
    	String output = graphPoetWithSelfLoop.poem(input);
    	assertEquals("expected that the first vertex has self-loop so that the first vertex output twice", "Hello hello World!", output);
    }
    
    @Test
    public void testWeightGreaterThanOne() {
    	String input = "A teacher XinGuoDong";
    	String output = graphPoetWithWeightGreaterThanOne.poem(input);
    	assertEquals("expected that add only one bridge word", "A teacher named XinGuoDong", output);
    }
    
    @Test
    public void testAtLeastTwoPaths() {
    	String input = "A XGD";
    	String output = graphPoetWithAtLeastTwoPaths.poem(input);
    	assertEquals("expected that choice the bridge word whose sum of weight is max", "A master XGD", output);
    }
    
    @Test
    public void testSecondVertexWeightGreaterThanOne() {
    	String input = "A teacher";
    	String output = graphPoetSecondVertexWeightGreaterThanOne.poem(input);
    	assertEquals("expected that add only one bridge word", "A great teacher", output);
    }
    
    @Test
    public void testToStringEmpty() {
    	System.out.println(graphPoetWithNoWords.toString());
    	assert graphPoetWithNoWords.toString() != null:"toString is non-null";
    }
    
    @Test
    public void testToStringNotEmpty() {
    	System.out.println(graphPoetWithAtLeastTwoPaths.toString());
    	assert graphPoetWithAtLeastTwoPaths.toString() != null:"toString is non-null";
    }
    
}
