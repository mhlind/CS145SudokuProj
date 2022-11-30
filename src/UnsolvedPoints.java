import java.awt.*;
import java.util.ArrayList;

public class UnsolvedPoints {

    Point pointOfUnsolvedLocation = new Point();

    char valueOfSquare;

    ArrayList<Integer> possibleValues;

    UnsolvedPoints (Point currentPoint, char currentValue, ArrayList<Integer> availableGuesses){
        pointOfUnsolvedLocation = currentPoint;
        valueOfSquare = currentValue;
        possibleValues = availableGuesses;
    }

    UnsolvedPoints (Point currentPoint){
        pointOfUnsolvedLocation = currentPoint;
        valueOfSquare = 0;
        possibleValues = new ArrayList<>();
    }

    public char getValueOfSquare (){
        return  valueOfSquare;
    }

    public Point getPointOfUnsolvedLocation (){
        return pointOfUnsolvedLocation;
    }

    public ArrayList<Integer> getPossibleValues (){
        return possibleValues;
    }
}
