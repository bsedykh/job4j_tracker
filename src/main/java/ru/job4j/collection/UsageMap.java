package ru.job4j.collection;

import java.util.HashMap;

public class UsageMap {
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        map.put("abc@example.com", "Ivanov Ivan Ivanovich");
        map.put("abc@example.com", "Petrov Petr Petrovich");
        map.put("test@example.com", "Sidorov Sidor Sidorovich");
        map.put("work@example.com", "Sergeev Sergey Sergeevich");
        for (String key : map.keySet()) {
            String value = map.get(key);
            System.out.println(key + " = " + value);
        }
    }
}
