/**
 * This is my own work: Ben Mabie
 * This program manipulates double linked lists and demonstrates a merge algorithm
 * Nov. 3 2014
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * solution file for DLinkedList Assignment Fall 2014
 *
 * @author lkfritz
 * @param <T>
 */

public class DLinkedList<T extends Comparable<T>>{

    //main method
    public static void main(String[] args) throws FileNotFoundException {

        DLinkedList<String> lst1 = new DLinkedList<>();
        DLinkedList<String> lst2 = new DLinkedList<>();

        Scanner darkly = new Scanner(new File("text1.in"));
        String str;

        //populate lst1
        while (darkly.hasNext()) {
            str = darkly.next();
            str = cleanUp(str);
            lst1.insertOrderUnique(str);
        }
        darkly.close();
        darkly = new Scanner(new File("text2.in"));

        //populate lst2
        while (darkly.hasNext()) {
            str = darkly.next();
            str = cleanUp(str);
            lst2.insertOrderUnique(str);
        }

        //display the lists
        System.out.println("list1: " + lst1);
        System.out.println("list2: " + lst2);
        
        //merge the lists into result
        DLinkedList<String> result = lst1.merge(lst2);
        
        //display the lists after merge
        System.out.println("list1: " + lst1);
        System.out.println("list2: " + lst2);
        System.out.println("result: " + result);
        
    }
    
    /**
     * ASSIGNED METHOD - DO NOT CHANGE SIGNATURE
     * remove any leading and trailing non-alpha chars from 
     * @param str
     * 
     * @return updated str in all lower case
     */
    public static String cleanUp(String str){
       
        str = str.toLowerCase();
        int startPos = 0;
        int endPos = str.length();

        while (str.length() > startPos && !Character.isLetter(str.charAt(startPos))) {
            startPos++;
        }

        while (endPos > 0 && !Character.isLetter(str.charAt(endPos - 1))) {
            endPos--;
        }

        str = str.substring(startPos, endPos);
        return str;
    }


    //inner DNode class:  PROVIDED
    private class DNode {

        private DNode next, prev;
        private T data;

        private DNode(T val) {
            this.data = val;
            next = prev = this;
        }
    }

    //DLinkedList fields:  PROVIDED
    private DNode header;

    //create an empty list:  PROVIDED
    public DLinkedList() {
        header = new DNode(null);
    }   

    //PROVIDED
    public String toString() {
        String str = "[";
        DNode curr = header.next;
        while (curr != header) {
            str += curr.data + " ";
            curr = curr.next;
        }
        if(str.length()>1)
            str = str.substring(0, str.length() - 1);
        return str + "]";
    }

    /**
     * ASSIGNED METHOD - DO NOT CHANGE SIGNATURE
     * 
     * remove 
     * @param val from list
     * @return true if successful, false otherwise
     */
    public boolean remove(T val) {
        /**
         * check to see if the value exists, if not add return false
         */
        if(val == null){  
           return false;   
        }
        else{
        /**
         * otherwise find and remove val, and re-link the nodes
         */   
        DNode curr = header.next;
        while (curr != header) {
            if (curr.data == val) {
            curr.prev.next = curr.next;
            curr.next.prev = curr.prev;
        }
            curr = curr.next;
        }
       return true; 
    }
    }

    /**
     * ASSIGNED METHOD - DO NOT CHANGE SIGNATURE
     *
     * insert
     * @param item into list so that list remains in ascending order
     */

    public void insertOrder(T item) {
         
        DNode curr = header.next;
 
        while (curr != header && item.compareTo(curr.data) > 0) {
            curr = curr.next;
            }
   
            DNode newNode = new DNode(item);
            newNode.prev = curr.prev;
            newNode.next = curr;
            curr.prev.next = newNode;
            curr.prev = newNode;
        }

    /**
     * ASSIGNED METHOD - DO NOT CHANGE SIGNATURE
     *
     * insert
     * @param item into list if item is not already in the list
     * list must remain in ascending order
     * return true if item is inserted, false otherwise
     * 
     */

    public boolean insertOrderUnique(T item) {
        DNode curr = header.next;
 
        while (curr != header && item.compareTo(curr.data) > 0) {
            curr = curr.next;
        }

    if (curr == header || item.compareTo(curr.data) != 0 ) {
        
        DNode newNode = new DNode(item);
        newNode.prev = curr.prev;
        newNode.next = curr;
        curr.prev.next = newNode;
        curr.prev = newNode;
        
    return true;   
    }
        return false;
    }

    /**
     * ASSIGNED METHOD - DO NOT CHANGE SIGNATURE
     * 
     * merge this list and 
     * @param rhs
     * @return list that contains merged list
     * return list must not contain duplicates and must remain in ascending 
     * order
     * both rhs and this must be empty lists when method returns
     */
    public DLinkedList merge(DLinkedList rhs) {
        
        DLinkedList result = new DLinkedList();
        DNode myResult = result.header;
        DNode node1 = header.next;
        DNode node2 = rhs.header.next;

        while (node1 != header && node2 != rhs.header) {
        /**Determine the values to be copied:
         * Case 1: node1 > node2 
         */    
        if (node1.data.compareTo(node2.data) > 0) {
            
            myResult.next = node2;
            node2.prev = myResult;
            node2 = node2.next;
            myResult = myResult.next;

        } 
        /**
         * Case 2: node1 < node2
         */
        else if (node1.data.compareTo(node2.data) < 0) {
            
            myResult.next = node1;
            node1.prev = myResult;
            node1 = node1.next;
            myResult = myResult.next;
  
        } 
        /**
         * Case 3: node1 = node2
         */
        else {
            
            myResult.next = node1;
            node1.prev = myResult;
            node1 = node1.next;
            node2 = node2.next;
            myResult = myResult.next;
            }
        }
        /**
         * place remaining nodes in myResult from this.lst
         * if lst2 is empty
         */
        while (node1 != header) {
            
            myResult.next = node1;
            node1.prev = myResult;
            node1 = node1.next;
            myResult = myResult.next;
        }
        /**
         * ...from lst2 if this.lst is empty
         */
        while (node2 != rhs.header) {
            
            myResult.next = node2;
            node2.prev = myResult;
            node2 = node2.next;
            myResult = myResult.next;
        }
        /**
         * Clean up and make everything pretty
         */
            myResult.next = result.header;
            result.header.prev = myResult;
            header.next = header;
            header.prev = header;
            rhs.header.next = rhs.header;
            rhs.header.prev = rhs.header;
            
        return result;
    }
}
