package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import viewmodel.ViewModelFactory;
/**
 * A class representing a handler for all the controllers which also has access to the modelFactory
 * @see viewmodel.ViewModelFactory
 */
public class ViewHandler
{
  private Scene currentScene;
  private Stage primaryStage;
  private ViewModelFactory viewModelFactory;
  private StartViewController startViewController;
  private MovieViewController movieViewController;
  private HomeViewController homeViewController;
  private AdminViewController adminViewController;
  private ProfileViewController profileViewController;
  private SearchViewController searchViewController;

  /**
   * @param viewModelFactory a viewModelFactory variable for delegating the work to the viewModels
   *
   *  An 1 argument constructor to initialize the viewModelFactory and the currentScene
   */
  public ViewHandler(ViewModelFactory viewModelFactory)
  {
    this.viewModelFactory = viewModelFactory;
    this.currentScene = new Scene(new Region());
  }

  /**
   * @param primaryStage a Stage variable to open the GUI
   *
   *  A method called to open the first GUI window, setting the primary stage and calling the method openView inside the viewHandler
   *  @see view.ViewHandler
   */
  public void start(Stage primaryStage)
  {
    this.primaryStage = primaryStage;
    openView("start");
  }

  /**
   * @param id a String variable that allows us to know which GUI window to open
   *
   *  A method called everytime we open a GUI window.
   *    Setting the root to null and using a switch to assign the proper value for the root using different load methods
   *       based on the id parameter
   * @see view.ViewHandler
   *    Setting the root for the current scene and setting the stage with a title, scene, width, height. Showing the stage.
   */
  public void openView(String id)
  {
    Region root = null;
    switch (id)
    {
      case "start":
        root = loadStartView("start.fxml");
        break;
      case "movie":
        root = loadMovieView("movie.fxml");
        break;
      case "home":
        root = loadHomeView("home.fxml");
        break;
      case "admin":
        root = loadAdminView("admin.fxml");
        break;
      case "profile":
        root = loadProfileView("profile.fxml");
        break;
      case "search":
        root = loadSearchView("search.fxml");
        break;
    }
    currentScene.setRoot(root);
    String title = "";
    if (root.getUserData() != null)
    {
      title += root.getUserData();
    }
    primaryStage.setTitle(title);
    primaryStage.setScene(currentScene);
    primaryStage.setWidth(root.getPrefWidth());
    primaryStage.setHeight(root.getPrefHeight());
    primaryStage.show();
  }

  /**
   * A method used to close the stage i.e. to close the GUI windows without terminating the run.
   */
  public void closeView()
  {
    primaryStage.close();
  }

