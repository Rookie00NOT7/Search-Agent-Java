package code.mission;

import java.util.ArrayList;
import java.util.StringTokenizer;

import code.generic.Node;
import code.generic.SearchProblem;


public class MissionImpossible extends SearchProblem {
	public static int xSizeOfGrid;
	public static int ySizeOfGrid;
	public static Position[] agentsPosition;
	public static int[] agentsHealth;
	public static Position submarinePosition = new Position();
	public static Position ethanPosition = new Position();
	public static int truckSize;
	public static int nodesExpanded;
	public static Agent[] agents;
	public static MissionImpossibleNode[][] grid2D;
	static MissionImpossibleNode[][] grid;
	int totalAgents;
	int numberOfAgents;
	ArrayList<MissionImpossibleNode> goalNodes;
	int agentsCollectedSoFar;
	static int[] finalHealth;
	
	public ArrayList<String> getOperators() {
		return this.operators;
	}
	
	public void setOperators(ArrayList<String> operators) {
		this.operators = operators;
	}
	
	public MissionImpossibleNode getInitialState() {
		return initialState;
	}
	
	public void setInitialState(MissionImpossibleNode initialState) {
		MissionImpossible.initialState = initialState;
	}
	
	public ArrayList<MissionImpossibleState> getStateSpace() {
		return stateSpace;
	}
	
	public void setStateSpace(ArrayList<MissionImpossibleState> stateSpace) {
		this.stateSpace = stateSpace;
	}
	
	public MissionImpossibleState getGoalState() {
		return goalState;
	}
	
	public void setGoalState(MissionImpossibleState goalState) {
		this.goalState = goalState;
	}
	
	public MissionImpossibleNode[][] getGrid2D() {
		return grid2D;
	}
	
	public void setGrid2D(MissionImpossibleNode[][] grid2d) {
		grid2D = grid2d;
	}
	
	public static int random(int min, int max) {  // generates a random number in the range(min --> max)
		return (int) ((Math.random() * (max - min)) + min);
	}
	
	public static boolean checkIfTaken(Position [] positionsTaken, Position test) { // checks if the position test is taken previously 
		for (int i = 0; i<positionsTaken.length ; i++) {
			if(positionsTaken[i] == null) 
					return false;
			if (test.equals(positionsTaken[i]))
				return true;
			
		}
		return false;
		
	}

	public static String genGrid() {
		String grid = "";
		int gridXSize = random(5,16);
		int gridYSize = random(5,16);
		int agents = random(5,11);
		int c = random(1,11);
		Position [] positionsTaken = new Position [gridXSize*gridYSize];
		int numberOfPositionsTaken = 0;
		int [] health = new int [agents];
		for(int i= 0 ; i<agents ; i++) {
			health[i] = random(1,100);
		}
		
		Position ethan = new Position(random(0,gridXSize), random(0,gridYSize));
		positionsTaken[numberOfPositionsTaken] = ethan; //inserting ethan
		numberOfPositionsTaken++;
		
		Position submarine = new Position(random(0,gridXSize), random(0,gridYSize));
		while(checkIfTaken(positionsTaken , submarine)) {// if the submarine's position is taken before
			System.out.println("same position changing ...");
			submarine = new Position(random(0,gridXSize), random(0,gridYSize));
		}
		positionsTaken[numberOfPositionsTaken] = submarine; //inserting submarine
		numberOfPositionsTaken++;
		
		Position [] IMFAgents = new Position[agents];
		for (int i = 0; i<agents ; i++) { // placing the agents on the grid
				IMFAgents[i] = new Position(random(0,gridXSize), random(0,gridYSize));	
				
				while(checkIfTaken(positionsTaken , IMFAgents[i])) {// if the IMFAgent's position is taken before
//					System.out.println("same position changing ........");
					IMFAgents[i] = new Position(random(0,gridXSize), random(0,gridYSize));
				}
				positionsTaken[numberOfPositionsTaken] = IMFAgents[i]; //inserting the IMFAgent
				numberOfPositionsTaken++;
		}
		
		grid += gridXSize + "," + gridYSize + ";" + ethan.x + "," + ethan.y + ";" + submarine.x + "," + submarine.y + ";" ;
		for(int i=0 ; i<agents-1 ; i++) {
			grid += IMFAgents[i].x +","  + IMFAgents[i].y + ",";
		}
		grid += IMFAgents[agents-1].x +","  + IMFAgents[agents-1].y +";";
		for(int i=0 ; i<agents-1 ; i++) {
			grid += health[i] + ",";
		}
		grid+= health[agents-1] +";" + c ;
		
		System.out.println("grid = " + grid);
//		System.out.println("size = " + gridXSize + " * "+ gridYSize);
//		System.out.println("agents = " + agents);
//		System.out.println("ethan = " +ethan);
//		System.out.println("submarine = "+ submarine);
//		System.out.println("c = "+ c);
//		for(int i=0 ; i<agents ; i++) {
//			System.out.println("IMFAgent" + (i+1) + " health = " + health[i]);
//		}
//		for(int i = 0; i<agents ; i++) {
//			System.out.println("IMFAgent" + (i+1) + " = " + IMFAgents[i]);
//		}
		return grid;
	}
	
	public static String solve(String grid, String strategy, boolean visualize) {
		InterpretGrid(grid);
		MissionImpossible mI = new MissionImpossible();
		mI.setGoalState(getGoalStates());
		mI.setGrid2D(gridTogrid2D());
		mI.setInitialState(findInitialState());
		mI.goalNodes = new ArrayList<MissionImpossibleNode>();
		mI.totalAgents = (agentsPosition.length);
		mI.numberOfAgents = mI.totalAgents;
		mI.agentsCollectedSoFar = 0;
		MissionImpossible.nodesExpanded = 0;
		MissionImpossible.finalHealth = new int[agentsHealth.length];
		
		
		String path = "";
		MissionImpossibleNode result = null;
		
		
		if(strategy.equals("ID")) {
			result = mI.iterativeSearch(truckSize);
		}
		if(strategy.equals("DF")) {
			result = mI.depthSearch(truckSize);
		}
	
		if(strategy.equals("BF")) {
			result = mI.breadthFirst(truckSize);	
		}
		
		if(strategy.equals("UC")) {
			result = mI.uniformCostSearch(truckSize);
		}
		if(strategy.equals("GR1")) {
			result = mI.greedy1(truckSize);
		}
		if(strategy.equals("GR2")) {
			result= mI.greedy2(truckSize);
		}
		if(strategy.equals("AS1")) {
			result= mI.as1(truckSize);
		}
		if(strategy.equals("AS2")) {
			result= mI.as2(truckSize);
		}
		
		path = getPath(result);
		if(visualize)
			visualize(path);
		return path;
		
	}
	
