public class test {

    public static void main(String[] args) {

    }

    public class Node<T> {
        public T data;
        public Node<T> left;
        public Node<T> right;
    }

    public int mysteryRec(Node current) {
        if (current == null) {
            return 0;
        }

        int left = mysteryRec(current.left);
        int right = mysteryRec(current.right);
        int tmp = left + current.data + right;

        if (tmp > 15) {
            System.out.println(current.data);
        }
        return tmp;
    }
}
