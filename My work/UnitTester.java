import java.util.List;

public class UnitTester {

    //constructer
    public UnitTester() { }


    //BeforeClass testers
    @BeforeClass public static void bBeforeclass(){
        boolean test = false; 
        if(test == true){
            throw new RuntimeException("here");
        }
        System.out.println("----bBeforeClass done------");
        return;
    }

    @BeforeClass public static void aBeforeclass(){
        boolean test = false; 
        if(test == true){
            throw new UnsupportedOperationException();
        }
        System.out.println("----aBeforeClass done------");
        return;
    }
    

    //Before testers 
    @Before public void bBefore(){
        boolean test = false; 
        if(test == true){
            throw new UnsupportedOperationException();
        }
        System.out.println("----bBefore done------");
        return;
    }

    @Before public void aBefore(){
        boolean test = false; 
        if(test == true){
            throw new UnsupportedOperationException();
        }
        System.out.println("----aBefore done------");
        return;
    }


    //Test testers
    @Test public void aTest(){
        boolean test = false; 
        if(test == false){
            throw new UnsupportedOperationException();
        }
        System.out.println("----aTest done------");
        return;
    }

    @Test public void bTest(){
        boolean test = false; 
        if(test == true){
            throw new UnsupportedOperationException();
        }
        System.out.println("----bTest done------");
        return;
    }


    //After testers 
    @After public void aAfter(){
        boolean test = false; 
        if(test == true){
            throw new UnsupportedOperationException();
        }
        System.out.println("----aAfter done------");
        return;
    }

    @After public void bAfter(){
        boolean test = false; 
        if(test == true){
            throw new UnsupportedOperationException();
        }
        System.out.println("----bAfter done------");
        return;
    }


    //Afterclass testers
    @AfterClass public static void aAfterclass(){
        boolean test = false; 
        if(test == true){
            throw new UnsupportedOperationException();
        }
        System.out.println("----aAfterclass done------");
        return;
    }

    @AfterClass public static void bAfterclass(){
        boolean test = false; 
        if(test == true){
            throw new UnsupportedOperationException();
        }
        System.out.println("----bAfterclass done------");
        return;
    }

    public static void bproperty(@StringSet(strings={"s1", "s2"}) String s, @IntRange(min=-10, max=10) Integer i){
        boolean test = false; 
        if(test == true){
            throw new UnsupportedOperationException();
        }
        System.out.println("----bproperty done------");
        return;
    }

    public static Boolean aproperty(@IntRange(min=-10, max=10) Integer i, @IntRange(min=-1, max=2) Integer j){
        boolean test = false; 
        if(test == true){
            throw new UnsupportedOperationException();
        }
        System.out.println("----aproperty done------" + i + "space" + j);
        return false;
    }

    public static Boolean dproperty(@ListLength(min=0, max=2) List<@IntRange(min=5, max=7) Integer> j){
        boolean test = false; 
        if(test == true){
            throw new UnsupportedOperationException();
        }
        System.out.println("----dproperty done------");
        return true;
    }

    public static Boolean cproperty(@ListLength(min=0, max=2) List<@StringSet(strings={"s1", "s2"})  String> j){
        boolean test = false; 
        if(test == true){
            throw new UnsupportedOperationException();
        }
        System.out.println("----cproperty done------");
        return true;
    }

    @Property public static Boolean fproperty(@ForAll(name="testforall", times=3) Object o){
        boolean test = false; 
        if(test == true){
            throw new UnsupportedOperationException();
        }
        System.out.println("----fproperty done------");
        return false;
    }

    public static Object testforall(){
        Object result = 5;
        return result;
    }


}