	public static void InterpretGrid(String grid) { //manipulates the grid to extract the information in it
		int token = 0;
		StringTokenizer st = new StringTokenizer(grid , ";");
		while(st.hasMoreTokens()) {
//			System.out.println("entered here");
			token++;
			//StringTokenizer st1 = new StringTokenizer(st.nextToken() , ",");
			//while(st1.hasMoreTokens()) {
				//System.out.println("entered once");
				switch(token){
				case 1:
					StringTokenizer st1 = new StringTokenizer(st.nextToken() , ",");
					xSizeOfGrid = Integer.parseInt(st1.nextToken());
					ySizeOfGrid = Integer.parseInt(st1.nextToken());
					break;
				case 2:
					StringTokenizer st2 = new StringTokenizer(st.nextToken() , ",");
					ethanPosition = new Position();
					ethanPosition.x = Integer.parseInt(st2.nextToken());
					ethanPosition.y = Integer.parseInt(st2.nextToken());
					break;
				case 3:
					StringTokenizer st3 = new StringTokenizer(st.nextToken() , ",");
					submarinePosition = new Position();
					submarinePosition.x = Integer.parseInt(st3.nextToken());
					submarinePosition.y = Integer.parseInt(st3.nextToken());
					break;
				case 4:
					StringTokenizer st4 = new StringTokenizer(st.nextToken() , ",");
					agentsPosition = new Position[st4.countTokens()/2];
					for(int i =0; i<agentsPosition.length ; i++) {
						agentsPosition[i] = new Position();
						agentsPosition[i].x = Integer.parseInt(st4.nextToken()); 
						agentsPosition[i].y = Integer.parseInt(st4.nextToken());
					}
					break;
				case 5:
					StringTokenizer st5 = new StringTokenizer(st.nextToken() , ",");
					agentsHealth =new int[st5.countTokens()];
					for(int i =0; i<agentsHealth.length ; i++) {
						agentsHealth[i] = Integer.parseInt(st5.nextToken());
					}
					break;
				case 6:
					StringTokenizer st6 = new StringTokenizer(st.nextToken() , ",");
					truckSize = Integer.parseInt(st6.nextToken());
					break;
				default:
					break;
					
				//}
			}
		}
	}
	
	public static int[] convertToIntArray(String[] array) {
		int[] resArray = new int[array.length];
		for(int i=0;i<array.length;i++) {
			resArray[i] = Integer.parseInt(array[i]);
		}
		
		return resArray;
	}
	
	public static int[][] convertPostoTupleArray(int[] array){
		int[][] resArr = new int[array.length/2][2];
		int j =0;
		for(int i = 0;i<array.length;i+=2) {
			int[] tuple = {array[i],array[i+1]};
			resArr[j] = tuple ;
			j+=1;
			
		}
		
		return resArr;
	}
	
	public static MissionImpossibleNode[][] gridTogrid2D(){
		
		MissionImpossibleNode[][] grid2D = new MissionImpossibleNode[xSizeOfGrid][ySizeOfGrid];
		MissionImpossibleState s = new MissionImpossibleState();
		MissionImpossibleNode n1 = new MissionImpossibleNode();
		
		//set ethan Node on the grid2D
		s.setPosition(ethanPosition);
		s.setAgentsLeftSoFar(agentsHealth.length);
		s.setSubmarine(false);
		s.setHasAgent(false);
		n1.setState(s);
		n1.setDepth(0);
		n1.setParentNode(null);
		n1.setOperator("none");
		grid2D[ethanPosition.x][ethanPosition.y] = n1;
		
		
		//set submarine Node on grid2D
		MissionImpossibleState s1 = new MissionImpossibleState();
		MissionImpossibleNode n2 = new MissionImpossibleNode();
		s1.setPosition(submarinePosition);
		s1.setSubmarine(true);
		s1.setAgentsLeftSoFar(agentsPosition.length);
		s1.setHasAgent(false);
		n2.setState(s1);
		grid2D[submarinePosition.x][submarinePosition.y] = n2;

		
		MissionImpossible.agents = new Agent[agentsPosition.length];
		//set the IMF agents on the grid2D
		for(int i = 0;i<agentsPosition.length;i++) {
			MissionImpossibleNode n3 = new MissionImpossibleNode();
			MissionImpossibleState s3 = new MissionImpossibleState();
			Agent a = new Agent(agentsPosition[i],agentsHealth[i]);
			MissionImpossible.agents[i] = a;
			s3.setPosition(agentsPosition[i]);
			s3.setHasAgent(true);
			s3.setSubmarine(false);
			s3.setAgentsLeftSoFar(agentsPosition.length);
			n3.setState(s3);
			grid2D[agentsPosition[i].x][agentsPosition[i].y] = n3; //y go through the rows and x go through the columns

			
		}
		

		
		return grid2D;
	}
	
	public static MissionImpossibleState getGoalStates() {
		MissionImpossibleState s6 = new MissionImpossibleState();
		MissionImpossibleNode n6 = new MissionImpossibleNode();
		Agent[] agentsEnd = new Agent[agentsHealth.length];
		s6.setPosition(submarinePosition);
		s6.setAgentsLeftSoFar(0);
		s6.setAgents(agentsEnd);
		s6.setHasAgent(false);
		s6.setSubmarine(true);
		n6.setState(s6);
		
		return s6;
	}
	
	public static MissionImpossibleNode findInitialState() {
		
		MissionImpossibleState s5 = new MissionImpossibleState();
		MissionImpossibleNode n5 = new MissionImpossibleNode();
		
		
		s5.setPosition(ethanPosition);
		s5.setAgentsLeftSoFar(agentsPosition.length);
		s5.setAgents(MissionImpossible.agents);
		s5.setSubmarine(false);
		s5.setHasAgent(false);
		n5.setState(s5);
		n5.setDepth(0);
		n5.setOperator("start");
		n5.setParentNode(null);
		
		return n5;
	}
	
	public boolean statePresent(ArrayList<MissionImpossibleNode> states, MissionImpossibleNode s) {
		if(states.isEmpty())
			return false;
		return states.stream().anyMatch(p ->p.state.position.x == s.state.position.x && p.state.position.y == s.state.position.y 
				&& p.state.agentsLeftSoFar==s.state.agentsLeftSoFar );
		
	}
	
