class Mystack{
    private int arr[];
    private int top;
    private int capacity;

    Mystack(int cap){
        top = -1;
        capacity = cap;
        arr = new int[cap];
    }

    void push(int data){
        if(top==capacity-1){
            System.out.println("Stack overflow");
            return;
        }
        System.out.println("Pushed data: "+data);
        arr[++top] = data;
    }

    int pop(){
        if(top==-1){
            System.out.println("Stack underflow");
        }
        return arr[top--];
    }

    int peek(){
        if(top==-1){
            System.out.println("System underflow");
        }
        return arr[top];
    }

    boolean isEmpty(){
        if(top==-1){
            return true;
        }
        return false;
    }

    boolean isFull(){
        if(top==capacity-1){
            return true;
        }
        return false;
    }

}

class stackimplement{
    public static void main(String [] args){
        Mystack s = new Mystack(3);
        s.push(1);
        s.push(2);
        s.push(3);
        System.out.println("Poped value: "+s.pop());
        s.push(4);
        System.out.println("Peek of the stack: "+s.peek());
        System.out.println("Is the stack empty: "+s.isEmpty());
        System.out.println("Is the stack is full: "+s.isFull());
        System.out.println("Poped value: "+s.pop());
        System.out.println("Poped value: "+s.pop());
        System.out.println("Poped value: "+s.pop());
        System.out.println("Is the stack empty: "+s.isEmpty());



    }
}