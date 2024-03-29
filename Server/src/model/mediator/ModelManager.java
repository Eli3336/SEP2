package model.mediator;

import model.domain.*;

import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * The ModelManager class is the main class of the model. It is responsible for managing the model. Implements the model
 * @see Model
 */
public class ModelManager implements Model
{

  private Server server;


  private MovieList movieList;
  private RentalList rentalList;
  private PersonList personList;

  /**
   * Empty constructor which initializes the movieList, rentalList, personList, and database
   */
  public ModelManager() throws RemoteException
  {
    this.server = new Server();
    this.movieList = new MovieList();
    this.rentalList = new RentalList();
    this.personList = new PersonList();
    ArrayList<Person> list = server.getAllPersons();
    for(int i=0;i<list.size();i++)
    {
      personList.addPerson(list.get(i));
      System.out.println(list.get(i));
    }

  }

  /**
   * @throws SQLException exception
   *         A method to get the movies from the database and store it
   */
  public void getDatabaseMovies() throws SQLException, RemoteException
  {
    MovieList movies = new MovieList();
    ArrayList<Movie> dbMovies = server.getAllMovies();
    if (!dbMovies.isEmpty())
    {
      for (int i = 0; i < dbMovies.size(); i++)
      {
        movies.addMovie(dbMovies.get(i));
      }
      this.movieList = movies;
    }
  }

  /**
   * @throws SQLException exception
   *         A method to get the information from the database and store it
   */
  public void getAllInfo() throws SQLException, RemoteException
  {
    MovieList movies = new MovieList();
    ArrayList<Movie> dbMovies = server.getAllMovies();
    if (!dbMovies.isEmpty())
    {
      for (int i = 0; i < dbMovies.size(); i++)
      {
        movies.addMovie(dbMovies.get(i));
      }
      this.movieList = movies;
    }

    PersonList persons = new PersonList();
    ArrayList<Person> dbPersons = server.getAllPersons();
    if (!dbPersons.isEmpty())
    {
      for (int i = 0; i < dbPersons.size(); i++)
      {
        persons.addPerson(dbPersons.get(i).getName(),
            dbPersons.get(i).getUserName(), dbPersons.get(i).getPassword(),
            dbPersons.get(i).getPhoneNumber(), dbPersons.get(i).getAge(),
            dbPersons.get(i).getType());
      }
      this.personList = persons;
    }

    Date date = new Date();
    RentalList rentals = new RentalList();
    ArrayList<Rental> dbRentals = server.getAllRentals();
    if (!dbRentals.isEmpty())
    {
      for (int i = 0; i < dbRentals.size(); i++)
      {
        if(dbRentals.get(i).getExpirationDate().before(date))
        {
          server.removeRental(dbRentals.get(i).getRentedMovie().getTitle(), dbRentals.get(i).getUserName());
        }
        else
        {
          rentals.addRental(dbRentals.get(i).getRentedMovie(), dbRentals.get(i).getExpirationDate(), dbRentals.get(i).getUser());
        }
      }
      this.rentalList = rentals;
    }
  }

  /**
   * @param name        the person's name to be added
   * @param username    the person's username to be added
   * @param password    the person's password to be added
   * @param phoneNumber the person's phone number to be added
   * @param age         the person's age to be added
   * @param type        the person's type to be added
   *                    A method to add a person to the person list
   */
  public void addPerson(String name, String username, String password,
      String phoneNumber, int age, String type)
  {
    personList.addPerson(name, username, password, phoneNumber, age, type);

  }

  /**
   * @return an array list containing the movies that are not rented
   */
  @Override public ArrayList<Movie> getNotRentedMovies()
  {
    int deleted = 0;
    ArrayList<Movie> allMovies = getAllMovies();
    for (int i = 0; i < getAllMovies().size(); i++)
    {
      deleted = 0;
      Movie toCheck = getAllMovies().get(i);
      for (int j = 0; j < getAllRentals().size() && deleted == 0; j++)
      {
        if (toCheck.equals(getAllRentals().get(j).getRentedMovie()))
        {
          allMovies.remove(toCheck);
          deleted = 1;
        }
      }
    }
    return allMovies;
  }

  /**
   * @param title the title of the movie
   * @return a boolean true if the movie is rented, false if it isn't
   */
  public boolean checkMovieIsRented(String title)
  {
    ArrayList<Rental> rentals = rentalList.getRentalsWithMovie(
        getMovieWithTitle(title));
    return !rentals.isEmpty();
  }