	public boolean isParent(MissionImpossibleNode n1,MissionImpossibleNode n2) {
		return n1.equals(n2);
	}
	
	public boolean goalTest(MissionImpossibleNode node) {
		if(node!=null) {
			if(findAgentsLeft(node.state.agents) == 0&& node.state.isSubmarine) {
				return true;
			}
		}
		
		return false;
	}
	
	public MissionImpossibleNode generalSearchProblem (String strategy,int truckSize) {
		ArrayList<MissionImpossibleNode> nodes = new ArrayList<MissionImpossibleNode>();
		ArrayList<MissionImpossibleNode> previousStates = new ArrayList<MissionImpossibleNode>();
		nodes.add(initialState);
		MissionImpossibleNode node = null;
		int allowedDepth = 0;
		boolean newDepth = false;
		boolean [] [] removedAgents = new boolean [xSizeOfGrid] [ySizeOfGrid];
		while(!nodes.isEmpty()) {
			node = nodes.remove(0);
			if(goalTest(node)) {
				return node;
			}
			else{
				if(strategy.equals("ID")) {
					if(newDepth) {
						previousStates.clear();
						newDepth = false;
					}
						
					if( !statePresent(previousStates,node)&& !isParent(node,node.parentNode)) {
						if(node.getOperator().equals("carry")) {
							agentsCollectedSoFar ++;
							grid[node.getState().getPosition().x][node.getState().getPosition().y].getState().setHasAgent(false);
							removedAgents[node.getState().getPosition().x][node.getState().getPosition().y] = true;
							previousStates.clear();
							nodes.clear();
						}
						if(node.getOperator().equals("drop")) {
							agentsCollectedSoFar = 0 ;
							previousStates.clear();
							nodes.clear();
						}
						ArrayList<MissionImpossibleNode> nodeChildren = expandNode(node,agentsCollectedSoFar,truckSize,"DF");
						for(MissionImpossibleNode n : nodeChildren) {
							if(n.depth<allowedDepth)
								nodes.add(0, n);
						}
						
						nodesExpanded++;
					}
					if(nodes.isEmpty()) {
						nodes.add(initialState);
						allowedDepth++;
						newDepth = true;
						previousStates.clear();
						agentsCollectedSoFar = 0;
						for(int i = 0;i<removedAgents.length;i++) {
							for(int j = 0;j<removedAgents[i].length;j++) {
								if(removedAgents[i][j])
									grid[i][j].state.setHasAgent(true);
							}
						}
					}
				
				}
				if(strategy.equals("DF")) {
					if( !statePresent(previousStates,node)&& !isParent(node,node.parentNode)) {
						if(node.getOperator().equals("carry")) {
							agentsCollectedSoFar ++;
							grid[node.getState().getPosition().x][node.getState().getPosition().y].getState().setHasAgent(false);
							previousStates.clear();
							nodes.clear();
						}
						if(node.getOperator().equals("drop")) {
							agentsCollectedSoFar = 0 ;
							previousStates.clear();
							nodes.clear();
						}
						ArrayList<MissionImpossibleNode> nodeChildren = expandNode(node,agentsCollectedSoFar,truckSize,strategy);
						for(MissionImpossibleNode n : nodeChildren) {
								nodes.add(0, n);
						}
						
						nodesExpanded++;
					}
				}
				
				if(strategy.equals("BF")) {
					if( !statePresent(previousStates,node)&& !isParent(node,node.parentNode)) {
						if(node.getOperator().equals("carry")) {
							agentsCollectedSoFar ++;
							grid[node.getState().getPosition().x][node.getState().getPosition().y].getState().setHasAgent(false);
							previousStates.clear();
							nodes.clear();
						}
						if(node.getOperator().equals("drop")) {
							agentsCollectedSoFar = 0 ;
							previousStates.clear();
							nodes.clear();
						}
						ArrayList<MissionImpossibleNode> nodeChildren = expandNode(node,agentsCollectedSoFar,truckSize,strategy);
						for(MissionImpossibleNode n : nodeChildren) {
								nodes.add(n);
						}
						nodesExpanded++;
					}
				}
				if(strategy.equals("UC")) {
					if( !statePresent(previousStates,node)&& !isParent(node,node.parentNode)) {
						if(node.getOperator().equals("carry") ) {
							agentsCollectedSoFar ++;
							grid[node.getState().getPosition().x][node.getState().getPosition().y].getState().setHasAgent(false);
							nodes.clear();
							previousStates.clear();
						}
						if(node.getOperator().equals("drop")) {
							agentsCollectedSoFar = 0 ;
							nodes.clear();
							previousStates.clear();
						}
						ArrayList<MissionImpossibleNode> nodeChildren = expandNode(node,agentsCollectedSoFar,truckSize,strategy);
						for(MissionImpossibleNode n : nodeChildren) {
								nodes = orderedInsert(nodes,n);
						}
						nodesExpanded++;
					}
				}
				if(strategy.equals("GR1")) {
					if( !statePresent(previousStates,node) && !isParent(node,node.parentNode)) {
						if(node.getOperator().equals("carry")) {
							agentsCollectedSoFar ++;
							grid[node.getState().getPosition().x][node.getState().getPosition().y].getState().setHasAgent(false);
							previousStates.clear();
							nodes.clear();
						}
						if(node.getOperator().equals("drop")) {
							agentsCollectedSoFar = 0 ;
							nodes.clear();
							previousStates.clear();
						}
						ArrayList<MissionImpossibleNode> nodeChildren = expandNode(node,agentsCollectedSoFar,truckSize,strategy);
						for(MissionImpossibleNode n : nodeChildren) {
								nodes = orderedInsertHeuristic(nodes,n);
						}
						nodesExpanded++;
					}
				}
				if(strategy.equals("AS1")) {
					if( !statePresent(previousStates,node) && !isParent(node,node.parentNode)) {
						if(node.getOperator().equals("carry")) {
							agentsCollectedSoFar ++;
							grid[node.getState().getPosition().x][node.getState().getPosition().y].getState().setHasAgent(false);
							previousStates.clear();
							nodes.clear();
						}
						if(node.getOperator().equals("drop")) {
							agentsCollectedSoFar = 0 ;
							nodes.clear();
							previousStates.clear();
						}
						ArrayList<MissionImpossibleNode> nodeChildren = expandNode(node,agentsCollectedSoFar,truckSize,strategy);
						for(MissionImpossibleNode n : nodeChildren) {
								nodes = orderedInsertHeuristic(nodes,n);
						}
						nodesExpanded++;
					}
				}
				if(strategy.equals("GR2")) {
					if( !statePresent(previousStates,node)&& !isParent(node,node.parentNode)) {
						if(node.getOperator().equals("carry")) {
							agentsCollectedSoFar +=1;
							grid[node.getState().getPosition().x][node.getState().getPosition().y].getState().setHasAgent(false);
							previousStates.clear();
							nodes.clear();
						}
						if(node.getOperator().equals("drop")) {
							agentsCollectedSoFar = 0 ;
							nodes.clear();
							previousStates.clear();
						}
						ArrayList<MissionImpossibleNode> nodeChildren = expandNode(node,agentsCollectedSoFar,truckSize,strategy);
						for(MissionImpossibleNode n : nodeChildren) {
								nodes = orderedInsertHeuristic(nodes,n);
						}
						nodesExpanded++;
					}
				}
				
				if(strategy.equals("AS2")) {
					if( !statePresent(previousStates,node)&& !isParent(node,node.parentNode)) {
						if(node.getOperator().equals("carry")) {
							agentsCollectedSoFar +=1;
							grid[node.getState().getPosition().x][node.getState().getPosition().y].getState().setHasAgent(false);
							previousStates.clear();
							nodes.clear();
						}
						if(node.getOperator().equals("drop")) {
							agentsCollectedSoFar = 0 ;
							nodes.clear();
							previousStates.clear();
						}
						ArrayList<MissionImpossibleNode> nodeChildren = expandNode(node,agentsCollectedSoFar,truckSize,strategy);
						for(MissionImpossibleNode n : nodeChildren) {
								nodes = orderedInsertHeuristic(nodes,n);
						}
						nodesExpanded++;
					}
				}
				
			}
			previousStates.add(node);
		
		}
		return null;
	}
	
