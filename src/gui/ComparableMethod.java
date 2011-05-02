package gui;
import java.lang.reflect.*;

public class ComparableMethod 
	implements Comparable {
	Method m;
    String s1 = this.toString();

	ComparableMethod(Method _m) {
		m = _m;
	}
    
	public String toString() {
		return m+"";
	}
	public Method getMethod() {
		return m;
	}
    public boolean equals(Object other) {
    	return (s1.compareTo(other.toString()) == 0);
    	
    }
    public int hashCode() {
    	return s1.hashCode();
    }
    public boolean isLess(Object other) {
    	return (s1.compareTo(other.toString()) < 0);
    }
    public boolean isGreater(Object other) {
    	return (s1.compareTo(other.toString()) > 0);
    }
	
	
}