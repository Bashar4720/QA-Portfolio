from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

class InventoryPage:

    def __init__(self, driver):
        self.driver = driver
        self.inventory_container = (By.ID, "inventory_container")
        self.add_to_cart_button = (By.XPATH, "//button[contains(text(),'Add to cart')]")
        self.cart_icon = (By.CLASS_NAME, "shopping_cart_link")

    def is_loaded(self):
        WebDriverWait(self.driver, 10).until(EC.presence_of_element_located(self.inventory_container))
        return self.driver.find_element(*self.inventory_container).is_displayed()

    def add_first_item_to_cart(self):
        WebDriverWait(self.driver, 10).until(EC.presence_of_element_located(self.add_to_cart_button))
        self.driver.find_element(*self.add_to_cart_button).click()

    def go_to_cart(self):
        WebDriverWait(self.driver, 10).until(EC.presence_of_element_located(self.cart_icon))
        self.driver.find_element(*self.cart_icon).click()