package tests;

import common.BaseTest;
import libs.Database;
import models.MovieModel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class MovieTests extends BaseTest {

    private Database db;

    @BeforeMethod
    public void setup(){
        login
                .open()
                .with("root@carol.com", "pwd123");

        side.loggedUser().shouldHave(text("Carol"));
    }

    @BeforeSuite
    public void delorean() {
        db = new Database();
        db.resetMovies();
    }

    @Test
    public void shouldRegisterANewMovie() {
        MovieModel movieData = new MovieModel(
                "Jumanji - Proxima fase",
                "Pré-venda",
                2020,
                "16/01/2020",
                Arrays.asList("The Rock", "Jack Black", "Kevin Hart", "Karen Gillan", "Danny DeVito"),
                "Tentando a revisitar o mundo de Jumanji, Spencer decide "
                        + "consertar o bug no jogo do game que permite que sejam transportados ao local",
                "jumanji2.jpg"
        );

        movie
                .add()
                .create(movieData)
                .items().findBy(text(movieData.title)).shouldBe(visible);
    }

    @Test
    public void shouldSearchTwoMovies()
    {
        movie.search("Batman").items().shouldHaveSize(2);
    }
}
