package pkg20questions;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class TwentyQuestions {

    public static TreeNode root;

    public static void buildStarterTree() {
        root = new TreeNode("Is the person ailve?");
        root.left = new TreeNode("You");
        root.right = new TreeNode("Steve Jobs");
    }

    public static boolean yesNo(Scanner s) {
        while (true) {
            String answer = s.nextLine().toLowerCase();
            char firstChar = answer.charAt(0);

            if (firstChar == 'y') {
                return true;
            } else if (firstChar == 'n') {
                return false;
            }
            System.out.println("Please enter a yes or no.");
        }
    }

    public static void saveTree() {
        try (FileOutputStream file = new FileOutputStream("treefile.ser");
                BufferedOutputStream buffer = new BufferedOutputStream(file);
                ObjectOutputStream output = new ObjectOutputStream(buffer);) {
            output.writeObject(root);
        } catch (IOException e) {
            System.out.println("Oops! I couldn't save the tree.");
            System.out.println("Problem was: " + e);
        }
    }

    public static void loadTree() {
        try (FileInputStream file = new FileInputStream("treefile.ser");
                BufferedInputStream buffer = new BufferedInputStream(file);
                ObjectInputStream input = new ObjectInputStream(buffer);) {
            root = (TreeNode) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            buildStarterTree();
        }
    }

    public static void main(String[] args) {
        loadTree();
        Scanner s = new Scanner(System.in);
        boolean keepPlaying = true;

        while (keepPlaying) {
            System.out.println("Welcome to Twenty Questions!");
            System.out.println("You think of something and I'll try to guess what it is.");
            System.out.println("The theme of this game is going to be people so please think of a person.");
            TreeNode current = root;
            while (current.left != null) {
                System.out.println(current.data);
                boolean answer = yesNo(s);
                if (answer == true) {
                    current = current.left;
                } else {
                    current = current.right;
                }
            }

            System.out.printf("Is is %s? ", current.data);
            boolean correct = yesNo(s);
            if (correct == true) {
                System.out.println("Yay! I guessed correctly!");
            } else {
                System.out.print("I give up! Who was it? ");
                String person = s.nextLine();
                System.out.printf("Help me learn. Please enter a question whose answer is yes for %s, and no for %s\n",
                        current.data, person);
                String question = s.nextLine();
                current.left = new TreeNode(current.data);
                current.right = new TreeNode(person);
                current.data = question;
            }
            System.out.print("Would you like to play again? ");
            keepPlaying = yesNo(s);
        }
        saveTree();
    }
}
