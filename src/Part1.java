import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Part1 {

    public static void main(String[] args) {
        String locationData = "Day6/Data/d6input.txt";
        BufferedReader br = null;
        ArrayList<String[]> mazeList = new ArrayList<>();
        int r_loc = 0, c_loc = 0; //location of the '^' or '>'
        int numPos;
        String orientation = "";

        //try - catch - finally block for reading files
        try {
            br = new BufferedReader(new FileReader(locationData));
            String line;
            while ((line = br.readLine()) != null) {
                String[] input = line.split("");
                mazeList.add(input);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                if (br != null) br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String[][] maze = mazeList.toArray(new String[mazeList.size()][]);
        for (int r = 0; r < maze.length; r++) {
            for (int c = 0; c < maze[0].length; c++) {
                if (maze[r][c].equals("^")) {
                   orientation = "^";
                    r_loc = r;
                    c_loc = c;
                    break;
                }
                if (maze[r][c].equals(">")) {
                    orientation = ">";
                    r_loc = r;
                    c_loc = c;
                    break;
                }
                if (maze[r][c].equals("v")) {
                    orientation = "v";
                    r_loc = r;
                    c_loc = c;
                    break;
                }
                if (maze[r][c].equals("<")) {
                    orientation = "<";
                    r_loc = r;
                    c_loc = c;
                    break;
                }
            }
        }

        numPos = computePos(maze, r_loc, c_loc, orientation);
        System.out.println("The number of distinct positions visited is " + numPos);
    }
    public static int computePos(String[][] maze, int r_loc, int c_loc, String orientation) {
        int r = r_loc;
        int c = c_loc;
        String currOrientation = orientation;
        HashSet<Point> visited = new HashSet<>();
        int counter = 0;

        while ((r >= 0 && r <= maze.length - 1) && (c >= 0 && c <= maze[0].length - 1)) {
            if (!maze[r][c].equals("#")) {
                counter++;
                //System.out.println("r: " + r + " c: " + c);
                Point currP = new Point(r, c);
                visited.add(currP);
                if (currOrientation.equals("^")) r--;
                if (currOrientation.equals(">")) c++;
                if (currOrientation.equals("v")) r++;
                if (currOrientation.equals("<")) c--;
            }
            else {
                if (currOrientation.equals("^")) {
                    currOrientation = ">";
                    r++;
                }
                else if (currOrientation.equals(">")) {
                    currOrientation = "v";
                    c--;
                }
                else if (currOrientation.equals("v")) {
                    currOrientation = "<";
                    r--;
                }
                else {
                    currOrientation = "^";
                    c++;
                }
            }
        }
        //System.out.println("counter " + counter);
        return visited.size();
    }
}