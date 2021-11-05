@all @jira(JENKINS-01)
Feature: Main puppies website functionality

  Background: Navigate to puppies.herokuapp website
    Given user is on puppies.herokuapp landing page

  @id(ts01) @smoke @wip
  Scenario: Adopt one puppy
    As puppy site user, I want to adopt one puppy from puppy list.

    Given I select the puppy named "Hanna" by clicking on View Details
    And click on "Adopt Me!" on "Puppy Details" page
    And click on "Complete the Adoption" on "Shopping Cart" page
    When I place the order with the following details:
    | name     | John Wick                     |
    | address  | 10005, 82 Beaver St, New York |
    | email    | john.wick@thecontinental.org  |
    | pay_type | Credit card                   |
    Then I should be redirected to the homepage
    And the message "Thank you for adopting a puppy!" should be displayed

  @id(ts02) @wip
  Scenario: Adopt two puppies
    As puppy site user, I want to adopt two puppies from puppy list.

    Given I select the puppy named "Brook" by clicking on View Details
    And click on "Adopt Me!" on "Puppy Details" page
    And click on "Adopt Another Puppy" on "Shopping Cart" page
    And select the puppy named "Hanna" by clicking on View Details
    And navigate to Checkout page
    When I place the order with the following details:
      | name     | John Wick                     |
      | address  | 10005, 82 Beaver St, New York |
      | email    | john.wick@thecontinental.org  |
      | pay_type | Credit card                   |
    Then I should be redirected to the homepage
    And the message "Thank you for adopting a puppy!" should be displayed

  @id(ts03) @smoke @wip
  Scenario: Two puppies adoption correct amount
    As puppy site user, I want to see the correct amount of my purchase.

    Given I want to adopt the following puppies:
    | Brook |
    | Hanna |
    When I add them to my shopping cart
    Then total sum of the cost of the puppies must be displayed on the Shopping Cart page

  @id(ts04)
  Scenario: Puppies list pagination
    As puppy site user, I want to see maximum 4 puppies per page

    When I navigate among all the views of the list
    Then at most 4 records should be displayed on each puppy list

  @id(ts05)
  Scenario: Puppies list displayed correctly
    As puppy site user, I want to see displayed in each puppy record of the puppies list
    the name, breed and sex of the puppy.

    When I navigate among all the views of the list
    Then each puppy record should display the name, breed and sex

  @id(ts06) @wip
  Scenario: Empty shopping cart
    As puppy site user, I want to empty my shopping cart before purchase.

    Given I want to adopt the following puppies:
    | Brook    |
    | Hanna    |
    And I add them to my shopping cart
    When I click on "Change your mind" on "Shopping Cart" page
    Then I should be redirected to the homepage
    And the message "Your car is currently empty" should be displayed

  @id(ts07) @smoke
  Scenario: Page menu
    Page should contain 6 items menu.

    Then the following menu items should be displayed on the Landing page:
    | Adopt a Puppy   |
    | Learn           |
    | Animal Shelters |
    | Classifieds     |
    | Message Boards  |
    | Pet News        |

