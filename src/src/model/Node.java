package model;

import java.util.Comparator;

public class Node<E> {
	private E item;
	private Node<E> parent;
	private Node<E> left;
	private Node<E> right;
	private int fb;
	private int height;

	public Node(E e){
		this.item = e;
		this.height = 1;
		this.fb=0;
	}
	
	public E getItem() {
		return this.item;
	}
	
	public void setItem(E item) {
		this.item = item;
	}
	
	public Node<E> getLeft() {
		return left;
	}

	public void setLeft(Node<E> left) {
		this.left = left;
		if (left != null) left.setParent(this);
	}

	public Node<E> getRight() {
		return right;
	}

	public void setRight(Node<E> right) {
		this.right = right;
		if (right != null) right.setParent(this);
	}

	public int getFb() {
		return fb;
	}

	public void setFb(int fb) {
		this.fb = fb;
	}
	
	public Node<E> getParent() {
		return parent;
	}

	public void setParent(Node<E> parent) {
		this.parent = parent;
	}
	
	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Node<E> addNode(Node<E> node, Comparator<E> comparator) {
		if (comparator.compare(this.getItem(), node.getItem())>0) {
			fb++;
			if (right==null) {
				if (isLeaf()) {
					height++;
					if (parent != null) parent.updateHeight();
				}
				setRight(node);
			}
			else {
				right.addNode(node, comparator);
			}
		} else {
			fb--;
			if (left == null) {
				if (isLeaf()) {
					height++;
					if (parent != null) parent.updateHeight();
				}
				setLeft(node);
			}
			else {
				left.addNode(node, comparator);		
			}	
		}

		return node;
	}
	
	public void update() {
		int r = 0;
		int l = 0;

		if (right !=null) r = right.getHeight();
		if (left !=null) l = left.getHeight();
		fb = r-l;
		int tempHeight = r>l?r:l;
		tempHeight++;

		if (tempHeight != height) {
			height = tempHeight;
			if (parent != null) parent.update();
		}
	}

	public boolean isLeaf() {
        return (right==null && left==null);
    }

	public String inOrder() {
	String msg="";
	msg+= item.toString()+" ";
	if (left!=null) {
		msg+=left.inOrder()+" ";
	}
	
		if (right!=null) {
		msg+=" "+right.inOrder();
	}
	return msg;
    }

	public void health() {
		System.out.println(this.item.toString() + " -> fb: " + this.fb + " height: " + this.height
		+ " Left: " + (left!=null?left.getItem().toString():"null") + " Right: " + (right!=null?right.getItem().toString(): "null"));
		if (left != null) left.health();
		if (right != null) right.health();
	} 

	public Node<E> getSuccessor() {
		if (left == null) return this;
		else return left.getSuccessor();
	}

	public Node<E> delete(Node<E> toDelete, Comparator<Node<E>> comparator) {
		if (this == toDelete) {
			if (isLeaf()) return null;
			else if (left == null || right == null) {
				if (left != null) return left;
				else return right;
			} else {
				Node<E> successor = right.getSuccessor();
				setItem(successor.getItem());
				right = right.delete(successor, comparator);
				return this;
			}
		} else {
			if (left != null && comparator.compare(toDelete, this) < 0) {
				left = left.delete(toDelete, comparator);
			} else if (right != null) {
				right = right.delete(toDelete, comparator);
			}

			return this;
		}
	}
	/*
	public Node<E> searchNode(double searched) {
		
		if (Math.abs(searched - value) < TOLERANCE) {
			return true;
		} else if (searched < value && left != null) {
			return left.searchNode(searched);
		} else if (searched > value && right != null) {
			return right.searchNode(searched);
		}

		return false;
	}*/
}