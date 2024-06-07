/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;


import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }

    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    // toString():
    //   1.the graph has not vertices
    //   2.the graph has some vertices mutated by add()
    //     2.1.there is no edges
    //     2.2.there are some edges mutated by set()
    
    // TODO tests for ConcreteEdgesGraph.toString()
    
    @Test
    public void testEdgesGraphWithoutVertices() {
    	Graph<String> graph = emptyInstance();
    	System.out.println("A graph without any vertcies:" + graph.toString());
    }
    
    @Test
    public void testEdgesGraphWithVerticesButNoEdges() {
    	Graph<String> graph = emptyInstance();
    	graph.add("a vertex");
    	System.out.println("A graph with vertcies but no edges:" + graph.toString());
    }
    
    @Test
    public void testEdgesGraphWithVerticesAndEdges() {
    	Graph<String> graph = emptyInstance();
    	graph.add("source vertex");
    	graph.add("target vertex");
    	graph.set("source vertex", "target vertex", 1);
    	System.out.println("A graph with vertcies and edges:" + graph.toString());
    }
    
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //   equals(Edge):
    //     1.Edge1 is the same as Edge2
    //     2.Edge1 is equal to Edge2
    //     3.Edge1 is different from Edge2
    //     4.one of the edges to be tested is null
    //     5.the types of two edges are different
    //   hashCode():
    //     equal objects have the same hash code but the converse is not all true
    
    @Test
    public void testCompareTheSameEdgeByEquals() {
    	Edge<?> edge = new Edge<String>("source vertex", "target vertex", 1);
    	assertTrue("expected the field values of the two edges are the same", edge.equals(edge) && edge.equals(edge));
    }
    
    @Test
    public void testCompareTheEqualEdgeByEquals() {
    	Edge<?> edge1 = new Edge<String>("source vertex", "target vertex", 1);
    	Edge<?> edge2 = new Edge<String>("source vertex", "target vertex", 1);
    	assertTrue("expected the field values of the two edges are the same", edge1.equals(edge2) && edge2.equals(edge1));
    }
    
    @Test
    public void testCompareTheDifferentEdgeByEquals() {
    	Edge<?> edge1 = new Edge<String>("source vertex", "target vertex", 1);
    	Edge<?> edge2 = new Edge<String>("different source vertex", "different target vertex", 1);
    	assertFalse("expected the field values of the two edges are different so that they are not equal", edge1.equals(edge2) && edge2.equals(edge1));
    }
    
    @Test
    public void testCompareEdgeAndNullByEquals() {
    	Edge<?> edge1 = new Edge<String>("source vertex", "target vertex", 1);
    	Edge<?> edge2 = null;
    	assertFalse("expected one of the edges is null so that they are not equal", edge1.equals(edge2) && edge2 == edge1);
    }
    
    @Test
    public void testCompareTwoEdgesWithDifferentTypeByEquals() {
    	Edge<?> edge1 = new Edge<String>("source vertex", "target vertex", 1);
    	Integer edge2 = Integer.valueOf(1);
    	assertFalse("expected the types of the edges are different so that they are not equal", edge1.equals(edge2) && edge2.equals(edge1));
    }
    
    @Test
    public void testHashCode() {
    	Edge<?> edge1 = new Edge<String>("source vertex", "target vertex", 1);
    	Edge<?> edge2 = new Edge<String>("source vertex", "target vertex", 1);
    	assertEquals("expected that the equal objects should have the same hash code", edge1, edge2);
    }
    
    // The equal objects must have the same hash code
    // The not equal objects may have the same hash code
    
    // TODO tests for operations of Edge
    
}
