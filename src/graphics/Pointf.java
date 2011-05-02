package graphics;
import java.awt.*;

/**
 * The <code>Pointf</code> class represents a location in a
 * two-dimensional (<i>x</i>,&nbsp;<i>y</i>) coordinate space.
 *
 * @version 	1.0, 1/6/99
 * @author 	Douglas Lyon
 * @since       Kahindu 2.4
 */
public class Pointf extends Component 
	 implements java.io.Serializable {
	public float x = 0f;
	public float y = 0f;
	public float dotSize = 10;
    /*
     * JDK 1.1 serialVersionUID 
     */
    private static final long serialVersionUID = -5276940640259749850L;

    /**
     * Constructs and initializes a point at the origin 
     * (0,&nbsp;0) of the coordinate space. 
     * @param       x   the <i>x</i> coordinate.
     * @param       y   the <i>y</i> coordinate.
     * @since       Kahindu 2.4
     */
    public Pointf() {
		this(0f, 0f);
    }

    /**
     * Constructs and initializes a point with the same location as
     * the specified <code>Point</code> object.
     * @param       p a point.
     * @since       Kahindu 2.4
     */
    public Pointf(Pointf p) {
		this(p.x, p.y);
    }

    /**
     * Constructs and initializes a point at the specified 
     * (<i>x</i>,&nbsp;<i>y</i>) location in the coordinate space. 
     * @param       x   the <i>x</i> coordinate.
     * @param       y   the <i>y</i> coordinate.
     * @since       Kahindu 2.4
     */
    public Pointf(float x, float y) {
	this.x = x;
	this.y = y;
    }

    /**
     * Returns the location of this point.
     * This method is included for completeness, to parallel the
     * <code>getLocation</code> method of <code>Component</code>.
     * @return      a copy of this point, at the same location.
     * @see         java.awt.Component#getLocation
     * @see         java.awt.Point#setLocation(java.awt.Point)
     * @see         java.awt.Point#setLocation(int, int)
     * @since       Kahindu 2.4
     */
    public Point getLocation() {
		return new Point((int)x, (int)y);
    }	

    /**
     * Sets the location of the point to the specificed location.
     * This method is included for completeness, to parallel the
     * <code>setLocation</code> method of <code>Component</code>.
     * @param       p  a point, the new location for this point.
     * @see         java.awt.Component#setLocation(java.awt.Point)
     * @see         java.awt.Point#getLocation
     * @since       Kahindu 2.4
     */
    public void setLocation(Pointf p) {
	setLocation(p.x, p.y);
    }	

    /**
     * Changes the point to have the specificed location.
     * <p>
     * This method is included for completeness, to parallel the
     * <code>setLocation</code> method of <code>Component</code>.
     * Its behavior is identical with <code>move(int,&nbsp;int)</code>.
     * @param       x  the <i>x</i> coordinate of the new location.
     * @param       y  the <i>y</i> coordinate of the new location.
     * @see         java.awt.Component#setLocation(int, int)
     * @see         java.awt.Point#getLocation
     * @see         java.awt.Point#move(int, int)
     * @since        Kahindu 2.4
     */
    public void setLocation(float x, float y) {
	move(x, y);
    }	

    /**
     * Moves this point to the specificed location in the 
     * (<i>x</i>,&nbsp;<i>y</i>) coordinate plane. This method
     * is identical with <code>setLocation(int,&nbsp;int)</code>.
     * @param       x  the <i>x</i> coordinate of the new location.
     * @param       y  the <i>y</i> coordinate of the new location.
     * @see         java.awt.Component#setLocation(int, int)
     * @since        Kahindu 2.4
     */
    public void move(float x, float y) {
	this.x = x;
	this.y = y;
    }	

    /**
     * Translates this point, at location (<i>x</i>,&nbsp;<i>y</i>), 
     * by <code>dx</code> along the <i>x</i> axis and <code>dy</code> 
     * along the <i>y</i> axis so that it now represents the point 
     * (<code>x</code>&nbsp;<code>+</code>&nbsp;<code>dx</code>, 
     * <code>y</code>&nbsp;<code>+</code>&nbsp;<code>dy</code>). 
     * @param       dx   the distance to move this point 
     *                            along the <i>x</i> axis.
     * @param       dy    the distance to move this point 
     *                            along the <i>y</i> axis.
     * @since       Kahindu 2.4
     */
    public void translate(float x, float y) {
	this.x += x;
	this.y += y;
    }	

    /**
     * Returns the hashcode for this point.
     * @return      a hash code for this point.
     * @since       Kahindu 2.4
     */
    public int hashCode() {
	return (int)x ^ ((int)y*31);
    }

    /**
     * Determines whether two points are equal. Two instances of
     * <code>Point</code> are equal if the values of their 
     * <code>x</code> and <code>y</code> member fields, representing
     * their position in the coordinate space, are the same.
     * @param      obj   an object to be compared with this point.
     * @return     <code>true</code> if the object to be compared is
     *                     an instance of <code>Point</code> and has
     *                     the same values; <code>false</code> otherwise.
     * @since     kahindu 2.4
     */
    public boolean equals(Object obj) {
	if (obj instanceof Pointf) {
	    Pointf pt = (Pointf)obj;
	    return (x == pt.x) && (y == pt.y);
	}
	return false;
    }

    /**
     * Returns a representation of this point and its location
     * in the (<i>x</i>,&nbsp;<i>y</i>) coordinate space as a string.
     * @return    a string representation of this point, 
     *                 including the values of its member fields.
     * @since     kahindu 2.4
     */
    public String toString() {
	return getClass().getName() + "[x=" + x + ",y=" + y + "]";
    }
}
