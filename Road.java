/**
 * The class Road that can represent the edges of a Graph of Towns. The class must implement Comparable.
 * The class stores references to the two vertices(Town endpoints), the distance between vertices, and a name,
 * and the traditional methods (constructors, getters/setters, toString, etc.), and a compareTo, which compares two
 * Road objects. Since this is a undirected graph, an edge from A to B is equal to an edge from B to A.
 * @author John Mobarry
 */
public class Road implements Comparable<Road>{

    Town source;
    Town destination;
    int weight;
    String name;

    /**
     * Paramaterized constructor to create road
     * @param source
     * @param destination
     * @param degrees
     * @param name
     */
    public Road(Town source, Town destination, int degrees, String name) {
        this.source = source;
        this.destination = destination;
        this.weight = degrees;
        this.name = name;
    }

    /**
     * Partially paramaterized constructor with default weight value
     * @param source
     * @param destination
     * @param name
     */
    public Road(Town source, Town destination, String name){
        this(source, destination, 1, name);
    }

    /**
     * compareto method in terms of road weight
     * @param o
     * @return int
     */
    @Override
    public int compareTo(Road o) {
        return this.weight - o.weight;
    }

    /**
     * contains method to check if either the source or the destination of the road is equal to the town
     * @param t
     * @return boolean
     */
    public boolean contains(Town t) {
        return this.source.equals(t) || this.destination.equals(t);
    }

    /**
     * Equals that checks if the roads have the same source and destination (regardless of orientation)
     * @param r
     * @return boolean
     */
    public boolean equals(Object r) {
        return (this.source.equals(((Road) r).source) && this.destination.equals(((Road) r).destination) ||
                this.source.equals(((Road) r).destination) && this.destination.equals(((Road) r).source));
    }

    /**
     * getter method for the roads source
     * @return source
     */
    public Town getSource() {
        return this.source;
    }

    /**
     * getter method for the roads destination
     * @return destination
     */
    public Town getDestination() {
        return this.destination;
    }

    /**
     * getter method for the roads weight
     * @return weight
     */
    public int getWeight() {
        return this.weight;
    }

    /**
     * toString method for the road that meets the paramaters of the JUnit tests
     * @return created representational string
     */
    public String toString(){
        return this.name+ " ["+source + "," + destination+"] " +weight;
    }

    /**
     * method to return the roads name
     * @return
     */
    public String getName() {
        return this.name ;
    }

    /**
     * hashcode method for the graph class based off of the roads name
     * @return name.hashCode()
     */
    public int hashCode(){
        return this.name.hashCode();
    }

}
