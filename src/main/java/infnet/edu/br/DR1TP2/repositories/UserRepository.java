package infnet.edu.br.DR1TP2.repositories;

import infnet.edu.br.DR1TP2.models.User;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class UserRepository {
    private final Map<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(0);

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public Optional<User> findById(Integer id) {
        if (id == null) return Optional.empty();
        return Optional.ofNullable(users.get(id));
    }

    public Optional<User> findByEmail(String email) {
        return users.values().stream().filter(u -> u.getEmail().equals(email)).findFirst();
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(idGenerator.getAndIncrement());
        }
        users.put(user.getId(), user);
        return user;
    }

    public void deleteById(Integer id) {
        if (id != null) {
            users.remove(id);
        }
    }

    public boolean existsById(Integer id) {
        return id != null && users.containsKey(id);
    }
}
