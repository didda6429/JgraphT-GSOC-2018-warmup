This is my solution for the JgraphT GSOC 2018 warmup.

## Usage
To compile the program, call mvn package in the command line.
The compiled .jar require 3 inputs on the command line:
1) the location of the .DOT file containing the graph to work with
2) the name of the first person to search with
3) the name of the second person to search with

This program was developed using the sampleGraph.DOT file as an example. It contains
a simple family tree from Game of Thrones and is used for the tests.

## ALGORITHM
The algorithm I've implemented works using a double depth-first search in the tree.
Essentially, given 2 nodes, it works its way upwards from the first node, and at each
ancestor, searches downwards again to see if the second node is a descendant.

All the code is in WarmupProgram.java, and there are tests for the two methods in AncestorTests.java
and CommonAncestorTest.java.