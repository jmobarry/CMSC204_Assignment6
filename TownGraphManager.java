import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

/**
 * This manager class creates an instance of the Graph class and uses methods to manipulate the data and visualize
 * @author John Mobarry
 */
public class TownGraphManager implements TownGraphManagerInterface{

    Graph graphUtility = new Graph();


    /**
     * This method searches for the town1 and town2 via string, checks to confirm a road does not already exist, and
     * then creates a new road and adds it to the road set
     * @param town1 name of town 1 (lastname, firstname)
     * @param town2 name of town 2 (lastname, firstname)
     * @param weight
     * @param roadName name of road
     * @return boolean
     */
    @Override
    public boolean addRoad(String town1, String town2, int weight, String roadName) {
        Town source = getTown(town1);
        Town destination = getTown(town2);
        if ((source != null||destination != null)&&!(graphUtility.containsEdge(source,destination))){
            this.graphUtility.addEdge(source,destination,weight,roadName);
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * gets a road based on two strings entered
     * if get edge is null then output default string "Not a road"
     * @param town1 name of town 1 (lastname, firstname)
     * @param town2 name of town 2 (lastname, firstname)
     * @return
     */
    @Override
    public String getRoad(String town1, String town2) {
       if(this.graphUtility.getEdge(this.getTown(town1),this.getTown(town2))==null){
           return "Not a road";
       }
       else{
           return this.graphUtility.getEdge(this.getTown(town1),this.getTown(town2)).getName();
       }
    }

    /**
     * this method checks if the string correlates to an existing town
     * if not it adds the defautlt town based on string to the townSet
     * @param v the town's name  (lastname, firstname)
     * @return
     */
    @Override
    public boolean addTown(String v) {

//      if(this.graphUtility.townSet.contains(this.getTown(v))){
//          return false;
//      }
//     Town newtown = new Town(v);
     return this.graphUtility.addVertex(new Town(v));


    }

    /**
     *This method iterates through the graph's townset to check if there is a town with an equal name
     * if there is return the town the iterator represents
     * @param name the town's name
     * @return
     */
    @Override
    public Town getTown(String name) {

        Town townResult = null;
        for(Town townIterator: this.graphUtility.townSet){

            if(townIterator.getName().equals(name)){
                townResult = townIterator;
                break;
            }
            else{
                continue;
            }
        }
        return townResult;
    }

    /**
     * Creates a town based on the string using the getTown method
     * searches the townSet for the town
     * @param v the town's name
     * @return
     */
    @Override
    public boolean containsTown(String v) {
      Town search = this.getTown(v);
      return this.graphUtility.townSet.contains(search);
    }

    /**
     * checks to confirm if there is a road between the two towns
     * @param town1 name of town 1 (lastname, firstname)
     * @param town2 name of town 2 (lastname, firstname)
     * @return
     */
    @Override
    public boolean containsRoadConnection(String town1, String town2) {
        return ! this.getRoad(town1,town2).equals("Not a road");
    }

    /**
     * This method creates an ArrayList of Strings representing all of the roads in the edge set by name
     * @return ArrayList</String>
     */
    @Override
    public ArrayList<String> allRoads() {
        ArrayList<String> allroads = new ArrayList<>();
        for(Road edges: this.graphUtility.edgeSet()){
            allroads.add(edges.getName());
        }
        Collections.sort(allroads);
        return allroads;
    }

    /**
     * This method searches for a road based off of three string parameters
     * if it does not exist then return false
     * if it does then return true
     * @param town1 name of town 1 (lastname, firstname)
     * @param town2 name of town 2 (lastname, firstname)
     * @param road the road name
     * @return boolean
     */
    @Override
    public boolean deleteRoadConnection(String town1, String town2, String road) {
        Town search1 = this.getTown(town1);
        Town search2 = this.getTown(town2);

        for (Road roadIterator: this.graphUtility.roadSet){
            if(road!= null && !roadIterator.name.equalsIgnoreCase(road)){
                continue;
            }
            if(roadIterator.contains(search1) && roadIterator.contains(search2)){
                this.graphUtility.roadSet.remove(roadIterator);
                return true;
            }
            else{
                continue;
            }
        }
        return false;
    }

    /**
     * creates a town via string
     * removes vertex via created town
     * @param v name of town (lastname, firstname)
     * @return boolean to confirm deletion
     */
    @Override
    public boolean deleteTown(String v) {
        Town toDelete = this.getTown(v);
        return this.graphUtility.removeVertex(toDelete);
    }

    /**
     * This method creates an ArrayList</String> representing all of the towns in the Graph
     * @return ArrayList</String>
     */
    @Override
    public ArrayList<String> allTowns() {
        ArrayList<String> alltowns = new ArrayList<>();
        for(Town vertex: this.graphUtility.vertexSet()){
            alltowns.add(vertex.getName());
        }
        Collections.sort(alltowns);
        return alltowns;
    }

    /**
     *
     * @param town1 name of town 1 (lastname, firstname)
     * @param town2 name of town 2 (lastname, firstname)
     * @return
     */
    @Override
    public ArrayList<String> getPath(String town1, String town2) {
        Town townA = getTown(town1);
        Town townB = getTown(town2);
        return graphUtility.shortestPath(townA,townB);
    }


    /**
     * This method takes a file name and reads its contents in order to populate a graph
     * @param file
     * @return void
     */
    public void populateTownGraph(File file) throws FileNotFoundException {

        java.util.Scanner keyboard = null;

            keyboard = new java.util.Scanner(file);



        ArrayList<String> fileReader = new ArrayList<>();

        while (keyboard.hasNextLine()) {
            fileReader.add(keyboard.nextLine());
        }
        keyboard.close();

        for(String fileLine: fileReader){

            String[] fullRoads = fileLine.split(";");
            String[] roadInfo = fullRoads[0].split(",");

            String roadName = roadInfo[0].trim();
            int roadWeight = Integer.parseInt(roadInfo[1]);
            String source = fullRoads[1].trim();
            String destination = fullRoads[2].trim();

            if(source!= null && destination!=null && roadName!= null && roadWeight>0) {
                this.addRoad(source, destination, roadWeight, roadName);
                this.addTown(source);
                this.addTown(destination);
            }
        }
    }



}

