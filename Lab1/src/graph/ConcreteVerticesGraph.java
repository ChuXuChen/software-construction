/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {
    
    private final List<Vertex<L>> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   AF(vertices) = a graph that
    //     its vertices are all the labels in the List(vertices)
    //     for any vertex, its predecessors are stored as key in its predecessorMap
    //       for a key value pair in this map
    //       the value corresponding to the key are the weight of edge from the predecessor to this vertex
    //       the same goes for its successors
    // Representation invariant:
	//     the key of elements in predecessorMap or successorMap belongs to the set of all labels in Vertex
	//     if A is the predecessor of B, B is the successor of A and their weight are the same, vice versa
	//     there are no repeating elements in vertices
    // Safety from rep exposure:
    //   vertices is private and final but mutable
    //   producer and observer should make defensive copy of it
    
    // TODO constructor
    public ConcreteVerticesGraph() {
    	
    }
    
    // TODO checkRep
    /**
     * the key of elements in predecessorMap or successorMap belongs to the set of all labels in Vertex
     * if A is the predecessor of B, B is the successor of A and their weight are the same, vice versa
     * there are no repeating elements in vertices
     */
    public void checkRep() {
    	Set<L> labelSet = new HashSet<>();
    	Iterator<Vertex<L>> iterator = this.vertices.iterator();
    	while(iterator.hasNext()) {
    		Vertex<L> next = iterator.next();
    		labelSet.addAll(next.getPredecessorMap().keySet());
    		labelSet.addAll(next.getSuccessorMap().keySet());
    		Iterator<Map.Entry<L, Integer>> iteratorPredecessorMap = next.getPredecessorMap().entrySet().iterator();
    		while (iteratorPredecessorMap.hasNext()){
    			Map.Entry<L, Integer> nextEntry = iteratorPredecessorMap.next();
    			if(!this.vertices().contains(nextEntry.getKey()))
    				continue;
    			Vertex<L> sourceVertex = this.filterByLabel(nextEntry.getKey());
    			Map<L, Integer> newSuccessorMap = sourceVertex.getSuccessorMap();
    			assert newSuccessorMap.keySet().contains(next.getLabel()):"if A is the predecessor of B, B is the successor of A, vice versa";
    			assert newSuccessorMap.get(next.getLabel()).equals(nextEntry.getValue()):"the weight from A to B are stored in two places:A's successorMap and B's predecessorMap, they shpuld be equal";
    		}
    		Iterator<Map.Entry<L, Integer>> iteratorSuccessorMap = next.getSuccessorMap().entrySet().iterator();
    		while (iteratorSuccessorMap.hasNext()){
    			Map.Entry<L, Integer> nextEntry = iteratorSuccessorMap.next();
    			if(!this.vertices().contains(nextEntry.getKey()))
    				continue;
    			Vertex<L> targetVertex = this.filterByLabel(nextEntry.getKey());
    			Map<L, Integer> newPredecessorMap = targetVertex.getPredecessorMap();
    			assert newPredecessorMap.keySet().contains(next.getLabel()):"if A is the predecessor of B, B is the successor of A, vice versa";
    			assert newPredecessorMap.get(next.getLabel()).equals(nextEntry.getValue()):"the weight from A to B are stored in two places:A's successorMap and B's predecessorMap, they shpuld be equal";
    		}
    	}
    	assert this.vertices().containsAll(labelSet):"the key of elements in predecessorMap or successorMap should belong to the set of all labels in Vertex";
    	assert this.vertices().size() == this.vertices.size():"there should be no repeating elements in vertices";
    }
    
    private Vertex<L> filterByLabel(L label){
    	List<Vertex<L>> vertexListFilteredByLabel = this.vertices.stream().filter(item -> item.getLabel().equals(label)).collect(Collectors.toList());
    	return vertexListFilteredByLabel.get(0);
    }
    
    @Override public boolean add(L vertex) {
//        throw new RuntimeException("not implemented");
    	Boolean b = false;
    	Map<L, Integer> predecessorMap = new HashMap<>();
    	Map<L, Integer> successorMap = new HashMap<>();
    	Vertex<L> newVertex = new Vertex<L>(vertex, predecessorMap, successorMap);
    	Set<L> labelSet = this.vertices();
    	if(!labelSet.contains(vertex)) {
    		b = true;
    		this.vertices.add(newVertex);
    	}
    	checkRep();
    	return b;
    }
    
    @Override public int set(L source, L target, int weight) {
//        throw new RuntimeException("not implemented");
    	if(!this.vertices().contains(source)) {
    		this.add(source);
    	}
    	if(!this.vertices().contains(target)) {
    		this.add(target);
    	}
    	Vertex<L> sourceVertex = this.filterByLabel(source);
    	Vertex<L> targetVertex = this.filterByLabel(target);
    	int result = sourceVertex.addEdgeToTarget(target, weight);
    	targetVertex.addEdgeFromSource(source, weight);
    	checkRep();
    	return result;
    }
    
    @Override public boolean remove(L vertex) {
//        throw new RuntimeException("not implemented");
    	Boolean b = false;
    	if(this.vertices().contains(vertex)) {
    		b = true;
    		Vertex<L> theVertexToRemove = this.filterByLabel(vertex);
    		Iterator<Map.Entry<L, Integer>> iterator = theVertexToRemove.getPredecessorMap().entrySet().iterator();
    		while (iterator.hasNext()){
    			// TODO
    			Map.Entry<L, Integer> next = iterator.next();
    			this.set(next.getKey(), vertex, 0);
    		}
    		Iterator<Map.Entry<L, Integer>> iterator2 = theVertexToRemove.getSuccessorMap().entrySet().iterator();
    		while (iterator2.hasNext()){
    			Map.Entry<L, Integer> next = iterator2.next();
    			this.set(vertex, next.getKey(), 0);
    		}
    		this.vertices.remove(theVertexToRemove);
    	}
    	checkRep();
    	return b;
    }
    
    @Override public Set<L> vertices() {
//        throw new RuntimeException("not implemented");
    	List<L> labelList = (List<L>) this.vertices.stream().map(Vertex<L>::getLabel).distinct().collect(Collectors.toList());
    	Set<L> verticesShallowCopy = new HashSet<>(labelList);
    	Set<L> unmodifiableVertices = Collections.unmodifiableSet(verticesShallowCopy);
    	return unmodifiableVertices;
    }
    
    @Override public Map<L, Integer> sources(L target) {
//        throw new RuntimeException("not implemented");
    	Map<L, Integer> newSourcesMap = new HashMap<>();
    	if(this.vertices().size() > 0) {
    		Vertex<L> newVertex = this.filterByLabel(target);
    		newSourcesMap.putAll(newVertex.getPredecessorMap());
    	}
    	Map<L, Integer> unmodifiableSourcesMap = Collections.unmodifiableMap(newSourcesMap);
    	return unmodifiableSourcesMap;
    }
    
    @Override public Map<L, Integer> targets(L source) {
//        throw new RuntimeException("not implemented");
    	Map<L, Integer> newTargetsMap = new HashMap<>();
    	if(this.vertices().size() > 0) {
    		Vertex<L> newVertex = this.filterByLabel(source);
    		newTargetsMap.putAll(newVertex.getSuccessorMap());
    	}
    	Map<L, Integer> unmodifiableTargetsMap = Collections.unmodifiableMap(newTargetsMap);
    	return unmodifiableTargetsMap;
    }
    
    // TODO toString()
	@Override
	public String toString() {
		String ConcreteVerticesGraphString = "ConcreteVerticesGraph{"
				+ "vertices=" + vertices + "}";
		return ConcreteVerticesGraphString;
	}
    
}

