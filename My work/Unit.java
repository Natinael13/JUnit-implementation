import java.lang.annotation.*; 
import java.lang.reflect.*;
import java.util.*;

public class Unit {

    public static Map<String, Throwable> testClass(String name) {

    //create hashamp
    HashMap<String,Throwable> resultmap = new HashMap<>();


    //get class test complete
    Class<?> c;
    try {
        c = Class.forName(name);
    } catch (Exception e) {
        throw new UnsupportedOperationException("no class found");
    }
    
    //get constructor test complete 
    Constructor<?> cons = null;
    try {
        cons = c.getConstructor();
    } catch (Exception e) {
        throw new UnsupportedOperationException("no constructer found");
    }
   
    Object o = null;
    //create instance of class using constructor 
    try {
        o = cons.newInstance();
    } catch (Exception e) {
        throw new UnsupportedOperationException("problem with new instance");
    }


    //loop through and get all methods from the class test complete
    Method[] allmethods = c.getMethods();

    //check if any methods have more than 1 annotations
    multiAnnotationChecker(allmethods);

    //sort methods in alphabetical order
    Method[] allmethods2 = alphabeticalorder(allmethods);

    //Seperate annoations into seperate ArrayLists
    ArrayList<Method> BeforeClassmethods = annotationfinder(allmethods2, "BeforeClass");
    // for(int i = 0; i < BeforeClassmethods.size(); i++) {   
    //     System.out.println(BeforeClassmethods.get(i).toString());
    //     System.out.println("-----------");
    // }

    ArrayList<Method> beforemethods = annotationfinder(allmethods2, "Before");
    // for(int i = 0; i < beforemethods.size(); i++) {   
    //     System.out.println(beforemethods.get(i).toString());
    //     System.out.println("-----------");
    // }

    ArrayList<Method> testmethods = annotationfinder(allmethods2, "Test");
    // for(int i = 0; i < testmethods.size(); i++) {   
    //     System.out.println(testmethods.get(i).toString());
    //     System.out.println("-----------");
    // }

    ArrayList<Method> aftermethods = annotationfinder(allmethods2, "After");
    // for(int i = 0; i < aftermethods.size(); i++) {   
    //     System.out.println(aftermethods.get(i).toString());
    //     System.out.println("-----------");
    // }

    ArrayList<Method> AfterClassmethods = annotationfinder(allmethods2, "AfterClass");
    // for(int i = 0; i < AfterClassmethods.size(); i++) {   
    //     System.out.println(AfterClassmethods.get(i).toString());
    //     System.out.println("-----------");
    // }


    
    //invoke all BeforeClass Methods
    try{
    for(Method m: BeforeClassmethods){
        if(Modifier.isStatic(m.getModifiers()) == false){
            throw new UnsupportedOperationException("BeforeClass method not static");
        }
        try {
            m.invoke(o);
        } catch(InvocationTargetException e){ 
            throw e;
        }
        
    }
    } catch (Exception e) {
        throw new UnsupportedOperationException("problem with invoking BeforeClass Methods");
    }

    //invoke test methods
    for(Method m: testmethods){
        resultmap = testrunner(resultmap, beforemethods, aftermethods, m, o);
    }
    
    //invoke all AfterClass Methods
    try{
    for(Method m: AfterClassmethods){
        if(Modifier.isStatic(m.getModifiers()) == false){
            throw new UnsupportedOperationException("BeforeClass method not static");
        }
        try {
            m.invoke(o);
        } catch(InvocationTargetException e){ 
            throw e;
        }
    }
    } catch (Exception e) {
        throw new UnsupportedOperationException("problem with invoking AfterClass Methods");
    }
    

    //return hashmap
    return resultmap;
    }




    public static Map<String, Object[]> quickCheckClass(String name) {
	
    //create hashamp
    HashMap<String,Object[]> resultmap = new HashMap<>();


    //get class test complete
    Class<?> c;
    try {
        c = Class.forName(name);
    } catch (Exception e) {
        throw new UnsupportedOperationException("no class found");
    }
    
    //get constructor test complete 
    Constructor<?> cons = null;
    try {
        cons = c.getConstructor();
    } catch (Exception e) {
        throw new UnsupportedOperationException("no constructer found");
    }
   
    Object o = null;
    //create instance of class using constructor 
    try {
        o = cons.newInstance();
    } catch (Exception e) {
        throw new UnsupportedOperationException("problem with new instance");
    }


    //loop through and get all methods from the class test complete
    Method[] allmethods = c.getMethods();

    //check if any methods have more than 1 annotations
    multiAnnotationChecker(allmethods);

    //sort methods in alphabetical order
    Method[] allmethods2 = alphabeticalorder(allmethods);

    //Get all the property methods 
    ArrayList<Method> propertymethods = annotationfinder(allmethods2, "Property");
    // for(int i = 0; i < propertymethods.size(); i++) {   
    //     System.out.println(propertymethods.get(i).toString());
    //     System.out.println("-----------");
    // }

    
    //Loop through property methods and test each
    for(Method m: propertymethods){
        resultmap = propertyrunner(resultmap, m, o);
    }

    //return hashmap
    return resultmap;
    }




