import java.util.HashMap;
import java.util.HashSet;

public class Phonebook {

    private HashMap<String, HashSet<String>> phonebook;

    Phonebook() {
        phonebook = new HashMap<>();
    }

    public void add(String lastName, String phone) {

        HashSet<String> phoneSet = new HashSet<String>(1) {{
            add(phone);
        }};

        phonebook.merge(lastName, phoneSet, (currentPhoneSet, newPhoneSet) -> {
            currentPhoneSet.addAll(newPhoneSet);
            return currentPhoneSet;
        });
    }

    public HashSet<String> get(String lastName) {
        return phonebook.get(lastName);
    }
}
