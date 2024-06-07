/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Map;
import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    // Testing strategy
	//   add(vertex):
	//     1.input a new vertex
	//     2.input a existing vertex
	//     observe with vertices()
	//   set(source, target, weight):
    //     it can be divided into three situations by edges:
	//       add a new edge, change a existing edge or remove a existing edge
	//       1.for add, weight is positive and the edge does not exists
	//         1.1.source vertex and target vertex are both exists
	//         1.2.source vertex exists but target vertex does not exists
	//         1.3.source vertex does not exists but target vertex exists
	//         1.4.neither source vertex nor target vertex exists
	//       2.for change, weight is positive and the edge exists
	//       3.for remove, weight is zero and the edge exists
	//     observe with sources() or targets()
	//   remove(vertex):
	//     1.input a non-existing vertex
	//     2.input an existing vertex
	//     observe with vertices(), sources() and targets()
	//   vertices():
	//     1.the graph is empty
	//     2.the graph is not empty and it is mutated by add()
	//   sources(target):
	//     1.input a non-existing target vertex
	//     2.input a existing target vertex mutated by add()
	//       2.1.the target has no sources
	//       2.2.the target has sources mutated by set()
	//   targets(source):
	//     1.input a non-existing source vertex
	//     2.input a existing source vertex mutated by add()
	//       2.1.the source has no targets
	//       2.2.the source has targets mutated by set()
    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    @Test
    public void testVerticesNotEmpty() {
    	Graph<String> graph = emptyInstance();
    	graph.add("a vertex");
    	assertEquals("expected the graph have 1 vertex", 1, graph.vertices().size());
    }
    
    @Test
    public void testAddNewVertex() {
    	Graph<String> graph = emptyInstance();
    	Boolean result = graph.add("new vertex");
    	assertTrue("expected successful addition", result);
    	assertTrue("expected vertices already contained the vertex just added", graph.vertices().contains("new vertex"));
    }
    
    @Test
    public void testAddExistingVertex() {
    	Graph<String> graph = emptyInstance();
    	graph.add("existing vertex");
    	Boolean result = graph.add("existing vertex");
    	assertFalse("expected addition failure", result);
    	assertTrue("expected only 1 vertex that its label is existing vertex", graph.vertices().size() == 1);
    }
    
    @Test
    public void testSetNewEdgeWithNeitherSourceNorTarget() {
    	Graph<String> graph = emptyInstance();
    	int result = graph.set("source vertex", "target vertex", 1);
    	assertEquals("expected to add a new edge from source vertex to target vertex successfully", 0, result);
    	Integer weight1 = graph.sources("target vertex").get("source vertex");
    	Integer weight2 = graph.targets("source vertex").get("target vertex");
    	Boolean b = (weight1 == 1 && weight2 == 1);
    	assertTrue("expected that sources and targets are both modified successfully", b);
    }
    
    @Test
    public void testSetNewEdgeWithSourceButNotTarget() {
    	Graph<String> graph = emptyInstance();
    	graph.add("source vertex");
    	int result = graph.set("source vertex", "target vertex", 1);
    	assertEquals("expected to add a new edge from source vertex to target vertex successfully", 0, result);
    	Integer weight1 = graph.sources("target vertex").get("source vertex");
    	Integer weight2 = graph.targets("source vertex").get("target vertex");
    	Boolean b = (weight1 == 1 && weight2 == 1);
    	assertTrue("expected that sources and targets are both modified successfully", b);
    }
    
    @Test
    public void testSetNewEdgeWithTargetButNotSource() {
    	Graph<String> graph = emptyInstance();
    	graph.add("target vertex");
    	int result = graph.set("source vertex", "target vertex", 1);
    	assertEquals("expected to add a new edge from source vertex to target vertex successfully", 0, result);
    	Integer weight1 = graph.sources("target vertex").get("source vertex");
    	Integer weight2 = graph.targets("source vertex").get("target vertex");
    	Boolean b = (weight1 == 1 && weight2 == 1);
    	assertTrue("expected that sources and targets are both modified successfully", b);
    }
    
    @Test
    public void testSetNewEdgeWithSourceAndTarget() {
    	Graph<String> graph = emptyInstance();
    	graph.add("source vertex");
    	graph.add("target vertex");
    	int result = graph.set("source vertex", "target vertex", 1);
    	assertEquals("expected to add a new edge from source vertex to target vertex successfully", 0, result);
    	Integer weight1 = graph.sources("target vertex").get("source vertex");
    	Integer weight2 = graph.targets("source vertex").get("target vertex");
    	Boolean b = (weight1 == 1 && weight2 == 1);
    	assertTrue("expected that sources and targets are both modified successfully", b);
    }
    
    @Test
    public void testUpdateExistingEdge() {
    	Graph<String> graph = emptyInstance();
    	graph.add("source vertex");
    	graph.add("target vertex");
    	graph.set("source vertex", "target vertex", 1);
    	int result = graph.set("source vertex", "target vertex", 2);
    	assertEquals("expected to update the edge that starting from source vertex and ending at target vertex successfully", 1, result);
    	Integer weight1 = graph.sources("target vertex").get("source vertex");
    	Integer weight2 = graph.targets("source vertex").get("target vertex");
    	Boolean b = (weight1 == 2 && weight2 == 2);
    	assertTrue("expected that sources and targets are both modified successfully", b);
    }
    
    @Test
    public void testRemoveExistingEdge() {
    	Graph<String> graph = emptyInstance();
    	graph.add("source vertex");
    	graph.add("target vertex");
    	graph.set("source vertex", "target vertex", 1);
    	int result = graph.set("source vertex", "target vertex", 0);
    	assertEquals("expected to remove the edge from source vertex to target vertex", 1, result);
    	Boolean b1 = graph.sources("target vertex").keySet().contains("source vertex");
    	Boolean b2 = graph.targets("source vertex").keySet().contains("target vertex");
    	assertFalse("expected that the edge from source vertex to target vertex does not exists", b1||b2);
    }
    
    @Test
    public void testRemoveExistingVertex() {
    	Graph<String> graph = emptyInstance();
    	graph.add("source vertex");
    	graph.add("intermediate vertex");
    	graph.add("target vertex");
    	graph.set("source vertex", "intermediate vertex", 1);
    	graph.set("intermediate vertex", "target vertex", 2);
    	Boolean result = graph.remove("intermediate vertex");
    	Boolean b = graph.vertices().contains("intermediate vertex");
    	assertFalse("expected that the vertex has already disappeared", b);
    	Boolean b1 = graph.sources("target vertex").keySet().contains("intermediate vertex");
    	Boolean b2 = graph.targets("source vertex").keySet().contains("intermediate vertex");
    	assertFalse("expected that the edges from source vertex to intermediate vertex and from intermediate vertex to target vertex does not exists", b1&&b2);
    	assertTrue("expected to remove the vertex and all edges to or from it successfully", result);
    }
    
    @Test
    public void tetRemoveNonExistingVertex() {
    	Boolean result = emptyInstance().remove("nonExistingVertex");
    	assertFalse("expected that the graph is not modified", result);
    }
    
    @Test
    public void testTargetsWhenSourcesNotExists() {
    	Map<String, Integer> targetsMap = emptyInstance().targets("non-existing vertex");
    	assertTrue("expected that targetsMap is empty because of the input source vertex does not exists", targetsMap.isEmpty());
    }
    
    @Test
    public void testTargetsWhenSourcesExistsButNotHasTargets() {
    	Graph<String> graph = emptyInstance();
    	graph.add("source vertex");
    	graph.add("target vertex");
    	Map<String, Integer> targetsMap = graph.targets("source vertex");
    	assertTrue("expected that targetsMap is empty because of there are no edges from source vertex to target vertex", targetsMap.isEmpty());
    }
    
    @Test
    public void testTargetsWhenSourcesExistsAndHasTargets() {
    	Graph<String> graph = emptyInstance();
    	graph.add("source vertex");
    	graph.add("target vertex");
    	graph.set("source vertex", "target vertex", 3);
    	Map<String, Integer> targetsMap = graph.targets("source vertex");
    	assertFalse("expected that targetsMap is not empty", targetsMap.isEmpty());
    	assertEquals("the weight of edge from source vertex to target vertex", 3, targetsMap.get("target vertex").intValue());
    }
    
    @Test
    public void testSourcesWhenTragetsNotExists() {
    	Map<String, Integer> sourcesMap = emptyInstance().targets("non-existing vertex");
    	assertTrue("expected that sourcesMap is empty because of the input source vertex does not exists", sourcesMap.isEmpty());
    }
    
    @Test
    public void testSourcesWhenTragetsExistsButNotHasSources() {
    	Graph<String> graph = emptyInstance();
    	graph.add("source vertex");
    	graph.add("target vertex");
    	Map<String, Integer> sourcesMap = graph.sources("target vertex");
    	assertTrue("expected that sourcesMap is empty because of there are no edges from source vertex to target vertex", sourcesMap.isEmpty());
    }
    
    @Test
    public void testSourcesWhenTragetsExistsAndHasSources() {
    	Graph<String> graph = emptyInstance();
    	graph.add("source vertex");
    	graph.add("target vertex");
    	graph.set("source vertex", "target vertex", 2);
    	Map<String, Integer> sourcesMap = graph.sources("target vertex");
    	assertFalse("expected that sourcesMap is not empty", sourcesMap.isEmpty());
    	assertEquals("the weight of edge from source vertex to target vertex", 2, sourcesMap.get("source vertex").intValue());
    }
    
    // TODO other tests for instance methods of Graph
    
}