    //HelperFunctions

    public static void multiAnnotationChecker(Method[] methods){
        //counter to keep track of # of annotations in a method
        int counter = 0;

        //loop through Methods and check if each method has more than 1 annoation if so throw expetion
        for(Method m: methods){
            if(m.isAnnotationPresent(BeforeClass.class)){
                counter = counter + 1;
            }
            if(m.isAnnotationPresent(Before.class)){
                counter = counter + 1;
            }
            if(m.isAnnotationPresent(Test.class)){
                counter = counter + 1;
            }
            if(m.isAnnotationPresent(After.class)){
                counter = counter + 1;
            }
            if(m.isAnnotationPresent(AfterClass.class)){
                counter = counter + 1;
            }
            if(counter > 1){
                throw new UnsupportedOperationException("More than 1 annotation present on this method");
            }
            //reset counter after current method
            counter = 0;
        }
        return;
    }


    public static ArrayList<Method>annotationfinder(Method[] methods, String annotationToFind){
        //create ArrayList to store methods with said annoations
        ArrayList<Method> result = new ArrayList<>();

        //add approriate methods to ArrayList
        if(annotationToFind.equals("BeforeClass")){
        for(Method m: methods){
            if(m.isAnnotationPresent(BeforeClass.class)){
                result.add(m);
            }
        }
        }

        if(annotationToFind.equals("Before")){
            for(Method m: methods){
                if(m.isAnnotationPresent(Before.class)){
                    result.add(m);
                }
            }
            }

        if(annotationToFind.equals("Test")){
        for(Method m: methods){
            if(m.isAnnotationPresent(Test.class)){
                result.add(m);
            }
        }
        }

        if(annotationToFind.equals("After")){
            for(Method m: methods){
                if(m.isAnnotationPresent(After.class)){
                    result.add(m);
                }
            }
            }

        if(annotationToFind.equals("AfterClass")){
        for(Method m: methods){
            if(m.isAnnotationPresent(AfterClass.class)){
                result.add(m);
            }
        }
        }

        if(annotationToFind.equals("Property")){
            for(Method m: methods){
                if(m.isAnnotationPresent(Property.class)){
                    result.add(m);
                }
            }
            }

        //return methods with said annoations in form of Arraylist
        return result;
    }

    public static Method[] alphabeticalorder(Method[] methods){

        // for(int i = 0; i < methods.length; i++){
        //     System.out.println(methods[i].toString() + "---------");
        // }
        
        //loop through and sort in alphabeticalorder
        for(int i = 0; i < methods.length; i++){
            for(int j = i + 1; j < methods.length;j++){
                if((methods[i].toString().compareTo(methods[j].toString())) > 0){
                    Method temp = methods[i];
                    methods[i] = methods[j];
                    methods[j] = temp;
                }
            }
        }

        // System.out.println("aftersort");
        // for(int i = 0; i < methods.length; i++){
        //     System.out.println(methods[i].toString() + "------------");
        // }

        return methods;
    }

    public static HashMap<String,Throwable>testrunner(HashMap<String,Throwable> hashmap, ArrayList<Method> beforemethods, ArrayList<Method> aftermethods, Method test, Object o){
        //run before methods
        try{
            for(Method m: beforemethods){
                try {
                    m.invoke(o);
                } catch(InvocationTargetException e){ 
                    throw e;
                }
            }
            } catch (Exception e) {
                throw new UnsupportedOperationException("problem with invoking AfterClass Methods");
            }


        //run test methods
        try{
        try{
        test.invoke(o);
        hashmap.put(test.getName(), null);
        } catch(InvocationTargetException e) { 
            Throwable realexeption = e.getTargetException();
            hashmap.put(test.getName(), realexeption);
        }
        } catch (Exception e) {
        }

        //run after methods
        try{
            for(Method m: aftermethods){
                try {
                    m.invoke(o);
                } catch(InvocationTargetException e){ 
                    throw e;
                }
            }
            } catch (Exception e) {
                throw new UnsupportedOperationException("problem with invoking AfterClass Methods");
            }


        //return hashmap 
        return hashmap;
        }


