package GamePaths;
import java.util.*;


public class IntBoard {
	
	public static int ROW = 0;
	public static int COLS = 0;
	public static int GRID_PIECES = ROW*COLS;
	private Map<Integer, LinkedList<Integer>> adjMtx;
	private Boolean[] visited;
	private Set<Integer> targets;
	
	public IntBoard(){
		adjMtx = new TreeMap<Integer, LinkedList<Integer>>();
		targets = new TreeSet<Integer>();
		visited = new Boolean[GRID_PIECES];
		Arrays.fill(visited, false);
	}
	
	public void calcAdjacencies(){
		LinkedList<Integer> local;
		for(int i=0; i<ROW; i++) {
			for(int j=0; j<COLS; j++) {
				local = new LinkedList<Integer>();
				if(i!=0)
					local.add(COLS*i + j - COLS);
				if (i!=ROW-1) 
					local.add(COLS*i + j + COLS);
				if(j!=0)
					local.add(COLS*i + j - 1);
				if (j!=COLS-1)
					local.add(COLS*i + j + 1);
				adjMtx.put(COLS*i + j, local);
			}
		}
	}
	
	public void calcTargets(int start, int steps){
		int place;
		visited[start] = true;
		if(steps == 0){
			targets.add(start);
			visited[start] = false;
			return;
		}
		else{
			for(int i=0; i<getAdjList(start).size(); i++)
			{
				place = getAdjList(start).get(i);
				if(visited[place] == false)
					calcTargets(place , steps-1);
			}
		}
		visited[start] = false;
			
	}
	
	public Set<Integer> getTargets(){
		return targets;
	}
	
	public LinkedList<Integer> getAdjList(int x){
		return adjMtx.get(x);
	}
	
	public int calcIndex(int x, int y){
		return x*COLS + y;
	}

}
