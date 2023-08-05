package cmsc256;

import bridges.base.BinTreeElement;
import java.util.ArrayList;
import java.util.Stack;
/*Braeden Ferguson
 *03/31/2023
 *Project05
 *CMSC256-901
 *Creating a tree to parse through an equation to solve it recursively
 */

public class Project5 extends BinTreeElement<String>{

    public static void main(String[] args) {
        try {
            BinTreeElement<String> newTree = buildParseTree("( ( 7.0 * 3.0 ) - ( ( 1.0 * 2.0 ) + ( 1.0 - 1.0 ) ) )");
            System.out.println(evaluate(newTree));
            System.out.println(getEquation(newTree));
        } catch (IllegalArgumentException e) {System.out.println("invalid equation");}
        try {
            BinTreeElement<String> newTree = buildParseTree("( 7 * 0 )");
            System.out.println(evaluate(newTree));
            System.out.println(getEquation(newTree));
        } catch (IllegalArgumentException e) {System.out.println("invalid equation");}
        try {
            BinTreeElement<String> newTree = buildParseTree("( ( 7 + 4 ) * ( 3 * 3 ) )");
            System.out.println(evaluate(newTree));
            System.out.println(getEquation(newTree));
        }
        catch (IllegalArgumentException e) {System.out.println("invalid equation");}
        try {
            BinTreeElement<String> newTree = buildParseTree("");
            System.out.println(evaluate(newTree));
            System.out.println(getEquation(newTree));
        }
        catch (IllegalArgumentException e) {System.out.println("invalid equation");}
    }


    public static BinTreeElement<String> buildParseTree(String expression) throws IllegalArgumentException {
        String operators = "+-*/";
        BinTreeElement<String> root = new BinTreeElement<>("");
        BinTreeElement<String> currentNode;
        Stack<BinTreeElement<String>> treeStack = new Stack<>();
        ArrayList<String> tokens = getTokens(expression);
        currentNode = root;
        for (String token : tokens) {
            if (token.equals("(")) {
                BinTreeElement<String> newNode = new BinTreeElement<>("");
              currentNode.setLeft(newNode);
              treeStack.push(currentNode);
              currentNode = newNode;
            } else if (token.equals(")")) {
                if (!treeStack.empty()) {
                    currentNode = treeStack.pop();
                } else {
                    if (tokens.isEmpty()) {
                        break;
                    }
                }
            } else if (operators.contains(token)) {
                BinTreeElement<String> newNode = new BinTreeElement<>("");
                currentNode.setRight(newNode);
                currentNode.setValue(token);
                treeStack.push(currentNode);
                currentNode = newNode;
            } else if (Double.parseDouble(token) >= 0) {
               currentNode.setValue(token);
               if (!treeStack.empty()) {
                   currentNode = treeStack.pop();
               }
            }

        }
        return currentNode;
    }


    public static ArrayList<String> getTokens(String expression) throws IllegalArgumentException{
        ArrayList<String> tokens = new ArrayList<>();
        String[] expressions = expression.split(" ");
        String goodTokens = "-+/*()";
        for (String currentToken: expressions) {
            if (goodTokens.contains(currentToken) || Double.parseDouble(currentToken) >= 0) {
                tokens.add(currentToken);
            }
        }
        return tokens;
    }


    public static double evaluate(BinTreeElement<String> tree) throws ArithmeticException, IllegalArgumentException{
        if (tree == null) {
            throw new IllegalArgumentException();
        }

        if (tree.getLeft() == null && tree.getRight() == null) {
            return Double.parseDouble(tree.getValue());
        }

        double value1 = evaluate(tree.getLeft());
        double value2 = evaluate(tree.getRight());

        if (tree.getValue().equals("+")) {
            return value1 + value2;
        }
        if (tree.getValue().equals("-")) {
            return value1 - value2;
        }
        if (tree.getValue().equals("/")) {
            if (value2 == 0) {
                throw new ArithmeticException();
            }
            return value1 / value2;
        }
        if (tree.getValue().equals("*")) {
            return value1 * value2;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static String getEquation(BinTreeElement<String> tree) {
        String equation = "";
        if (tree == null) {
            return "";
        } else if (tree.getLeft() != null && tree.getRight() != null) {
            equation += "( " + getEquation(tree.getLeft()) + " " + tree.getValue() + " " + getEquation(tree.getRight()) + " )";
        } else {
            equation = equation + tree.getValue();
        }
        return equation;
    }


}




