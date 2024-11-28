# Stray Pets Adoption Application Project

## Overview
This application will facilitate the adoption of stray pets by connecting potential adopters with local shelters and rescue organizations. Users will be able to browse available pets, submit adoption applications, and receive updates on their application status. Additionally, adopters can upload updates on their adopted pets’ status, helping to create a community of responsible pet owners. To maximum animal welfare, we want to keep track of all the animals in our sheltor, whether it's adopted or not, so we don't remove animal profile from our terminal. Users can also report stray pets they encounter.

## Target Users
- **Animal lovers** looking to adopt a pet
- **Shelter staff** managing pet listings and applications
- **Adopters** who want to share their pets' progress and stories

## Project Interest
This project is of particular interest to me because:
- I am passionate about animal welfare and want to help reduce the number of stray pets.
- I believe technology can play a crucial role in improving the adoption process and connecting pets with loving homes.

## Key Features
- **Pet Listings:** Users can view pets available for adoption.
- **Application Submission:** A streamlined process for users to apply for adoption.
- **Status Tracking:** Users can track the status of their application.
- **Adopter Updates:** Adopters can upload photos and updates on their pets’ well-being, fostering a sense of community.

## User Stories
- As a user, I want to view a list of pets available for adoption.
- As a user, I want to handin an application to adopt a pet.
- As a user, I want to view all my applications.
- As a user, I want to add pets to the shelter when I spotted a stray animal.
- As a user, I want to upload my adopt stories to keep a diary for my pet.
- As a user, I want to be able to save my applications to a file.
- As a user, I want to be able to save my adopt stories to file.
- As a user, when I select the quit option from the application menu, I want to be reminded to save my applications to
  file and have the option to do so or not.
- As a user, when I start the application, I want to be given the option too load my applications from file.

# Instructions for End User
- You can add a new pet to the shelter by go to the main menu and click on **Report a Stray Pet**. A form will appear prompting you to enter the pet's basic information. You can fill in the details and click **Submit** to add the pet to our shelter, then click **back to menu** button to go back to our main menu.
- You can submit an Adoption Application by click on **Submit an Adoption Application** from the main menu. Select a pet from the list of available pets, fill in the required information, and click **Submit**. Your application will be saved and can be viewed in the **View Your Applications** section.
- You can view all the pets in the **Available Pets List** by go to the main menu and click on **View Available Pets**, it shows the pets available in the shelter and it will update when pets are added, removed, or adopted.
- You can upload your adopt story by go to the main menu and click on **upload adopt story** button. A text area will appear with instructions. You can type your story there and click submit.
- You can locate my viual component at the start of this application, by the user set up, you can see a dog's paw image which is added as a visual component.
- You can save the state of the application by go to the **File** menu at the left-top corner and select **Save Data**. This will save your curent state to a JSON file 'myApplications.json' in the 'data' folder.
- You can reload the state previously saved by go to the **file** menu and select **Load Data**. This will retrieve the saved data from the JSON file and restore the application's state to the last saved version.

## Phase 4: Task 2
### Sample Event Log

Below is a sample log of events captured during application execution:

- Pet added to shelter: Otto
- Pet added to shelter: Bob
- Pet added to shelter: Boots
- Adoption application submitted for pet: Bob by user: Lars
- Adoption application status updated for pet: Bob by user Lars. New status: submitted
- Pet added to shelter: Moki
- Adoption story added by user: Lars