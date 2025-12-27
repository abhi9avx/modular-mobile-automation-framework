package pages.common;

public interface LoginPage {

    // High-level business action
    void login(String username, String password);

    // Individual actions (optional but useful)
    void enterUsername(String username);

    void enterPassword(String password);

    void tapLogin();

    // For validation
    String getErrorMessage();
}