	public ArrayList<MissionImpossibleNode> expandNode(MissionImpossibleNode node, int agentsCollectedSoFar,int truckSize,String strategy){
		ArrayList<MissionImpossibleNode> expandedNodes = new ArrayList<MissionImpossibleNode>();
		MissionImpossibleNode up = new MissionImpossibleNode();
		MissionImpossibleState u = new MissionImpossibleState();
		MissionImpossibleNode down = new MissionImpossibleNode();
		MissionImpossibleState d = new MissionImpossibleState();
		MissionImpossibleNode left = new MissionImpossibleNode();
		MissionImpossibleState l = new MissionImpossibleState();
		MissionImpossibleNode right = new MissionImpossibleNode();
		MissionImpossibleState r = new MissionImpossibleState();
		MissionImpossibleNode drop = new MissionImpossibleNode();
		MissionImpossibleState dr = new MissionImpossibleState();
		MissionImpossibleNode carry = new MissionImpossibleNode();
		MissionImpossibleState c = new MissionImpossibleState();
			
		int agentMaxHealthIndex = findIndexAgentWithMaxHealth(node.state.agents);
		Agent agentMaxHealth = node.state.agents[agentMaxHealthIndex];
		int agentsLeft = findAgentsLeft(node.state.agents);
		Agent[] reducedHealthAgents = reduceHealth(node.state.agents);
		
		//////////////////   UP  ////////////////////////
		if(node.getState().getPosition().x-1>=0) {
			u.setPosition(new Position(node.getState().getPosition().x-1,node.getState().getPosition().y));
			u.setAgentsLeftSoFar(agentsLeft);
			u.setAgents(reducedHealthAgents);
			up.setState(u);
			up.setOperator("up");
			up.setParentNode(node);
			up.setDepth(node.getDepth()+1);
			if(strategy.equals("GR1")||strategy.equals("AS1")) {
				if(agentsCollectedSoFar == truckSize) {
					int submarineEuclideanDistance = (int) Math.floor(Math.pow((goalState.position.x-u.position.x),2) + Math.pow((goalState.position.y-u.position.y),2));
					up.setHeuristic( submarineEuclideanDistance+findAgentsLeft(u.agents));
				}
				else {
					if(findAgentsLeft(u.agents) == 0){
						int submarineManhattanDistance = Math.abs(goalState.position.x-u.position.x) + Math.abs(goalState.position.y-u.position.y);
						up.setHeuristic( submarineManhattanDistance+findAgentsLeft(u.agents));
					}
					else {
						if(agentMaxHealth!=null) {
							up.setHeuristic( getHeuristic1(up));
						}						
					}
				}
			}
			if(strategy.equals("GR2")||strategy.equals("AS2")) {
				if(agentsCollectedSoFar == truckSize) {
					int submarineEuclideanDistance = (int) Math.floor(Math.pow((goalState.position.x-u.position.x),2) + Math.pow((goalState.position.y-u.position.y),2));
					up.setHeuristic( submarineEuclideanDistance+findAgentsLeft(u.agents));
				}
				else {
					if(findAgentsLeft(u.agents) == 0){
						int submarineManhattanDistance = Math.abs(goalState.position.x-u.position.x) + Math.abs(goalState.position.y-u.position.y);
						up.setHeuristic( submarineManhattanDistance+findAgentsLeft(u.agents));
					}
					else {
						if(agentMaxHealth!=null) 
							up.setHeuristic( getHeuristic2(up));	
							
					}
				}
			}
			if(strategy.equals("UC")||strategy.equals("AS1")||strategy.equals("AS2")) {
				up.setPathCost(pathCost(up)+node.pathCost);
			}
			
			up.setCostAndHeuristic(up.heuristic+up.pathCost);
			expandedNodes.add(up);
		}
		
		//////////////////  DOWN  ////////////////////////
		if(node.getState().getPosition().x+1<grid2D.length) {
			d.setPosition(new Position(node.getState().getPosition().x+1,node.getState().getPosition().y));
			d.setAgentsLeftSoFar(agentsLeft);
			d.setAgents(reducedHealthAgents);
			down.setState(d);
			down.setOperator("down");
			down.setParentNode(node);
			down.setDepth(node.getDepth()+1);
			if(strategy.equals("GR1")||strategy.equals("AS1")) {
				if(agentsCollectedSoFar == truckSize) {
					int submarineEuclideanDistance = (int) Math.floor(Math.pow((goalState.position.x-d.position.x),2) + Math.pow((goalState.position.y-d.position.y),2));
					down.setHeuristic( submarineEuclideanDistance+findAgentsLeft(d.agents));
				}
				else {
					if(findAgentsLeft(d.agents) == 0){
						int submarineManhattanDistance = Math.abs(goalState.position.x-d.position.x) + Math.abs(goalState.position.y-d.position.y);
						down.setHeuristic( submarineManhattanDistance+findAgentsLeft(d.agents));
					}
					else {
						if(agentMaxHealth!=null) {
							down.setHeuristic(getHeuristic1(down));
						}
					}
				}
			}
			if(strategy.equals("GR2")||strategy.equals("AS2")) {
				if(agentsCollectedSoFar == truckSize) {
					int submarineEuclideanDistance = (int) Math.floor(Math.pow((goalState.position.x-d.position.x),2) + Math.pow((goalState.position.y-d.position.y),2));
					down.setHeuristic( submarineEuclideanDistance+findAgentsLeft(d.agents));
				}
				else {
					if(findAgentsLeft(d.agents) == 0){
						int submarineManhattanDistance = Math.abs(goalState.position.x-d.position.x) + Math.abs(goalState.position.y-d.position.y);
						down.setHeuristic( submarineManhattanDistance+findAgentsLeft(d.agents));
					}
					else {
						if(agentMaxHealth!=null)
							down.setHeuristic(getHeuristic2(down));
					}
				}
			}
			if(strategy.equals("UC")||strategy.equals("AS1")||strategy.equals("AS2")) {
				down.setPathCost(pathCost(down)+node.pathCost);
			}
			down.setCostAndHeuristic(down.heuristic+down.pathCost);
			expandedNodes.add(down);
		}
						
		//////////////////  LEFT  ////////////////////////
		if(node.getState().getPosition().y-1>=0) {
			l.setPosition(new Position(node.getState().getPosition().x,node.getState().getPosition().y-1));
			l.setAgentsLeftSoFar(agentsLeft);
			l.setAgents(reducedHealthAgents);
			left.setState(l);
			left.setOperator("left");
			left.setParentNode(node);
			left.setDepth(node.getDepth()+1);
			if(strategy.equals("GR1")||strategy.equals("AS1")) {
				if(agentsCollectedSoFar == truckSize) {
					int submarineEuclideanDistance = (int) Math.floor(Math.pow((goalState.position.x-l.position.x),2) + Math.pow((goalState.position.y-l.position.y),2));
					left.setHeuristic( submarineEuclideanDistance+findAgentsLeft(l.agents));
				}
				else {
					if(findAgentsLeft(l.agents) == 0){
						int submarineManhattanDistance = Math.abs(goalState.position.x-l.position.x) + Math.abs(goalState.position.y-l.position.y);
						left.setHeuristic( submarineManhattanDistance+findAgentsLeft(l.agents));
					}
					else {
						if(agentMaxHealth!=null) {
							left.setHeuristic(getHeuristic1(left) );
						}
					}
				}
			}
			if(strategy.equals("GR2")||strategy.equals("AS2")) {
				if(agentsCollectedSoFar == truckSize) {
					int submarineEuclideanDistance = (int) Math.floor(Math.pow((goalState.position.x-l.position.x),2) + Math.pow((goalState.position.y-l.position.y),2));
					left.setHeuristic( submarineEuclideanDistance+findAgentsLeft(l.agents));
				}
				else {
					if(findAgentsLeft(l.agents) == 0){
						int submarineManhattanDistance = Math.abs(goalState.position.x-l.position.x) + Math.abs(goalState.position.y-l.position.y);
						left.setHeuristic( submarineManhattanDistance+findAgentsLeft(l.agents));
					}
					else {
						if(agentMaxHealth!=null)
							left.setHeuristic(getHeuristic2(left));
					}
					
				}
			}
			if(strategy.equals("UC")||strategy.equals("AS1")||strategy.equals("AS2")) {
				left.setPathCost(pathCost(left)+node.pathCost);
			}
			left.setCostAndHeuristic(left.heuristic+left.pathCost);
			expandedNodes.add(left);
		}
		
		//////////////////  RIGHT  ////////////////////////
		if(node.getState().getPosition().y+1<grid2D[0].length) {
			r.setPosition(new Position(node.getState().getPosition().x,node.getState().getPosition().y+1));
			r.setAgentsLeftSoFar(agentsLeft);
			r.setAgents(reducedHealthAgents);
			right.setState(r);
			right.setOperator("right");
			right.setParentNode(node);
			right.setDepth(node.getDepth()+1);
			if(strategy.equals("GR1")||strategy.equals("AS1")) {
				if(agentsCollectedSoFar == truckSize) {
					int submarineEuclideanDistance = (int) Math.floor(Math.pow((goalState.position.x-r.position.x),2) + Math.pow((goalState.position.y-r.position.y),2));
					right.setHeuristic( submarineEuclideanDistance+findAgentsLeft(r.agents));
				}
				else {
					if(findAgentsLeft(r.agents) == 0){
						int submarineManhattanDistance = Math.abs(goalState.position.x-r.position.x) + Math.abs(goalState.position.y-r.position.y);
						right.setHeuristic( submarineManhattanDistance+findAgentsLeft(r.agents));
					}
					else {
						if(agentMaxHealth!=null){
							right.setHeuristic(getHeuristic1(right) );
						}
					}
				}
			}
			if(strategy.equals("GR2")||strategy.equals("AS2")) {
				if(agentsCollectedSoFar == truckSize) {
					int submarineEuclideanDistance = (int) Math.floor(Math.pow((goalState.position.x-r.position.x),2) + Math.pow((goalState.position.y-r.position.y),2));
					right.setHeuristic( submarineEuclideanDistance+findAgentsLeft(r.agents));
				}
				else {
					if(findAgentsLeft(r.agents) == 0){
						int submarineManhattanDistance = Math.abs(goalState.position.x-r.position.x) + Math.abs(goalState.position.y-r.position.y);
						right.setHeuristic( submarineManhattanDistance+findAgentsLeft(r.agents));
					}
					else {
						if(agentMaxHealth!=null)
							right.setHeuristic( getHeuristic2(right));
					}
					
				}
			}
			if(strategy.equals("UC")||strategy.equals("AS1")||strategy.equals("AS2")) {
				right.setPathCost(pathCost(right)+node.pathCost);
			}
			right.setCostAndHeuristic(right.heuristic+right.pathCost);
			expandedNodes.add(right);
		}
		
		if(grid[node.getState().getPosition().x][node.getState().getPosition().y] != null) {
			
			///////////////////   CARRY  ////////////////////////
			if(grid[node.getState().getPosition().x][node.getState().getPosition().y].getState().hasAgent ) {
				c.setPosition(new Position(node.getState().getPosition().x,node.getState().getPosition().y));
				c.setHasAgent(false);
				c.setSubmarine(false);
				c.setAgents(reducedHealthAgents);
				carry.setState(c);
				carry.setOperator("carry");
				carry.setParentNode(node);
				carry.setDepth(node.getDepth()+1);
				if(strategy.equals("GR1")||strategy.equals("AS1")) {
					if(agentsCollectedSoFar<truckSize ) {
						carry.setHeuristic(0);
						int indexOfAgent = findAgentCarried(carry.state.agents,carry.state.position);
						if(indexOfAgent>=0) {
							finalHealth[indexOfAgent] = carry.state.agents[indexOfAgent].healthLevel;
							carry.state.agents[indexOfAgent] = null;
						}
						carry.state.setAgentsLeftSoFar(findAgentsLeft(carry.state.agents));
					}
					else {
						carry.setHeuristic(getHeuristic1(carry));
						carry.state.setAgentsLeftSoFar(findAgentsLeft(carry.state.agents));
					}
				}
				if(strategy.equals("GR2")||strategy.equals("AS2")) {
					if(agentsCollectedSoFar<truckSize ) {
						carry.setHeuristic(0);
						int indexOfAgent = findAgentCarried(carry.state.agents,carry.state.position);
						if(indexOfAgent>=0) {
							finalHealth[indexOfAgent] = carry.state.agents[indexOfAgent].healthLevel;
							carry.state.agents[indexOfAgent] = null;
						}
						carry.state.setAgentsLeftSoFar(findAgentsLeft(carry.state.agents));
					}
					else {
						carry.setHeuristic(getHeuristic2(carry));
						carry.state.setAgentsLeftSoFar(findAgentsLeft(carry.state.agents));
					}
					
				}
				if(strategy.equals("UC")||strategy.equals("AS1")||strategy.equals("AS2")) {
					if(agentsCollectedSoFar<truckSize) {
						int indexOfAgent = findAgentCarried(carry.state.agents,carry.state.position);
						carry.setPathCost(0+node.pathCost);
						if(indexOfAgent>=0) {
							finalHealth[indexOfAgent] = carry.state.agents[indexOfAgent].healthLevel;
							carry.state.agents[indexOfAgent] = null;
						}
						carry.state.setAgentsLeftSoFar(findAgentsLeft(carry.state.agents));
					}
					else {
						carry.setPathCost(pathCost(carry)+node.pathCost);
						carry.state.setAgentsLeftSoFar(findAgentsLeft(carry.state.agents));
					}
				}
				
				else {
					if(agentsCollectedSoFar<truckSize) {
						for(int i = 0;i<carry.state.agents.length;i++) {
							if(carry.state.agents[i]!= null) {
								if(carry.state.agents[i].position.x == carry.state.position.x && carry.state.agents[i].position.y == carry.state.position.y)
								{
									finalHealth[i] = carry.state.agents[i].healthLevel;
									carry.state.agents[i] = null;
								}
							}
							
						}
					}
					carry.state.setAgentsLeftSoFar(findAgentsLeft(carry.state.agents));
				}
				carry.setCostAndHeuristic(carry.heuristic+carry.pathCost);
				expandedNodes.add(carry);
			}
			
			////////////////////   DROP  ////////////////////////
			if(grid[node.getState().getPosition().x][node.getState().getPosition().y].getState().isSubmarine && agentsCollectedSoFar>0) {
				dr.setPosition(new Position(node.getState().getPosition().x,node.getState().getPosition().y));
				dr.setHasAgent(false);
				dr.setSubmarine(true);
				dr.setAgents(reducedHealthAgents);
				dr.setAgentsLeftSoFar(node.getState().getAgentsLeftSoFar() - agentsCollectedSoFar);
				drop.setState(dr);
				drop.setOperator("drop");
				drop.setParentNode(node);
				drop.setDepth(node.getDepth()+1);
				if(strategy.equals("GR1")||strategy.equals("AS1")) {
					if(truckSize == agentsCollectedSoFar) {
						if(node.getState().getAgentsLeftSoFar() == 0 )
							drop.setHeuristic(0);
						else {
							drop.setHeuristic(1 );
						}
					}
					else {
						drop.setHeuristic(getHeuristic1(drop));
					}
				}
				if(strategy.equals("GR2")||strategy.equals("AS2")) {
					
					if(truckSize == agentsCollectedSoFar) {
						if(node.getState().getAgentsLeftSoFar() == 0 )
							drop.setHeuristic(0);
						else {
							drop.setHeuristic(1 );
						}
					}
					else {
						drop.setHeuristic(getHeuristic2(drop));
					}
				}
				if(strategy.equals("UC")||strategy.equals("AS1")||strategy.equals("AS2")) {
					if(truckSize == agentsCollectedSoFar) {
						if(node.getState().getAgentsLeftSoFar() == 0 )
							drop.setPathCost(0+node.pathCost);
						else
							drop.setPathCost(1+node.pathCost);
					}
					else
						drop.setPathCost(pathCost(drop)+node.pathCost);
				}
				drop.setCostAndHeuristic(drop.heuristic+drop.pathCost);
				expandedNodes.add(drop);
			}
		}
		
			
			return expandedNodes;
	}
	
