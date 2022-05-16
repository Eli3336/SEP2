package viewmodel;

import model.mediator.Model;

/**
 * A class representing the model factory for the view models
 */
public class ViewModelFactory
{
  private MovieViewModel movieViewModel;
  private StartViewModel startViewModel;
  private HomeViewModel homeViewModel;
  private AdminViewModel adminViewModel;
  private ViewModelState state = null;
  private ViewModelStateUser stateUser = null;

  /**
   * @param model a Model variable that we need to create the viewModels
   *  An 1 argument constructor that creates the viewModels
   */
  public ViewModelFactory(Model model)
  {
    this.state = new ViewModelState();
    movieViewModel = new MovieViewModel(model, state, stateUser);
    startViewModel = new StartViewModel(model, stateUser);
    homeViewModel = new HomeViewModel(model, state);
    adminViewModel = new AdminViewModel(model, state);
  }

  /**
   * @return the viewModel
   * A getter for the MovieViewModel
   * @see viewmodel.MovieViewModel
   */
  public MovieViewModel getMovieViewModel()
  {
    return movieViewModel;
  }

  /**
   * @return the viewModel
   * A getter for the StartViewModel
   * @see viewmodel.StartViewModel
   */
  public StartViewModel getStartViewModel()
  {
    return startViewModel;
  }

  /**
   * @return the viewModel
   * A getter for the HomeViewModel
   * @see HomeViewModel
   */
  public HomeViewModel getHomeViewModel()
  {
    return homeViewModel;
  }

  /**
   * @return the viewModel
   *
   * A getter for the AdminViewModel
   */
  public AdminViewModel getAdminViewModel(){ return adminViewModel;}
}