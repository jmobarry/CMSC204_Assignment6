import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Town_STUDENT_Test {
    private Town town1;
    private Town town2;
    private Town town3;
    private Town town4;
    private Town town5;


    @Before
    public void setUp() throws Exception{
        town1 = new Town("Gaithersburg");
        town2 = new Town("Germantown");
        town3 = new Town("Poolesville");
        town4 = new Town (town1);
        town5 = new Town(town3);

    }

    @After
    public void tearDown() throws Exception{
        town1 = null;
        town2 = null;
    }

    @Test
    public void testRoad(){

    assertEquals("Gaithersburg",town1.getName());
    assertEquals("Gaithersburg",town4.getName());
    assertEquals(true,town1.equals(town4));
    assertEquals(true,town1.hashCode()==town4.hashCode());
    assertEquals(0,town1.compareTo(town4));
    assertEquals(-9,town2.compareTo(town3));
    assertEquals("Germantown",town2.toString());



    }
}
