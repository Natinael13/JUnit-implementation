import java.util.Map;
public class TesterMain {
   
   
public static void main(String[] args) {

    
    Map<String,Object[]> resultmap = Unit.quickCheckClass("UnitTester");
    System.out.println(resultmap);

    

    // Assertion.assertThat(true).isFalse();


    
}
}