	public ArrayList<MissionImpossibleNode> orderedInsert(ArrayList<MissionImpossibleNode> array,MissionImpossibleNode node){
		if(array.isEmpty()) {
			array.add(node);
			return array;
		}
		else {
			boolean added = false;
			for(int j = 0;j<array.size();j++) {
				MissionImpossibleNode elementAtj = array.remove(j);
				if(node.pathCost<=elementAtj.pathCost) {
					array.add(j,node);
					array.add(j+1,elementAtj);
					added = true;
					break;
					
				}
				array.add(j,elementAtj);
			}
			if(!added)
				array.add(node);
			
				
		}
		return array;
		
	}
	
	public ArrayList<MissionImpossibleNode> orderedInsertHeuristic(ArrayList<MissionImpossibleNode> array,MissionImpossibleNode node){
		if(array.isEmpty()) {
			array.add(node);
			return array;
		}
		else {
			boolean added = false;
			for(int j = 0;j<array.size();j++) {
				MissionImpossibleNode elementAtj = array.remove(j);
				if(node.heuristic<=elementAtj.heuristic) {
					array.add(j,node);
					array.add(j+1,elementAtj);
					added=true;
					break;
					
				}
				if(!added)
					array.add(j,elementAtj);
			}
			
			if(!added)
				array.add(node);
		}
		return array;
		
	}
		
	
	public ArrayList<MissionImpossibleNode> orderedInsertAS(ArrayList<MissionImpossibleNode> array,MissionImpossibleNode node){
		if(array.isEmpty()) {
			array.add(node);
			return array;
		}
		else {
			boolean added = false;
			for(int j = 0;j<array.size();j++) {
				MissionImpossibleNode elementAtj = array.remove(j);
				if(node.costAndHeuristic<=elementAtj.costAndHeuristic) {
					array.add(j,node);
					array.add(j+1,elementAtj);
					added=true;
					break;
					
				}
					
				if(!added)
					array.add(j,elementAtj);
			}
			
			if(!added)
				array.add(node);
		}
		return array;
		
	}
		
