package bot;

import java.util.ArrayList;
import java.util.HashMap;

public class UserNameMap {
    HashMap<String, String> userNames = new HashMap<>();
    ArrayList<String> idList = new ArrayList<>(userNames.keySet());
    ArrayList<String> values = new ArrayList<>(userNames.values());

    public void addUserNames(String id, String name) {
        userNames.put(id, name);
    }

    public HashMap<String, String> getUserNames() {
        return userNames;
    }

    public String getChatId(int i) {
        return idList.get(i);
    }

}