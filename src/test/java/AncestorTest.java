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


public class AncestorTest {

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
    public void testDirectAncestors(){
        Assert.assertTrue(WarmupProgram.isAncestor(graph, "Tywin", "Cersei"));
        Assert.assertTrue(WarmupProgram.isAncestor(graph, "Aegon_V", "Rhaelle"));
        Assert.assertTrue(WarmupProgram.isAncestor(graph, "Rickard", "Brandon"));
        Assert.assertTrue(WarmupProgram.isAncestor(graph, "Eddard", "Brandon"));
    }

    @Test
    public void testSecondLevelAncestors(){
        Assert.assertTrue(WarmupProgram.isAncestor(graph, "Tywin", "Tommen"));
        Assert.assertTrue(WarmupProgram.isAncestor(graph, "Ormund", "Stannis"));
        Assert.assertTrue(WarmupProgram.isAncestor(graph, "Rickard", "Sansa"));
        Assert.assertTrue(WarmupProgram.isAncestor(graph, "Joanna", "Joffrey"));
    }

    @Test
    public void testThirdLevelAncestors(){
        Assert.assertTrue(WarmupProgram.isAncestor(graph, "Aegon_V", "Viserys"));
        Assert.assertTrue(WarmupProgram.isAncestor(graph, "Rhaelle", "Shireen"));
    }

    @Test
    public void testNotAncestors(){
        Assert.assertFalse(WarmupProgram.isAncestor(graph, "Cersei", "Tywin"));
    }

}
