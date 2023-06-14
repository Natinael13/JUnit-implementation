public class AssertionObject {
    
    private Object o;

    public AssertionObject(Object o){
        this.o = o;
    }

    public AssertionObject isNotNull(){
        if(o.equals(null)){
            throw new UnsupportedOperationException();
        }
        else{
            return this;
        }
    }

    public AssertionObject isNull(){
        if(o != null){
            throw new UnsupportedOperationException();
        }
        else{
            return this;
        }
    }

    public AssertionObject isEqualTo(Object o2){
        if(o.equals(o2) == false){
            throw new UnsupportedOperationException();
        }
        else{
            return this;
        }
    }

    public AssertionObject isNotEqualTo(Object o2){
        if(o.equals(o2)){
            throw new UnsupportedOperationException();
        }
        else{
            return this;
        }
    }

    public AssertionObject isInstanceOf(Class c){
        if(c.isInstance(o) == false){
            throw new UnsupportedOperationException();
        }
        else{
            return this;
        }
    }




}
