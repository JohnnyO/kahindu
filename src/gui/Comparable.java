package gui;

public interface Comparable {
    public boolean equals(Object other);
    public boolean isLess(Object other);
    public boolean isGreater(Object other);
    public int hashCode();
}
