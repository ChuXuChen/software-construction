/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {
    
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();
    
    // Abstraction function:
    //   AF(vertices, edges) = graph G(vertices, edges)
    // Representation invariant:
    //   for all edge in edges, edge's source and target must be in vertices
    //   there are no repeating elements in edges
    // Safety from rep exposure:
    //   All fields are private and final
    //   vertices and edges are mutable
    //   so producer and observer should make defensive copy of them
    
    // TODO constructor
    public ConcreteEdgesGraph() {
    	
    }
    
    // TODO checkRep
    private void checkRep() {
    	if (this.vertices.isEmpty()) {
    		assert this.edges.isEmpty():"if G has not vertices, then it cannot have edges";
    	}
    	Set<Edge<L>> edgeSet = new HashSet<Edge<L>>(this.edges);
    	assert this.edges.size() == edgeSet.size():"edges has no repeating elements";
    	Set<L> verticesSetForEdges = new HashSet<>();
    	for(var edge:edges) {
    		verticesSetForEdges.add((L) edge.getSource());
    		verticesSetForEdges.add((L) edge.getTarget());
    	}
    	assert this.vertices.containsAll(verticesSetForEdges):"for all edge in edges, edge's source and target must be in vertices";
    }
    
    @Override public boolean add(L vertex) {
//        throw new RuntimeException("not implemented");
    	Boolean b = false;
    	if (this.vertices.contains(vertex)) {
    		b = false;
    	} else {
    		vertices.add(vertex);
    		b = true;
    	}
    	checkRep();
    	return b;
    }
    
    @Override public int set(L source, L target, int weight) {
//        throw new RuntimeException("not implemented");
    	if(!this.vertices.contains(source)) {
    		this.add(source);
    	}
    	if(!this.vertices.contains(target)) {
    		this.add(target);
    	}
    	Edge<L> newEdge = new Edge<L>(source, target, weight);
    	Integer previousWeight = 0;
    	Boolean edgeExistingFlag = false, changeFlag = false;
    	Iterator<Edge<L>> iterator = this.edges.iterator();
    	while(iterator.hasNext()) {
    		Edge<L> edge = (Edge<L>)iterator.next();
    		if(edge.getSource().equals(newEdge.getSource()) && edge.getTarget().equals(newEdge.getTarget())) {
    			edgeExistingFlag = true;
				iterator.remove();
				previousWeight = edge.getWeight();
				if(newEdge.getWeight() > 0) {
					changeFlag = true;
				}
    		}
    	}
    	if(changeFlag) {
    		edges.add(newEdge);
    	}
    	if(!edgeExistingFlag && newEdge.getWeight() > 0) {
    		previousWeight = 0;
    		edges.add(newEdge);
    	}
    	checkRep();
    	return previousWeight;
    }
    
    @Override public boolean remove(L vertex) {
//        throw new RuntimeException("not implemented");
    	Boolean b = false;
    	if(this.vertices.contains(vertex)) {
    		vertices.remove(vertex);
    		Iterator<Edge<L>> iterator = this.edges.iterator();
    		while(iterator.hasNext()) {
    			Edge<L> edge = (Edge<L>)iterator.next();
    			if(edge.getSource().equals(vertex) || edge.getTarget().equals(vertex)) {
    				iterator.remove();
    			}
    		}
    		b = true;
    	}
    	checkRep();
    	return b;
    }
    
    @Override public Set<L> vertices() {
//        throw new RuntimeException("not implemented");
    	Set<L> verticesShallowCopy = new HashSet<>(this.vertices);
    	Set<L> unmodifiableVertices = Collections.unmodifiableSet(verticesShallowCopy);
    	return unmodifiableVertices;
    }
    
    @Override public Map<L, Integer> sources(L target) {
//        throw new RuntimeException("not implemented");
    	Iterator<Edge<L>> iterator = this.edges.iterator();
    	Map<L, Integer> sourcesMap = new HashMap<>();
    	while(iterator.hasNext()) {
    		Edge<L> edge = (Edge<L>)iterator.next();
    		if(edge.getTarget().equals(target)) {
    			sourcesMap.put((L) edge.getSource(), edge.getWeight());
    		}
    	}
    	Map<L, Integer> unmodifiableSourcesMap = Collections.unmodifiableMap(sourcesMap);
    	return unmodifiableSourcesMap;
    }
    
    @Override public Map<L, Integer> targets(L source) {
//        throw new RuntimeException("not implemented");
    	Iterator<Edge<L>> iterator = this.edges.iterator();
    	Map<L, Integer> targetsMap = new HashMap<>();
    	while(iterator.hasNext()) {
    		Edge<L> edge = (Edge<L>)iterator.next();
    		if(edge.getSource().equals(source)) {
    			targetsMap.put((L) edge.getTarget(), edge.getWeight());
    		}
    	}
    	Map<L, Integer> unmodifiableTargetsMap = Collections.unmodifiableMap(targetsMap);
    	return unmodifiableTargetsMap;
    }
    
    // TODO toString()
	@Override
	public String toString() {
		String ConcreteEdgesGraphString = "ConcreteEdgesGraph{"
				+ "vertices=" + vertices + ""
				+ ",edges=" + edges + "}";
		return ConcreteEdgesGraphString;
	}
    
}

/**
 * an edge from source to target with weight weight.
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 */
final class Edge<L> {
    
    // TODO fields
	private final L source;
	private final L target;
	private Integer weight;
    
    // Abstraction function:
    //   AF(source, target, weight) = an edge with weight from source to target
    // Representation invariant:
    //   source and target are non-null
	//   weight is non-negative
	//   L must be immutable
    // Safety from rep exposure:
    //   all fields are private
	//   the class is final
    
    // TODO constructor
	/**
	 * constructor
	 * @param source vertex that the edge from
	 * @param target vertex that the edge to
	 * @param weight weight of the edge from source to target
	 */
	public Edge(L source, L target, Integer weight) {
		this.source = source;
		this.target = target;
		this.weight = weight;
		checkRep();
	}
    
    // TODO checkRep
    private void checkRep() {
    	assert this.source != null && this.target != null && this.weight >= 0:"source and target must be non-null string and weight should be non-negative";
    }
    
    // TODO methods
    public L getSource() {
    	return source;
    }
    
    public L getTarget() {
    	return target;
    }
    
    public int getWeight() {
    	return weight;
    }
    
    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge<?> other = (Edge<?>) obj;
		return Objects.equals(source, other.source) && Objects.equals(target, other.target) && weight == other.weight;
	}
    
    @Override
	public int hashCode() {
		return Objects.hash(source, target, weight);
	}
    
    // TODO toString()
	@Override
	public String toString() {
		String edgeString = 
				"Edge{" + "source=" + source  +
                ",target=" + target  + ",weight=" + weight + "}";
		return edgeString;
	}
    
}
