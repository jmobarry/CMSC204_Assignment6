import javafx.util.Pair;
import java.util.*;


/**
 *This method creates the skeleton of the graph of Roads and Towns that will be utilized to find shortest paths
 * @author John Mobarry
 */
public class Graph implements GraphInterface<Town,Road>{

     Set<Town> townSet = new HashSet<>();
     Set<Road> roadSet = new HashSet<>();

    LinkedList<String> nextTowns = new LinkedList<>();
    Map<String, List<String>> adjacentList1;
    Map<String,Set<String>> adjacentList;
    Map<String,Integer> bestDistance = new HashMap<>();
    Map<String,String> bestFromTown = new HashMap<>();
    Map<Pair<String, String>,Road> edges = new HashMap<>();


    /**
     * this method searches the roadset to find a road that contains the entered sourcevertex and destination vertex
     * @param sourceVertex source vertex of the edge.
     * @param destinationVertex target vertex of the edge.
     * @return correlating town or null
     */
    @Override
    public Road getEdge(Town sourceVertex, Town destinationVertex) {

        Road getEdge = null;

        for(Road roadIterator: roadSet){
            if((roadIterator.contains(sourceVertex) && roadIterator.contains(destinationVertex))){
                getEdge = roadIterator;
                break;
            }
            else {
                continue;
            }
        }
        return getEdge;
    }

    /**
     * method I created during the debugging processs to confirm I could find a vertex based off of name
     * @param townname
     * @return Town
     */
    public Town getVertex(String townname){

        Town vertex = null;

        for(Town t: townSet){
            if(t.name.equals(townname)){
                vertex = t;
            }
        }
        return vertex;
    }

    /**
     * This method will add an edge between two roads assuming a road does not already exist
     * @param sourceVertex source vertex of the edge.
     * @param destinationVertex target vertex of the edge.
     * @param weight weight of the edge
     * @param description description for edge
     * @return created road
     */
    @Override
    public Road addEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {
        if (sourceVertex == null||destinationVertex == null ||this.containsVertex(sourceVertex) == false ||
                this.containsVertex(destinationVertex)== false){
            throw new NullPointerException("Error. Debug your code.");
        }
        Road newRoad = new Road(sourceVertex,destinationVertex,weight,description);
        if(!townSet.contains(destinationVertex)) {
            townSet.add(destinationVertex);
        }
        if(!townSet.contains(sourceVertex)) {
            townSet.add(sourceVertex);
        }
        if(!roadSet.contains(newRoad)) {
            roadSet.add(newRoad);
        }
        return newRoad;
    }

    /**
     * Method that checks to confirm if the town exists, if it does not adds the town to townset and returns true
     * @param town
     * @return boolean
     */
    @Override
    public boolean addVertex(Town town) {
        if(this.townSet.contains(town)){
            return false;
        }
        else{
            this.townSet.add(town);
        }
        return true;
    }

    /**
     * this method checks if getEdge of two vertexes exists, if so return true, else return null
     * @param sourceVertex source vertex of the edge.
     * @param destinationVertex target vertex of the edge.
     * @return boolean
     */
    @Override
    public boolean containsEdge(Town sourceVertex, Town destinationVertex) {
        if (this.getEdge(sourceVertex,destinationVertex)!= null){
            return true;
        }
        return false;
    }

    /**
     * This method iterates through the town vertex skipping over towns that do not match until it potentially
     * finds one if no match return false
     * @param town
     * @return boolean
     */
    @Override
    public boolean containsVertex(Town town) {
        boolean containsVertex = false;

        for(Town townIterator: townSet){
            if(!town.equals(townIterator)){
                continue;
            }
            else{
                containsVertex = true;
                break;
            }
        }
        return containsVertex;
    }

    /**
     * returns the roadSet of the Graph
     * @return roadSet
     */
    @Override
    public Set<Road> edgeSet() {
        return this.roadSet;
    }

    /**
     * Creates a new hasSet and adds roads that connect to the vertex to the new set
     * @param vertex the vertex for which a set of touching edges is to be
     * returned.
     * @return created Set
     */
    @Override
    public Set<Road> edgesOf(Town vertex) {
        Set<Road> s = new HashSet<>();

        for(Road roadIterator: this.roadSet){
           if (roadIterator.source.equals(vertex) || roadIterator.destination.equals(vertex)){
               s.add(roadIterator);

           }
           else {
               continue;
           }
        }
        return s;
    }