  /**
   * @param movie the movie to be added to the movie list
   *              A method to add a movie to the movie list
   */
  public void addMovie(Movie movie)
  {

    movieList.addMovie(movie);
    try
    {
      server.addMovie(movie);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * @param movie the movie to be removed
   *              A method to remove a movie from the movie list
   */
  @Override public void removeMovie(Movie movie)
  {
    movieList.removeMovie(movie);
    try
    {
      server.removeMovie(movie);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
    try
    {
      getDatabaseMovies();
    }
    catch (SQLException | RemoteException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * @return the top 10 movies with the highest average ratings
   * A method to return the top 10 movies with the highest average ratings
   */
  @Override public ArrayList<Movie> getTop10TopRatedMovies()
  {
    return movieList.getTop10TopRatedMovies();
  }

  /**
   * @param title the title of the movie
   * @return an array list with movies that contain that title
   */
  @Override public ArrayList<Movie> getMovieLike(String title)
  {
    return movieList.getMovieLike(title);
  }

  /**
   * @param title the title of the movie
   * @return a movie object with that title
   */
  @Override public Movie getMovieWithTitle(String title)
  {
    ArrayList<Movie> movies = movieList.getAllMovies();
    for (int i = 0; i <movies.size(); i++)
    {
      if (movies.get(i).getTitle().equals(title))
      {
        return movies.get(i);
      }
    }
    return null;
  }

  /**
   * @return the trending movies
   * A method to return the trending movies
   */
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
   * @param genre the genre
   * @return an array list of movies with the genre
   */
  @Override public ArrayList<Movie> getMoviesWithGenre(String genre)
  {
    return movieList.getMoviesWithGenre(genre);
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
    try
    {
      server.addRental(movie, expirationDate, user);
    }
    catch (RemoteException e)
    {
      e.printStackTrace();
    }
  }

  /**
   * @return all rentals
   * A method to return all rentals
   */
  @Override public ArrayList<Rental> getAllRentals()
  {
    return rentalList.getAllRentals();
  }

  /**
   * @param title the title of the movie
   * @param username  the username
   *              A method to remove a rental from the rental list
   */
  @Override public void cancelRental(String title, String username)
  {
    rentalList.removeRental(title, username);
    server.removeRental(title, username);
  }

  /**
   * @param username the username
   * @return an array list with rental which contains the user
   */
  @Override public ArrayList<Rental> getRentalsWithUser(String username)
  {
    return rentalList.getRentalsWithUser(username);
  }

  /**
   * @param listener the listener to be removed
   *                 A method to remove a listener
   */
  public void removeListener(PropertyChangeListener listener) {}

  /**
   * @param title a String variable representing the title of a movie
   * @param user  the user
   *       A method to rent the movie
   */
  @Override public void rentMovie(String title, User user)
  {
    long rentalDate = System.currentTimeMillis() + (86400 * 7 * 1000);
    Date expirationDate = new Date(rentalDate);
    rentalList.addRental(movieList.getMovieWithTitle(title), expirationDate, user);

    try
    {
      server.addRental(getMovieWithTitle(title), expirationDate, user);
    }
    catch (RemoteException e)
    {
      throw new RuntimeException(e);
    }
  }

  /**
   * @param username string variable
   * @param password string variable
   *                 Checks if the username exists and has a matching password
   */
  @Override public void login(String username, String password)
  {
    Person person=new User("n",username,password,"123", 12);
    person.setPassword(password);
    person.setUserName(username);
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
   * @param age         a String variable representing the age of the user
   *                    A method to create a new User and add it to the person list
   */
  @Override public void createUser(String name, String userName,
      String password, String phoneNumber, String age)
  {
    for (int i = 0; i < personList.getPersons().size(); i++)
    {
      if (personList.getPersons().get(i).getUserName().equals(userName))
        throw new IllegalStateException("Username already exists!");
    }
    Person person= new User(name, userName, password, phoneNumber,
        Integer.parseInt(age));
    person.setAge(Integer.parseInt(age));
    person.setUserName(userName);
    person.setPassword(password);
    person.setName(name);
    person.setPhoneNumber(phoneNumber);
    personList.addPerson(name, userName, password, phoneNumber,
        Integer.parseInt(age), "user");
    server.addUser(name, phoneNumber, userName, password,
        Integer.parseInt(age));
  }

  /**
   * @param userName the username of the person
   * @param password the password of the person
   * @return a String variable, the type of the person (admin, user) or null in case it does not found the person
   */
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

  /**
   * @param userName the username
   * @return a User variable
   * A method to get the user by username (username is unique). Returns null if it does not exist
   */
  @Override public User getUser(String userName)
  {
    User user = new User("", "", "", "", 0);
    for (int i = 0; i < personList.getPersons().size(); i++)
    {
      Person person = personList.getPersons().get(i);
      if (userName.equals(person.getUserName()))
      {
        user.setName(person.getName());
        user.setUserName(person.getUserName());
        user.setPassword(person.getPassword());
        user.setPhoneNumber(person.getPhoneNumber());
        user.setAge(person.getAge());
        return user;
      }
    }
    return null;
  }

  /**
   * @param title the title of the movie
   * @return a boolean value to see it we can add a movie
   */
  @Override public boolean validateAddMovie(String title)
  {
    ArrayList<Movie> movies = movieList.getAllMovies();
    for (int i = 0; i < movies.size(); i++)
    {
      if (movies.get(i).getTitle().equals(title))
      {
        return false;
      }
    }
    return true;
  }

  /**
   * @param movie the movie
   * @return an array list with the comments for the movie
   */
  public ArrayList<Review> getReviewsForMovie(Movie movie)
  {
    return movieList.getReviewsForMovie(movie);
  }

  /**
   * @param comment the comment
   * @param star    the rating
   * @param title   the title of the movie
   *                A method to leave a review
   */
  @Override public void leaveReview(String comment, int star, String title,
      String user)
  {
    ArrayList<String> badwords = new ArrayList<>();

    badwords.add("fuck");
    badwords.add("shit");
    badwords.add("bitch");
    badwords.add("asshole");
    badwords.add("ass");
    badwords.add("dick");
    badwords.add("cock");
    badwords.add("pussy");
    badwords.add("nigger");
    badwords.add("nigga");
    badwords.add("hitler");
    badwords.add("Helvete");
    badwords.add("puta");
    badwords.add("Kurwa");
    badwords.add("penis");

    for (int i = 0; i < badwords.size(); i++)
    {
      if (comment.contains(badwords.get(i)))
      {
        throw new IllegalArgumentException("Swear words are not allowed!");
      }
    }
    movieList.getMovieWithTitle(title).addReview(comment, star);

    server.addReview(title, star, comment, user);
  }
}

