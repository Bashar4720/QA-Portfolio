from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

class CartPage:

    def __init__(self, driver):
        self.driver = driver
        self.remove_button = (By.XPATH, "//button[contains(text(),'Remove')]")
        self.checkout_button = (By.ID, "checkout")

    def remove_item(self):
        WebDriverWait(self.driver, 10).until(EC.presence_of_element_located(self.remove_button))
        self.driver.find_element(*self.remove_button).click()

    def click_checkout(self):
        WebDriverWait(self.driver, 10).until(EC.presence_of_element_located(self.checkout_button))
        self.driver.find_element(*self.checkout_button).click()