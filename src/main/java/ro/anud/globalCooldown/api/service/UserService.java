package ro.anud.globalCooldown.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.anud.globalCooldown.data.model.UserModel;

import java.util.*;


@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final Map<String, UserModel> usernameToUserModel;
    private final Map<String, UserModel> connectionIdToUserName;
    private final Map<String, List<String>> usernameToConnectionList;

    public UserService() {
        connectionIdToUserName = new HashMap<>();
        usernameToConnectionList = new HashMap<>();
        usernameToUserModel = new HashMap<>();
    }

    public boolean notExists(UserModel userModel) {
        return !usernameToUserModel.containsKey(userModel.getUsername());
    }

    public void addUser(UserModel userModel) {
        usernameToUserModel.put(userModel.getUsername(), userModel);
        usernameToUserModel.forEach((s, userModel1) -> LOGGER.info(s + " : " + userModel1));
    }

    public List<UserModel> getUserModelList() {
        return new ArrayList<>(usernameToUserModel.values());
    }

    public List<String> getConnectionListByName(String username) {

        return Optional.ofNullable(usernameToConnectionList.get(username))
                .orElseGet(ArrayList::new);
    }

    public Optional<UserModel> getUsernameFromConnectionId(String name) {
        return Optional.ofNullable(connectionIdToUserName.get(name));
    }

    public void logout(String connectionId) {
        usernameToConnectionList
                .values()
                .forEach(connectionIdList -> connectionIdList.remove(connectionId));
    }

    public void login(UserModel userModel, String connection) {
        usernameToConnectionList.putIfAbsent(userModel.getUsername(), new ArrayList<>());
        usernameToConnectionList.get(userModel.getUsername()).add(connection);
        connectionIdToUserName.put(connection, userModel);
        LOGGER.info(usernameToConnectionList.toString());
    }

    public void reset() {
        usernameToConnectionList.clear();
        connectionIdToUserName.clear();
        usernameToUserModel.clear();
    }
}