    public static HashMap<String,Object[]>propertyrunner(HashMap<String,Object[]> hashmap, Method property, Object o){

        //get parameters 
        AnnotatedType[] annotatedparamtypes  = property.getAnnotatedParameterTypes();
        
        //Create Arraylist of Arraylists where each ArrayList is the possiblites of a single parameter 
        ArrayList<ArrayList<?>> parampossible = new ArrayList<>();

        //Check for single parameter object and complete it if there 
        Annotation forallcheck = annotatedparamtypes[0].getAnnotations()[0];
        if(forallcheck.annotationType().equals(ForAll.class)){
            hashmap = foralldoer(((ForAll)forallcheck).name(), ((ForAll)forallcheck).times(), o, hashmap, property);
            return hashmap;
        }


        //loop through Parameters 
        for(AnnotatedType i: annotatedparamtypes){
            //make holder for i that is type annotation
            Annotation holder = i.getAnnotations()[0];


            //if Annoation is a intrange plug into repective finder function and add result arraylist to parampossible 
            if(holder.annotationType().equals(IntRange.class)){
                //if Annoation is a intrange plug into repective finder function and add result arraylist to parampossible 
                if(((i.getAnnotations()[0]).annotationType()).equals(IntRange.class)){
                    parampossible.add(intrangeFinder( ((IntRange)holder).min(), ((IntRange)holder).max() ));
                    // System.out.println(parampossible);
                }
            }


            //if Annoation is a Stringset plug into repective finder function and add result arraylist to parampossible 
            if(((i.getAnnotations()[0]).annotationType()).equals(StringSet.class)){
                parampossible.add(stringsetFinder(((StringSet)holder).strings()));
                // System.out.println(parampossible);
            } 


            //if Annoation is a List plug into repective finder function and add result arraylist to parampossible 
            if(holder.annotationType().equals(ListLength.class)){
            
                //get type of list
                var type = ((AnnotatedParameterizedType)i).getAnnotatedActualTypeArguments()[0];
                //arraylist to hold possible values for list (either a list of strings or list of ints)
                ArrayList<Object> listTypePoss = new ArrayList<>();
                
                //get possible elements for list (either a list of strings or list of ints)
                if(type.getAnnotation(IntRange.class) != null){
                    Annotation inttype = type.getAnnotations()[0];
                    listTypePoss = intrangeFinder( ((IntRange)inttype).min(), ((IntRange)inttype).max() );
                    // System.out.println(listTypePoss);
                }
                else if(type.getAnnotation(StringSet.class) != null){
                    Annotation stringtype = type.getAnnotations()[0];
                    listTypePoss =stringsetFinder(((StringSet)stringtype).strings());
                    // System.out.println(listTypePoss);
                }
            
                //find all permuations and add it to parampossible
                parampossible.add(ListFinder(listTypePoss, ((ListLength)holder).min(),((ListLength)holder).max()));
                // System.out.println(parampossible);
            }


        }

        // System.out.println(parampossible);

        //lists for coming function
        ArrayList<Object> currlist = new ArrayList<>();
        ArrayList<ArrayList<Object>> allpossibleparameters = new ArrayList<>();
        
        //find all combinations of paramteres 
        allpossibleparameters = PermFinder(allpossibleparameters, parampossible, currlist, 0);

        // System.out.println(allpossibleparameters);

        //actually run all the different combinations of properties
        for(int i = 0; i < allpossibleparameters.size();i++){
            Object[] argholder = (allpossibleparameters.get(i)).toArray();
            hashmap.put(property.getName(), null);
            //run test methods
            try{
            try{
            //run with given parameter
            Object tf = property.invoke(o, argholder);
            //if we obtain false add args to hashmap and exit
            if((Boolean)tf == false){
                hashmap.remove(property.getName());
                hashmap.put(property.getName(), argholder);
                break;
            } 
            } catch(InvocationTargetException e) { 
                //if we have a throwable add args to hashmap and exit 
                hashmap.remove(property.getName());
                hashmap.put(property.getName(), argholder);
                break;
            }
            } catch (Exception e) {
            }

            //if we get to 100 tests we exit the loop
            if((i+1) == 100){
                break;
            }


        }


        //return hashmap
        return hashmap;
        }

