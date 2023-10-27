package com.demoqa.tests;

import com.demoqa.models.AddBooksListModel;
import com.demoqa.models.DeleteBookModel;
import com.demoqa.models.IsbnModel;
import com.demoqa.models.LoginResponseModel;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.Cookie;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.demoqa.tests.TestData.credentials;

public class BooksListTest extends TestBase {

    @Test
    void addBookToCollectionTest() {

        LoginResponseModel loginResponse = authorizationApi.login(credentials);
        IsbnModel isbnModel = new IsbnModel();
        isbnModel.setIsbn("9781449331818");
        List<IsbnModel> isbnList = new ArrayList<>();
        isbnList.add(isbnModel);

        AddBooksListModel listOfBooks = new AddBooksListModel();
        listOfBooks.setUserId(loginResponse.getUserId());
        listOfBooks.setCollectionOfIsbns(isbnList);

        DeleteBookModel deleteBook = new DeleteBookModel();
        deleteBook.setIsbn("9781449331818");
        deleteBook.setUserId(loginResponse.getUserId());

        booksApi.deleteAllBooks(loginResponse);
        booksApi.addBook(loginResponse, listOfBooks);
        booksApi.deleteOneBook(loginResponse, deleteBook);

        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", loginResponse.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("token", loginResponse.getToken()));
        getWebDriver().manage().addCookie(new Cookie("expires", loginResponse.getExpires()));

        open("/profile");
        $("[id='see-book-Learning JavaScript Design Patterns']").shouldHave(text("Learning JavaScript Design Patterns"));

    }
}

