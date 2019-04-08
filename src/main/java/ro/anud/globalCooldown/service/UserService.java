package ro.anud.globalCooldown.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.model.UserModel;

import java.util.*;


@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final Map<String, UserModel> userModelMap;
    private final Map<String, List<String>> usernameToConnectionListMap;

    public UserService() {
        usernameToConnectionListMap = new HashMap<>();
        userModelMap = new HashMap<>();
    }

    public boolean notExists(UserModel userModel) {
        return !userModelMap.containsKey(userModel.getUsername());
    }

    public void addUser(UserModel userModel) {
        userModelMap.put(userModel.getUsername(), userModel);
        userModelMap.forEach((s, userModel1) -> LOGGER.info(s + " : " + userModel1));
    }

    public List<String> getConnectionListByName(String username) {

        return Optional.ofNullable(usernameToConnectionListMap.get(username))
                .orElseGet(ArrayList::new);
    }

    public void login(UserModel userModel, String connection) {
        usernameToConnectionListMap.putIfAbsent(userModel.getUsername(), new ArrayList<>());
        usernameToConnectionListMap.get(userModel.getUsername()).add(connection);
        LOGGER.info(usernameToConnectionListMap.toString());
    }
}
