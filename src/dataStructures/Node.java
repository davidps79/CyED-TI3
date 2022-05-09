package dataStructures;

import java.io.Serializable;
import java.util.Comparator;

import model.Person;

public class Node<E extends Person> implements Serializable {
	private static final long serialVersionUID = 1L;
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
			if (right==null) {
				if (isLeaf()) {
					height++;
					if (parent != null) parent.update();
				}
				setRight(node);
			}
			else {
				right.addNode(node, comparator);
			}
		} else {
			if (left == null) {
				if (isLeaf()) {
					height++;
					if (parent != null) parent.update();
				}
				setLeft(node);
			}
			else {
				left.addNode(node, comparator);		
			}	
		}

		node.updateR();
		return node;
	}
	
	public void update() {
		int r = 0;
		int l = 0;

		if (right !=null) r = right.getHeight();
		if (left !=null) l = left.getHeight();
		fb = r-l;
		int tempHeight = r>l?r:l;
		height = tempHeight+1;
	}

	public void updateR() {
		int r = 0;
		int l = 0;

		if (right !=null) r = right.getHeight();
		if (left !=null) l = left.getHeight();
		fb = r-l;
		int tempHeight = r>l?r:l;
		height = tempHeight+1;

		if (parent!=null) parent.updateR();
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

	public String health() {
		String s = this.item.toString() + " -> fb: " + this.fb + " height: " + this.height + "; " + this.item.getId()
		+ " Left: " + (left!=null?left.getItem().toString():"null") + " Right: " + (right!=null?right.getItem().toString(): "null") + " Parent: ";
		if (parent != null) s+= ((Person) parent.getItem()).getName();
		s+="\n";
		if (left != null) s+=left.health();
		if (right != null) s+=right.health();
		return s;
	} 

	public Node<E> getSuccessor() {
		if (left == null) return this;
		else return left.getSuccessor();
	}

	public Node<E> searchId(String id){
		String id1 = ((Person) this.getItem()).getId();
		if(id.compareTo(id1)>0){
			return right.searchId(id);
		}else if (id.compareTo(id1)<0) {
			return left.searchId(id);
		}else{
			return this;
		}
	}
	
}