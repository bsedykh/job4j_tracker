package ru.job4j.tracker.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.tracker.Item;

import java.util.List;
import java.util.function.Function;

public class HbmTracker implements Store {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Override
    public Item add(Item item) {
        executeInTransaction(session -> session.save(item));
        return item;
    }

    @Override
    public boolean replace(int id, Item item) {
        return executeInTransaction(session -> session
                .createQuery("UPDATE Item SET name = :name, created = :created WHERE id = :id")
                .setParameter("name", item.getName())
                .setParameter("created", item.getCreated())
                .setParameter("id", id)
                .executeUpdate() > 0);
    }

    @Override
    public void delete(int id) {
        executeInTransaction(session -> session
                .createQuery(
                        "DELETE Item WHERE id = :id")
                .setParameter("id", id)
                .executeUpdate());
    }

    @Override
    public List<Item> findAll() {
        return executeInSession(session -> session
                .createQuery("FROM Item", Item.class)
                .list());
    }

    @Override
    public List<Item> findByName(String key) {
        return executeInSession(session -> session
                .createQuery("FROM Item i WHERE i.name = :name", Item.class)
                .setParameter("name", key)
                .list());
    }

    @Override
    public Item findById(int id) {
        return executeInSession(session -> session
                .createQuery("FROM Item i WHERE i.id = :id", Item.class)
                .setParameter("id", id)
                .uniqueResult());
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    private <T> T executeInTransaction(Function<Session, T> action) {
        return executeInSession(session -> {
            Transaction tr = null;
            try {
                tr = session.beginTransaction();
                var result = action.apply(session);
                tr.commit();
                return result;
            } catch (Exception e) {
                if (tr != null) {
                    tr.rollback();
                }
                throw e;
            }
        });
    }

    private <T> T executeInSession(Function<Session, T> action) {
        try (var session = sf.openSession()) {
            return action.apply(session);
        }
    }
}