	public int findIndexAgentWithMaxHealth(Agent[] agentsArray) {
		int maxHealth = -9999;
		int index = 0;
		for(int i = 0;i<agentsArray.length;i++) {
			if(agentsArray[i]!=null) {
				if(agentsArray[i].healthLevel>maxHealth) {
					index = i;
					maxHealth = agentsArray[i].healthLevel;
				}
			}
		}
		return index;
	}
	
	public static int findAgentsLeft(Agent[] agents) {
		int x = 0;
		for(int i = 0;i<agents.length;i++) {
			if(agents[i] != null)
				x++;
		}
		
		return x;
	}
	
	public Agent[] reduceHealth(Agent[] agents) {
		Agent[] res = new Agent[agents.length];
		for(int i = 0;i<agents.length;i++) {
			if(agents[i]!=null)
				if(agents[i].healthLevel<99)
					agents[i].healthLevel+=2;
		}
		res = agents.clone();
		return res;
	}
	
	
	public int[] getMinDistanceToAgents(Agent[] agents,Position pos) {
		int minDistance = 99999;
		int minIndex = 0;
		for(int i = 0;i<agents.length;i++) {
			if(agents[i]!=null) {
				int manhattanDistance = Math.abs(agents[i].position.x-pos.x) + Math.abs(agents[i].position.y-pos.y);
				if(manhattanDistance<minDistance){
					minDistance = manhattanDistance;
					minIndex = i;
				}
			}
		}
		int[] disIndex = {minDistance,minIndex};
		return disIndex;
	}
	public MissionImpossibleNode iterativeSearch(int truckSize) {
		grid = MissionImpossible.grid2D.clone();
		return generalSearchProblem("ID",truckSize);
	}
	public MissionImpossibleNode depthSearch(int truckSize) {
		grid = MissionImpossible.grid2D.clone();
		return generalSearchProblem ("DF",truckSize);
	}
	