    public static ArrayList<Object> intrangeFinder(int min, int max){
        //create Arraylist to store all possible int's
        ArrayList<Object> possibleint = new ArrayList<>();

        //loop from min to max adding values to Arraylist
        for(int i = min; i < (max + 1);i++){
            //create Arraylist to store a possible int
            possibleint.add(i);
        }

        //return Arraylist of possible ints's
        return  possibleint;
    }

    public static ArrayList<Object> stringsetFinder(String[] array){
        //create Arraylist to store possible strings
        ArrayList<Object> possiblestring = new ArrayList<>();

        //loop through array adding values to Arraylist
        for(int i = 0; i < array.length;i++){
            //create Arraylist to store a possible string
            possiblestring.add(array[i]);
        }

        //return Arraylist of possible strings
        return possiblestring;
    }

    public static ArrayList<ArrayList<Object>> ListFinder(ArrayList<Object> listTypePoss, int min, int max){
        //create Arraylist to store possible strings
        ArrayList<ArrayList<Object>> possiblelists = new ArrayList<>();

        //loop through possible sizes of array finding all permuations of each and adding it to possiblelists
        for(int i = min; i < (max + 1); i++){
            ArrayList<Object> currlist = new ArrayList<>();
            possiblelists = ListFinderhelper(possiblelists, listTypePoss, currlist, i);
        }

        //return Arraylist of possible lists
        return possiblelists; 
    }

    public static ArrayList<ArrayList<Object>> ListFinderhelper(ArrayList<ArrayList<Object>> possiblelists, ArrayList<Object> listTypePoss,  ArrayList<Object> currlist, int size){
        
        //base case~list is desired size
        if(currlist.size() == size){
            //add permuation and return
            possiblelists.add(currlist);
            return possiblelists;
        }


        //loop through all the possible elements recursively calling ListFinderhelper with element i added to currlist
        for(int i = 0; i < listTypePoss.size(); i++){
            //get current element
            Object holder = listTypePoss.get(i);

            //fix issue of same currlist being added to next recursion 
            ArrayList<Object> currlist2 = new ArrayList(currlist);
            //add current element to currlist
            currlist2.add(holder);

            //recurse with added element
            possiblelists = ListFinderhelper(possiblelists, listTypePoss, currlist2, size);
        }

        //return all perms of this size
        return possiblelists;
    }


    public static ArrayList<ArrayList<Object>> PermFinder(ArrayList<ArrayList<Object>> AllPerms, ArrayList<ArrayList<?>> parampossible, ArrayList<Object> currlist, int index){

        //Base case~if we have size equal to number of paramters we add the list to Allperms
        if((currlist.size()) == (parampossible.size())){
            //add permuation and return
            AllPerms.add(currlist);
            return AllPerms;
        }


        //loop through elements of a parameter and add current element
        for(int i = 0; i < parampossible.get(index).size(); i++){
            //get element
            Object holder = parampossible.get(index).get(i);

            //fix issue of same currlist being added to next recursion
            ArrayList<Object> currlist2 = new ArrayList(currlist);
            //add current element to currlist
            currlist2.add(holder);

            //recurse with currentlist with new element
            PermFinder(AllPerms, parampossible, currlist2, (index+1));

        }

        //return all permuatations
        return AllPerms;
    }


    public static HashMap<String,Object[]> foralldoer(String name, int times, Object o, HashMap<String,Object[]> hashmap, Method property){
        //create Arraylist to store the possible i's 
        ArrayList<Object> alli= new ArrayList<>();

        //get the method
        try {
            Method m = o.getClass().getMethod(name);
            //call method appropriate number of times
            for(int i = 0; i < times;i++){
                alli.add(m.invoke(o));
            }
        } catch (Exception e) {
        }
        Object[] Objecthold = alli.toArray();
        //actually run all the different combinations of properties
        for(int i = 0; i < alli.size();i++){
            hashmap.put(property.getName(), null);
            Object[] holdit = new Object[1];
            holdit[0] = (Object)alli.get(i);
            //run test methods
            try{
            try{
            //run with given parameter
            Object tf = property.invoke(o, alli.get(i));
            //if we obtain false add args to hashmap and exit
            if((Boolean)tf == false){
                hashmap.remove(property.getName());
                hashmap.put(property.getName(), holdit);
                break;
            } 
            } catch(InvocationTargetException e) { 
                //if we have a throwable add args to hashmap and exit 
                hashmap.remove(property.getName());
                hashmap.put(property.getName(), holdit);
                break;
            }
            } catch (Exception e) {
            }

            //if we get to 100 tests we exit the loop
            if((i+1) == 100){
                break;
            }

        }
        return hashmap;
    }


}



