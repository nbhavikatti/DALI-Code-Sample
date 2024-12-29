import java.util.Objects;

public class OrientedPoint {
    int xCoor;
    int yCoor;
    String orientation;

    public OrientedPoint (int xCoor, int yCoor, String orientation) {
        this.xCoor = xCoor;
        this.yCoor = yCoor;
        this.orientation = orientation;
    }

    @Override
    public boolean equals(Object obj) {
        OrientedPoint c = (OrientedPoint) obj;
        return ((c.xCoor == this.xCoor) && (c.yCoor == this.yCoor) && (c.orientation.equals(this.orientation)));
    }

    @Override
    public int hashCode() {
        return Objects.hash(xCoor, yCoor, orientation);
    }
}