	public MissionImpossibleNode breadthFirst(int truckSize) {
		grid = MissionImpossible.grid2D.clone();
		return generalSearchProblem ("BF",truckSize);
	}
	
	public MissionImpossibleNode uniformCostSearch(int truckSize) {
		grid = MissionImpossible.grid2D.clone();
		return generalSearchProblem ("UC",truckSize);
	}
	
	@Override
	
	public MissionImpossibleNode greedy1(int truckSize) {
		return bestFirst(truckSize,"GR1");
	}
	
	@Override
	public MissionImpossibleNode greedy2(int truckSize) {
		return bestFirst(truckSize,"GR2");
	}
	
	public MissionImpossibleNode as1(int truckSize) {
		return bestFirst(truckSize,"AS1");
	}
	
	public MissionImpossibleNode as2(int truckSize) {
		return bestFirst(truckSize,"AS2");
	}
	
	@Override
	public MissionImpossibleNode bestFirst(int truckSize,String strategy) {
		grid = MissionImpossible.grid2D.clone();
		return generalSearchProblem (strategy,truckSize);
	}
	
	public static String getPath(Node goalNode) {
		String path = "";
		if(goalNode==null) 
			return  "no solution";
		else {
			Node n = goalNode;
			while(n!=null) {
				if(!n.operator.equals("start"))
					path = n.operator +","+ path;
					n = n.parentNode;
			}
			path = path.substring(0, path.length()-1);
			int dead = 0;
			for(int z :finalHealth)
				if(z == 100)
					dead++;
			path = path + ";"+dead+ ";" ;
			for(int i : finalHealth) {
				path = path + i + ",";
			}
			path = path.substring(0,path.length()-1);
			path = path + ";" + nodesExpanded;
		}
		return path;
	}
	
	public static int findAgentCarried(Agent[] agents,Position pos) {
		for(int i = 0;i<agents.length;i++) {
			if(agents[i]!=null) {
				if(agents[i].position.x  == pos.x && agents[i].position.y == pos.y) {
					return i;
				}
			}
		}
		return -1;
	}

	public static int getHeuristic2(MissionImpossibleNode node) {
		int agentEuclideanDistance = 0;
		int totalDamage = 0;
		int [] healthAfterReaching = new int [agents.length];
		for(int i = 0 ; i < agents.length; i++) { // to get the health of agents after ethan reaches them
			agentEuclideanDistance = Math.abs(node.state.position.x-agents[i].position.x) + Math.abs(node.state.position.y-agents[i].position.y);
			healthAfterReaching[i] = 2*agentEuclideanDistance+agents[i].healthLevel;
		}
		int max = 0;
		int previousMax = 101;
		int index = 0;
		boolean [] agentsTaken = new boolean[agents.length];
		Agent [] agentsTakenIntoConsideration;
		if(agents.length>truckSize)  // if the agents left is less than the truck size
			agentsTakenIntoConsideration = new Agent [agents.length]; 
		else
			agentsTakenIntoConsideration = new Agent [truckSize];
		
		for(int j= 0;j<agentsTakenIntoConsideration.length;j++) {  // get the (truckSize) agents with the highest hp to take them into consideration while calculating the damage
			for(int i =0;i<healthAfterReaching.length;i++) {
				if(healthAfterReaching[i] > max && healthAfterReaching[i] <=previousMax && !agentsTaken[i] && healthAfterReaching[i]<100) {
					max = healthAfterReaching[i];
					index = i;
				}
			}
			agentsTakenIntoConsideration[j] = agents[index];
			agentsTaken[index] = true;
			previousMax = max;
			max= 0;
		}
		for(int i =0 ; i<agentsTakenIntoConsideration.length;i++) {  // if there are agents dead in the agents array and there is place left in the agents taken into consideration array
			if(agentsTakenIntoConsideration[i] == null)
				for(int j= 0;j<agentsTaken.length; j++) {
					if(agentsTaken[j] == false);
					agentsTakenIntoConsideration[i] = agents[j];
					agentsTaken[j] = true;
				}
		}
		for(int i =0; i<truckSize; i++) {  // calculate the damage taken by the agents taken into consideration
			agentEuclideanDistance = (int) Math.floor(Math.pow(Math.pow((node.state.position.x-agentsTakenIntoConsideration[i].position.x),2) + Math.pow((node.state.position.y-agentsTakenIntoConsideration[i].position.y),2),0.5));
			totalDamage += (2 *agentEuclideanDistance)* Math.pow(0.5, Math.ceil((100 - agentsTakenIntoConsideration[i].healthLevel)/10));;
		}
		return totalDamage;
	}
	
