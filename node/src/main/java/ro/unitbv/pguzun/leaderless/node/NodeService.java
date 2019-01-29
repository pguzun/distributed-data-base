package ro.unitbv.pguzun.leaderless.node;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class NodeService {

    private final Map<String, Map<String, Data>> collections = new HashMap<>();

    public void save(String collection, String identifier, Data data) {
        Map<String, Data> dataCollection = collections.get(collection);
        if (dataCollection == null) {
            dataCollection = new ConcurrentHashMap<>();
            collections.put(collection, dataCollection);
        }

        dataCollection.put(identifier, data);
        
        System.out.println("Saved " + identifier +" with " + data);
    }

    public Optional<Data> get(String collection, String identifier) {
        Map<String, Data> dataCollection = collections.get(collection);
        if (dataCollection == null) {
            return Optional.empty();
        }

        Data result = dataCollection.get(identifier);
        
        System.out.println("Getting for " + identifier +" value " + result);
        return Optional.ofNullable(result);
    }
}
