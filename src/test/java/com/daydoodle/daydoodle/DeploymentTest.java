package com.daydoodle.daydoodle;

import com.daydoodle.daydoodle.common.UserDto;
import com.daydoodle.daydoodle.ejb.UserBean;
import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(ArquillianExtension.class)
public class DeploymentTest {

    private int initialUserCount;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackages(true, "com.daydoodle.daydoodle")
                .addAsResource("META-INF/beans.xml", "META-INF/beans.xml");
    }

    @Inject
    private UserBean userBean;

    @BeforeEach
    public void setup() {
        // Get the initial number of users in the database
        initialUserCount = userBean.findAllUsers().size();

        // Add a test user
        userBean.createUser("testuser", "testuser@example.com", "password");
    }

    @Test
    public void testServiceInjection() {
        assertNotNull(userBean, "UserBean should be injected.");
    }

    @Test
    public void testFindAllUsers() {
        List<UserDto> userDtoList = userBean.findAllUsers();
        assertNotNull(userDtoList, "User list should not be null.");
        assertEquals(initialUserCount + 1, userDtoList.size(), "User list should contain the initial number of users plus one.");

        // Verify the details of the test user
        UserDto user = userBean.findUserByUsername("testuser", userDtoList);
        assertNotNull(user, "Test user should be found in the user list.");
        assertEquals("testuser", user.getUsername(), "Username should be 'testuser'.");
        assertEquals("testuser@example.com", user.getEmail(), "Email should be 'testuser@example.com'.");
    }
}
