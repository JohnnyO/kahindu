package gui;

 class Cint implements Comparable {
	int i;
	Cint(int a) {
		i = a;
	}

	public boolean equals(Object other) {
		return (i == ((Cint)other).i);
	}
    public boolean isLess(Object other) {
    	return (i < ((Cint)other).i);
    }
    public boolean isGreater(Object other){
    	return (i > ((Cint)other).i);
    }
    
    public String toString() {
    	return i+" ";
    }
    public int hashCode() {
    	return i;
    }

}
