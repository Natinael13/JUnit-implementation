public class AssertionString {

    private String o;

    public AssertionString(String o){
        this.o = o;
    }

    public AssertionString isNotNull(){
        if(o.equals(null)){
            throw new UnsupportedOperationException();
        }
        else{
            return this;
        }
    }

    public AssertionString isNull(){
        if(o != null){
            throw new UnsupportedOperationException();
        }
        else{
            return this;
        }
    }

    public AssertionString isEqualTo(Object o2){
        if(o.equals(o2) == false){
            throw new UnsupportedOperationException();
        }
        else{
            return this;
        }
    }

    public AssertionString isNotEqualTo(Object o2){
        if(o.equals(o2)){
            throw new UnsupportedOperationException();
        }
        else{
            return this;
        }
    }

    public AssertionString startsWith(String s2){
        if(o.startsWith(s2) == false){
            throw new UnsupportedOperationException();
        }
        else{
            return this;
        }
    }

    public AssertionString isEmpty(){
        if(o.isEmpty() == false){
            throw new UnsupportedOperationException();
        }
        else{
            return this;
        }
    }

    public AssertionString contains(String s2){
        if(o.contains(s2) == false){
            throw new UnsupportedOperationException();
        }
        else return this;
    }

}
