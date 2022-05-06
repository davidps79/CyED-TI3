package dataStructures;

import java.util.ArrayList;
import java.util.Comparator;

import javax.xml.bind.helpers.ValidationEventImpl;

import model.Person;

public class AvlTree<E extends Person> {
	private Node<E> root;
    private Comparator<E> comparator;
	private int searchCounter;
	private int maxCoincidences;
	private ArrayList<Person> coincidences;

    public AvlTree(Comparator<E> comparator, int maxCoincidences) {
        this.comparator = comparator;
		this.maxCoincidences = maxCoincidences;
		this.coincidences = new ArrayList<Person>();
		this.searchCounter = 0;
    }
	
	public void add(E e) {
		Node<E> node = new Node<E>(e);
		
		if (this.root==null) this.root = node;
		else checkFb(root.addNode(node, comparator));
	}

    public Node<E> getRoot(){
        return root;
    }
    
    public void setRoot(Node<E> root){
        this.root=root;
        root.setParent(null);
    }

    public String inOrder() {
        if (root==null) {
            return "[]"; 
        }else{
           return root.inOrder();
        }
    }

    public void checkHealth() {
        if (root != null)
            root.health();
    }

	public void checkFb(Node<E> node) {
		if (node.getFb()<-1 || node.getFb()>1) balance(node);
		else if (node.getParent() != null) checkFb(node.getParent());
	}

	public void balance(Node<E> node) {
		int fbq; 
		if (node.getFb() == 2) {
			fbq = node.getRight().getFb();
			if (fbq == 0 || fbq == 1) preLeftRotate(node);
			else {
				preRightRotate(node.getRight());
				preLeftRotate(node);
			}
		} else if (node.getFb() == -2) {
			fbq = node.getLeft().getFb();
			if (fbq == 0 || fbq == -1) preRightRotate(node);
			else {
				preLeftRotate(node.getLeft());
				preRightRotate(node);
			}
			//System.out.println("START---------------");
			//checkHealth();
			//System.out.println("END----------");

		} else {
			checkHealth();
			System.out.println(node.getFb() + " EERRROR");
			String s = "left: ";
			if (node.getLeft()!=null) s+= node.getLeft().getHeight();
			s += "\nright: ";
			if (node.getRight()!=null) s+= node.getRight().getHeight();
			System.out.println(s);
			System.exit(0);
		}
	}

	public void preLeftRotate(Node<E> node) {
		Node<E> tempParent = node.getParent();
		if (tempParent != null) {
			if (tempParent.getLeft() == node) tempParent.setLeft(leftRotate(node));
			else tempParent.setRight(leftRotate(node));
			tempParent.updateR();
		} else setRoot(leftRotate(node));
	}

	public Node<E> leftRotate(Node<E> node) {
		Node<E> tempRight = node.getRight();
		Node<E> tempChild = node.getRight().getLeft();
		node.getRight().setLeft(node);
		node.setRight(tempChild);

		node.update();
		tempRight.update();

		return tempRight;
	}

	public void preRightRotate(Node<E> node) {
		Node<E> tempParent = node.getParent();
		if (tempParent != null) {
			if (tempParent.getLeft() == node) tempParent.setLeft(rightRotate(node));
			else tempParent.setRight(rightRotate(node));
			tempParent.updateR();
		} else setRoot(rightRotate(node));
	}

	public Node<E> rightRotate(Node<E> node) {
		Node<E> tempLeft = node.getLeft();
		Node<E> tempChild = node.getLeft().getRight();
		node.getLeft().setRight(node);
		node.setLeft(tempChild);

		node.update();
		tempLeft.update();

		return tempLeft;
	}

	public void resetSearch(){
		searchCounter=0;
		coincidences.clear();
	}

	public void delete(Node<E> toDelete) {
		if (toDelete.getParent()!= null) {
			if (toDelete.isLeaf()) { // hoja
				Node<E> validation = toDelete.getParent().getLeft();
				if (validation!=null && validation==toDelete) toDelete.getParent().setLeft(null);
				else toDelete.getParent().setRight(null);
				toDelete.getParent().updateR();
				checkFb(toDelete.getParent());
			} else { // con al menos un hijo
				if (toDelete.getRight() != null && toDelete.getLeft() != null) { // Dos hijos
					Node<E> successor = toDelete.getRight().getSuccessor();
					Node<E> successorParent = successor.getParent();
					delete(successor);

					if (toDelete.getParent().getLeft() == toDelete) toDelete.getParent().setLeft(successor);
					else toDelete.getParent().setRight(successor);
		
					successor.setLeft(toDelete.getLeft());
					successor.setRight(toDelete.getRight());

					successorParent.updateR();
					checkFb(successorParent);
				} else { // Uno de los dos es nulo
					Node<E> tempParent = toDelete.getParent();
					if (toDelete.getRight() == null) {
						if (toDelete.getParent().getLeft() == toDelete) toDelete.getParent().setLeft(toDelete.getLeft());
						else toDelete.getParent().setRight(toDelete.getLeft());
					} else {
						if (toDelete.getParent().getLeft() == toDelete) toDelete.getParent().setLeft(toDelete.getRight());
						else toDelete.getParent().setRight(toDelete.getRight());
					}
					tempParent.updateR();
					checkFb(tempParent);
				}
			}
		} else { //raiz
			Node<E> successor = toDelete.getRight().getSuccessor();
			Node<E> successorParent = successor.getParent();

			if (successorParent == root) {
				root.getRight().setLeft(root.getLeft());
				setRoot(root.getRight());
				root.updateR();
				checkFb(root);
			} else {
				delete(successor);
	
				successor.setLeft(toDelete.getLeft());
				successor.setRight(toDelete.getRight());
				setRoot(successor);
				successorParent.updateR();
				checkFb(successorParent);
			}
		}
	}

	public ArrayList<Person> searchCoincidenceByName(Node<E> node, String toSearch) {
		if (searchCounter<maxCoincidences) {
			String name = (node.getItem()).getName();
			//System.out.println("Comparo con " + name);
			if (name.length()>=toSearch.length()) { // Para que se le pueda sacar substring
				int result = toSearch.compareTo(name.substring(0, toSearch.length()));
				System.out.println(name.substring(0, toSearch.length()));
				//System.out.println("Resultado: " + result);
				if (result>0){
					if (node.getRight() != null) searchCoincidenceByName(node.getRight(), toSearch);
				} else if (result<0) {
					if (node.getLeft() != null) searchCoincidenceByName(node.getLeft(), toSearch);
				} else {
					coincidences.add(node.getItem());
					searchCounter++;
					if (node.getLeft() != null) searchCoincidenceByName(node.getLeft(), toSearch);
					if (node.getRight() != null) searchCoincidenceByName(node.getRight(), toSearch);
				}
			} else {
				if (node.getLeft() != null) searchCoincidenceByName(node.getLeft(), toSearch);
				if (node.getRight() != null) searchCoincidenceByName(node.getRight(), toSearch);
			}
		}
		return coincidences;
	}

	public ArrayList<Person> searchCoincidenceByName(String toSearch) {
		resetSearch();

		if (root != null) return searchCoincidenceByName(root, toSearch);
		else return null;
	}

	public void searchId(String id){
		if(root!=null){
			root.searchId(id);
		}
	}
}