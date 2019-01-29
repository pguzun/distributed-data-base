package ro.unitbv.pguzun.leaderless.node;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {

    @Autowired
    private NodeService service;

    @RequestMapping(path = "/{collection}/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@PathVariable("collection") String collection, @PathVariable("id") String identifier,
            @RequestBody Data data) throws Exception {
        service.save(collection, identifier, data);
    }

    @RequestMapping(path = "/{collection}/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Data> get(@PathVariable("collection") String collection, @PathVariable("id") String identifier)
            throws Exception {

        Optional<Data> result = service.get(collection, identifier);
        if (!result.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result.get());
    }
}
