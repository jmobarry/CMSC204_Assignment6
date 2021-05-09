/**
 * @author John Mobarry
 * This class represents an town as a node of a graph. The Town class holds the name of the town and a list of adjacent towns,
 * and other fields as desired, and the traditional methods (constructors, getters/setters, toString, etc.).
 */

public class Town implements Comparable<Town>{

    String name = "";


    /**
     * Constructor
     * @param n
     */
    public Town(String n){

        this.name = n;
    }

    /**
     * Copy constructor
     * @param templateTown
     */
    public Town(Town templateTown){
        this.name = templateTown.name;

    }

    /**
     *
     * @param o
     * @return integer representing the comparison between paramater and implicit name
     */
    @Override
    public int compareTo(Town o) {

       return this.name.compareTo(o.name);
    }

    /**
     * equals method
     * @param o
     * @return confirms if the name of the object matches the implicit (ignoring case)
     */
    public boolean equals(Object o) {
      return (this.name.equalsIgnoreCase(((Town) o).name));
    }

    /**
     * method to return this name
     * @return name
     */
    public String getName(){
        return this.name;
    }


    /**
     * overridden hashcode method for this class
     * @return integer
     */
    public int hashCode(){
        return this.name.hashCode();
    }

    /**
     * method representing toString to print out name instead of address
     * @return
     */
    @Override
    public String toString() {
        return this.name;
    }

}