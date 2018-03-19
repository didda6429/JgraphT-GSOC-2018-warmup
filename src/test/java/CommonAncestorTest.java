import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.io.Attribute;
import org.jgrapht.io.DOTImporter;
import org.jgrapht.io.EdgeProvider;
import org.jgrapht.io.VertexProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import junit.framework.TestCase;

import java.io.File;
import java.util.Map;
import java.util.Set;

public class CommonAncestorTest {

    private DefaultDirectedGraph<String, DefaultEdge> graph;

    //Read in the graph
    @Before
    public void setUp() throws Exception{
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
        graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        dotImporter.importGraph(graph, new File("sampleGraph.DOT"));
    }

    @Test
    public void testShireenAndJoffrey(){
        Set<String> ancestors = WarmupProgram.findCommonAncestors(graph, "Shireen", "Joffrey");
        Assert.assertTrue(ancestors.size() == 1);
        Assert.assertTrue(ancestors.iterator().next().equals("Steffon"));
    }

    @Test
    public void testJaimeAndTyrion(){
        Set<String> ancestors = WarmupProgram.findCommonAncestors(graph, "Jaime", "Tyrion");
        Assert.assertTrue(ancestors.size() == 2);
        Assert.assertTrue(ancestors.contains("Tywin"));
        Assert.assertTrue(ancestors.contains("Joanna"));
    }

    @Test
    public void testJonAndAegon(){
        Set<String> ancestors = WarmupProgram.findCommonAncestors(graph, "Jon", "Aegon");
        Assert.assertTrue(ancestors.size() == 1);
        Assert.assertTrue(ancestors.contains("Rhaegar"));
    }

}
