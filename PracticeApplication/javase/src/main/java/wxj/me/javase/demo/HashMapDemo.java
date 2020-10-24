package wxj.me.javase.demo;

public class HashMapDemo {

    public static void main(String[] args) {
        SimpleHasMap simpleHasMap = new SimpleHasMap();
        for (int i = 0; i < 50; i++) {
            simpleHasMap.put("key" + i, "value" + i);
        }
        simpleHasMap.printHashMap();
    }

}

class SimpleHasMap {
    private Node[] tab;
    private int capacity;
    private int size;

    public void put(String key, String value) {

        if (null == tab) {
            tab = createTab();
            Node node = new Node(key, value, null);
            int index = key.hashCode() % capacity;
            tab[index] = node;
        } else {
            int index = key.hashCode() % capacity;
            if (null == tab[index]) {
                Node node = new Node(key, value, null);
                tab[index] = node;
            } else if (key.equals(tab[index].key)) {
                tab[index].value = value;
            } else {
                Node pNode = tab[index];
                while (null != pNode.node) {
                    pNode = pNode.node;
                }
                Node newNode = new Node(key, value, null);
                pNode.node = newNode;
            }
        }
        //size++;
    }

    public void printHashMap() {
        if (null != tab) {
            for (int i = 0; i < tab.length; i++) {
                Node pNode = tab[i];
                while (null != pNode.node) {
                    System.out.print(pNode);
                    System.out.print("    ");
                    pNode = pNode.node;
                }
                System.out.println(pNode);
            }
        }
    }


    private Node[] createTab() {
        capacity = 16;
        tab = new Node[capacity];
        return tab;
    }
}

class Node {
    public String key;
    public String value;
    public Node node;

    public Node(String key, String value, Node node) {
        this.key = key;
        this.value = value;
        this.node = node;
    }

    @Override
    public String toString() {
        return "Node{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}

// 1.使用 T 存储 value