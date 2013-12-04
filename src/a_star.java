import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;
import java.lang.Math;


public class a_star {

	private static int count;
	private static int eucLvl;
	private static int taxiLvl;
	private static int chessLvl;

	public static void main(String[] args) throws IOException {

		File inputFile = new File("input.txt");
		Scanner reader;
		PrintWriter out = new PrintWriter(new FileWriter("output.txt"));
		LinkedList<node> frontier = new LinkedList<node>();
		LinkedList<node> searched = new LinkedList<node>();
		Stack<Integer> xEucStack = new Stack<Integer>();
		Stack<Integer> yEucStack = new Stack<Integer>();
		Stack<Integer> xChessStack = new Stack<Integer>();
		Stack<Integer> yChessStack = new Stack<Integer>();
		Stack<Integer> xTaxiStack = new Stack<Integer>();
		Stack<Integer> yTaxiStack = new Stack<Integer>();
		Stack<Integer> xI1Stack = new Stack<Integer>();
		Stack<Integer> yI1Stack = new Stack<Integer>();
		Stack<Integer> xI2Stack = new Stack<Integer>();
		Stack<Integer> yI2Stack = new Stack<Integer>();
		Stack<Integer> xI3Stack = new Stack<Integer>();
		Stack<Integer> yI3Stack = new Stack<Integer>();

		int inputCount = 1;


		int gridSize, aXRoot, aYRoot, bXRoot, bYRoot;
		try {
			reader = new Scanner(inputFile);
		} catch (FileNotFoundException e) {
			System.out.println("Problem finding input.txt, check location and try again.");
			return;
		}

		out.println("\t \t Nodes Expanded");
		out.println("\t \t Euclicean \t Manhattan \t Chessboard");

		while (reader.hasNextInt()){
			xEucStack.clear();
			yEucStack.clear();
			xChessStack.clear();
			yChessStack.clear();
			xTaxiStack.clear();
			yTaxiStack.clear();
			//frontier.clear();

			out.print("Input" + inputCount + "\t");

			gridSize = reader.nextInt();
			aXRoot = reader.nextInt(); 
			aYRoot = reader.nextInt();
			bXRoot = reader.nextInt(); 
			bYRoot = reader.nextInt();

			//System.out.println("start euc" + inputCount);
			frontier.clear();
			searched.clear();
			//xEucStack.clear();
			//yEucStack.clear();
			eucLvl = 0;
			node Root = new node(aXRoot, aYRoot, bXRoot, bYRoot, 9999, 0, null);
			frontier.add(Root);

			if (eucSearch(frontier, searched, gridSize, out, xEucStack, yEucStack) == false){
				out.print("  -  			");
				xEucStack.push(-1);
			} else {
				out.print("  " + eucLvl + " 			");
			}
			

			
			//System.out.println("start Taxi" + inputCount);
			frontier.clear();
			searched.clear();
			//xTaxiStack.clear();
			//yTaxiStack.clear();
			taxiLvl = 0;
			node Root2 = new node(aXRoot, aYRoot, bXRoot, bYRoot, 9999, 0, null);
			frontier.add(Root2);

			if (taxiSearch(frontier, searched, gridSize, out, xTaxiStack, yTaxiStack) == false){
				out.print("-  			");
				xTaxiStack.push(-1);
			} else {
				out.print(taxiLvl + " 			");
			}
			
			//System.out.println("start chess" + inputCount);
			frontier.clear();
			searched.clear();
			//xChessStack.clear();
			//yChessStack.clear();
			chessLvl = 0;
			node Root3 = new node(aXRoot, aYRoot, bXRoot, bYRoot, 9999, 0, null);
			frontier.add(Root3);

			if (chessSearch(frontier, searched, gridSize, out, xChessStack, yChessStack) == false){
				out.print("-  \n");
				xChessStack.push(-1);
			} else {
				out.print(chessLvl + "\n");
			}

			int min = 100;
			int answer = -1;
			int[] data = {eucLvl, taxiLvl, chessLvl};

			for (int i = 0; i < 3; i++){
				if (data[i] > 0 && data[i] < min){
					min = data[i];
					answer = i;
				}
			}
			//System.out.println(inputCount);
			switch (answer){
			case 0:
				switch (inputCount){
				case 1:
					xI1Stack.addAll(xEucStack);
					yI1Stack.addAll(yEucStack);
					break;
				case 2:
					xI2Stack.addAll(xEucStack);
					yI2Stack.addAll(yEucStack);
					break;
				case 3:
					xI3Stack.addAll(xEucStack);
					yI3Stack.addAll(yEucStack);
					break;
				}
				break;
			case 1:
				switch (inputCount){
				case 1:
					xI1Stack.addAll(xTaxiStack);
					yI1Stack.addAll(yTaxiStack);
					break;
				case 2:
					xI2Stack.addAll(xTaxiStack);
					yI2Stack.addAll(yTaxiStack);
					break;
				case 3:
					xI3Stack.addAll(xTaxiStack);
					yI3Stack.addAll(yTaxiStack);
					break;
				}
				break;
			case 2:
				switch (inputCount){
				case 1:
					xI1Stack.addAll(xChessStack);
					yI1Stack.addAll(yChessStack);
					break;
				case 2:
					xI2Stack.addAll(xChessStack);
					yI2Stack.addAll(yChessStack);
					break;
				case 3:
					xI3Stack.addAll(xChessStack);
					yI3Stack.addAll(yChessStack);
					break;
				}
				break;
			default:
				switch (inputCount){
				case 1:
					xI1Stack.addAll(xEucStack);
					yI1Stack.addAll(yEucStack);
					break;
				case 2:
					xI2Stack.addAll(xEucStack);
					yI2Stack.addAll(yEucStack);
					break;
				case 3:
					xI3Stack.addAll(xEucStack);
					yI3Stack.addAll(yEucStack);
					break;
				}
				break;
			}

			inputCount += 1;
		}

		out.println("\n \n");
		out.println("Optimal Path Solution");
		out.print("Input1: ");
		if (xI1Stack.empty()){
			out.print("No input");
		} else {
			if (xI1Stack.peek() == -1){
				out.print(-1);
			} else {
				while (xI1Stack.empty() == false){
					out.print(xI1Stack.pop());
					out.print(",");
					out.print(yI1Stack.pop() + "   ");
				}
			}
		}
		out.print("\n");

		out.print("Input2: ");
		if (xI2Stack.empty()){
			out.print("No input");
		} else {
			if (xI2Stack.peek() == -1){
				out.print(-1);
			} else {
				while (xI2Stack.empty() == false){
					out.print(xI2Stack.pop());
					out.print(",");
					out.print(yI2Stack.pop() + "   ");
				}
			}
		}
		out.print("\n");

		out.print("Input3: ");
		if (xI3Stack.empty()){
			out.print("No input");
		} else {
			if (xI3Stack.peek() == -1){
				out.print(-1);
			} else {
				while (xI3Stack.empty() == false){
					out.print(xI3Stack.pop());
					out.print(",");
					out.print(yI3Stack.pop() + "   ");
				}
			}
		}
		out.print("\n");

		out.close();
	}