	public static int pathCost(MissionImpossibleNode node) {
		int health=0;
		int agentManhattenDistance = 0;
		int totalDamage = 0;
		int [] healthAfterReaching = new int [agents.length];
		for(int i = 0 ; i < agents.length; i++) {
			agentManhattenDistance = Math.abs(node.state.position.x-agents[i].position.x) + Math.abs(node.state.position.y-agents[i].position.y);
			healthAfterReaching[i] = 2*agentManhattenDistance+agents[i].healthLevel;
		}
		for(int i = 0; i<agents.length ; i++) {
			health = agents[i].healthLevel;	
			agentManhattenDistance = Math.abs(node.state.position.x-agents[i].position.x) + Math.abs(node.state.position.y-agents[i].position.y);
			if(healthAfterReaching[i]>=100)
				totalDamage += (agentManhattenDistance * 2);
			else
				totalDamage += (agentManhattenDistance * 2) * Math.pow(0.5, Math.ceil((100 - health)/10));
		}
		return totalDamage;
	}
	
	public static int getHeuristic1(MissionImpossibleNode node) {
		int health=0;
		int agentEuclideanDistance = 0;
		int totalDamage = 0;
		int []healthAfterReaching = new int[agents.length];
		for(int i = 0 ; i < agents.length; i++) {
			agentEuclideanDistance = Math.abs(node.state.position.x-agents[i].position.x) + Math.abs(node.state.position.y-agents[i].position.y);
			healthAfterReaching[i] = 2*agentEuclideanDistance+agents[i].healthLevel;
		}
		for(int i = 0; i<agents.length ; i++) {
			health = agents[i].healthLevel;	
			agentEuclideanDistance = (int) Math.floor(Math.pow(Math.pow((node.state.position.x-agents[i].position.x),2) + Math.pow((node.state.position.y-agents[i].position.y),2),0.5));
			if(healthAfterReaching[i]>=100)
				totalDamage += (agentEuclideanDistance * 2);
			else
				totalDamage += (agentEuclideanDistance * 2) * Math.pow(0.5, Math.ceil((100 - health)/10));
		}
		return totalDamage;
	}
	

	public static void visualize(String path) {
		boolean taken = false;
		StringTokenizer st = new StringTokenizer(path, ";");
		String steps = st.nextToken(";");
		StringTokenizer st1 = new StringTokenizer(steps, ",");
		String currentStep = "";
		Position currentEthanPosition = new Position(ethanPosition.x , ethanPosition.y);
		Position[] currentAgentsPosition = new Position[agentsPosition.length];
		boolean printed = false;
		for(int i=0;i<agentsPosition.length;i++) {
			System.out.println(agentsPosition[i]);
		}
		System.out.println("steps : "+ steps);
		for(int i = 0; i<currentAgentsPosition.length;i++) {
			currentAgentsPosition[i] = new Position(agentsPosition[i].x , agentsPosition[i].y);
		}
		while(st1.hasMoreTokens()) {
			printed = false;
			currentStep = st1.nextToken();
			if(currentStep.equals("up"))
				currentEthanPosition.x =  currentEthanPosition.x -1;
			if(currentStep.equals("down"))
				currentEthanPosition.x =  currentEthanPosition.x +1;
			if(currentStep.equals("left"))
				currentEthanPosition.y =  currentEthanPosition.y -1;
			if(currentStep.equals("right"))
				currentEthanPosition.y =  currentEthanPosition.y +1;
			
			System.out.println(currentEthanPosition.x + " , " + currentEthanPosition.y );	
		for(int i = 0;i<xSizeOfGrid;i++) {
			for(int j = 0;j<ySizeOfGrid;j++) {
				taken = false;
				
				if(i == currentEthanPosition.x) {
					if(j == currentEthanPosition.y) {
						
						if(i == submarinePosition.x) { //if it is submarine's position
							if(j == submarinePosition.y) {
								System.out.print("ES |");
								printed = true;
							}
						}
						
						for(int k = 0;k<currentAgentsPosition.length ; k++) { //if it is an agent's position
							if(currentAgentsPosition[k] != null) {
							if(i == currentAgentsPosition[k].x) {
								if(j == currentAgentsPosition[k].y) {
									if(currentStep.equals("carry")) {
										currentAgentsPosition[k] = null;
										System.out.print(" E |");
										printed = true;
									}
									else {
									System.out.print("EA |");
									printed = true;
									}
								}}}}
						
						if(!printed) //if the position is free
						System.out.print(" E |");
						taken = true;
					}
				}
				
				if(i == submarinePosition.x) {  // for submarine
					if(j == submarinePosition.y) {
						if(!taken) {
						System.out.print(" S |");
						taken = true;
						}
					}
				}
				
				for(int k = 0;k<currentAgentsPosition.length ; k++) { // for the agents
					
					if(currentAgentsPosition[k] != null) {
					if(i == currentAgentsPosition[k].x) {
						if(j == currentAgentsPosition[k].y) {
							if(currentStep.equals("carry")) {
							}
							else {
								if(!taken) {
							System.out.print(" A |");
							taken = true;
								}
						}
						}
					}
				}
				}
				if(!taken) {
					System.out.print("---|");
				}
			}
			System.out.println("");
		}
		}
	}
	

	
	
}
