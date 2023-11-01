package ru.job4j.queue;

import java.util.Queue;

public class AppleStore {
    private final Queue<Customer> queue;
    private final int count;

    public AppleStore(Queue<Customer> queue, int count) {
        this.queue = queue;
        this.count = count;
    }

    public String getLastHappyCustomer() {
        Customer customer = null;
        for (int i = 0; i < count; i++) {
            if (queue.isEmpty()) {
                break;
            }
            customer = queue.remove();
        }
        return customer == null ? "" : customer.name();
    }

    public String getFirstUpsetCustomer() {
        for (int i = 0; i < count; i++) {
            if (queue.poll() == null) {
                break;
            }
        }
        return queue.isEmpty() ? "" : queue.element().name();
    }
}