	public static Boolean eucSearch(LinkedList<node> toSearch, LinkedList<node> searched, int gridSize, PrintWriter out, Stack<Integer> xStack, Stack<Integer> yStack){
		count += 1;
		Boolean loopFlag = true;

		if (count > 1000){
			System.out.println("Search too broad");
			return false;
		}

		if (toSearch.isEmpty()){
			return false;
		}

		node currentNode = toSearch.pop();
		searched.add(currentNode);
		//System.out.println(currentNode.aX);
		//System.out.println(currentNode.aY);
		//System.out.println(currentNode.bX);
		//System.out.println(currentNode.bY);

		if (currentNode.aX == currentNode.bX && currentNode.aY == currentNode.bY){
			//WSystem.out.println("Found it!");
			xStack.push(currentNode.aX);
			yStack.push(currentNode.aY);
			eucLvl = currentNode.level;
			while (currentNode.parent != null){
				currentNode = currentNode.parent;
				xStack.push(currentNode.aX);
				yStack.push(currentNode.aY);
			}
			//toSearch.clear();
			return true;
		}

		if (currentNode.aX < gridSize && currentNode.bY > 1){
			//double thisDist = Math.sqrt(Math.pow( Math.abs(currentNode.aX - currentNode.bX) ,2)+ Math.pow( Math.abs(currentNode.aY - currentNode.bY) ,2));
			//System.out.println(thisDist);
			double nextDist = Math.sqrt(Math.pow( Math.abs((currentNode.aX + 1) - currentNode.bX) ,2)+ Math.pow( Math.abs(currentNode.aY - (currentNode.bY - 1)) ,2));
			//System.out.println(nextDist);
			node NewChild = new node(currentNode.aX + 1, currentNode.aY, currentNode.bX, currentNode.bY - 1, nextDist + currentNode.fValue, currentNode.level + 1, currentNode);
			for (int z = 0; z < searched.size(); z++){
				if (searched.get(z).aX == NewChild.aX && searched.get(z).bX == NewChild.bX && searched.get(z).aY == NewChild.aY && searched.get(z).bY == NewChild.bY ){
					loopFlag = false;
				}
			}
			if (loopFlag){
				toSearch.add(NewChild);
			}
		}
		
		loopFlag = true;

		if (currentNode.aY < gridSize && currentNode.bX < gridSize){
			//double thisDist = Math.sqrt(Math.pow( Math.abs(currentNode.aX - currentNode.bX) ,2)+ Math.pow( Math.abs(currentNode.aY - currentNode.bY) ,2));
			//System.out.println(thisDist);
			double nextDist = Math.sqrt(Math.pow( Math.abs(currentNode.aX - (currentNode.bX + 1)) ,2)+ Math.pow( Math.abs((currentNode.aY + 1) - currentNode.bY) ,2));
			//System.out.println(nextDist);
			node NewChild = new node(currentNode.aX, currentNode.aY + 1, currentNode.bX + 1, currentNode.bY, nextDist + currentNode.fValue, currentNode.level + 1, currentNode);
			for (int z = 0; z < searched.size(); z++){
				if (searched.get(z).aX == NewChild.aX && searched.get(z).bX == NewChild.bX && searched.get(z).aY == NewChild.aY && searched.get(z).bY == NewChild.bY ){
					loopFlag = false;
				}
			}
			if (loopFlag){
				toSearch.add(NewChild);
			}
		}
		
		loopFlag = true;

		if (currentNode.aX > 1 && currentNode.bY < gridSize){
			//double thisDist = Math.sqrt(Math.pow( Math.abs(currentNode.aX - currentNode.bX) ,2)+ Math.pow( Math.abs(currentNode.aY - currentNode.bY) ,2));
			//System.out.println(thisDist);
			double nextDist = Math.sqrt(Math.pow( Math.abs((currentNode.aX - 1) - currentNode.bX) ,2)+ Math.pow( Math.abs(currentNode.aY - (currentNode.bY + 1)) ,2));
			//System.out.println(nextDist);
			node NewChild = new node(currentNode.aX - 1, currentNode.aY, currentNode.bX, currentNode.bY + 1, nextDist + currentNode.fValue, currentNode.level + 1, currentNode);
			for (int z = 0; z < searched.size(); z++){
				if (searched.get(z).aX == NewChild.aX && searched.get(z).bX == NewChild.bX && searched.get(z).aY == NewChild.aY && searched.get(z).bY == NewChild.bY ){
					loopFlag = false;
				}
			}
			if (loopFlag){
				toSearch.add(NewChild);
			}
		}
		
		loopFlag = true;

		if (currentNode.aY > 1 && currentNode.bX > 1){
			//double thisDist = Math.sqrt(Math.pow( Math.abs(currentNode.aX - currentNode.bX) ,2)+ Math.pow( Math.abs(currentNode.aY - currentNode.bY) ,2));
			//System.out.println(thisDist);
			double nextDist = Math.sqrt(Math.pow( Math.abs(currentNode.aX - (currentNode.bX + 1)) ,2)+ Math.pow( Math.abs((currentNode.aY + 1) - currentNode.bY) ,2));
			//System.out.println(nextDist);
			node NewChild = new node(currentNode.aX, currentNode.aY - 1, currentNode.bX - 1, currentNode.bY, nextDist + currentNode.fValue, currentNode.level + 1, currentNode);
			for (int z = 0; z < searched.size(); z++){
				if (searched.get(z).aX == NewChild.aX && searched.get(z).bX == NewChild.bX && searched.get(z).aY == NewChild.aY && searched.get(z).bY == NewChild.bY ){
					loopFlag = false;
				}
			}
			if (loopFlag){
				toSearch.add(NewChild);
			}
		}

		sortlist(toSearch);

		if (eucSearch(toSearch, searched, gridSize, out, xStack, yStack) == true){
			return true;
		}


		return false;
	}