  /**
   * @param fxmlFile a String variable that is the name of the fxml file we want to open
   * @return the root for the controller, so that in the openView method we can utilize it
   * @see view.ViewHandler
   *
   * A method to get the StartViewController's root and initialize the window.
   *  If it is the first we are loading the controller, we will get the controller and root from the loader and call the method init
   *  @see view.StartViewController
   *  If it is not the first time we are loading, we will call the reset method and return the controller's root
   *  @see view.StartViewController
   */
  private Region loadStartView(String fxmlFile)
  {
    Region root = null;
    if (startViewController == null)
    {
      // load from FXML
      try
      {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        root = loader.load();
        startViewController = loader.getController();
        currentScene.getStylesheets().add(
            String.valueOf(getClass().getResource("style.css")));

        startViewController.init(this,  viewModelFactory.getStartViewModel(), root);

      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      // reset window
      startViewController.reset();
    }
    return startViewController.getRoot();
  }
  /**
   * @param fxmlFile a String variable that is the name of the fxml file we want to open
   * @return the root for the controller, so that in the openView method we can utilize it
   * @see view.ViewHandler
   *
   * A method to get the MovieViewController's root and initialize the window.
   *  If it is the first we are loading the controller, we will get the controller and root from the loader and call the method init
   *  @see view.MovieViewController
   *  If it is not the first time we are loading, we will call the reset method and return the controller's root
   *  @see view.MovieViewController
   */
  private Region loadMovieView(String fxmlFile)
  {
    Region root = null;
    if (movieViewController == null)
    {
      // load from FXML
      try
      {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        root = loader.load();
        movieViewController = loader.getController();
        viewModelFactory.getMovieViewModel().reset();
        movieViewController.init(this, viewModelFactory.getMovieViewModel(), root);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      // reset window
      movieViewController.reset();
    }
    return movieViewController.getRoot();
  }

  /**
   * @param fxmlFile  a String variable that is the name of the fxml file we want to open
   * @return the root for the controller, so that in the openView method we can utilize it
   * @see view.ViewHandler
   *
   * A method to get the HomeViewController's root and initialize the window.
   * If it is the first we are loading the controller, we will get the controller and root from the loader and call the method init
   * @see view.HomeViewController
   * If it is not the first time we are loading, we will call the reset method and return the controller's root
   * @see view.HomeViewController
   */
  private Region loadHomeView(String fxmlFile)
  {
    Region root = null;
    if (homeViewController == null)
    {
      // load from FXML
      try
      {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        root = loader.load();
        homeViewController = loader.getController();
        homeViewController.init(this, viewModelFactory.getHomeViewModel(), root);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      // reset window
      homeViewController.reset();
    }
    return homeViewController.getRoot();
  }
  /**
   * @param fxmlFile a String variable that is the name of the fxml file we want to open
   * @return the root for the controller, so that in the openView method we can utilize it
   * @see view.ViewHandler
   *
   * A method to get the AdminViewController's root and initialize the window.
   *  If it is the first we are loading the controller, we will get the controller and root from the loader and call the method init
   *  @see view.AdminViewController
   *  If it is not the first time we are loading, we will call the reset method and return the controller's root
   *  @see view.AdminViewController
   */
  private Region loadAdminView(String fxmlFile)
  {
    Region root = null;
    if (adminViewController == null)
    {
      // load from FXML
      try
      {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        root = loader.load();
        adminViewController = loader.getController();
        adminViewController.init(this,  viewModelFactory.getAdminViewModel(), root);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      // reset window
      adminViewController.reset();
    }
    return adminViewController.getRoot();
  }

  /**
   * @param fxmlFile a String variable that is the name of the fxml file we want to open
   * @return the root for the controller, so that in the openView method we can utilize it
   * @see view.ViewHandler
   *
   * A method to get the ProfileViewController's root and initialize the window.
   *  If it is the first we are loading the controller, we will get the controller and root from the loader and call the method init
   *  @see view.ProfileViewController
   *  If it is not the first time we are loading, we will call the reset method and return the controller's root
   *  @see view.ProfileViewController
   */
  private Region loadProfileView(String fxmlFile)
  {
    Region root = null;
    if (profileViewController == null)
    {
      // load from FXML
      try
      {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        root = loader.load();
        profileViewController = loader.getController();
        viewModelFactory.getProfileViewModel().reset();
        profileViewController.init(this,  viewModelFactory.getProfileViewModel(), root);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      // reset window
      profileViewController.reset();
    }
    return profileViewController.getRoot();
  }
  /**
   * @param fxmlFile a String variable that is the name of the fxml file we want to open
   * @return the root for the controller, so that in the openView method we can utilize it
   * @see view.ViewHandler
   *
   * A method to get the SearchViewController's root and initialize the window.
   *  If it is the first we are loading the controller, we will get the controller and root from the loader and call the method init
   *  @see view.SearchViewController
   *  If it is not the first time we are loading, we will call the reset method and return the controller's root
   *  @see view.SearchViewController
   */
  private Region loadSearchView(String fxmlFile)
  {
    Region root = null;
    if (searchViewController == null)
    {
      // load from FXML
      try
      {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        root = loader.load();
        searchViewController = loader.getController();
        searchViewController.init(this,  viewModelFactory.getSearchViewModel(), root);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    else
    {
      // reset window
      searchViewController.reset();
    }
    return searchViewController.getRoot();
  }
}
