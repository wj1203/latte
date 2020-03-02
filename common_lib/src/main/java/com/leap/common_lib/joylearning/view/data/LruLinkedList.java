package com.leap.common_lib.joylearning.view.data;

/**
 * @author: leap
 * @time: 2020/3/2
 * @classname: LruLinkedList
 * @description:    lru算法的链表实现
 */
public class LruLinkedList<T> {

    Node head;
    int size;
    int max = 5;

    public void lruAdd(T data){
        if (size >= 5 ){
            throw new IndexOutOfBoundsException("");
        }
        Node node = new Node(data);
        if (head == null){
            head = node;
        }else {
            node.next = head;
        }
        size++;
    }

    public T lruGet(int index){
        if (!(index>=0 && index<size)){
            throw new IndexOutOfBoundsException("");
        }
        Node temp = head;
        Node pre = head;
        for (int i = 0;i<index;i++){
            pre = temp;
            temp = temp.next;
        }
        // 移动到链表头
        pre.next = temp.next;
        temp.next = head;
        head = temp;

        return head.data;
    }

    public T lruRemove(){
        if (size<=0){
            throw new IndexOutOfBoundsException("");
        }
        Node temp = head;
        Node pre = head;
        while (temp.next!=null){
            pre = temp;
            temp = temp.next;
        }
        pre.next = null;

        return temp.data;
    }





    class Node{
        T data;
        Node  next;

        public Node(T data) {
            this.data = data;
        }
    }
}

