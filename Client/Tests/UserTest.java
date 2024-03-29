import model.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest
{

  private User user1;

  @BeforeEach void setUp()
  {

    user1 = new User("Test", "Test", "Test", "12345678", 18);
  }

  @AfterEach void tearDown()
  {
  }

  @Test void setInvalidPhoneNumber()
  {
    assertThrows(IllegalArgumentException.class, () -> {
      user1.setPhoneNumber("");
    });
  }

  @Test void setInvalidName()
  {
    assertThrows(IllegalArgumentException.class, () -> {
      user1.setName("");
    });

  }

  @Test void setInvalidUserName()
  {
    assertThrows(IllegalArgumentException.class, () -> {
      user1.setUserName("");
    });
  }

  @Test void setNullUserName()
  {
    assertThrows(IllegalArgumentException.class, () -> {
      user1.setUserName(null);
    });

  }

  @Test void setNullName()
  {
    assertThrows(IllegalArgumentException.class, () -> {
      user1.setName(null);
    });

  }

  @Test void setNullPhoneNumber()
  {
    assertThrows(IllegalArgumentException.class, () -> {
      user1.setPhoneNumber(null);
    });

  }

}

