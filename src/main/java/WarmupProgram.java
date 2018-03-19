import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.io.*;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class WarmupProgram {

    private static DefaultDirectedGraph<String, DefaultEdge> readGraph(String inputFile) throws ImportException {
        //read in the graph from the .DOT file
        DOTImporter<String, DefaultEdge> dotImporter = new DOTImporter<>(
                new VertexProvider<>() {
                    public String buildVertex(String s, Map<String, Attribute> map) {
                        return s; //we don't need anything from the map for the sample problem
                    }
                },
                new EdgeProvider<>() {
                    public DefaultEdge buildEdge(String s, String v1, String s2, Map<String, Attribute> map) {
                        return new DefaultEdge();
                    }
                }
        );
        DefaultDirectedGraph<String, DefaultEdge> inputGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
        dotImporter.importGraph(inputGraph, new File(inputFile));
        return inputGraph;
    }

    //Implements a upwards breadt-first search from person 1 until it reaches an ancestor who is shared with person2.
    //this is checked using the isAncestor method below
    public static Set<String> findCommonAncestors(DefaultDirectedGraph<String, DefaultEdge> graph, String person1, String person2){
        HashSet<String> returnValues = new HashSet<>();
        if(isAncestor(graph, person1, person2)){
            returnValues.add(person1);
        } else if(!(graph.inDegreeOf(person1) == 0)){
            //we can still go up in the tree
            Set<DefaultEdge> upwardEdges = graph.incomingEdgesOf(person1);
            Set<String> vertices = upwardEdges.stream().map(graph::getEdgeSource).collect(Collectors.toSet());
            //filter out cycles of order 2
            vertices.removeIf(x -> graph.containsEdge(person1, x));
            for(String vertex : vertices){
                returnValues.addAll(findCommonAncestors(graph, vertex, person2));
            }
        }
        return returnValues;
    }

    //Implements a downwards breadth-first search from the suspected ancestor. returns true if the possibleChild, is
    //actually a descendant
    public static boolean isAncestor(DefaultDirectedGraph<String, DefaultEdge> graph, String possibleAncestor, String possibleChild){
        if(graph.outDegreeOf(possibleAncestor) == 0){
            return false;
        }
        if(possibleAncestor.equals(possibleChild)){
            return true;
        }
        Set<DefaultEdge> edges = graph.outgoingEdgesOf(possibleAncestor);
        Set<String> vertices = edges.stream().map(graph::getEdgeTarget).collect(Collectors.toSet());
        for(String vertex : vertices) {
            if (vertex.equals(possibleChild)) {
                return true;
            }
        }
        //filter out cycles of order 2
        vertices.removeIf(x -> graph.containsEdge(x, possibleAncestor));
        boolean[] anyMatches = new boolean[vertices.size()];
        int i = 0;
        for(String vertex : vertices){
            anyMatches[i] = isAncestor(graph, vertex, possibleChild);
            if(anyMatches[i]){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws ImportException {
        if(args.length != 3) throw new IllegalArgumentException("Please provide all 3 necessary inputs");

        DefaultDirectedGraph<String, DefaultEdge> inputGraph = readGraph(args[0]);
        System.out.println(findCommonAncestors(inputGraph, args[1], args[2]));
    }

}
