public class AssertionInt {
    
    private Integer o;

    public AssertionInt(Integer o){
        this.o = o;
    }

    public AssertionInt isEqualTo(int i2){
        if(o.equals(i2) == false){
            throw new UnsupportedOperationException();
        }
        else{
            return this;
        }
    }

    public AssertionInt isLessThan(int i2){
        if(o >= i2){
            throw new UnsupportedOperationException();
        }
        else{
            return this;
        }
    }

    public AssertionInt isGreaterThan(int i2){
        if(o <= i2){
            throw new UnsupportedOperationException();
        }
        else{
            return this;
        }
    }
}
