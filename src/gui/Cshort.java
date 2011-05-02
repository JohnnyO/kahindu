 package gui;
 
 class Cshort implements Comparable {
	short i;
	Cshort(short a) {
		i = a;
	}
	Cshort(int a) {
		i = (short)a;
	}
	
	public short getValue() {
		return i;
	}
	public void setValue(short a) {
		i = a;
	}
	public void setValue(int a) {
		i = (short)a;
	}

	public boolean equals(Object other) {
		return (i == ((Cshort)other).i);
	}
	public int hashCode() {
		return i;
	}
    public boolean isLess(Object other) {
    	return (i < ((Cshort)other).i);
    }
    public boolean isGreater(Object other){
    	return (i > ((Cshort)other).i);
    }
    
    public String toString() {
    	return i+" ";
    }

}
