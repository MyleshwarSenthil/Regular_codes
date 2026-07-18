class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

class LinkedList {
    Node head;

    static LinkedList insert(LinkedList list, int data) {
        Node new_node = new Node(data);
        if (list.head == null) {
            list.head = new_node;
        } 
        else {
            Node current = list.head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = new_node;
        }
        return list;
    }

    static void printList(LinkedList list) {
        Node current = list.head;
        System.out.print("Linked List: ");
        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }
}

public class LL {
    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        
        list = LinkedList.insert(list, 1);
        list = LinkedList.insert(list, 2);
        list = LinkedList.insert(list, 3);
        list = LinkedList.insert(list, 4);
        list = LinkedList.insert(list, 5);
        System.out.println(ListNode(list.head));
        
        System.out.println(ListNode(list.head));
        
        System.out.println(ListNode(list.head));
        
        System.out.println(ListNode(list.head));
        
        System.out.println(ListNode(list.head));
    }

    static int ListNode(Node head){
        Node a = head;
        Node b = head.next;
        while(b==null){
            return 0;
        }
        while(b.next!=null){
            b=b.next;
            a=a.next;
        }
        a.next = null;
        a=head;

        while(a.next!=null){
            a=a.next;
        }
        return a.data;
    }
}
