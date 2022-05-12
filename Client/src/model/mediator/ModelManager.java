package model.mediator;

import model.domain.*;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;

/**
 * The ModelManager class is the main class of the model. It is responsible for managing the model.
 */
public class ModelManager implements Model
{
  private MovieList movieList;
  private RentalList rentalList;
  private PersonList personList;

  private SEPPersistence database;

  /**
   * Empty constructor which initializes the movieList, rentalList, and personList
   */
  public ModelManager()
  {
    this.movieList = new MovieList();
    this.rentalList = new RentalList();
    this.personList = new PersonList();
  }

  /**
   * @param name        the person's name to be added
   * @param username    the person's username to be added
   * @param password    the person's password to be added
   * @param phoneNumber the person's phone number to be added
   * @param dob         the person's date of birth to be added
   * @param type        the person's type to be added
   *                    A method to add a person to the person list
   */
  public void addPerson(String name, String username, String password, String phoneNumber, Date dob, String type)
  {
    System.out.println("I am in the model manager method");
    personList.addPerson(name, username, password, phoneNumber, dob, type);
  }

  /**
   * @param movie the movie to be added to the movie list
   *              A method to add a movie to the movie list
   */
  public void addMovie(Movie movie)
  {
    movieList.addMovie(movie);
  }

  @Override public void removeMovie(Movie movie)
  {
    movieList.removeMovie(movie);
  }

  /**
   * @return the top 10 movies with the highest average ratings
   * A method to return the top 10 movies with the highest average ratings
   */
  @Override public ArrayList<Movie> getTop10TopRatedMovies()
  {
    return movieList.getTop10TopRatedMovies();
  }

  @Override public ArrayList<Movie> getTrendingMovies()
  {
    return movieList.getTrendingMovies();
  }

  /**
   * @return the movie list
   * A method to return the movie list
   */
  public ArrayList<Movie> getAllMovies()
  {
    return movieList.getAllMovies();
  }

  /**
   * @param listener the listener to be added
   *                 A method to add a listener
   */
  @Override public void addListener(PropertyChangeListener listener)
  {
  }

  /**
   * @param movie          the movie to be rented
   * @param expirationDate the expiration date of the rental
   *                       A method to add a rental to the rental list
   */
  @Override public void addRental(Movie movie, Date expirationDate, User user)
  {
    rentalList.addRental(movie, expirationDate, user);
  }

  /**
   * @return all rentals
   * A method to return all rentals
   */
  @Override public ArrayList<Rental> getAllRentals()
  {
    return rentalList.getAllRentals();
  }

  @Override public void cancelRental(String title, User user)
  {

    rentalList.removeRental(title, user);
  }


/*
--For the next sprint :)
  @Override public ArrayList<Rental> getRentalsWithUser(User user)
  {
    return rentalList.getRentalsWithUser(user);
  }

  @Override public ArrayList<Rental> getRentalsWithMovie(Movie movie)
  {
    return rentalList.getRentalsWithMovie(movie);
  }
*/

  /**
   * @param listener the listener to be removed
   *                 A method to remove a listener
   */
  public void removeListener(PropertyChangeListener listener)
  {
  }

  /**
   * @param title a String variable representing the title of a movie
   *              //@param averageRating a String variable representing the average rating of a movie
   *              A method to check if the user can rent the movie
   */
  @Override public void rentMovie(String title, User user)
  {
    long rentalDate = System.currentTimeMillis() + (86400 * 7 * 1000);
    Date expirationDate = new Date(rentalDate);
    addRental(movieList.getMovieWithTitle(title), expirationDate, user);
  }

  /**
   * @param username string variable
   * @param password string variable
   *                 Checks if the username exists and has a matching password
   */
  @Override public void login(String username, String password)
  {
    System.out.println("I'm in the login model. Person list: " + personList.getPersons());
    boolean exists = false;
    for (int i = 0; i < personList.getPersons().size(); i++)
    {
      if (username.equals(personList.getPersons().get(i).getUserName()))
      {
        exists = true;
        if (!(password.equals(personList.getPersons().get(i).getPassword())))
        {
          throw new IllegalArgumentException("password is incorrect");
        }
      }
    }
    if (!exists)
      throw new IllegalArgumentException("The username doesn't exist");
  }

  /**
   * @param name        a String variable representing the name of the user
   * @param userName    a String variable representing the username chosen
   * @param password    a String variable representing the password chosen
   * @param phoneNumber a String variable representing the phone number of the user
   * @param dob         a String variable representing the date of birth
   */
  @Override public void createUser(String name, String userName, String password, String phoneNumber, String dob)
  {
    for (int i = 0; i < personList.getPersons().size(); i++)
    {
      if (personList.getPersons().get(i).getUserName().equals(userName))
        throw new IllegalStateException("Username already exists!");
    }
    personList.addPerson(name, userName, password, phoneNumber, dob, "user");
  }

  @Override public String checkPerson(String userName, String password)
  {

    for (int i = 0; i < personList.getPersons().size(); i++)
    {
      if (personList.getPersons().get(i).getUserName().equals(userName))
      {
        if (personList.getPersons().get(i).getPassword().equals(password))
        {
          return personList.getPersons().get(i).getType();
        }
      }
    }

    return null;
  }

  @Override public User getUser(String userName)
  {

    for (int i = 0; i < personList.getPersons().size(); i++)
    {
      if (personList.getPersons().get(i).getUserName().equals(userName))
      {
        return (User) personList.getPersons().get(i);
      }
    }

    return null;
  }
}