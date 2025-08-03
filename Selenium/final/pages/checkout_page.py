from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

class CheckoutPage:

    def __init__(self, driver):
        self.driver = driver
        self.first_name_field = (By.ID, "first-name")
        self.last_name_field = (By.ID, "last-name")
        self.postal_code_field = (By.ID, "postal-code")
        self.continue_button = (By.ID, "continue")
        self.finish_button = (By.ID, "finish")
        self.complete_header = (By.CLASS_NAME, "complete-header")

    def fill_checkout_information(self, first_name, last_name, postal_code):
        WebDriverWait(self.driver, 10).until(EC.presence_of_element_located(self.first_name_field))
        self.driver.find_element(*self.first_name_field).send_keys(first_name)
        self.driver.find_element(*self.last_name_field).send_keys(last_name)
        self.driver.find_element(*self.postal_code_field).send_keys(postal_code)

    def continue_checkout(self):
        WebDriverWait(self.driver, 10).until(EC.presence_of_element_located(self.continue_button))
        self.driver.find_element(*self.continue_button).click()

    def finish_checkout(self):
        WebDriverWait(self.driver, 10).until(EC.presence_of_element_located(self.finish_button))
        self.driver.find_element(*self.finish_button).click()

    def get_success_message(self):
        WebDriverWait(self.driver, 10).until(EC.presence_of_element_located(self.complete_header))
        return self.driver.find_element(*self.complete_header).text