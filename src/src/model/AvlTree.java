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
		if (node.getParent() != null) checkFb(node.getParent());
	}

    private void balance(Node<E> node) {
		int fbq; 
		if (node.getFb() == 2) {
			fbq = node.getRight().getFb();
			if (fbq == 0 || fbq == 1) leftRotate(node);
			else {
				rightRotate(node.getRight());
				leftRotate(node);
			}
		} else if (node.getFb() == -2) {
			fbq = node.getLeft().getFb();
			if (fbq == 0 || fbq == -1) rightRotate(node);
			else {
				leftRotate(node.getLeft());
				rightRotate(node);
			}
		} else {
			checkHealth();
			System.out.println(((Person) node.getItem()).getName()+node.getFb() + " EERRROR");
			System.exit(0);
		}
	}

	/*public void leftRotate(Node<E> node) {
		Node<E> tempRight = node.getRight();
    
		if (node.getParent() != null) {
			if (node.getParent().getLeft() != null && node.getParent().getLeft().equals(node)) node.getParent().setLeft(node.getRight());
			else node.getParent().setRight(node.getRight());
		}

		if (node.getRight().getLeft() != null) node.setRight(node.getRight().getLeft());
		else node.setRight(null);

		tempRight.setLeft(node);

		node.updateHeight();

		if (tempRight.getParent() != null) {
			tempRight.getParent().updateHeight();
			tempRight.getParent().recalculateFb();
		}

        if (node.equals(root)) setRoot(tempRight);
	}

	public void rightRotate(Node<E> node) {
		Node<E> tempLeft = node.getLeft();

		if (node.getParent() != null) {
			if (node.getParent().getLeft() != null && node.getParent().getLeft().equals(node)) node.getParent().setLeft(node.getLeft());
			else node.getParent().setRight(node.getLeft());
		}

		if (node.getLeft().getRight() != null) node.setLeft(node.getLeft().getRight());
		else node.setLeft(null);

		tempLeft.setRight(node);

		node.updateHeight();

		if (tempLeft.getParent() != null) {
			tempLeft.getParent().updateHeight();
			tempLeft.getParent().recalculateFb();
		}

        if (node.equals(root)) setRoot(tempLeft);
	}*/
/*
	public boolean delete(Node<E> toDelete) {
		if (root == null) return false;
		else {
			boolean found = true; // root.search(toDelete);
			if(found) {
				root = root.delete(toDelete);
			}
		}
		return true;
	}
	
	public boolean search(double value) {
		if (root != null) {
			return root.searchNode(value);
		} else {
			return false;
		}
	}
*/

}