	public static Boolean chessSearch(LinkedList<node> toSearch, LinkedList<node> searched, int gridSize, PrintWriter out, Stack<Integer> xStack, Stack<Integer> yStack){
		count += 1;
		Boolean loopFlag = true;

		if (count > 1000){
			System.out.println("Search too broad");
			return false;
		}

		if (toSearch.isEmpty()){
			return false;
		}

		node currentNode = toSearch.pop();
		searched.add(currentNode);
		//System.out.println(currentNode.aX);
		//System.out.println(currentNode.aY);
		//System.out.println(currentNode.bX);
		//System.out.println(currentNode.bY);

		if (currentNode.aX == currentNode.bX && currentNode.aY == currentNode.bY){
			//System.out.println("Found it!");
			xStack.push(currentNode.aX);
			yStack.push(currentNode.aY);
			chessLvl = currentNode.level;
			while (currentNode.parent != null){
				currentNode = currentNode.parent;
				xStack.push(currentNode.aX);
				yStack.push(currentNode.aY);
			}
			return true;
		}

		if (currentNode.aX < gridSize && currentNode.bY > 1){
			//double thisDist = Math.max(Math.abs(currentNode.aX - currentNode.bX), Math.abs(currentNode.aY - currentNode.bY));
			//System.out.println(thisDist);
			double nextDist = Math.max(Math.abs((currentNode.aX + 1) - currentNode.bX), Math.abs(currentNode.aY - (currentNode.bY - 1)));
			//System.out.println(nextDist);
			node NewChild = new node(currentNode.aX + 1, currentNode.aY, currentNode.bX, currentNode.bY - 1, nextDist + currentNode.fValue, currentNode.level + 1, currentNode);
			for (int z = 0; z < searched.size(); z++){
				if (searched.get(z).aX == NewChild.aX && searched.get(z).bX == NewChild.bX && searched.get(z).aY == NewChild.aY && searched.get(z).bY == NewChild.bY ){
					loopFlag = false;
				}
			}
			if (loopFlag){
				toSearch.add(NewChild);
			}
		}
		
		loopFlag = true;

		if (currentNode.aY < gridSize && currentNode.bX < gridSize){
			//double thisDist = Math.max(Math.abs(currentNode.aX - currentNode.bX), Math.abs(currentNode.aY - currentNode.bY));
			//System.out.println(thisDist);
			double nextDist = Math.max(Math.abs(currentNode.aX - (currentNode.bX + 1)), Math.abs((currentNode.aY + 1) - currentNode.bY));
			//System.out.println(nextDist);
			node NewChild = new node(currentNode.aX, currentNode.aY + 1, currentNode.bX + 1, currentNode.bY, nextDist + currentNode.fValue, currentNode.level + 1, currentNode);
			for (int z = 0; z < searched.size(); z++){
				if (searched.get(z).aX == NewChild.aX && searched.get(z).bX == NewChild.bX && searched.get(z).aY == NewChild.aY && searched.get(z).bY == NewChild.bY ){
					loopFlag = false;
				}
			}
			if (loopFlag){
				toSearch.add(NewChild);
			}
		}
		
		loopFlag = true;

		if (currentNode.aX > 1 && currentNode.bY < gridSize){
			//double thisDist = Math.max(Math.abs(currentNode.aX - currentNode.bX), Math.abs(currentNode.aY - currentNode.bY));
			//System.out.println(thisDist);
			double nextDist = Math.max(Math.abs((currentNode.aX - 1) - currentNode.bX), Math.abs(currentNode.aY - (currentNode.bY + 1)));
			//System.out.println(nextDist);
			node NewChild = new node(currentNode.aX - 1, currentNode.aY, currentNode.bX, currentNode.bY + 1, nextDist + currentNode.fValue, currentNode.level + 1, currentNode);
			for (int z = 0; z < searched.size(); z++){
				if (searched.get(z).aX == NewChild.aX && searched.get(z).bX == NewChild.bX && searched.get(z).aY == NewChild.aY && searched.get(z).bY == NewChild.bY ){
					loopFlag = false;
				}
			}
			if (loopFlag){
				toSearch.add(NewChild);
			}
		}
		
		loopFlag = true;

		if (currentNode.aY > 1 && currentNode.bX > 1){
			//double thisDist = Math.max(Math.abs(currentNode.aX - currentNode.bX), Math.abs(currentNode.aY - currentNode.bY));
			//System.out.println(thisDist);
			double nextDist = Math.max(Math.abs(currentNode.aX - (currentNode.bX + 1)), Math.abs((currentNode.aY + 1) - currentNode.bY));
			//System.out.println(nextDist);
			node NewChild = new node(currentNode.aX, currentNode.aY - 1, currentNode.bX - 1, currentNode.bY, nextDist + currentNode.fValue, currentNode.level + 1, currentNode);
			for (int z = 0; z < searched.size(); z++){
				if (searched.get(z).aX == NewChild.aX && searched.get(z).bX == NewChild.bX && searched.get(z).aY == NewChild.aY && searched.get(z).bY == NewChild.bY ){
					loopFlag = false;
				}
			}
			if (loopFlag){
				toSearch.add(NewChild);
			}
		}

		sortlist(toSearch);

		if (chessSearch(toSearch, searched, gridSize, out, xStack, yStack) == true){
			return true;
		}


		return false;
	}

