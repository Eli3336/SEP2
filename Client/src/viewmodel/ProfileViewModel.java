package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.mediator.Model;
import model.domain.Movie;
import model.domain.Rental;
import model.domain.User;

import java.util.ArrayList;

/**
 * The Class ProfileViewModel.
 */
public class ProfileViewModel
{
  private Model model;
  private ViewModelStateUser userState;
  private StringProperty userNameLabel;
  private StringProperty nameLabel;
  private StringProperty phoneNoLabel;
  private StringProperty ageLabel;
  private ObservableList<String> rentedMovies;
  private ViewModelState state;

  /**
   * @param model the model to set
   * @param userState the userState to set
   * @param state the state to set
   *
   *     Constructor for ProfileViewModel.
   */
  public ProfileViewModel(Model model, ViewModelState state, ViewModelStateUser userState)
  {
    this.model = model;
    this.userState = userState;
    this.state = state;

    this.rentedMovies = FXCollections.observableArrayList();

      this.userNameLabel = new SimpleStringProperty();
      this.nameLabel = new SimpleStringProperty();
      this.phoneNoLabel = new SimpleStringProperty();
      this.ageLabel = new SimpleStringProperty();
  }

  /**
   * @return the userNameLabel
   *
   *         Getter for userNameLabel.
   */
  public StringProperty getUsernameProperty()
  {
    return userNameLabel;
  }

  /**
   * @return the userNameLabel
   *
   *         Getter for userNameLabel.
   */
  public StringProperty getNameProperty()
  {
    return nameLabel;
  }

  /**
   * @return the phoneNoLabel
   *
   *         Getter for phoneNoLabel.
   */
  public StringProperty getPhoneNoProperty()
  {
    return phoneNoLabel;
  }

  /**
   * @return the ageLabel
   *
   *         Getter for ageLabel.
   */
  public StringProperty getAgeProperty()
  {
    return ageLabel;
  }

  /**
   * Reset the view.
   */
  public void reset()
  {
    rentedMovies.clear();

    rentedMovies = getRentedMovies();

    User selected = userState.getUser();
    userNameLabel.set(""+selected.getUserName());
    phoneNoLabel.set(""+selected.getPhoneNumber());
    ageLabel.set(""+selected.getAge());
    nameLabel.set(""+selected.getName());

  }

  /**
   * @return the rentedMovies
   *
   * Sets the ListView to the rented movies.
   */
  public ObservableList<String> getRentedMovies()
  {
    rentedMovies.clear();
    ArrayList<Rental> rentals = model.getRentalsWithUser(userState.getUser().getUserName());
    if(!rentals.isEmpty())
    {
      for (int i = 0; i < rentals.size(); i++)
      {
        rentedMovies.add(rentals.get(i).getRentedMovie().getTitle());
      }
    }
    return rentedMovies;
  }

  /**
   * @param string Movie title
   * @return  Whether the movie exists or not.
   */
  public boolean showDetails(String string)
  {
    Movie movie = null;

    for (int i = 0; i < model.getAllMovies().size(); i++)
    {
      if (model.getAllMovies().get(i).getTitle().equals(string))
      {
        movie = model.getAllMovies().get(i);

        if (movie != null)
        {
          state.setMovie(movie);
          return true;
        }
        else
        {
          state.setMovie(null);
          return false;
        }
      }
    }
    return false;
  }
}
