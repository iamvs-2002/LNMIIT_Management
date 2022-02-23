package in.ac.lnmiit.management.Modules.Classes;

import android.util.Log;

import java.util.*;

public class Graph {
    public static boolean[] visited;
    static Map<Integer, Integer> V = new HashMap<>();
    // TreeSet is used to get clear understand of graph.
    static HashMap<Integer, TreeSet<Integer>> graph;

    // Graph Constructor
    public Graph(int v) {
        graph = new HashMap<>();
        for (int i = 1; i <= v; i++) {
            graph.put(i, new TreeSet<>());
        }
    }

    // Adds an edge to an undirected graph
    public void addEdge(int src, int dest) {
        // Add an edge from src to dest into the set
        graph.get(src).add(dest);
        // Since graph is undirected, add an edge
        // from dest to src into the set
        graph.get(dest).add(src);
    }

    public static int Findclique(int no_of_subject) {
        // Log.e("TAG","Inside find clique");
        int v = no_of_subject;
        visited = new boolean[v + 1];
        int subject = 1;
        int count = 0;
        for (int i = 1; i <= no_of_subject; i++) {
            if (graph.containsKey(i))
                V.put(i, graph.get(i).size());
        }
        while (subject <= no_of_subject) {
            if (visited[subject]) {
                subject++;
            } else {
                Stack<Integer> clique = new Stack<Integer>();
                makeclique(subject, clique);
                count++;
                subject++;
                Log.e("TAG", "clique " + count + ":");
                while (!clique.isEmpty()) {
                    Log.e("TAG", clique.pop() + " ");
                }

                Log.e("TAG", "\n");
                for (int i = 1; i <= no_of_subject; i++) {
                    if (graph.containsKey(i))
                        V.put(i, graph.get(i).size());
                }
            }
        }
        return count;
    }

    public static void makeclique(int subject, Stack<Integer> clique) {
        // Log.e("TAG","Inside make clique");
        clique.add(subject);
        visited[subject] = true;
        Map<Integer, Integer> adjacent = FindAdjacent(subject);

        Map<Integer, Integer> newV = new HashMap<>();

        for (Map.Entry<Integer, Integer> entry : adjacent.entrySet()) {
            int x = entry.getKey();
            if (V.containsKey(x)) {
                newV.put(x, entry.getValue());
            }
        }
        V = newV;
        if (!V.isEmpty()) {
            int max_degree_node = Integer.MIN_VALUE;
            for (Map.Entry<Integer, Integer> entry : V.entrySet()) {
                if (entry.getValue() > max_degree_node) {
                    max_degree_node = entry.getKey();
                }
            }
            makeclique(max_degree_node, clique);
        }

    }

    public static Map<Integer, Integer> FindAdjacent(int subject) {
        Map<Integer, Integer> adjacent = new HashMap<>();
        if (graph.containsKey(subject)) {
            Iterator<Integer> set = graph.get(subject).iterator();
            while (set.hasNext()) {
                int x = set.next();
                adjacent.put(x, graph.get(x).size());
            }
        }
        return adjacent;
    }
}