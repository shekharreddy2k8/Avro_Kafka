package core;

/**
 * @author Sangala Shekhar Reddy
 *
 */

import java.util.StringTokenizer;

public class ExpresssionEvaluation {

 public static void main2(String[] args) {
	 
	 String json="{\"users\":{ \"name\":\"shekhar\", \"address\":{\"city\":\"bng\",\"street\":\"KR puram\"}}}";
	 
	 //String exps = "((2*5)+(6/2))";
	 String exps = "[\"AND\",[\"EQ\",\"user.name\",\"shekhar\"],[\"EQ\",\"shekhar\",\"shekhar\"]]";
	 exps = exps.replaceAll("\\s+", "");
	 //StringTokenizer tokens = new StringTokenizer(exps, "{}()*/+-", true);
	 StringTokenizer tokens = new StringTokenizer(exps, "[]", true);
		/** remove if any spaces from the expression **/
		
		
		/** we assume that the expression is in valid format **/
	 ExpresssionStack<String> stack = new ExpresssionStack<String>(exps.length());
		/** break the expression into tokens **/
		//StringTokenizer tokens = new StringTokenizer(exps, "{}()*/+-", true);
		while (tokens.hasMoreTokens()) {
			String tkn = tokens.nextToken();
			System.out.println(tkn);
		}
}
	
	
	public static String evaluateExp(String exps) {

		/** remove spaces from the expression **/
		exps = exps.replaceAll("\\s+", "");
		
		/** I assume that the expression is in valid format **/
		ExpresssionStack<String> stack = new ExpresssionStack<String>(exps.length());
		
		/** break the expression into tokens **/
		StringTokenizer tokens = new StringTokenizer(exps, "[]", true);
		while (tokens.hasMoreTokens()) {
			String tkn = tokens.nextToken();
			if (tkn.equals("[") || tkn.equals("EQ") || tkn.equals("GT")|| tkn.equals("LT") || tkn.equals("IN")) {
				stack.push(tkn);
			} else if (tkn.equals("]")) {
				try {
					String exp = stack.pop();
					String oprnd,op1,op2;
					String []opes=null;
					if(exp.contains(",")){
						 opes=exp.replaceAll("\"", "").split(",");	
						 oprnd = opes[0];
						 op1 = opes[1];
						 op2 = opes[2];
					}else{
						opes=exp.replaceAll("\"", "").split(",");
						op2 = opes[0].replaceAll(",", "").replace("\"", "");;
					    op1 = stack.pop().replaceAll("\"", "").replace(",", "");
						oprnd = stack.pop().replaceAll("\"", "").replace(",", "");
					}
					
					if (!stack.isStackEmpty()) {
						stack.pop();
					}

					Boolean result = false;
					if (oprnd.equals("EQ")) {
						result = op1.equals(op2);
					} else if (oprnd.equals("GT")) {
						result = Integer.parseInt(op1) > Integer.parseInt(op2);
					} else if (oprnd.equals("LT")) {
						result = Integer.parseInt(op1) < Integer.parseInt(op2);
					} else if (oprnd.equals("AND")) {
						result = Boolean.valueOf(op1) && Boolean.valueOf(op2);
					} else if (oprnd.equals("OR")) {
						result = Boolean.valueOf(op1) || Boolean.valueOf(op2);
					} 
					stack.push(result+"");
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}else if (!tkn.equals(",")){
				stack.push(tkn);
			}
		}
		
		String finalResult = "";
		try {
			finalResult = stack.pop();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finalResult;
	}

	public static void main1(String a[]) {
		String json="{\"users\": \"address\":{\"city\":\"bng\",\"street\":\"KR puram\"}}";
		
		String expr = "[\"OR\",[\"EQ\",\"shekhar2\",\"shekhar\"],[\"LT\",\"30\",\"20\"]]";
		System.out.println("Expression: " + expr);
		System.out.println("Final Result: " + evaluateExp(expr));
	}
}


class ExpresssionStack<T extends Object> {

	private int stackSize;
	private T[] stackArr;
	private int top;

	/**
	 * constructor to create stack with size
	 * 
	 * @param size
	 */
	@SuppressWarnings("unchecked")
	public ExpresssionStack(int size) {
		this.stackSize = size;
		this.stackArr = (T[]) new Object[stackSize];
		this.top = -1;
	}

	/**
	 * This method adds new entry to the top of the stack
	 * 
	 * @param entry
	 * @throws Exception
	 */
	public void push(T entry) {
		if (this.isStackFull()) {
			System.out.println(("Stack is full. Increasing the capacity."));
			this.increaseStackCapacity();
		}
		System.out.println("Adding: " + entry);
		this.stackArr[++top] = entry;
	}

	/**
	 * This method removes an entry from the top of the stack.
	 * 
	 * @return
	 * @throws Exception
	 */
	public T pop() throws Exception {
		if (this.isStackEmpty()) {
			throw new Exception("Stack is empty. Can not remove element.");
		}
		T entry = this.stackArr[top--];
		System.out.println("Removed entry: " + entry);
		return entry;
	}

	/**
	 * This method returns top of the stack without removing it.
	 * 
	 * @return
	 */
	public T peek() {
		return stackArr[top];
	}

	private void increaseStackCapacity() {

		@SuppressWarnings("unchecked")
		T[] newStack = (T[]) new Object[this.stackSize * 2];
		for (int i = 0; i < stackSize; i++) {
			newStack[i] = this.stackArr[i];
		}
		this.stackArr = newStack;
		this.stackSize = this.stackSize * 2;
	}

	/**
	 * This method returns true if the stack is empty
	 * 
	 * @return
	 */
	public boolean isStackEmpty() {
		return (top == -1);
	}

	/**
	 * This method returns true if the stack is full
	 * 
	 * @return
	 */
	public boolean isStackFull() {
		return (top == stackSize - 1);
	}
}

