import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
//The main idea is:
// 1. Loop through the entire 2D maze
// if not a hash already there, add it. Then call doesItLoop
// doesItLoop: Maintains a HashSet which accounts for position and orientation.
// If we EVER arrive at the SAME location in the SAME orientation, return true (found a loop). Otherwise return false
// if doesItLoop keep a simple counter variable obstacleCount and increment
// at the end, return obstacleCount.

public class Part2 {

    public static void main(String[] args) {
        String locationData = "Day6/Data/d6input.txt";
        BufferedReader br = null;
        ArrayList<String[]> mazeList = new ArrayList<>();
        int r_loc = 0, c_loc = 0; //location of the '^' or '>'
        int obstacleCount;
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

        obstacleCount = obstacleLoopCount(maze, r_loc, c_loc, orientation);
        System.out.println("The number of distinct obstacle loop-inducing positions is " + obstacleCount);
    }

    public static int obstacleLoopCount(String[][] maze, int r_loc, int c_loc, String orientation) {
        int obstacleCount = 0;
        for (int r = 0; r < maze.length; r++) {
            for (int c = 0; c < maze[0].length; c++) {
                if (!maze[r][c].equals("#")) {
                    maze[r][c] = "#";
                    if (doesItLoop(maze, r_loc, c_loc, orientation)) obstacleCount++;
                    maze[r][c] = "."; //restore the maze to original condition.
                }
            }
        }
        return obstacleCount;
    }

    public static boolean doesItLoop(String[][] maze, int r_loc, int c_loc, String orientation) {
        int r = r_loc;
        int c = c_loc;
        String currOrientation = orientation;
        HashSet<OrientedPoint> visited = new HashSet<>();

        while ((r >= 0 && r <= maze.length - 1) && (c >= 0 && c <= maze[0].length - 1)) {
            if (!maze[r][c].equals("#")) {
                OrientedPoint currOP = new OrientedPoint(r, c, currOrientation);
                if (visited.contains(currOP)) {
                    return true;
                }
                visited.add(currOP);
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
        return false;
    }
}
