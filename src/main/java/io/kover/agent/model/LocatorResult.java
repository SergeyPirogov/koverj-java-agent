<<<<<<< HEAD:src/main/java/io/koverj/agent/model/LocatorResult.java
package io.koverj.agent.model;
=======
package io.kover.agent.model;
>>>>>>> Move code to main dir for creation jar file:src/main/java/io/kover/agent/model/LocatorResult.java

import java.util.List;

/**
 * Created by alpa on 2/25/20
 */
public class LocatorResult {

    private String name;
    private List<Locator> locators;


    public LocatorResult(String name, List<Locator> locators) {
        this.name = name;
        this.locators = locators;
    }

    public String getName() {
        return name;
    }

    public List<Locator> getLocators() {
        return locators;
    }

    @Override
    public String toString() {
        return "LocatorResult{" +
                "name=" + name +
                ", locators=" + locators +
                '}';
    }
}