/**
 * a vertex named label and its predecessors are stored in predecessorMap, its successors are stored in successorMap
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 */
class Vertex<L> {
    
    // TODO fields
	private L label;
	private Map<L, Integer> predecessorMap = new HashMap<>();
	private Map<L, Integer> successorMap = new HashMap<>();
    
    // Abstraction function:
    //   AF(label, predecessorMap, successorMap) = 
	//     a vertex that 
	//       the key of the element in predecessorMap is the label of its predecessor,
	//       the key of the element in successorMap is the label of its successor and
	//       the value corresponding to the key is the weight of edge from predecessor
	//         to vertex or from vertex to successor
    //   Representation invariant:
    //     label is non-null
	//     the key of elements in predecessorMap or successorMap is non-null
	//     the value of elements in predecessorMap or successorMap is positive
    //   Safety from rep exposure:
    //     label is immutable
	//     two Maps are mutable so let the constructor return  defensive copies
    
    // TODO constructor
	public Vertex(L label, Map<L, Integer> predecessorMap, Map<L, Integer> successorMap) {
		this.label = label;
		// the method putAll() is a shallow copy
		this.predecessorMap.putAll(predecessorMap);
		this.successorMap.putAll(successorMap);
	}
    
    // TODO checkRep
	public void checkRep() {
		assertFalse("expected that label is non-null", label == null);
		Iterator<Map.Entry<L, Integer>> iterator = predecessorMap.entrySet().iterator();
		while (iterator.hasNext()){
			Map.Entry<L, Integer> next = iterator.next();
			assertFalse("expected that key is non-null", next.getKey() == null);
			assertTrue("expected that value is positive", next.getValue() > 0);
		}
		Iterator<Map.Entry<L, Integer>> iterator2 = successorMap.entrySet().iterator();
		while (iterator2.hasNext()){
			Map.Entry<L, Integer> next = iterator2.next();
			assertFalse("expected that key is non-null", next.getKey() == null);
			assertTrue("expected that value is positive", next.getValue() > 0);
		}
	}
    
