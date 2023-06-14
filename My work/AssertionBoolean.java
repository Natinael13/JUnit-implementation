public class AssertionBoolean {
    
    private Boolean o;

    public AssertionBoolean(Boolean o){
        this.o = o;
    }

    public AssertionBoolean isEqualTo(boolean b2){
        if(o.equals(b2) == false){
            throw new UnsupportedOperationException();
        }
        else{
            return this;
        }
    }

    public AssertionBoolean isTrue(){
        if(o == false){
            throw new UnsupportedOperationException();
        }
        else{
            return this;
        }
    }

    public AssertionBoolean isFalse(){
        if(o == true){
            throw new UnsupportedOperationException();
        }
        else{
            return this;
        }
    }

}