    /**
     *This method searches the RoadSet for a road connecting the two Vertexes and removes them
     * @param sourceVertex source vertex of the edge.
     * @param destinationVertex target vertex of the edge.
     * @param weight weight of the edge
     * @param description description of the edge
     * @return removed Road
     */
    @Override
    public Road removeEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {

        Road edgeToRemove = null;
        for (Road roadIterator: this.roadSet){
            if(roadIterator.contains(sourceVertex)&&roadIterator.contains(destinationVertex)){
                townSet.remove(sourceVertex);
                townSet.remove(destinationVertex);
                edgeToRemove =roadIterator;
                this.roadSet.remove(roadIterator);
                break;
            }
            if(description!= null && !roadIterator.name.equalsIgnoreCase(description)){
                continue;
            }
            if(roadIterator.weight!= weight && weight>-1){
                continue;
            }
            else{
                continue;
            }
        }
        return edgeToRemove;
    }

    /**
     * This method removes a specified vertex by calling the remove edge method
     * @param town
     * @return
     */
    @Override
    public boolean removeVertex(Town town) {
        if(town==null ){
            return false;
        }
        else if(!this.townSet.contains(town)){
            return false;
        }
        else{
            for(Road roadIterator: this.edgesOf(town)){
                removeEdge(roadIterator.source,roadIterator.destination,
                        roadIterator.weight,roadIterator.name);
            }
           townSet.remove(town);
        }
        return this.townSet.contains(town);
    }

    /**
     * This method returns the setofTowns
     * @return townSet
     */
    @Override
    public Set<Town> vertexSet() {
        return this.townSet;
    }


    /**
     * This method calls dijkstras shortest path and then generates an ArrayList of Strings to represent the
     * shortest path visually
     * @param sourceVertex starting vertex
     * @param destinationVertex ending vertex
     * @return bPath
     */
    @Override
    public ArrayList<String> shortestPath(Town sourceVertex, Town destinationVertex) {

        dijkstraShortestPath(destinationVertex);
        ArrayList<String> bPath = new ArrayList<String>();
        String currentTown = sourceVertex.name;
        String nextTown;
        do{
           nextTown = bestFromTown.get(currentTown);
           assert(nextTown!= null);
           var edge = edges.get(new Pair<>(currentTown,nextTown));
           if(edge == null){
               break;
           }
           String toAdd = currentTown + " via " + edge.name + " to " + nextTown + " " +edge.weight + " mi";
           bPath.add(toAdd);
           System.out.println(toAdd);
           currentTown = nextTown;
        }while(!nextTown.equals(destinationVertex.name));
        return bPath;
    }

    /**
     * This method finds the shortest path for an entered node
     * To avoid flipping the collection and downstream issues you can also enter the destination node
     * @param sourceVertex the vertex to find shortest path from
     */
    @Override
    public void dijkstraShortestPath(Town sourceVertex) {
        Set<String> visited = new HashSet<>();
        adjacentList1 = new HashMap<>();
        adjacentList = new HashMap<>();
        initializeAdjacencyList();
        bestDistance.put(sourceVertex.name, 0);
        bestFromTown.put(sourceVertex.name, sourceVertex.name);
        nextTowns.add(sourceVertex.name);

        do {
            String currentName = nextTowns.removeLast();
            if(visited.contains(currentName)){
               continue;
            }

            Set<String> neighbors = adjacentList.get(currentName);
            if (neighbors == null){
                break;
            }
            for(String neighborName: neighbors){
                var key = new Pair<>(currentName, neighborName);
                Integer theWeight = edges.get(key).weight;
                if(bestDistance.get(currentName) + theWeight < bestDistance.get(neighborName)){
                    bestDistance.put(neighborName, bestDistance.get(currentName) + theWeight);
                    bestFromTown.put(neighborName, currentName);
                }
                if(!visited.contains(neighborName)){ nextTowns.push(neighborName);}
            }
            visited.add(currentName);
        } while(!nextTowns.isEmpty());

    }

    /**
     * this helper method creates an ajacencyList when dijkstraShortestPath method is called in order to get the
     * bestDistance, bestFromTown, edges, and adjacentList Maps Generated
     * @return void
     */
    public void initializeAdjacencyList(){

        for(Town town: this.townSet){
            bestDistance.put(town.name, Integer.MAX_VALUE);
            bestFromTown.put(town.name, "");
        }
        for(Road road: roadSet){

            Town town1 = road.source;
            Town town2 = road.destination;
            edges.put(new Pair<>(town1.name,town2.name),road);
            edges.put(new Pair<>(town2.name,town1.name),road);


            Set<String> town1Neighbors = adjacentList.get(town1.name);
            if(town1Neighbors == null){
                town1Neighbors = new HashSet<>();
            }
            town1Neighbors.add(town2.name);
            Set<String> town2Neighbors = adjacentList.get(town2.name);
            if(town2Neighbors == null){
                town2Neighbors = new HashSet<>();
            }
            town2Neighbors.add(town1.name);
            adjacentList.put(town1.name, town1Neighbors);
            adjacentList.put(town2.name, town2Neighbors);
        }
    }

}
