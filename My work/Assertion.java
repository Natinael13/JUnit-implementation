public class Assertion {
    /* You'll need to change the return type of the assertThat methods */
    static AssertionObject assertThat(Object o) {
        AssertionObject result = new AssertionObject(o);
        return result;
    }
    static AssertionString assertThat(String s) {
        AssertionString result = new AssertionString(s);
        return result;
    }
    static AssertionBoolean assertThat(boolean b) {
        AssertionBoolean result = new AssertionBoolean(b);
        return result;
    }
    static AssertionInt assertThat(int i) {
        AssertionInt result = new AssertionInt(i);
        return result;
    }
}