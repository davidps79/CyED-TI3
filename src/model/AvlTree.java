package model;

import java.util.Comparator;

public class AvlTree<E> {
	private Node<E> root;
    private Comparator<E> comparator;

    public AvlTree(Comparator<E> comparator) {
        this.comparator = comparator;
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
			//System.out.println("START---------------");
			//checkHealth();
			//System.out.println("END----------");
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

	/*public boolean search(double value) {
		if (root != null) {
			return root.searchNode(value);
		} else {
			return false;
		}
	}*/

}