	public static Boolean taxiSearch(LinkedList<node> toSearch, LinkedList<node> searched, int gridSize, PrintWriter out, Stack<Integer> xStack, Stack<Integer> yStack){
		count += 1;
		Boolean loopFlag = true;

		if (count > 1000){
			System.out.println("Search too broad");
			return false;
		}

		if (toSearch.isEmpty()){
			return false;
		}

		node currentNode = toSearch.pop();
		searched.add(currentNode);
		//System.out.println(currentNode.aX);
		//System.out.println(currentNode.aY);
		//System.out.println(currentNode.bX);
		//System.out.println(currentNode.bY);

		if (currentNode.aX == currentNode.bX && currentNode.aY == currentNode.bY){
			//WSystem.out.println("Found it!");
			xStack.push(currentNode.aX);
			yStack.push(currentNode.aY);
			taxiLvl = currentNode.level;
			while (currentNode.parent != null){
				currentNode = currentNode.parent;
				xStack.push(currentNode.aX);
				yStack.push(currentNode.aY);
			}
			return true;
		}

		if (currentNode.aX < gridSize && currentNode.bY > 1){
			//double thisDist = Math.abs(currentNode.aX - currentNode.bX) + Math.abs(currentNode.aY - currentNode.bY);
			//System.out.println(thisDist);
			double nextDist = Math.abs((currentNode.aX + 1) - currentNode.bX) + Math.abs(currentNode.aY - (currentNode.bY - 1));
			//System.out.println(nextDist);
			node NewChild = new node(currentNode.aX + 1, currentNode.aY, currentNode.bX, currentNode.bY - 1, nextDist + currentNode.fValue, currentNode.level + 1, currentNode);
			for (int z = 0; z < searched.size(); z++){
				if (searched.get(z).aX == NewChild.aX && searched.get(z).bX == NewChild.bX && searched.get(z).aY == NewChild.aY && searched.get(z).bY == NewChild.bY ){
					loopFlag = false;
				}
			}
			if (loopFlag){
				toSearch.add(NewChild);
			}
		}
		
		loopFlag = true;

		if (currentNode.aY < gridSize && currentNode.bX < gridSize){
			//double thisDist = Math.abs(currentNode.aX - currentNode.bX) + Math.abs(currentNode.aY - currentNode.bY);
			//System.out.println(thisDist);
			double nextDist = Math.abs(currentNode.aX - (currentNode.bX + 1)) + Math.abs((currentNode.aY + 1) - currentNode.bY);
			//System.out.println(nextDist);
			node NewChild = new node(currentNode.aX, currentNode.aY + 1, currentNode.bX + 1, currentNode.bY, nextDist + currentNode.fValue, currentNode.level + 1, currentNode);
			for (int z = 0; z < searched.size(); z++){
				if (searched.get(z).aX == NewChild.aX && searched.get(z).bX == NewChild.bX && searched.get(z).aY == NewChild.aY && searched.get(z).bY == NewChild.bY ){
					loopFlag = false;
				}
			}
			if (loopFlag){
				toSearch.add(NewChild);
			}
		}
		
		loopFlag = true;

		if (currentNode.aX > 1 && currentNode.bY < gridSize){
			//double thisDist = Math.abs(currentNode.aX - currentNode.bX) + Math.abs(currentNode.aY - currentNode.bY);
			//System.out.println(thisDist);
			double nextDist = Math.abs((currentNode.aX - 1) - currentNode.bX) + Math.abs(currentNode.aY - (currentNode.bY + 1));
			//System.out.println(nextDist);
			node NewChild = new node(currentNode.aX - 1, currentNode.aY, currentNode.bX, currentNode.bY + 1, nextDist + currentNode.fValue, currentNode.level + 1, currentNode);
			for (int z = 0; z < searched.size(); z++){
				if (searched.get(z).aX == NewChild.aX && searched.get(z).bX == NewChild.bX && searched.get(z).aY == NewChild.aY && searched.get(z).bY == NewChild.bY ){
					loopFlag = false;
				}
			}
			if (loopFlag){
				toSearch.add(NewChild);
			}
		}
		
		loopFlag = true;

		if (currentNode.aY > 1 && currentNode.bX > 1){
			//double thisDist = Math.abs(currentNode.aX - currentNode.bX) + Math.abs(currentNode.aY - currentNode.bY);
			//System.out.println(thisDist);
			double nextDist = Math.abs(currentNode.aX - (currentNode.bX + 1)) + Math.abs((currentNode.aY + 1) - currentNode.bY);
			//System.out.println(nextDist);
			node NewChild = new node(currentNode.aX, currentNode.aY - 1, currentNode.bX - 1, currentNode.bY, nextDist + currentNode.fValue, currentNode.level + 1, currentNode);
			for (int z = 0; z < searched.size(); z++){
				if (searched.get(z).aX == NewChild.aX && searched.get(z).bX == NewChild.bX && searched.get(z).aY == NewChild.aY && searched.get(z).bY == NewChild.bY ){
					loopFlag = false;
				}
			}
			if (loopFlag){
				toSearch.add(NewChild);
			}
		}

		sortlist(toSearch);

		if (taxiSearch(toSearch, searched, gridSize, out, xStack, yStack) == true){
			return true;
		}


		return false;
	}

	public static void sortlist(LinkedList<node> list){
		if (!list.isEmpty()){
			for (int i = 0; i < list.size(); i++){
				for (int j = 1; j < list.size() - i ; j++){
					//System.out.println("sorting " + i);
					if (list.get(j).fValue < list.get(j-1).fValue){
						node temp = list.get(j);
						list.set(j, list.get(j-1));
						list.set(j-1, temp);
					}
				}	
			}
		}
	}
}
