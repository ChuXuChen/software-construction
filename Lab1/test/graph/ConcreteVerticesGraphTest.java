/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;
import java.util.Map;
import java.util.HashMap;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    // Testing strategy for ConcreteVerticesGraph.toString()
    //   1.the graph has not vertices
    //   2.the graph has some vertices
    
    // tests for ConcreteVerticesGraph.toString()
    @Test
    public void testVerticesGraphWithoutVertices() {
    	Graph<String> graph = emptyInstance();
    	System.out.println(graph.toString());
    }
    
    @Test
    public void testVerticesGraphWithVertices() {
    	Graph<String> graph = emptyInstance();
    	graph.add("vertex1");
    	graph.add("vertex2");
    	graph.set("vertex1", "vertex2", 1);
    	System.out.println(graph.toString());
    }
    
    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    //   addEdgeToTarget(target, weight):
    //     1.the edge already exists and weight is positive
    //     2.the edge already exists and weight is zero
    //     3.the edge does not exists and weight is positive
    //   addEdgeFromSource(source, weight):
    //     1.the edge already exists and weight is positive
    //     2.the edge already exists and weight is zero
    //     3.the edge does not exists and weight is positive
    
    @Test
    public void testAddEdgeToTarget() {
    	Map<String, Integer> predecessorMap = new HashMap<>();
    	Map<String, Integer> successorMap = new HashMap<>();
    	Vertex<? super String> vertex = new Vertex<String>("this vertex", predecessorMap, successorMap);
    	int result = vertex.addEdgeToTarget("target vertex", 1);
    	assertEquals("expected to add a new edge from this vertex to target vertex", 0, result);
    }
    
    @Test
    public void testChangeEdgeToTarget() {
    	Map<String, Integer> predecessorMap = new HashMap<>();
    	Map<String, Integer> successorMap = new HashMap<>();
    	Vertex<? super String> vertex = new Vertex<String>("this vertex", predecessorMap, successorMap);
    	vertex.addEdgeToTarget("target vertex", 1);
    	int result = vertex.addEdgeToTarget("target vertex", 2);
    	assertEquals("expected to change the edge from this vertex to target vertex", 1, result);
    }
    
    @Test
    public void testRemoveEdgeToTarget() {
    	Map<String, Integer> predecessorMap = new HashMap<>();
    	Map<String, Integer> successorMap = new HashMap<>();
    	Vertex<? super String> vertex = new Vertex<String>("this vertex", predecessorMap, successorMap);
    	vertex.addEdgeToTarget("target vertex", 1);
    	int result = vertex.addEdgeToTarget("target vertex", 0);
    	assertEquals("expected to remove the edge from this vertex to target vertex", 1, result);
    }
    
    @Test
    public void testAddEdgeFromSource() {
    	Map<String, Integer> predecessorMap = new HashMap<>();
    	Map<String, Integer> successorMap = new HashMap<>();
    	Vertex<? super String> vertex = new Vertex<String>("this vertex", predecessorMap, successorMap);
    	int result = vertex.addEdgeFromSource("source vertex", 1);
    	assertEquals("expected to add a new edge from source vertex to this vertex", 0, result);
    }
    
    @Test
    public void testChangeEdgeFromSource() {
    	Map<String, Integer> predecessorMap = new HashMap<>();
    	Map<String, Integer> successorMap = new HashMap<>();
    	Vertex<? super String> vertex = new Vertex<String>("this vertex", predecessorMap, successorMap);
    	vertex.addEdgeFromSource("source vertex", 1);
    	int result = vertex.addEdgeFromSource("source vertex", 2);
    	assertEquals("expected to add a new edge from source vertex to this vertex", 1, result);
    }
    
    @Test
    public void testRemoveEdgeFromSource() {
    	Map<String, Integer> predecessorMap = new HashMap<>();
    	Map<String, Integer> successorMap = new HashMap<>();
    	Vertex<? super String> vertex = new Vertex<String>("this vertex", predecessorMap, successorMap);
    	vertex.addEdgeFromSource("source vertex", 1);
    	int result = vertex.addEdgeFromSource("source vertex", 0);
    	assertEquals("expected to add a new edge from source vertex to this vertex", 1, result);
    }
    
    // TODO tests for operations of Vertex
    
}
