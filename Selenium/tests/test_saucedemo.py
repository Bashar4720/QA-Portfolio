import time

from pages.login_page import LoginPage
from pages.inventory_page import InventoryPage
from pages.cart_page import CartPage
from pages.checkout_page import CheckoutPage

# בדיקה 1 - התחברות תקינה
def test_valid_login(driver):
    login_page = LoginPage(driver)
    login_page.login("standard_user", "secret_sauce")
    inventory_page = InventoryPage(driver)
    assert inventory_page.is_loaded()

# בדיקה 2 - התחברות עם סיסמה שגויה
def test_invalid_password(driver):
    login_page = LoginPage(driver)
    login_page.login("standard_user", "wrong_password")
    assert "Username and password do not match any user in this service" in login_page.get_error_message()

# בדיקה 3 - הוספת מוצר לסל
def test_add_item_to_cart(driver):
    login_page = LoginPage(driver)
    login_page.login("standard_user", "secret_sauce")
    inventory_page = InventoryPage(driver)
    inventory_page.add_first_item_to_cart()
    inventory_page.go_to_cart()
    assert "REMOVE" in driver.page_source.upper()

# בדיקה 4 - הסרת מוצר מהסל
def test_remove_item_from_cart(driver):
    login_page = LoginPage(driver)
    login_page.login("standard_user", "secret_sauce")
    inventory_page = InventoryPage(driver)
    inventory_page.add_first_item_to_cart()
    inventory_page.go_to_cart()
    cart_page = CartPage(driver)
    cart_page.remove_item()
    assert "Your cart is empty" in driver.page_source or "continue shopping" in driver.page_source.lower()

# בדיקה 5 - תהליך רכישה מלא
def test_complete_checkout(driver):
    login_page = LoginPage(driver)
    login_page.login("standard_user", "secret_sauce")
    inventory_page = InventoryPage(driver)
    inventory_page.add_first_item_to_cart()
    inventory_page.go_to_cart()
    cart_page = CartPage(driver)
    cart_page.click_checkout()
    checkout_page = CheckoutPage(driver)
    checkout_page.fill_checkout_information("Test", "User", "12345")
    checkout_page.continue_checkout()
    checkout_page.finish_checkout()
    assert "THANK YOU FOR YOUR ORDER" in checkout_page.get_success_message().upper()

# בדיקה 6 - ניסיון Checkout על סל ריק (טסט יצירתי)
def test_checkout_empty_cart(driver):
    login_page = LoginPage(driver)
    login_page.login("standard_user", "secret_sauce")
    inventory_page = InventoryPage(driver)
    inventory_page.go_to_cart()
    cart_page = CartPage(driver)
    cart_page.click_checkout()
    checkout_page = CheckoutPage(driver)
    checkout_page.fill_checkout_information("Test", "User", "12345")
    checkout_page.continue_checkout()
    checkout_page.finish_checkout()
    assert "THANK YOU FOR YOUR ORDER" in checkout_page.get_success_message().upper()