    // TODO methods
	public L getLabel() {
		return label;
	}

	public void setLabel(L label) {
		this.label = label;
	}

	public Map<L, Integer> getPredecessorMap() {
		Map<L, Integer> map = new HashMap<>();
		map.putAll(predecessorMap);
		return map;
	}

	public void setPredecessorMap(Map<L, Integer> predecessorMap) {
		this.predecessorMap.putAll(predecessorMap);
	}

	public Map<L, Integer> getSuccessorMap() {
		Map<L, Integer> map = new HashMap<>();
		map.putAll(successorMap);
		return map;
	}

	public void setSuccessorMap(Map<L, Integer> successorMap) {
		this.successorMap.putAll(successorMap);
	}
	
	public int addEdgeToTarget(L label, Integer weight) {
		int previousWeight = 0;
		if(!this.successorMap.containsKey(label) && weight > 0) {
			// add
			previousWeight = 0;
			this.successorMap.put(label, weight);
		} else if(this.successorMap.containsKey(label) && weight > 0) {
			// change
			previousWeight = this.successorMap.get(label);
			this.successorMap.put(label, weight);
		} else if(this.successorMap.containsKey(label) && weight == 0) {
			// remove
			previousWeight = this.successorMap.get(label);
			this.successorMap.remove(label);
		}
		checkRep();
		return previousWeight;
	}
	
	public int addEdgeFromSource(L label, Integer weight) {
		int previousWeight = 0;
		if(!this.predecessorMap.containsKey(label) && weight > 0) {
			// add
			previousWeight = 0;
			this.predecessorMap.put(label, weight);
		} else if(this.predecessorMap.containsKey(label) && weight > 0) {
			// change
			previousWeight = this.predecessorMap.get(label);
			this.predecessorMap.put(label, weight);
		} else if(this.predecessorMap.containsKey(label) && weight == 0) {
			// remove
			previousWeight = this.predecessorMap.get(label);
			this.predecessorMap.remove(label);
		}
		checkRep();
		return previousWeight;
	}
    
    // TODO toString()
	public String toString() {
		String vertexString = 
				"Vertex{" + "label=" + label +
				",predecessorMap=" + predecessorMap.toString() +
				",successorMap=" + successorMap.toString() + "}";
		return vertexString;
	}
    
}
