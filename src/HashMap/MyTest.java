package HashMap;

public class MyTest {
    public static void main(String[] args) {
        MyHashMap<String,String> map = new MyHashMap<String,String>(9,0.75f);
        map.put("user1","world");
        map.put("user2","zzy");
        map.put("user3","ysy");
        map.put("user4","niubi");
        map.put("user5","niub1i");

        System.out.println(map.get("user1"));
        System.out.println(map.get("user2"));
        System.out.println(map.get("user3"));
        System.out.println(map.get("user4"));
        System.out.println(map.get("user5"));
    };
}
